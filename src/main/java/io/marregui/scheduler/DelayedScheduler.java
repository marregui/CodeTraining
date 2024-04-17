package io.marregui.scheduler;

import java.io.Closeable;

public interface DelayedScheduler extends Closeable {
    boolean delayedRun(long timeToRunFromNowMillis, Runnable task);
}
