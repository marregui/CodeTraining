package io.marregui;

public class StarPyramid {

    static int solution(int n) {
        int widthAtBase = 2 * (n + 1) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int numStars = 2 * i + 1;
            int prefixLen = (widthAtBase - numStars) / 2;
            for (int k = 0; k < prefixLen; k++) {
                sb.append(" ");
            }
            for (int k = 0; k < numStars; k++) {
                sb.append("*");
            }
            System.out.println(sb);
            sb.setLength(0);
        }
        return n;
    }

    public static void main(String[] args) {
        solution(5);
    }
}
