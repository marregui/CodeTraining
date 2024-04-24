package io.marregui;

public class BasicCalculator {

    // <INT>  := '-'? [0..9]+
    // <OP>   := '+' | '-'
    // <EXPR> := '(' + <Expr> + ')' | <Expr>
    // <Expr> := <INT> <OP> <INT> | <INT> <OP> <EXPR> | <EXPR> <OP> <INT>


    private int[] digits;
    private int digitsIdx;
    private Node[] stack;
    private int top;

    public BasicCalculator() {
        digits = new int[16];
        stack = new Node[16];
        top = -1;
    }


    public int calculate(String s) {
        if (s == null) {
            throw new IllegalArgumentException("cannot calculate on null");
        }
        int limit = s.length();
        int i = 0;
        char lastSeen = '\0';
        while (i < limit) {
            i = skipWhites(s, i, limit);
            if (i >= limit) {
                break;
            }
            char c = s.charAt(i);
            if (isStartOfInt(c) && (lastSeen == '\0' || isOp(lastSeen))) {
                i = parseInt(c, s, i, limit);
                lastSeen = s.charAt(i - 1);
            } else if (isOp(c)) {
                if (top > -1) {
                    stack[top] = new OpNode(c, stack[top]);
                    i++;
                } else {
                    throw new IllegalArgumentException("missing left operand at offset: " + i);
                }
                lastSeen = c;
            }
        }
        if (top == 0) {
            return stack[top--].eval();
        }
        throw new IllegalArgumentException("bad expression: " + s);
    }


    private int parseInt(char c, String s, int i, int limit) {
        int sign = 1;
        if (c == '-') {
            sign = -1;
            int originalI = i;
            i++;
            i = skipWhites(s, i, limit);
            if (i < limit) {
                c = s.charAt(i);
                if (!isDigit(c)) {
                    return originalI;
                }
            } else {
                return originalI;
            }
        }
        digitsIdx = 0;
        while (i < limit && isDigit(c = s.charAt(i))) {
            if (digitsIdx >= digits.length) {
                int[] t = new int[digits.length * 2];
                System.arraycopy(digits, 0, t, 0, digits.length);
                digits = t;
            }
            digits[digitsIdx++] = c - 48;
            i++;
        }
        int factor = 1;
        int n = 0;
        for (int j = digitsIdx - 1; j > -1; j--) {
            n = digits[j] * factor;
            factor *= 10;
        }
        IntNode inode = new IntNode(n * sign);
        if (top > -1) {
            Node node = stack[top];
            if (!node.isLeaf()) {
                node.right = inode;
                return i;
            }
        }
        addToStack(inode);
        return i;
    }

    private void addToStack(Node node) {
        if (top + 1 >= stack.length) {
            Node[] t = new Node[stack.length * 2];
            System.arraycopy(stack, 0, t, 0, stack.length);
            stack = t;
        }
        stack[++top] = node;
    }

    private static boolean isStartOfInt(char c) {
        return (c > 47 && c < 58) || c == '-';
    }

    private static boolean isOp(char c) {
        return c == '+' || c == '-';
    }

    private static boolean isDigit(char c) {
        return (c > 47 && c < 58);
    }

    private static int skipWhites(String s, int i, int limit) {
        while (i < limit && s.charAt(i) == ' ') {
            i++;
        }
        return i;
    }

    private abstract static class Node {
        protected Node left;
        protected Node right;

        protected Node(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

        abstract boolean isLeaf();

        abstract int eval();
    }

    private static class OpNode extends Node {
        private final char op;

        OpNode(char op, Node left) {
            super(left, null);
            this.op = op;
        }

        @Override
        boolean isLeaf() {
            return false;
        }

        @Override
        int eval() {
            return switch (op) {
                case '+' -> left.eval() + right.eval();
                case '-' -> left.eval() - right.eval();
                default -> throw new IllegalArgumentException("unknown op: " + op);
            };
        }
    }

    private static class IntNode extends Node {
        private final int value;

        IntNode(int value) {
            super(null, null);
            this.value = value;
        }

        @Override
        boolean isLeaf() {
            return true;
        }

        @Override
        int eval() {
            return value;
        }
    }
}

