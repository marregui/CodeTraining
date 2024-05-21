package io.marregui.datastructures.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LRUCacheTest {

    @Test
    public void test0() {
        LRUCache c = new LRUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        Assertions.assertEquals(1, c.get(1));
        c.put(3, 3);
        System.out.println(c);
        Assertions.assertEquals(-1, c.get(2));
        c.put(4, 4);
        System.out.println(c);
        Assertions.assertEquals(-1, c.get(1));
        Assertions.assertEquals(3, c.get(3));
        Assertions.assertEquals(4, c.get(4));
    }

//    @Test
//    public void testDDLifo() {
//        DLLifo<Integer> c = new DLLifo<>(5);
//        for (int i=0; i<c.getCapacity(); i++) {
//            c.pushRight(new DLLifo.Node<>(i, i));
//        }
//        System.out.println(c);
//
//        c.moveLast(c.getHead().getNext().getNext());
//
//        System.out.println(c);


//        DLLifo.Node<Integer> n;
//        while ((n = c.popLeft())!= null) {
//            System.out.println("Popped: "+ n);
//            System.out.println(c);
//        }
//        Assertions.assertEquals(0, c.getSize());
//    }
}
