package io.marregui.lists;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode toList(int... values) {
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

    public static void printList(ListNode l1) {
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

}
