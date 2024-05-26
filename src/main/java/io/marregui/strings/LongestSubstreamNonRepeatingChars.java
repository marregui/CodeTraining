package io.marregui.strings;

import java.util.*;

public class LongestSubstreamNonRepeatingChars {

    public static int lengthOfLongestSubstring(String s) {
        Deque<Character> q = new LinkedList<>();
        int max = Integer.MIN_VALUE;
        for (int i=0, limit =s.length(); i<limit; i++) {
            char c = s.charAt(i);
            while (q.contains(c)) {
                q.removeFirst();
            }
            q.addLast(c);
            max = Math.max(max, q.size());
        }
        return max == Integer.MIN_VALUE ? s.length() : max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring(" "));
        System.out.println(lengthOfLongestSubstring("aab"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }
}
