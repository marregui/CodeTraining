package io.marregui.arrays;

public class MinSubarrayLen {

    public static int minSubArrayLen(int target, int[] nums) {

        int min = Integer.MAX_VALUE;
        int start = 0;
        int accum = 0;
        int i = 0;
        while (i < nums.length) {
            if (accum + nums[i] >= target) {
                int len = i - start + 1;
                if (len < min) {
                    min = len;
                }
                start++;
                i = start;
                accum = 0;
            } else {
                accum += nums[i];
                i++;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public static void main(String[] args) {
        minSubArrayLen(7, new int[]{2,3,1,2,4,3});
    }
}
