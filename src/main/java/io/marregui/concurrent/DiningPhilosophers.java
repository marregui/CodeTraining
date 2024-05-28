package io.marregui.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    private final Lock leftLock = new ReentrantLock();
    private final Lock rightLock = new ReentrantLock();

    public void wantsToEat(
            int philosopher,
            Runnable pickLeftFork,
            Runnable pickRightFork,
            Runnable eat,
            Runnable putLeftFork,
            Runnable putRightFork
    ) throws InterruptedException {

        while (!leftLock.tryLock(100, TimeUnit.MILLISECONDS)) {
            // no-op
        }
        while (!rightLock.tryLock(100, TimeUnit.MILLISECONDS)) {
            // no-op
        }
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putRightFork.run();
        putLeftFork.run();
        rightLock.unlock();
        leftLock.unlock();
    }
}
