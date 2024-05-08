package io.marregui;

import java.util.HashMap;
import java.util.Map;

public class ClimbStairs {
    // You are climbing a staircase. It takes n steps to reach the top.
    //
    // Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
    //
    //
    //
    // Example 1:
    //
    // Input: n = 2
    // Output: 2
    // Explanation: There are two ways to climb to the top.
    // 1. 1 step + 1 step
    // 2. 2 steps
    // Example 2:
    //
    // Input: n = 3
    // Output: 3
    // Explanation: There are three ways to climb to the top.
    // 1. 1 step + 1 step + 1 step
    // 2. 1 step + 2 steps
    // 3. 2 steps + 1 step

    private static final Map<Integer, Integer> memo = new HashMap<>();
    static {
        memo.put(0, 0);
        memo.put(1, 1);
        memo.put(2, 2);
    }

    public static int climbStairs(int n) {
        Integer result = memo.get(n);
        if (result != null) {
            return result;
        }
        memo.put(n, climbStairs(n - 1) + climbStairs(n - 2));
        return memo.get(n);
    }
}
