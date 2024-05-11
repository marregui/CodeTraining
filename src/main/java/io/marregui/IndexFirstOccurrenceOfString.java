package io.marregui;

public class IndexFirstOccurrenceOfString {

    // Given two strings needle and haystack, return the index of the
    // first occurrence of needle in haystack, or -1 if needle is not
    // part of haystack.
    //
    // Example 1:
    //
    // Input: haystack = "sadbutsad", needle = "sad"
    // Output: 0
    // Explanation: "sad" occurs at index 0 and 6.
    // The first occurrence is at index 0, so we return 0.
    // Example 2:
    //
    // Input: haystack = "leetcode", needle = "leeto"
    // Output: -1
    // Explanation: "leeto" did not occur in "leetcode", so we return -1.

    public static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.length() > haystack.length() || needle.length() < 1) {
            return -1;
        }
        for (int i = 0, limit = haystack.length() - needle.length() + 1; i < limit; i++) {
            boolean matches = true;
            for (int j = i, k = 0; k < needle.length(); j++, k++) {
                if (haystack.charAt(j) != needle.charAt(k)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(strStr("sadbutsad", "sad"));
    }
}
