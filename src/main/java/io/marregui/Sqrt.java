package io.marregui;

public class Sqrt {

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

    public static void main(String[] args) {
        for (int x : new int[]{8, 3, 2147395599}) {
            int expected = (int) Math.round(Math.sqrt(x));
            int actual = mySqrt(x);
            Log.log("x:%d, expected:%d, actual:%d%n", x, expected, actual);
        }
    }
}
