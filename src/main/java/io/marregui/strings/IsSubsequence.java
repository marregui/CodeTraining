package io.marregui.strings;

public class IsSubsequence {

    // Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
    //
    // A subsequence of a string is a new string that is formed from the original string by
    // deleting some (can be none) of the characters without disturbing the relative positions
    // of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
    //
    // Example 1:
    //
    // Input: s = "abc", t = "ahbgdc"
    // Output: true
    // Example 2:
    //
    // Input: s = "axc", t = "ahbgdc"
    // Output: false

    public static boolean isSubsequence(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        if (s.length() > t.length()) {
            return false;
        }
        int matches = 0;
        for (int i = 0, j=0; i < s.length() && j < t.length(); i++, j++) {
            char ch = s.charAt(i);
            while (j < t.length() && t.charAt(j) != ch) {
                j++;
            }
            if (j < t.length()) {
                matches++;
            }
        }
        return matches == s.length();
    }

    public static void main(String[] args) {
        System.out.println(isSubsequence("abc", "ahbgdc"));
        System.out.println(isSubsequence("axc", "ahbgdc"));
        System.out.println(isSubsequence("aaaaaa", "bbaaaa"));
    }
}
