package io.marregui;

public class AddBinary {
    public static String addBinary(String a, String b) {

        int ai = a.length() - 1;
        int bi = b.length() - 1;

        StringBuilder sb = new StringBuilder();
        boolean carry = false;
        char ac;
        char bc;
        while (ai > -1 || bi > -1) {
            if (ai > -1) {
                ac = a.charAt(ai);
                ai--;
            } else {
                ac = '0';
            }
            if (bi > -1) {
                bc = b.charAt(bi);
                bi--;
            } else {
                bc = '0';
            }

            if (ac == bc) {
                sb.append(carry ? '1' : '0');
                carry = ac == '1';
            } else {
                if (carry) {
                    sb.append('0');
                } else {
                    sb.append('1');
                }
            }
        }
        if (carry) {
            sb.append('1');
        }
        return sb.reverse().toString();
    }

    public static void main(String [] args) {
        System.out.println(addBinary("1010", "1011"));
    }
}
