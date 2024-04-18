package io.marregui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.marregui.TextJustification.fullJustify;

public class TextJustificationTest {

    @Test
    public void test1() {
        Assertions.assertEquals(
                Arrays.asList(
                        "This    is    an",
                        "example  of text",
                        "justification.  "),
                fullJustify(new String[]{
                        "This", "is", "an", "example", "of", "text", "justification."
                }, 16)
        );
    }

    @Test
    public void test2() {
        Assertions.assertEquals(
                Arrays.asList(
                        "What   must   be",
                        "acknowledgment  ",
                        "shall be        "),
                fullJustify(new String[]{
                        "What", "must", "be", "acknowledgment", "shall", "be"
                }, 16)
        );
    }

    @Test
    public void test3() {
        Assertions.assertEquals(
                Arrays.asList(
                        "Science  is  what we",
                        "understand      well",
                        "enough to explain to",
                        "a  computer.  Art is",
                        "everything  else  we",
                        "do                  "),
                fullJustify(new String[]{
                        "Science", "is", "what", "we", "understand", "well", "enough",
                        "to", "explain", "to", "a", "computer.", "Art", "is", "everything",
                        "else", "we", "do"
                }, 20)
        );
    }

    @Test
    public void test4() {
        Assertions.assertEquals(
                Arrays.asList(
                        "ask   not   what",
                        "your country can",
                        "do  for  you ask",
                        "what  you can do",
                        "for your country"),
                fullJustify(new String[]{
                        "ask", "not", "what", "your", "country", "can", "do", "for", "you",
                        "ask", "what", "you", "can", "do", "for", "your", "country"
                }, 16)
        );
    }
}
