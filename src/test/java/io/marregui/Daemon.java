package io.marregui;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.marregui.Log.log;

public class Daemon {
    public static Thread getDaemon(String name, @Nullable CountDownLatch start, @Nullable CountDownLatch next, Runnable task) {
        Thread thread = new Thread(() -> {
            if (start != null) {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log("we are done by interrupt%n");
                    return;
                }
            }

            Throwable error = null;
            try {
                task.run();
            } catch (Throwable t) {
                error = t;
                log("failed! %s%n", t.getMessage());
            } finally {
                if (next != null) {
                    next.countDown();
                    if (error == null) {
                        log("entering wait loop%n");
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
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100L);
                if (cnt == Long.MAX_VALUE) {
                    cnt = -1;
                }
                cnt++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("we are done%n");
                return;
            }
        }
    }
}
