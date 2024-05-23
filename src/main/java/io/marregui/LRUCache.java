package io.marregui;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    // Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
    //
    // Implement the LRUCache class:
    //
    // "LRUCache(int capacity)": Initialize the LRU cache with positive size capacity.
    // "int get(int key)" Return the value of the key if the key exists, otherwise return -1.
    // "void put(int key, int value)" Update the value of the key if the key exists.
    //      Otherwise, add the key-value pair to the cache. If the number of keys exceeds
    //      the capacity from this operation, evict the least recently used key.
    // The functions get and put must each run in O(1) average time complexity.

    private final int capacity;
    private final DLLifo<Integer>[] values;
    private final DLLifo<Node<Integer>> keys;
    private int size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        keys = new DLLifo<>(capacity);
        values = new DLLifo[capacity];
    }

    public void put(int key, int value) {
        int bucket = key % capacity;
        Node<Node<Integer>> keyNode = keys.find(key);
        if (keyNode != null) { // key exists, update both value and access order
            Node<Integer> valueNode = keyNode.val;
            valueNode.val = value;
            keys.moveToTail(keyNode);
            values[bucket].moveToTail(valueNode);
        } else {
            if (size == capacity) {
                Node<Node<Integer>> evicted = keys.pop();
                values[evicted.key % capacity].pop();
                size--;
            }
            Node<Integer> valueNode = new Node<>(key, value);
            keys.append(new Node<>(key, valueNode));
            if (values[bucket] == null) {
                values[bucket] = new DLLifo<>(capacity);
            }
            values[bucket].append(valueNode);
            size++;
        }
    }

    public int get(int key) {
        int bucket = key % capacity;
        DLLifo<Integer> line = values[bucket];
        if (line == null) {
            return -1;
        }
        Node<Integer> val = line.find(key);
        if (val == null) {
            return -1;
        }
        Node<Node<Integer>> k = keys.find(key);
        keys.moveToTail(k);
        values[bucket].moveToTail(val);
        return val.val;
    }

    @Override
    public String toString() {
        return keys.toString();
    }

    static class Node<T> {
        final int key;
        T val;
        Node<T> prev;
        Node<T> next;

        Node(int key, T val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return new StringBuilder("[").append(key).append(',').append(val).append(']').toString();
        }
    }

    static class DLLifo<T> {
        Node<T> head;
        Node<T> tail;
        final Map<Integer, Node<T>> map;

        DLLifo(int capacity) {
            map = new HashMap<>(capacity);
        }

        Node<T> pop() {
            if (head == null) {
                return null;
            }
            Node<T> node = head;
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            node.prev = null;
            node.next = null;
            map.remove(node.key);
            return node;
        }

        void append(Node<T> node) {
            if (head == null) {
                node.prev = null;
                head = node;
                tail = head;
            } else {
                node.prev = tail;
                tail.next = node;
                tail = node;
            }
            node.next = null;
            map.put(node.key, node);
        }

        void moveToTail(Node<T> node) {
            if (node == null || node == tail || (node.prev == null && node.next == null)) {
                return;
            }
            if (node == head) {
                head = head.next;
                head.prev = null;
            } else {
                Node<T> p0 = node.prev;
                Node<T> p1 = node.next;
                p0.next = p1;
                p1.prev = p0;
            }
            node.prev = tail;
            node.next = null;
            tail.next = node;
            tail = tail.next;
        }

        Node<T> find(int key) {
            return map.get(key);
        }

        @Override
        public String toString() {
            return toString(head);
        }

        String toString(Node<T> start) {
            Node<T> ptr = start != null ? start : head;
            StringBuilder sb = new StringBuilder();
            while (ptr != null) {
                sb.append(ptr).append(' ');
                ptr = ptr.next;
            }
            int len = sb.length();
            if (len > 0) {
                sb.setLength(len - 1);
            }
            return sb.toString();
        }
    }
}
