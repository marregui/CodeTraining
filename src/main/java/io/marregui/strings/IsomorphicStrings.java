package io.marregui.strings;

import java.util.*;

public class IsomorphicStrings {
    // Given two strings s and t, determine if they are isomorphic.
    //
    // Two strings s and t are isomorphic if the characters in s can be
    // replaced to get t.
    //
    // All occurrences of a character must be replaced with another character
    // while preserving the order of characters. No two characters may map to
    // the same character, but a character may map to itself.
    //
    // Example 1:
    //
    // Input: s = "egg", t = "add"
    // Output: true
    // Example 2:
    //
    // Input: s = "foo", t = "bar"
    // Output: false
    // Example 3:
    //
    // Input: s = "paper", t = "title"
    // Output: true

    public static boolean isIsomorphic(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        int limit = s.length();
        if (limit != t.length()) {
            return false;
        }
        if (limit == 0) {
            return true;
        }

        Map<Character, Character> map = new HashMap<>();
        Set<Character> seen = new HashSet<>();
        for (int i = 0; i < limit; i++) {
            char c = s.charAt(i);
            char c2 = t.charAt(i);
            Character expected = map.get(c);
            if (expected == null) {
                map.put(c, c2);
                if (!seen.add(c2)) {
                    return false;
                }
            } else if (expected != c2) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isIsomorphic("egg", "add"));
        System.out.println(isIsomorphic("foo", "bar"));
        System.out.println(isIsomorphic("badc", "baba"));
        System.out.println(isIsomorphic("a", "a"));
        System.out.println(isIsomorphic("paper", "title"));
        System.out.println(isIsomorphic("bbbaaaba", "aaabbbba"));
        System.out.println(isIsomorphic("baa", "cfa"));
    }
}
