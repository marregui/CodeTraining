package io.marregui.trees;

import java.util.Deque;
import java.util.LinkedList;

public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;
    private final Deque<Node<T>> stack = new LinkedList<>();

    public void add(T x) {
        if (root == null) {
            root = new Node<>(x);
        } else {
            Node<T> ptr = root;
            while (ptr != null) {
                if (x.compareTo(ptr.x) < 1) {
                    if (ptr.left == null) {
                        ptr.left = new Node<>(x);
                        return;
                    }
                    ptr = ptr.left;
                } else {
                    if (ptr.right == null) {
                        ptr.right = new Node<>(x);
                        return;
                    }
                    ptr = ptr.right;
                }
            }
        }
    }

    public int height() {
        return height(root);
    }

    public int height2() {
        return height2(root);
    }

    private int height(Node ptr) {
        if (ptr == null) {
            return 0;
        }
        return 1 + Math.max(height(ptr.left), height(ptr.right));
    }

    private int height2(Node ptr) {
        if (ptr == null) {
            return 0;
        }

        int height = 0;
        int maxHeight = 0;
        stack.clear();
        Node<T> current = ptr;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.addFirst(current);
                current = current.left;
                height++;
            }
            current = stack.pop();
            current = current.right;
            if (height > maxHeight) {
                maxHeight = height;
            }
            height = 0;
        }
        return maxHeight;
    }


    private static class Node<T extends Comparable<T>> {
        private final T x;
        private Node<T> left;
        private Node<T> right;

        Node(T x) {
            this.x = x;
        }
    }
}
