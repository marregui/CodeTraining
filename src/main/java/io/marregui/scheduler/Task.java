package io.marregui.scheduler;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Task implements Comparable<Task> {
    private final int id;
    private final long when;
    private final Runnable code;
    private final AtomicReference<Throwable> error;
    private final AtomicBoolean completed;

    public Task(int id, long when, @NotNull Runnable code) {
        this.id = id;
        this.when = when;
        this.code = code;
        completed = new AtomicBoolean();
        error = new AtomicReference<>();
    }

    public Runnable getCode() {
        return code;
    }

    public void setCompleted() {
        completed.set(true);
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public void setError(Throwable error) {
        this.error.set(error);
    }

    public Throwable getError() {
        return error.get();
    }

    public long remaining() {
        return when - System.currentTimeMillis();
    }

    @Override
    public int hashCode() {
        return id * 31 + Long.hashCode(when);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Task that) {
            return this.id == that.id && this.when == that.when;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull Task that) {
        return (when < that.when) ? -1 : ((when == that.when) ? 0 : 1);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", when=" + when +
                ", remaining=" + remaining() +
                ", completed=" + isCompleted() +
                ", error=" + error.get() +
                '}';
    }
}
