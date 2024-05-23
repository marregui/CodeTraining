package io.marregui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.marregui.LRUCache.DLLifo;
import static io.marregui.LRUCache.Node;

public class LRUCacheTest {

    @Test
    public void test0() {
        LRUCache c = new LRUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        Assertions.assertEquals("[1,[1,1]] [2,[2,2]]", c.toString());
        Assertions.assertEquals(1, c.get(1));
        Assertions.assertEquals("[2,[2,2]] [1,[1,1]]", c.toString());
        c.put(3, 3);
        Assertions.assertEquals("[1,[1,1]] [3,[3,3]]", c.toString());
        Assertions.assertEquals(-1, c.get(2));
        Assertions.assertEquals("[1,[1,1]] [3,[3,3]]", c.toString());
        c.put(4, 4);
        Assertions.assertEquals("[3,[3,3]] [4,[4,4]]", c.toString());
        Assertions.assertEquals(-1, c.get(1));
        Assertions.assertEquals(3, c.get(3));
        Assertions.assertEquals("[4,[4,4]] [3,[3,3]]", c.toString());
        Assertions.assertEquals(4, c.get(4));
        Assertions.assertEquals("[3,[3,3]] [4,[4,4]]", c.toString());
    }

    @Test
    public void test1() {
        // ["LRUCache","put","put","get","get","put","get","get","get"]
        // [[2],[2,1],[3,2],[3],[2],[4,3],[2],[3],[4]]

        LRUCache ddl = new LRUCache(2);
        ddl.put(2, 1);
        ddl.put(3, 2);
        Assertions.assertEquals("[2,[2,1]] [3,[3,2]]", ddl.toString());
        ddl.get(3);
        ddl.get(2);
        Assertions.assertEquals("[3,[3,2]] [2,[2,1]]", ddl.toString());
        ddl.put(4, 3);
        Assertions.assertEquals("[2,[2,1]] [4,[4,3]]", ddl.toString());
        ddl.get(2);
        ddl.get(3);
        ddl.get(4);
        Assertions.assertEquals("[2,[2,1]] [4,[4,3]]", ddl.toString());
    }

    @Test
    public void test2() {
        LRUCache ddl = new LRUCache(10);
        ddl.put(7, 28);
        ddl.put(7, 1);
        ddl.put(8, 15);
        Assertions.assertEquals("[7,[7,1]] [8,[8,15]]", ddl.toString());
        ddl.get(6);
        ddl.put(10, 27);
        Assertions.assertEquals("[7,[7,1]] [8,[8,15]] [10,[10,27]]", ddl.toString());
        ddl.put(8, 10);
        ddl.get(8);
        Assertions.assertEquals("[7,[7,1]] [10,[10,27]] [8,[8,10]]", ddl.toString());
        ddl.put(6, 29);
        ddl.put(1, 9);
        ddl.get(6);
        Assertions.assertEquals("[7,[7,1]] [10,[10,27]] [8,[8,10]] [1,[1,9]] [6,[6,29]]", ddl.toString());
        ddl.put(10, 7);
        Assertions.assertEquals("[7,[7,1]] [8,[8,10]] [1,[1,9]] [6,[6,29]] [10,[10,7]]", ddl.toString());
        ddl.get(1);
        ddl.get(2);
        ddl.get(13);
        Assertions.assertEquals("[7,[7,1]] [8,[8,10]] [6,[6,29]] [10,[10,7]] [1,[1,9]]", ddl.toString());
        ddl.put(8, 30);
        ddl.put(1, 5);
        ddl.get(1);
        Assertions.assertEquals("[7,[7,1]] [6,[6,29]] [10,[10,7]] [8,[8,30]] [1,[1,5]]", ddl.toString());
        ddl.put(13, 2);
        Assertions.assertEquals("[7,[7,1]] [6,[6,29]] [10,[10,7]] [8,[8,30]] [1,[1,5]] [13,[13,2]]", ddl.toString());
        Assertions.assertEquals(-1, ddl.get(12));
    }

    @Test
    public void test3() {
        //[[3],[1,1],[2,2],[3,3],[4,4],[4],[3],[2],[1],[5,5],[1],[2],[3],[4],[5]]
        LRUCache ddl = new LRUCache(3);
        ddl.put(1, 1);
        ddl.put(2, 2);
        ddl.put(3, 3);
        ddl.put(4, 4);
        Assertions.assertEquals("[2,[2,2]] [3,[3,3]] [4,[4,4]]", ddl.toString());
        Assertions.assertEquals(-1, ddl.get(1));
        ddl.get(4);
        ddl.get(3);
        ddl.get(2);
        ddl.get(1);
        Assertions.assertEquals("[4,[4,4]] [3,[3,3]] [2,[2,2]]", ddl.toString());
        ddl.put(5, 5);
        Assertions.assertEquals("[3,[3,3]] [2,[2,2]] [5,[5,5]]", ddl.toString());
        Assertions.assertEquals(-1, ddl.get(1));
        Assertions.assertEquals(2, ddl.get(2)); // here
        Assertions.assertEquals("[3,[3,3]] [5,[5,5]] [2,[2,2]]", ddl.toString());
        ddl.get(3);
        Assertions.assertEquals("[5,[5,5]] [2,[2,2]] [3,[3,3]]", ddl.toString());
        ddl.get(4);
        ddl.get(5);
        Assertions.assertEquals("[2,[2,2]] [3,[3,3]] [5,[5,5]]", ddl.toString());
    }

    @Test
    public void testDDLifo() {
        DLLifo<Integer> ddl = new DLLifo<>();
        Assertions.assertNull(ddl.pop());
        Node<Integer> node0 = new Node<>(1, 2);
        Node<Integer> node1 = new Node<>(2, 5);
        Node<Integer> node2 = new Node<>(3, 10);
        ddl.append(node0);
        ddl.append(node1);
        ddl.append(node2);
        Assertions.assertEquals("[1,2] [2,5] [3,10]", ddl.toString());
        ddl.moveToTail(node0);
        Assertions.assertEquals("[2,5] [3,10] [1,2]", ddl.toString());
        ddl.moveToTail(node1);
        Assertions.assertEquals("[3,10] [1,2] [2,5]", ddl.toString());
        Node<Integer> node = ddl.pop();
        Assertions.assertEquals("[1,2] [2,5]", ddl.toString());
        ddl.moveToTail(node); // not in list
        Assertions.assertEquals("[1,2] [2,5]", ddl.toString());
    }

    @Test
    public void testMoveToTail() {
        DLLifo<Integer> ddl = new DLLifo<>();
        Assertions.assertNull(ddl.pop());
        Node<Integer> node0 = new Node<>(1, 2);
        Node<Integer> node1 = new Node<>(2, 5);
        Node<Integer> node2 = new Node<>(3, 10);
        Node<Integer> node3 = new Node<>(4, 11);
        ddl.append(node0);
        ddl.append(node1);
        ddl.append(node2);
        ddl.append(node3);
        Assertions.assertEquals("[1,2] [2,5] [3,10] [4,11]", ddl.toString());
        ddl.moveToTail(node1);
        Assertions.assertEquals("[1,2] [3,10] [4,11] [2,5]", ddl.toString());
        ddl.moveToTail(node2);
        Assertions.assertEquals("[1,2] [4,11] [2,5] [3,10]", ddl.toString());
        ddl.moveToTail(node0);
        Assertions.assertEquals("[4,11] [2,5] [3,10] [1,2]", ddl.toString());
    }
}
