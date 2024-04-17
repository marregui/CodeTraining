package io.marregui.datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.marregui.datastructures.BinaryTreeIntegerRepresentation.TreeNode;
import static io.marregui.datastructures.BinaryTreeIntegerRepresentation.flatten;


public class BinaryTreeIntegerRepresentationTest {


    @Test
    public void testSumNumbers() {
        BinaryTreeIntegerRepresentation r = new BinaryTreeIntegerRepresentation();
        r.setRoot(new TreeNode(
                4,
                new TreeNode(9, new TreeNode(5), new TreeNode(1)),
                new TreeNode(0)
        ));

        int[] tree = new int[]{4, 9, 0, 5, 1};
        BinaryTreeIntegerRepresentation r2 = new BinaryTreeIntegerRepresentation(tree);

        System.out.println(r.sumNumbers());
        Assertions.assertEquals(r.asString(), r2.asString());

        System.out.println(r.asString());
        flatten(r.getRoot());
        TreeNode ptr = r.getRoot();
        while (ptr != null) {
            System.out.print(" " + ptr.val);
            ptr = ptr.right;
        }
        System.out.println();
    }
}
