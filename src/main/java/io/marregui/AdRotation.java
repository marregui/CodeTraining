package io.marregui;

public class AdRotation {

    public static int changeAds(int base10) {
        String len = Integer.toBinaryString(base10);
        int result = 0;
        int factor = 1;
        for (int i=len.length()-1; i > 0; i--) {
            if (len.charAt(i) == '0') {
                result += factor;
            }
            factor *= 2;
        }
        return result;
    }

    public static void main(String[] args) {
        int input = 50;
        int output = changeAds(input);
        System.out.printf("%d -> %d%n", input, output);
    }
}
