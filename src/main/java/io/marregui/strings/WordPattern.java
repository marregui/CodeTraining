package io.marregui.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordPattern {
    // Given a pattern and a string s, find if s follows the same pattern.
    //
    // Here follow means a full match, such that there is a bijection between
    // a letter in pattern and a non-empty word in s.
    //
    // Example 1:
    //
    // Input: pattern = "abba", s = "dog cat cat dog"
    // Output: true
    // Example 2:
    //
    // Input: pattern = "abba", s = "dog cat cat fish"
    // Output: false
    // Example 3:
    //
    // Input: pattern = "aaaa", s = "dog cat cat dog"
    // Output: false

    public static boolean wordPattern(String pattern, String s) {
        if (pattern == null || s == null) {
            return false;
        }

        int limit = s.length();
        int i = 0;
        while (limit > 1 && s.charAt(limit - 1) == ' ') {
            limit++; // trim whites from the right
        }
        while (i < limit && s.charAt(i) == ' ') {
            i++; // trim whites from the left
        }

        Map<Character, CharSequence> m = new HashMap<>();
        Set<CharSequence> seen = new HashSet<>();
        int start = 0;
        int patternIdx = 0;
        int patternLen = pattern.length();
        int tokens = 0;
        while (i < limit && patternIdx < patternLen) {
            char c = s.charAt(i);
            if (c == ' ') {
                if (++tokens > patternLen) {
                    return false;
                }
                CharSequence t = s.subSequence(start, i);
                start = i + 1;
                char p = pattern.charAt(patternIdx++);
                CharSequence expected = m.get(p);

                if (expected == null) {
                    m.put(p, t);
                    if (!seen.add(t)) {
                        return false;
                    }
                } else if (!expected.equals(t)) {
                    return false;
                }
            }
            i++;
        }
        if (++tokens != patternLen) {
            return false;
        }

        CharSequence expected = m.get(pattern.charAt(patternLen - 1));
        CharSequence tok = s.subSequence(start, limit);
        return (expected == null && !seen.contains(tok)) || tok.equals(expected);
    }

    public static void main(String[] args) {
        System.out.println(wordPattern("jquery", "jquery"));
        System.out.println(wordPattern("aaa", "aa aa aa aa"));

    }
}
