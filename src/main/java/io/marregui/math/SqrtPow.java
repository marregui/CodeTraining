package io.marregui.math;

public class SqrtPow {

    public static int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        if (x == 1) {
            return 1;
        }

        double bestMatch = (x + 1) / 2;
        while (Math.abs(x - bestMatch * bestMatch) > 0.0000001) {
            bestMatch = (bestMatch + x / bestMatch) / 2;
        }
        return (int) Math.abs(bestMatch);
    }

    public static double myPow(double x, int n) {
        if (n == 1) {
            return x;
        }
        if (n == 0) {
            return 1.0;
        }
        if (n == -1) {
            return x != 0.0 ? 1.0 / x : Double.NaN;
        }

        double r = 1.0;
        double base = Math.abs(x);
        int times = Math.abs(n);
        if (n == Integer.MIN_VALUE) {
            times = Integer.MAX_VALUE;
        }
        while (times > 0) {
            if (times % 2 == 0) {
                base *= base;
                times /= 2;
            } else {
                r *= base;
                times--;
            }
        }
        if (x < 0.0 && n % 2 == 1) {
            r *= -1.0;
        }
        if (n < 0) {
            r = 1.0 / r;
        }
        return r;
    }

    public static void main(String[] args) {
        for (int x : new int[]{8, 3, 2147395599}) {
            int expected = (int) Math.round(Math.sqrt(x));
            int actual = mySqrt(x);
            System.out.printf("x:%d, expected:%d, actual:%d%n", x, expected, actual);
        }

        System.out.println(myPow(2.0, -2147483648));
    }
}
