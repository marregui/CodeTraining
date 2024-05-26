package io.marregui.util;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class LingeringDaemon {

    private static final ILogger log = Logger.loggerFor(LingeringDaemon.class);

    // must be interrupted for it to terminate
    public static Thread create(String name, @Nullable CountDownLatch start, @Nullable CountDownLatch next, Runnable task) {
        Thread thread = new Thread(() -> {
            if (start != null) {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.info("we are done by interrupt%n");
                    return;
                }
            }

            Throwable error = null;
            try {
                task.run();
            } catch (Throwable t) {
                error = t;
                log.info("failed! %s%n", t.getMessage());
            } finally {
                if (next != null) {
                    next.countDown();
                    if (error == null) {
                        log.info("entering wait loop%n");
                        waitLoop();
                    }
                }
            }
        }, name);
        thread.setDaemon(true);
        return thread;
    }

    private static void waitLoop() {
        long cnt = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100L);
                if (cnt == Long.MAX_VALUE) {
                    cnt = -1;
                }
                cnt++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                log.info("we are done%n");
            }
        }
    }
}
