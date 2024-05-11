package io.marregui;

import java.util.*;
import java.util.stream.Collectors;

public class Permutations {
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        permute(results, nums, 0);
        return results;
    }

    public static void permute(List<List<Integer>> results, int[] nums, int k) {
        for (int i = k; i < nums.length; i++) {
            swap(nums, i, k);
            permute(results, nums, k + 1);
            swap(nums, k, i);
        }
        if (k == nums.length - 1) {
            results.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));
    }
}
