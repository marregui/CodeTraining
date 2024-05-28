package io.marregui.math;

public class ReverseInteger {

    public static int reverse(int x) {
        int sign = 1;
        if (x < 0) {
            sign = -1;
            x = -x;
        }
        long result = 0;
        while (x > 0) {
            int d = x % 10;
            result = (result + d) * 10;
            x /= 10;
        }
        long r = sign * result / 10;
        if (sign < 0 && r < Integer.MIN_VALUE || sign > 0 && r > Integer.MAX_VALUE) {
            return 0;
        }
        return (int) r;
    }

    public static void main(String[] args) {
        System.out.println(reverse(-2147483412));

    }
}
