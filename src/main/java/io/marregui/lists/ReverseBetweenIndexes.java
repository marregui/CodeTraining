package io.marregui.lists;

import java.util.Deque;
import java.util.LinkedList;

public class ReverseBetweenIndexes {

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


    public static void main(String[] args) {
        ListNode l1 = reverseBetween(ListNode.toList(1, 2, 3, 4, 5), 2, 4);
        ListNode.printList(l1);
        ListNode l2 = reverseBetween(ListNode.toList(3, 5), 1, 2);
        ListNode.printList(l2);
        ListNode l3 = reverseBetween(ListNode.toList(5), 1, 1);
        ListNode.printList(l3);
    }
}
