package io.marregui;

import java.math.BigInteger;
import java.util.Arrays;

public class A {

    public static int hIndex(int[] citations) {

        int n = citations.length;
        int[] buckets = new int[n + 1];
        for (int c : citations) {
            if (c >= n) {
                buckets[n]++;
            } else {
                buckets[c]++;
            }
        }
        int count = 0;
        for (int i = n; i >= 0; i--) {
            count += buckets[i];
            if (count >= i) {
                return i;
            }
        }
        return 0;

    }

    public static void main(String[] args) {
        BigInteger result = BigInteger.ONE;
        int prev = 0;
        for (int i=2; i < 100; i++) {
            result = result.multiply(BigInteger.valueOf(i));

            String r = result.toString();
            int last = r.length() - 1;
            while (last > -1 && r.charAt(last) == '0') {
                last--;
            }
            int trailing = r.length() - last - 1;
            if (trailing > 0) {
                if (trailing > prev) {
                    System.out.printf("%d -> %d  %d%n", i, trailing, i/5);
                    prev = trailing;
                }
            } else {
                System.out.printf("%d -> %d  %d%n", i, trailing, i/5);
            }
        }
    }
}
