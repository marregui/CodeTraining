package io.marregui.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DelayedSchedulerImpl implements DelayedScheduler, AutoCloseable {
    private static final int UNBOUND_QUEUE = -1;
    private static final QueueFullException QUEUE_FULL_EXCEPTION = new QueueFullException();
    private static final AtomicInteger IDS = new AtomicInteger();
    private static final int CHECK_WORKERS_MARK = 100;

    private final int maxTasks;
    private final AtomicLong taskCount;
    private final AtomicBoolean isAlive;
    private final AtomicBoolean isClosed;
    private final BlockingQueue<Task> tasks;
    private final Lock workersLock;
    private final Thread[] workers;


    public DelayedSchedulerImpl() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public DelayedSchedulerImpl(int maxThreads) {
        this(maxThreads, UNBOUND_QUEUE);
    }

    public DelayedSchedulerImpl(int maxThreads, int maxTasks) {
        this.maxTasks = maxTasks;
        taskCount = new AtomicLong();
        isAlive = new AtomicBoolean();
        isClosed = new AtomicBoolean();
        tasks = maxTasks != UNBOUND_QUEUE ? new PriorityBlockingQueue<>(maxTasks) : new PriorityBlockingQueue<>();
        workers = new Thread[maxThreads];
        workersLock = new ReentrantLock();
        checkWorkers(CHECK_WORKERS_MARK);
        isAlive.set(true);
    }

    @Override
    public boolean delayedRun(long timeToRunFromNowMillis, Runnable code) {
        if (isClosed.get()) {
            return false;
        }
        long when = System.currentTimeMillis() + timeToRunFromNowMillis;
        addTask(new Task(uniqueId(), when, code));
        return true;
    }

    private void addTask(Task task) {
        if (maxTasks != UNBOUND_QUEUE && taskCount.get() >= maxTasks) {
            throw QUEUE_FULL_EXCEPTION;
        }
        tasks.add(task);
        taskCount.incrementAndGet();
    }

    private int checkWorkers(int callCount) {
        int next = callCount + 1;
        if (next > CHECK_WORKERS_MARK) {
            workersLock.lock();
            try {
                for (int i = 0; i < workers.length; i++) {
                    Thread t = workers[i];
                    if (t == null || !t.isAlive()) {
                        int id = i;
                        t = new Thread(this::worker, "worker-" + id);
                        t.start();
                        workers[id] = t;
                    }
                }
            } finally {
                workersLock.unlock();
            }
            next = 0;
        }
        return next;
    }

    private void worker() {
        int checkWorkersCounter = 0;
        while (isAlive.get()) {
            try {
                if (tasks.isEmpty()) {
                    TimeUnit.MILLISECONDS.sleep(10L);
                    checkWorkersCounter = checkWorkers(checkWorkersCounter);
                    continue;
                }

                Task task = tasks.take();
                long remaining = task.remaining();
                if (remaining > 0) {
                    addTask(task);
                    checkWorkersCounter = checkWorkers(checkWorkersCounter);
                    TimeUnit.MILLISECONDS.sleep(remaining - 1);
                    continue;
                }

                try {
                    Runnable code = task.getCode();
                    if (code != null) {
                        code.run();
                    }
                    task.setCompleted();
                } catch (Throwable t) {
                    task.setError(t);
                    System.err.printf("failed task: %s -> %s%n", task, t.getMessage());
                } finally {
                    taskCount.decrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            int attempts = 20;
            while (!tasks.isEmpty() && attempts > -1) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100L);
                    attempts--;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (isAlive.compareAndSet(true, false)) {
                workersLock.lock();
                try {
                    for (int i = 0; i < workers.length; i++) {
                        Thread t = workers[i];
                        if (t != null && t.isAlive()) {
                            t.interrupt();
                        }
                        workers[i] = null;
                    }
                } finally {
                    workersLock.unlock();
                }
            }
        }
    }

    private static int uniqueId() {
        int id = IDS.getAndIncrement();
        if (id > Integer.MAX_VALUE - 1) {
            IDS.compareAndSet(id, 0);
        }
        return IDS.getAndIncrement();
    }

    public static void main(String[] args) throws Exception {
        try (DelayedScheduler scheduler = new DelayedSchedulerImpl(2)) {
            scheduler.delayedRun(1000, () -> {
                System.out.printf("Hello!%n");
            });
            scheduler.delayedRun(10, () -> {
                System.out.printf("Hola!%n");
            });

            scheduler.delayedRun(1001, () -> {
                System.out.printf("adios!%n");
            });
        }
    }
}
