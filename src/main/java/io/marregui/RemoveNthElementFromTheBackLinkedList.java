package io.marregui;

public class RemoveNthElementFromTheBackLinkedList {
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

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        int len = 0;
        for (ListNode ptr = head; ptr != null; ptr = ptr.next) {
            len++;
        }
        if (n > len) {
            return null;
        }
        int steps = len - n - 1;
        if (steps < 0) {
            return head.next;
        }
        ListNode ptr = head;
        while (ptr != null && steps > 0) {
            ptr = ptr.next;
            steps--;
        }
        ListNode tail = ptr.next;
        if (tail != null) {
            tail = tail.next;
        }
        ptr.next = tail;
        return head;
    }

    public static void main(String[] args) {
        printList(removeNthFromEnd(toList(1, 2), 2));
        printList(removeNthFromEnd(toList(1), 1));
        printList(removeNthFromEnd(toList(1, 2), 1));
        printList(removeNthFromEnd(toList(1, 2, 3, 4, 5), 2));
    }
}
