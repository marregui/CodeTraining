package io.marregui.lists;

import static io.marregui.lists.ListNode.printList;
import static io.marregui.lists.ListNode.toList;

public class DeleteDuplicates {

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode prev0 = null;
        ListNode prev1 = null;
        ListNode p = head;
        while (p != null) {
            if (prev1 != null) {
                ListNode last = prev1;
                ListNode it = p;
                while (it != null && last.val == it.val) {
                    last = it;
                    it = it.next;
                }
                if (it != p) {
                    if (prev0 == null) {
                        head = it;
                        prev1 = null;
                    } else {
                        prev0.next = it;
                        prev1 = prev0;
                    }
                    p = it;
                }
            }

            prev0 = prev1;
            prev1 = p;
            if (p == null) {
                break;
            }
            p = p.next;
        }
        return head;
    }

    public static void main(String[] args) {
        printList(deleteDuplicates(toList(1, 2, 2)));
        printList(deleteDuplicates(toList()));
        printList(deleteDuplicates(toList(1,1,1,3,5)));
        printList(deleteDuplicates(toList(1)));
        printList(deleteDuplicates(toList(1, 1)));
        printList(deleteDuplicates(toList(1, 2, 3, 3, 4, 4, 5)));
        printList(deleteDuplicates(toList(1, 2, 3, 4, 5)));
    }
}
