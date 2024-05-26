package io.marregui.util;


public final class ThrlStringBuilder {
    private static final ThreadLocal<StringBuilder> SB = ThreadLocal.withInitial(StringBuilder::new);

    public static StringBuilder get() {
        return SB.get();
    }

    private ThrlStringBuilder() {
        throw new IllegalStateException("utils class only");
    }
}
