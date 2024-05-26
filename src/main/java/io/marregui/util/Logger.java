package io.marregui.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class Logger implements ILogger {

    private static final ConcurrentMap<Class<?>, ILogger> LOGGERS = new ConcurrentHashMap<>();
    private static final Level DEFAULT_LEVEL = Level.INFO;

    public static final ILogger loggerFor(Class<?> clazz) {
        return LOGGERS.computeIfAbsent(clazz, Logger::new);
    }

    private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final AtomicReference<Level> level;


    private Logger(Class<?> ignore) {
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
            EXECUTOR.submit(() -> processLog(level, format, args));
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

    private static void processLog(Level level, String format, Object... args) {
        StringBuilder sb = ThrlStringBuilder.get();

        sb.append(level)
                .append(" |").append(TimeUnit.NANOSECONDS.toMicros(System.nanoTime())).append("| ")
                .append(Thread.currentThread().getName()).append(':');

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack.length > 1) {
            StackTraceElement e = stack[1];
            String className = e.getClassName();
            int j = className.length() - 1;
            while (j > 0 && className.charAt(j) != '.') {
                --j;
            }
            j++;
            sb.append(className.subSequence(j, className.length())).append('.').append(e.getMethodName());
            sb.append('.').append(e.getLineNumber()).append(") ");
        }

        // append the formatted message
        int argsIdx = 0;
        char c;
        for (int i = 0, limit = format.length(); i < limit; i++) {
            c = format.charAt(i);
            if (c != '%') {
                sb.append(c);
                continue;
            }

            // process %format
            i++;
            if (i < limit) {
                switch (format.charAt(i)) {
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
        synchronized (System.out) {
            System.out.println(sb);
        }
    }
}