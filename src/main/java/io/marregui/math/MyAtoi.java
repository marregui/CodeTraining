package io.marregui.math;


public class MyAtoi {

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
                    } else {
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
        System.out.println(myAtoi("   0000000000012345678"));

    }
}
