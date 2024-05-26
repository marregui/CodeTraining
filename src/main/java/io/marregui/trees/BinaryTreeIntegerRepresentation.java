package io.marregui.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BinaryTreeIntegerRepresentation {

    private TreeNode root;
    private int height;

    private final Deque<TreeNode> stack = new LinkedList<>();

    public BinaryTreeIntegerRepresentation() {

    }

    public BinaryTreeIntegerRepresentation(int[] tree) {
        for (int e : tree) {
            insert(e);
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void insert(int e) {
        if (root != null) {
            TreeNode ptr = root;
            while (ptr != null) {
                if (ptr.left == null) {
                    ptr.left = new TreeNode(e);
                    height++;
                    break;
                } else if (ptr.right == null) {
                    ptr.right = new TreeNode(e);
                    break;
                } else {
                    ptr = ptr.left;
                }
            }
        } else {
            root = new TreeNode(e);
            height++;
        }
    }

    public int height() {
        if (height == 0 && root != null) {
            height = height(root);
        }
        return height;
    }

    private int height(TreeNode ptr) {
        stack.clear();
        TreeNode current = ptr;
        int max = 0;
        int h = 0;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.addFirst(current);
                current = current.left;
                h++;
            }
            current = stack.pop();
            current = current.right;
            if (h > max) {
                max = h;
            }
            h = 0;
        }
        return max;
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        stack.clear();
        stack.addFirst(root);
        int len = 0;
        while (!stack.isEmpty()) {
            TreeNode ptr = stack.pop();
            sb.append(ptr.val).append(",");
            len++;
            if (ptr.left != null) {
                stack.addFirst(ptr.left);
            }
            if (ptr.right != null) {
                stack.addFirst(ptr.right);
            }
        }
        if (len > 0) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("] height=").append(height()).append(", len=").append(len);
        return sb.toString();
    }

    public static void flatten(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return;
        }

        if (root.left != null) {
            flatten(root.left);

            TreeNode tmp = root.right;
            root.right = root.left;
            root.left = null;
            TreeNode curr = root.right;
            while (curr.right != null) {
                curr = curr.right;
            }
            curr.right = tmp;
        }
        flatten(root.right);
    }

    public int sumNumbers() {
        List<Integer> nums = new ArrayList<>();
        sumNumbers(root, nums, new int[32], 0);
        return nums.stream().reduce(Integer::sum).orElse(0);
    }

    public void sumNumbers(TreeNode ptr, List<Integer> nums, int[] digits, int didx) {
        if (ptr == null) {
            return;
        }

        digits[didx] = ptr.val;
        if (ptr.left == null && ptr.right == null) {
            int factor = 1;
            int num = 0;
            for (int j = didx; j > -1; j--) {
                num += digits[j] * factor;
                factor *= 10;
            }
            nums.add(num);
        }

        sumNumbers(ptr.left, nums, digits, didx + 1);
        sumNumbers(ptr.right, nums, digits, didx + 1);
    }
}
