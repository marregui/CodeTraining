package io.marregui.datastructures.cache;

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
        keys = new DLLifo<>();
        values = new DLLifo[capacity];
    }

    public void put(int key, int value) {
        int bucket = key % capacity;
        Node<Node<Integer>> keyNode = keys.find(key);
        if (keyNode != null) { // key exists, update both value and access order
            Node<Integer> valueNode = keyNode.val;
            valueNode.val = value;
            keys.moveLast(keyNode);
            values[bucket].moveLast(valueNode);
        } else {
            if (size == capacity) {
                keys.popLeft();
                values[bucket].popLeft();
                size--;
            }
            Node<Integer> valueNode = new Node<>(key, value);
            keys.pushRight(new Node<>(key, valueNode));
            if (values[bucket] == null) {
                values[bucket] = new DLLifo<>();
            }
            values[bucket].pushRight(valueNode);
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
        values[bucket].moveLast(val);
        keys.moveLast(keys.find(key));
        return val.val;
    }

    @Override
    public String toString() {
        return keys.toString();
    }


    private static class Node<T> {
        final int key;
        T val;
        Node<T> prev;
        Node<T> next;

        public Node(int key, T val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return new StringBuilder("[k:").append(key).append(", v:").append(val).append(']').toString();
        }
    }

    private static class DLLifo<T> {
        Node<T> head;
        Node<T> tail;

        Node<T> popLeft() {
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
            return node;
        }

        void pushRight(Node<T> node) {
            if (head == null) {
                head = node;
                tail = head;
            } else {
                node.prev = tail;
                tail.next = node;
                tail = node;
                tail.next = null;
            }
        }

        void moveLast(Node<T> node) {
            if (node.prev == null && node.next == null) {
                return;
            }
            if (node.prev == null) {
                head = node.next;
                head.prev = null;
            } else {
                node.prev.next = node.next;
            }
            node.prev = tail;
            node.next = null;
            tail.next = node;
            tail = node;
        }

        Node<T> find(int key) {
            Node<T> ptr = head;
            while (ptr != null && ptr.key != key) {
                ptr = ptr.next;
            }
            return ptr;
        }

        @Override
        public String toString() {
            return toString(head);
        }

        String toString(Node<T> start) {
            Node<T> ptr = start != null ? start : head;
            StringBuilder sb = new StringBuilder();
            int s = 0;
            while (ptr != null) {
                sb.append(ptr).append(" <-> ");
                ptr = ptr.next;
                s++;
            }
            int len = sb.length();
            if (len > 0) {
                sb.setLength(len - 5);
            }
            sb.append(" size: ").append(s);
            return sb.toString();
        }
    }
}
