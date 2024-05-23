package io.marregui;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BinarySearchTreeTest {

    @Test
    public void testHeight() {
        BinarySearchTree tree = new BinarySearchTree();
        for (int x : Arrays.asList(3, 2, 1, 0, 5, 4, 13)) {
            tree.add(x);
        }
        int h = tree.height();
        System.out.printf("Height: %d%n", h);

        h = tree.height2();
        System.out.printf("Height: %d%n", h);
    }
}
