package io.marregui;


public final class ThrLocal {
    private static final ThreadLocal<StringBuilder> SB = ThreadLocal.withInitial(StringBuilder::new);

    public static StringBuilder getThreadLocalStringBuilder() {
        return SB.get();
    }

    private ThrLocal() {
        throw new IllegalStateException("utils class only");
    }
}
