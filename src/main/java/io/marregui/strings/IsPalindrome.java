package io.marregui.strings;


public class IsPalindrome {
    public static boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            char ci = s.charAt(i);
            char cj = s.charAt(j);
            if ((ci | 32) == (cj | 32)) {
                i++;
                j--;
            } else {
                boolean ciOk = Character.isLetterOrDigit(ci);
                boolean cjOk = Character.isLetterOrDigit(cj);
                if (ciOk && !cjOk) {
                    j--;
                } else if (!ciOk && cjOk) {
                    i++;
                } else if (!ciOk && !cjOk) {
                    i++;
                    j--;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("race a car"));
    }
}
