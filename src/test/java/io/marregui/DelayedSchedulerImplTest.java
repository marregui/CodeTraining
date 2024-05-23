package io.marregui;

import io.marregui.scheduler.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DelayedSchedulerImplTest {

    @Test
    public void testTask() {
        Assertions.assertEquals(new Task(1, 100, null), new Task(1, 100, null));
        Assertions.assertEquals(0, new Task(1, 100, null).compareTo(new Task(1, 100, null)));
    }
}
