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

    public static int myAtoi(String s) {
        if (s == null) {
            return 0;
        }
        int i = 0;
        int len = s.length();
        while (i < len && s.charAt(i) == ' ') {
            i++;
        }
        if (i == len) {
            return 0;
        }
        int sign = 1;
        char c = s.charAt(i);
        if (c == '-') {
            sign = -1;
            i++;
        } else if (c == '+') {
            i++;
        }
        if (i < len && s.charAt(i) == ' ' || i == len) {
            return 0;
        }
        while (i < len && s.charAt(i) == '0') {
            i++;
        }
        long r = 0;
        long factor = 1;
        for (int j = len - 1; j >= i; j--) {
            int d = s.charAt(j) - '0';
            if (d >= 0 && d <= 9) {
                r = r + d * factor;
                factor *= 10;
                if (factor < 0) {
                    if (sign < 0) {
                        r = Integer.MIN_VALUE;
                    } else  {
                        r = Integer.MAX_VALUE;
                    }
                    break;
                }
            } else {
                r = 0;
                factor = 1;
            }
        }
        if (sign < 0 && (-r < Integer.MIN_VALUE || r < 0)) {
            return Integer.MIN_VALUE;
        }
        if (sign > 0 && (r > Integer.MAX_VALUE || r < 0)) {
            return Integer.MAX_VALUE;
        }
        return sign * (int) r;
    }


    public static void main(String[] args) {
        System.out.println(sumOfEncryptedInt(new int[]{10, 103, 2}));
        System.out.println(isPowerOfTwo(16));
        System.out.println(isUgly(6));
        System.out.println(myAtoi("   0000000000012345678"));

    }
}
