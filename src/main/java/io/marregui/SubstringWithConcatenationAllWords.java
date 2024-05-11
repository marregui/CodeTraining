package io.marregui;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO
public class SubstringWithConcatenationAllWords {

    public static List<Integer> findSubstring(String s, String[] words) {
        if (s == null || s.isEmpty() || words == null || words.length == 0) {
            return Collections.emptyList();
        }

        int len = words[0].length();
        int targetLen = len * words.length;
        if (s.length() < targetLen) {
            return Collections.emptyList();
        }

        Map<CharSequence, Integer> map = Arrays.stream(words).
                collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
        int lo = 0;
        int limit = s.length() - len + 1;
        List<Integer> indexes = new LinkedList<>();
        int found = 0;
        while (lo < limit) {
            if (map.containsKey(s.subSequence(lo, lo + len))) {
                indexes.add(lo);
                lo += len;
                found++;
            } else {
                lo++;
            }
        }
        if (found < words.length) {
            return Collections.emptyList();
        }

        List<Integer> results = new ArrayList<>();
        int start = -1;
        int accum = 0;
        int prevI = indexes.get(0);
        for (int i = 1, n = indexes.size(); i < n; i++) {
            int idxI = indexes.get(i);
            if (start == -1) {
                start = idxI;
                accum = len;
            } else {

                int tmp = accum + len;
                if (tmp == targetLen) {
                    CharSequence w = s.subSequence(start, idxI + len);
                    results.add(start);
                    start = -1;
                } else if (tmp < targetLen) {
                    if (prevI + len == idxI) {
                        accum = tmp;
                    } else {
                        start = -1;
                    }
                } else {
                    start = -1;
                }
            }
            prevI = idxI;
        }
        return results;
    }

    public static void main(String[] args) {
//        System.out.println(jump(new int[]{2, 3, 1, 1, 4}));
        System.out.println(findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}));
//        System.out.println(findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}));
    }
}
