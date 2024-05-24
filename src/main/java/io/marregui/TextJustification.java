package io.marregui;

import java.util.*;

public class TextJustification {

    // Given an array of strings words and a width maxWidth, format the
    // text such that each line has exactly maxWidth characters and is
    // fully (left and right) justified.
    //
    // You should pack your words in a greedy approach; that is, pack as
    // many words as you can in each line. Pad extra spaces ' ' when
    // necessary so that each line has exactly maxWidth characters.
    //
    // Extra spaces between words should be distributed as evenly as possible.
    // If the number of spaces on a line does not divide evenly between words,
    // the empty slots on the left will be assigned more spaces than the slots
    // on the right.
    //
    //F or the last line of text, it should be left-justified, and no extra space
    // is inserted between words.
    public static List<String> fullJustify(String[] words, int maxWidth) {

        List<String> result = new LinkedList<>();
        List<String> wordsInLine = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();

        int lineLen = 0;
        int lineLenNoSpaces = 0;
        for (int k = 0; k < words.length; k++) {
            String word = words[k];
            int wordLen = word.length();
            int requiredLen = wordLen;
            int numWords = wordsInLine.size();
            if (numWords > 0) {
                requiredLen++; // space in between
            }

            if (lineLen + requiredLen <= maxWidth) {
                wordsInLine.add(word);
                lineLen += requiredLen;
                lineLenNoSpaces += wordLen;
            } else {
                // current word does not fit, format the line and add it to result
                tmp.setLength(0);
                int sta = maxWidth - lineLenNoSpaces;
                if (numWords > 2) {
                    int nGaps = numWords - 1;
                    int spacesToAdd = sta / nGaps;
                    int extraSpaces = sta % nGaps;
                    for (int i = 0; i < nGaps; i++) {
                        String w = wordsInLine.get(i);
                        tmp.append(w);
                        // append spaces
                        int spaces = spacesToAdd;
                        if (extraSpaces > 0) {
                            extraSpaces--;
                            spaces++;
                        }
                        for (int j = 0; j < spaces; j++) {
                            tmp.append(' ');
                        }
                    }
                    tmp.append(wordsInLine.get(wordsInLine.size() - 1));
                } else if (numWords == 2) {
                    tmp.append(wordsInLine.get(0));
                    for (int i = 0, n = sta; i < n; i++) {
                        tmp.append(' ');
                    }
                    tmp.append(wordsInLine.get(wordsInLine.size() - 1));
                }
                if (numWords == 1) {
                    tmp.append(wordsInLine.get(wordsInLine.size() - 1));
                    for (int i = 0, n = maxWidth - tmp.length(); i < n; i++) {
                        tmp.append(' ');
                    }
                }
                result.add(tmp.toString());

                // prepare new line, add current word
                wordsInLine.clear();
                wordsInLine.add(word);
                lineLen = wordLen;
                lineLenNoSpaces = wordLen;
            }
        }

        // process last line if there is one
        if (wordsInLine.size() > 0) {
            tmp.setLength(0);
            for (int i = 0; i < wordsInLine.size(); i++) {
                String w = wordsInLine.get(i);
                tmp.append(w).append(' ');
            }
            for (int i = 0, n = maxWidth - tmp.length(); i < n; i++) {
                tmp.append(' ');
            }
            if (tmp.length() > maxWidth) {
                tmp.setLength(maxWidth);
            }
            result.add(tmp.toString());
        }
        return result;
    }
}
