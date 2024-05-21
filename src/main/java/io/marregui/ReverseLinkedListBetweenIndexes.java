package io.marregui;

import java.util.Deque;
import java.util.LinkedList;

public class ReverseLinkedListBetweenIndexes {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    private static ListNode toList(int... values) {
        if (values.length == 0) {
            return null;
        }
        ListNode root = new ListNode(values[0]);
        ListNode ptr = root;
        for (int i = 1; i < values.length; i++) {
            ptr.next = new ListNode(values[i]);
            ptr = ptr.next;
        }
        return root;
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {

        ListNode prev = null; // will point to the node before left
        ListNode ptr = head;
        // find element before left
        int i = 0;
        int limit = left - 1;
        while (ptr != null && i < limit) {
            prev = ptr;
            ptr = ptr.next;
            i++;
        }
        if (ptr == null) {
            return head; // did not find left
        }

        Deque<ListNode> q = new LinkedList<>();
        // find right element
        limit = right - 1;
        while (ptr != null && i < limit) {
            q.addLast(ptr);
            ptr = ptr.next;
            i++;
        }
        if (ptr == null) {
            return head; // did not find right
        }
        q.addLast(ptr);
        ListNode tail = ptr.next; // tail is what follows the right element

        if (!q.isEmpty()) {
            if (prev == null) {
                prev = q.removeLast();
                head = prev;
            }
            while (!q.isEmpty()) {
                ListNode n = q.removeLast();
                n.next = null;
                prev.next = n;
                prev = prev.next;
            }
        }
        prev.next = tail;
        return head;
    }

    private static void printList(ListNode l1) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode n = l1; n != null; n = n.next) {
            sb.append(n.val + ",");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        sb.append(']');
        System.out.println(sb);
    }


    public static void main(String[] args) {
        ListNode l1 = reverseBetween(toList(1, 2, 3, 4, 5), 2, 4);
        printList(l1);
        ListNode l2 = reverseBetween(toList(3, 5), 1, 2);
        printList(l2);
        ListNode l3 = reverseBetween(toList(5), 1, 1);
        printList(l3);
    }
}
