package io.marregui.datastructures;

import java.util.*;

public class Trie<T> {

    private static class Node<T> {
        private T value;
        private Object[] children = new Object[16];
        private boolean isTerminal;
        private int refs;

        private Node<T> addChild(char c) {
            Node<T> n = getChild(c);
            if (n == null) {
                n = new Node<>();
                children[c] = n;
            }
            n.refs++;
            return n;
        }

        private Node<T> getChild(char c) {
            if (c < 0) {
                throw new IllegalArgumentException("indexes must start from 0 and be positive");
            }
            if (c >= children.length) {
                Object[] tmp = new Object[c + 1];
                System.arraycopy(children, 0, tmp, 0, children.length);
                children = tmp;
                return null;
            }
            return (Node<T>) children[c];
        }
    }

    private Node<T> root = new Node<>();
    private final Deque<Node<T>> stack = new LinkedList<>();

    public void insert(String key, T value) {
        Node<T> ptr = root;
        for (int i = 0; i < key.length(); i++) {
            ptr = ptr.addChild(key.charAt(i));
        }
        ptr.value = value;
        ptr.isTerminal = true;
    }

    public T search(String key) {
        Node<T> ptr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            ptr = ptr.getChild(c);
            if (ptr == null) {
                return null;
            }
        }
        return ptr.isTerminal ? ptr.value : null;
    }

    public boolean startsWith(String key) {
        Node<T> ptr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            ptr = ptr.getChild(c);
            if (ptr == null) {
                return false;
            }
        }
        return true;
    }

    public boolean delete(String key) {
        Node<T> ptr = root;
        stack.clear();
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            ptr = ptr.getChild(c);
            if (ptr == null) {
                return false;
            }
            stack.addFirst(ptr);
        }
        ptr.isTerminal = false;
        while (!stack.isEmpty()) {
            Node<T> n = stack.pop();
            n.refs--;
            if (n.refs < 1) {
                n.value = null;
                n.children = null;
            }
        }
        return true;
    }
}
