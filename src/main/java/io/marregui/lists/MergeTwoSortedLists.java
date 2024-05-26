package io.marregui.lists;


public class MergeTwoSortedLists {
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0, null);
        ListNode ptr = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                ptr.next = l1;
                l1 = l1.next;

            } else {
                ptr.next = l2;
                l2 = l2.next;
            }
            ptr = ptr.next;
        }
        ptr.next = l1 != null ? l1 : l2;
        return head.next;
    }
}
