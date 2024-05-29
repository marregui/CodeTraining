package io.marregui;


public class Tmp {


    public static int sumOfEncryptedInt(int[] nums) {
        int sum = 0;
        for (int n : nums) {
            int cnt = 0;
            int max = Integer.MIN_VALUE;
            while (n > 0) {
                max = Math.max(max, n % 10);
                n /= 10;
                cnt++;
            }
            int r = 0;
            for (int i = 0; i < cnt; i++) {
                r = r * 10 + max;
            }
            sum += r;
        }
        return sum;
    }

    public static boolean isPowerOfTwo(int n) {
        while (n > 1) {
            if (n % 2 != 0) {
                return false;
            }
            n /= 2;
        }
        return true;
    }

    public static boolean isUgly(int n) {
        if (n < 2) {
            return false;
        }

        while (n % 2 == 0) {
            n /= 2;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        while (n % 5 == 0) {
            n /= 5;
        }
        return n == 1;
    }

    public static void main(String[] args) {
        System.out.println(sumOfEncryptedInt(new int[]{10, 103, 2}));
        System.out.println(isPowerOfTwo(16));
        System.out.println(isUgly(6));

    }
}
