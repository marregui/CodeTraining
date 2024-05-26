package io.marregui.lists;

public class RemoveNthElementFromTheBack {
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
        ListNode.printList(removeNthFromEnd(ListNode.toList(1, 2), 2));
        ListNode.printList(removeNthFromEnd(ListNode.toList(1), 1));
        ListNode.printList(removeNthFromEnd(ListNode.toList(1, 2), 1));
        ListNode.printList(removeNthFromEnd(ListNode.toList(1, 2, 3, 4, 5), 2));
    }
}
