package io.marregui.datastructures;

import org.junit.jupiter.api.Test;

public class TrieTest {


    @Test
    public void testInsert() {
        Trie<String> trie = new Trie<>();
        trie.insert("sus", "pinya");
        trie.insert("sus0", "pinya0");
        trie.insert("batido", "chocolate");
        System.out.println(trie.search("sugus"));
        System.out.println(trie.search("sus0"));
        System.out.println(trie.search("batido"));
        System.out.println(trie.search("batid"));

        trie.delete("sus");
        System.out.println(trie.search("sus"));
        System.out.println(trie.search("sus0"));

        System.out.println(trie.startsWith("sus"));
        System.out.println(trie.startsWith("sus0"));
        System.out.println(trie.startsWith("sus01"));
    }
}
