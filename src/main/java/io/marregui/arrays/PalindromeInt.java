package io.marregui.arrays;


public class PalindromeInt {

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int digits = 1;
        int num = x;
        while ((num /= 10) > 0) {
            digits++;
        }

        long leftPow = (long) Math.pow(10, digits - 1);
        long rightPow = 10;
        long rightAdjust = 1;

        for (int i = 0, j = digits - 1; i < j; i++, j--) {
            long di = (x / leftPow) % 10;
            long dj = (x % rightPow) / rightAdjust;
            if (di != dj) {
                return false;
            }
            leftPow /= 10;
            rightPow *= 10;
            rightAdjust *= 10;
        }
        return true;
    }
}
