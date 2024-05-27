package io.marregui.util;


public final class ThrlStringBuilder {
    private static final ThreadLocal<StringBuilder> SB = ThreadLocal.withInitial(StringBuilder::new);

    public static StringBuilder get() {
        StringBuilder sb =  SB.get();
        sb.setLength(0);
        return sb;
    }

    private ThrlStringBuilder() {
        throw new IllegalStateException("utils class only");
    }
}
