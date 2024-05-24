package io.marregui.microservice;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class Logger implements ILogger {
    private static final ConcurrentMap<Class<?>, ILogger> LOGGERS = new ConcurrentHashMap<>();
    private static final Level DEFAULT_LEVEL = Level.INFO;

    private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    public static final ILogger loggerFor(Class<?> clazz) {
        ILogger logger = LOGGERS.get(clazz);
        if (logger == null) {
            ILogger other = LOGGERS.putIfAbsent(clazz, logger = new Logger());
            if (other != null) {
                logger = other;
            }
        }
        return logger;
    }

    private final AtomicReference<Level> level;

    private Logger() {
        level = new AtomicReference<>(DEFAULT_LEVEL);
    }

    @Override
    public void setLevel(Level newLevel) {
        level.set(newLevel);
    }

    @Override
    public Level getLevel() {
        return level.get();
    }

    @Override
    public void log(Level level, String format, Object... args) {
        if (getLevel().order() >= level.order()) {
            EXECUTOR.submit(() -> {
                String location = null;
                StringBuilder method = THR_SB.get();
                StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                for (int i = 0; i < stack.length; i++) {
                    StackTraceElement e = stack[i];
                    String className = e.getClassName();
                    int j = className.length() - 1;
                    while (j > 0 && className.charAt(j) != '.') {
                        --j;
                    }
                    j++;

                    method.setLength(0);
                    method.append(className.substring(j)).append(".").append(e.getMethodName());
                    location = method.append("(l:").append(e.getLineNumber()).append(")").toString();
                    break;
                }
                System.out.print(str("%s <%d> Thr(%s) %s -> %s\n",
                        level,
                        TimeUnit.NANOSECONDS.toMicros(System.nanoTime()),
                        Thread.currentThread().getName(),
                        location,
                        str(format, args)));
            });
        }
    }

    @Override
    public final void debug(String format, Object... args) {
        log(Level.DEBUG, format, args);
    }

    @Override
    public final void info(String format, Object... args) {
        log(Level.INFO, format, args);
    }

    @Override
    public final void warn(String format, Object... args) {
        log(Level.WARN, format, args);
    }

    @Override
    public final void error(String format, Object... args) {
        log(Level.ERROR, format, args);
    }

    public static final String str(String format, Object... args) {
        if (format == null) {
            return null;
        }

        int argsIdx = 0;
        StringBuilder sb = THR_SB.get();
        char[] formatBuff = format.toCharArray();
        char c;
        for (int i = 0, limit = formatBuff.length; i < limit; i++) {
            c = formatBuff[i];
            if (c != '%') {
                sb.append(c);
                continue;
            }

            // process %format
            i++;
            if (i < limit) {
                switch (formatBuff[i]) {
                    case 'n':
                        sb.append('\n');
                        break;
                    case '%':
                        sb.append('%');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 'f':
                        sb.append('\f');
                        break;
                    default:
                        if (argsIdx < args.length) {
                            sb.append(args[argsIdx++]);
                        } else {
                            throw new IllegalArgumentException("missing arguments");
                        }
                }
            } else {
                throw new IllegalArgumentException("bad format");
            }
        }
        return sb.toString();
    }

    public static final ThreadLocal<StringBuilder> THR_SB = new ThreadLocal<>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(255);
        }

        @Override
        public StringBuilder get() {
            StringBuilder sb = super.get();
            sb.setLength(0);
            return sb;
        }
    };
}