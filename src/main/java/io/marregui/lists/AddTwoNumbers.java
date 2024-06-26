package io.marregui.lists;

public class AddTwoNumbers {

    // You are given two non-empty linked lists representing two non-negative integers.
    // The digits are stored in reverse order, and each of their nodes contains a single
    // digit. Add the two numbers and return the sum as a linked list.
    //
    // You may assume the two numbers do not contain any leading zero, except the number
    // 0 itself.

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode root = new ListNode();
        ListNode ptr = root;
        int carry = 0;
        ListNode tracking = null;
        while (p1 != null || p2 != null) {
            int tmp = 0;
            if (p1 == null) {
                if (p2 != null) {
                    tmp = p2.val + carry;
                    p2 = p2.next;
                }
            } else if (p2 == null) {
                tmp = p1.val + carry;
                p1 = p1.next;
            } else {
                tmp = p1.val + p2.val + carry;
                p1 = p1.next;
                p2 = p2.next;
            }

            ptr.val = tmp % 10;
            carry = tmp / 10;
            ptr.next = new ListNode();
            tracking = ptr;
            ptr = ptr.next;
        }
        if (carry > 0) {
            ptr.val = carry;
        } else if (tracking != null) {
            tracking.next = null;
        }
        return root;
    }

    public static void main(String[] args) {
        ListNode r = addTwoNumbers(
                ListNode.toList(1, 9, 9, 9, 9, 9, 9, 9, 9, 9),
                ListNode.toList(9)
        );
    }
}
