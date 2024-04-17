package io.marregui;

public class Log {

    public static void log(String fmt, Object... args) {
        StringBuilder sb = ThrLocal.getThreadLocalStringBuilder();
        sb.setLength(0);
        sb.append("T[").append(Thread.currentThread().getName()).append("] ").append(String.format(fmt, args));
        String msg = sb.toString();
        sb.setLength(0);
        synchronized (System.out) {
            System.out.print(msg);
        }
    }
}
