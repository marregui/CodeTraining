package io.marregui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.marregui.Log.log;

public class ArrTest {

    private Arr arr;
    private Arr expected;

    @BeforeEach
    public void setUp() {
        arr = new Arr();
        expected = new Arr();
    }

    @AfterEach
    public void tearDown() {
        arr.close();
        arr = null;
        expected.close();
        expected = null;
    }

    @Test
    public void testInsert() {
        int capacity = arr.capacity();
        // can only insert within 0..capacity-1
        for (int i = 0; i < capacity; i++) {
            arr.insert(i, i);
            expected.insert(i, i);
        }
        try {
            arr.insert(13, capacity);
        } catch (IndexOutOfBoundsException e) {
            Assertions.assertEquals(arr.capacity(), capacity);
            Assertions.assertEquals(arr.size(), capacity);
            Assertions.assertEquals("Index out of range: " + capacity, e.getMessage());
        }
        Assertions.assertEquals(expected, arr);
        Assertions.assertEquals(expected.toString(), arr.toString());
    }

    @Test
    public void testAppend() {
        int capacity0 = arr.capacity();
        // can append beyond capacity, resulting in increase of capacity
        for (int i = 0; i < capacity0; i++) {
            arr.append(i);
            expected.append(i);
        }
        arr.append(13);
        expected.append(13);
        Assertions.assertEquals(arr.capacity(), capacity0 * 2);
        Assertions.assertEquals(expected, arr);
        Assertions.assertEquals(expected.toString(), arr.toString());

    }

    @Test
    public void testRemove() {
        int n = 111;
        for (int i = 0; i < n; i++) {
            arr.append(i);
            if (i % 2 == 0) {
                expected.append(i);
            }
        }

        // remove odd positions
        int limit = arr.size() - 1;
        if (limit % 2 == 0) {
            limit--;
        }
        for (int i = limit; i > -1; i -= 2) {
            arr.remove(i);
        }
        Assertions.assertEquals(expected, arr);
        Assertions.assertNotEquals(expected.capacity(), arr.capacity());
    }

    @Test
    public void testSort() {
        int n = 3466934;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                arr.append(i);
            }
            expected.append(i);
        }
        for (int i = 0; i < n; i++) {
            if (i % 2 != 0) {
                arr.append(i);
            }
        }
        arr.sort();
        Assertions.assertEquals(expected.size(), arr.size());
        Assertions.assertEquals(expected.getfirst(), arr.getfirst());
        Assertions.assertEquals(expected.getLast(), arr.getLast());
        Assertions.assertEquals(expected, arr);
    }

    @Test
    public void testConcurrent() throws Exception {

        int n = 15;
        Arr arr = new Arr();

        CountDownLatch sortStart = new CountDownLatch(1);
        CountDownLatch removeStart = new CountDownLatch(1);

        Thread insert = Daemon.getDaemon("insert", null, sortStart, () -> {
            // populate the array
            Random rnd = new Random(2024);
            for (int i = 0; i < n; i++) {
                arr.append(rnd.nextInt(20));
            }
            for (int i = 0, limit = n / 4; i < limit; i++) {
                arr.append(rnd.nextInt(6));
            }
            int middle = arr.size() / 2;
            arr.insert(2024, middle);
        });
        insert.start();

        Thread sort = Daemon.getDaemon("sort", sortStart, removeStart, arr::sort);
        sort.start();

        Thread remove = Daemon.getDaemon("remove", removeStart, null, () -> {
            // remove odd numbers
            for (int size = arr.size(), i = size - (size % 2 == 0 ? 1 : 2); i > -1; i -= 2) {
                arr.remove(i);
            }
        });
        remove.start();
        remove.join();

        TimeUnit.SECONDS.sleep(1L);

        insert.interrupt();
        sort.interrupt();

        log("ending%n", arr);
        log("the end: %s%n", arr);
    }
}
