package io.marregui.scheduler;

public class QueueFullException extends RuntimeException {
    public QueueFullException() {
        super("queue is full");
    }
}
