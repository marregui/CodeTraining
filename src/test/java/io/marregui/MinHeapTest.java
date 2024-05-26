package io.marregui;


import io.marregui.datastructures.MinHeap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class MinHeapTest {

    private MinHeap h;

    @BeforeEach
    public void setUp() {
        h = new MinHeap();
    }

    @Test
    public void testAddRemove() {
        for (int i = 10000; i > -1; i--) {
            h.add(i);
        }
        for (int expected = 0, limit = h.size(); expected < limit; expected++) {
            int top = h.remove();
            Assertions.assertEquals(expected, top);
        }
    }

    @Test
    public void testAddRemoveRandom() {
        Random rand = new Random();
        for (int i = 10000; i > -1; i--) {
            h.add(rand.nextInt());
        }
        int min = Integer.MIN_VALUE;
        for (int i = 0, limit = h.size(); i < limit; i++) {
            int top = h.remove();
            Assertions.assertTrue(top >= min);
            min = top;
        }
    }

    @Test
    public void testAddRemoveValue() {
        h.add(1);
        h.add(4);
        h.add(9);
        h.add(17);
        h.add(-1);
        h.add(3);

        h.remove(4);

        Assertions.assertEquals(-1, h.remove());
        Assertions.assertEquals(1, h.remove());
        Assertions.assertEquals(3, h.remove());
        Assertions.assertEquals(9, h.remove());

        h.remove(17);

        Assertions.assertEquals(h.size(), 0);

    }
}
