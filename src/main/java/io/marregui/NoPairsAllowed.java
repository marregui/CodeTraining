package io.marregui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoPairsAllowed {

    public static List<Integer> minimalOperations(List<String> words) {
        List<Integer> overallChanges = new ArrayList<>();
        for (String word : words) {
            int limit = word.length();
            int changes = 0;
            if (limit > 1) {
                char prev = word.charAt(0);
                for (int i=1; i < limit; i++) {
                    char curr = word.charAt(i);
                    if (prev == curr) {
                        changes++;
                        prev = '\0';
                    } else {
                        prev = curr;
                    }
                }
            }
            overallChanges.add(changes);
        }
        return overallChanges;
    }

    public static void main(String[] args) {
        List<Integer> output = minimalOperations(Arrays.asList("add", "book", "break"));
        System.out.printf("%s%n", output);
    }
}
