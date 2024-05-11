package io.marregui;

import java.util.Arrays;

public class LongestConsecutiveSequence {

    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }

        nums = Arrays.stream(nums).distinct().sorted().toArray();
        int maxLen = 0;
        int start = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] + 1 != nums[i]) {
                int len = i - start;
                if (len > maxLen) {
                    maxLen = len;
                }
                start = i;
            }
        }
        int len = nums.length - start;
        if (len > maxLen) {
            maxLen = len;
        }
        return maxLen == 0 ? nums.length : maxLen;
    }

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{9, 1, 4, 7, 3, -1, 0, 5, 8, -1, 6}));
    }
}
