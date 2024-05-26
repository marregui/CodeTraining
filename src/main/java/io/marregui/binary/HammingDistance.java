package io.marregui.binary;

public class HammingDistance {

    // The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
    //
    // Given two integers x and y, return the Hamming distance between them.
    //
    //
    //
    // Example 1:
    //
    // Input: x = 1, y = 4
    // Output: 2
    // Explanation:
    // 1   (0 0 0 1)
    // 4   (0 1 0 0)
    //       ↑   ↑
    // The above arrows point to positions where the corresponding bits are different.
    // Example 2:
    //
    // Input: x = 3, y = 1
    // Output: 1

    public static int hammingDistance(int x, int y) {
        int diff = x ^ y;
        int cnt = 0;
        while (diff > 0) {
            cnt += (diff & 1);
            diff >>= 1;
        }
        return cnt;
    }

    public static void main(String [] args) {
        System.out.printf(""+hammingDistance(1,4));
    }
}
