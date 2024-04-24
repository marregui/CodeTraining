package io.marregui;

public class MatchingParens {

    public static boolean isValid(String s) {
        char[] stack = new char[32];
        int stackTop = -1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '{':
                case '[':
                    if (stackTop + 1 >= stack.length) {
                        char[] tmp = new char[stack.length * 2];
                        System.arraycopy(stack, 0, tmp, 0, stack.length);
                        stack = tmp;
                    }
                    stackTop++;
                    stack[stackTop] = c;
                    break;

                case ')':
                case '}':
                case ']':
                    if (stackTop < 0) {
                        return false;
                    }
                    char top = stack[stackTop--];
                    switch (top) {
                        case '(':
                            if (c != ')') {
                                return false;
                            }
                            break;
                        case '{':
                            if (c != '}') {
                                return false;
                            }
                            break;
                        case '[':
                            if (c != ']') {
                                return false;
                            }
                            break;
                    }
                    break;
            }
        }
        return stackTop < 0;
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));
    }
}
