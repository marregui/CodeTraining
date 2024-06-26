package io.marregui.lists;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static io.marregui.lists.ListNode.printList;
import static io.marregui.lists.ListNode.toList;

public class PartitionList {
    // Given the head of a linked list and a value x, partition it such that all nodes
    // less than x come before nodes greater than or equal to x.
    //
    // You should preserve the original relative order of the nodes in each of the
    // two partitions.

    public static ListNode partition(ListNode head, int x) {
        ListNode left = null;
        ListNode leftTail = null;
        ListNode right = null;
        ListNode rightTail = null;

        ListNode ptr = head;
        while (ptr != null) { // traverse list
            if (ptr.val < x) { // elements smaller go to left
                if (left == null) {
                    left = ptr;
                    leftTail = ptr;
                } else {
                    leftTail.next = ptr;
                    leftTail = ptr;
                }
            } else { // elements greater or equal go to right
                if (right == null) {
                    right = ptr;
                    rightTail = ptr;
                } else {
                    rightTail.next = ptr;
                    rightTail = ptr;
                }
            }
            ptr = ptr.next;
        }
        if (right != null) {
            rightTail.next = null;
        }
        if (left == null) {
            return right;
        }
        leftTail.next = right;
        return left;
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        if (k == 0) {
            return head;
        }

        Map<ListNode, ListNode> prevNode = new HashMap<>();
        ListNode tail = head;
        int size = 1;
        while (tail.next != null) {
            prevNode.put(tail.next, tail);
            tail = tail.next;
            size++;
        }
        if (head == tail) {
            return head;
        }
        k = k < size ? k : size % k;
        for (int i = 0; i < k; i++) {
            ListNode ptr = prevNode.remove(tail);
            prevNode.put(head, tail);
            tail.next = head;
            head = tail;
            tail = ptr;
            tail.next = null;
        }
        return head;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        if (k < 2) {
            return head;
        }

        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode p = null;
        ListNode pt = null;
        ListNode ptr = head;
        ListNode remain = null;
        while (ptr != null) {
            stack.addLast(ptr);
            if (stack.size() == k) {
                ListNode next = ptr.next;
                while (!stack.isEmpty()) {
                    ListNode node = stack.removeLast();
                    node.next = null;
                    if (p == null) {
                        p = node;
                        pt = p;
                    } else {
                        pt.next = node;
                        pt = pt.next;
                    }
                }
                ptr = next;
                remain = ptr;
            } else {
                ptr = ptr.next;
            }
        }
        if (!stack.isEmpty()) {
            while (remain != null) {
                if (p == null) {
                    p = remain;
                    pt = remain;
                } else {
                    pt.next = remain;
                    pt = remain;
                }
                remain = remain.next;
            }
        }
        if (p != null) {
            pt.next = null;
        }
        return p;
    }

    public static void main(String[] args) {
        ListNode head = partition(toList(1, 4, 3, 2, 5, 2), 3);
        printList(head);

        head = rotateRight(toList(1, 2, 3, 4, 5), 2);
        printList(head);

        head = rotateRight(toList(0, 1, 2), 4);
        printList(head);

        head = reverseKGroup(toList(1, 2, 3, 4, 5), 2);
        printList(head);
    }
}
