package io.marregui;

import java.util.LinkedList;
import java.util.List;

public class MinStack {
    // Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
    //
    // Implement the MinStack class:
    //
    // MinStack() initializes the stack object.
    // void push(int val) pushes the element val onto the stack.
    // void pop() removes the element on the top of the stack.
    // int top() gets the top element of the stack.
    // int getMin() retrieves the minimum element in the stack.
    // You must implement a solution with O(1) time complexity for each function.

    private int[] stack;
    int top;
    private LinkedList<Integer> mins;

    public MinStack() {
        stack = new int[16];
        mins = new LinkedList<>();
        top = -1;
    }

    public void push(int val) {
        if (top + 1 >= stack.length) {
            int[] t = new int[stack.length * 2];
            System.arraycopy(stack, 0, t, 0, stack.length);
            stack = t;
        }
        stack[++top] = val;
        if (mins.isEmpty() || val <= mins.getFirst()) {
            mins.addFirst(val);
        }
    }

    public void pop() {
        if (top > -1) {
            int val = stack[top];
            top--;
            int min = mins.getFirst();
            if (min == val) {
                mins.removeFirst();
            }
        }
    }

    public int top() {
        return stack[top];
    }

    public int getMin() {
        return mins.getFirst();
    }

    public static void main(String[] args) {
        MinStack ms = new MinStack();
        ms.push(0);
        ms.push(1);
        ms.push(0);
        System.out.println(ms.getMin());
        ms.pop();
        System.out.println(ms.getMin());
    }
}
