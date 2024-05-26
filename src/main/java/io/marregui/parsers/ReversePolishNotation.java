package io.marregui.parsers;

import java.util.TreeSet;

public class ReversePolishNotation {

    private static final TreeSet<String> OPS = new TreeSet<>();

    static {
        OPS.add("+");
        OPS.add("-");
        OPS.add("*");
        OPS.add("/");
    }

    public static int evalRPN(String[] tokens) {
        int [] stack = new int[tokens.length];
        int top = -1;
        for (int i = 0; i < tokens.length; i++) {
            String tok = tokens[i];
            if (OPS.contains(tok)) {
                if (top > 0) {
                    int v0 = stack[top - 1];
                    int v1 = stack[top];
                    int r = 0;
                    switch (tok) {
                        case "+":
                            r = v0 + v1;
                            break;
                        case "-":
                            r = v0 - v1;
                            break;
                        case "*":
                            r = v0 * v1;
                            break;
                        case "/":
                            r = v0 / v1;
                            break;
                    }
                    stack[--top] = r;
                }
            } else {
                stack[++top] = Integer.parseInt(tok);
            }
        }
        return top > -1 ? stack[0] : 0;
    }

    public static void main(String[] args) {
        System.out.println(evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));

    }
}
