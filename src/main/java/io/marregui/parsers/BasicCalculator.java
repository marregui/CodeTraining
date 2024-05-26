package io.marregui.parsers;

public class BasicCalculator {

    private static final char SENTINEL = '#';

    private int[] digits;
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
        top = -1;
        while (i < limit) {
            i = skipWhites(s, i, limit);
            if (i >= limit) {
                break;
            }
            char c = s.charAt(i);
            if (isStartOfInt(c) && (lastSeen == '\0' || lastSeen == '(' || isOp(lastSeen))) {
                int originalI = i;
                i = parseInt(c, s, i, limit);
                if (i > originalI) {
                    lastSeen = s.charAt(i - 1);
                } else {
                    addToStack(new NegNode());
                    i++;
                }
            } else if (isOp(c)) {
                if (top > -1) {
                    Node node = stack[top];
                    if (node instanceof OpNode opNode && opNode.op == SENTINEL) {
                        opNode.op = c;
                    } else {
                        stack[top] = new OpNode(c, stack[top]);
                    }
                } else {
                    throw new IllegalArgumentException("missing left operand at offset: " + i);
                }
                lastSeen = c;
                i++;
            } else if (c == '(') {
                addToStack(new OpNode(SENTINEL, null));
                lastSeen = c;
                i++;
            } else if (c == ')') {
                if (top > 0) {
                    Node node = stack[top--];
                    Node topNode = stack[top];
                    if (topNode.left == null) {
                        topNode.left = node;
                    } else {
                        topNode.right = node;
                    }
                }
                lastSeen = c;
                i++;
            }
        }

        if (top > -1) {
            while (top > 0) {
                Node node = stack[top--];
                Node topNode = stack[top];
                if (topNode instanceof NegNode) {
                    topNode.left = node;
                }
            }
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
        int digitsIdx = 0;
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
            n += digits[j] * factor;
            factor *= 10;
        }
        IntNode inode = new IntNode(n * sign);
        if (top > -1) {
            if (stack[top] instanceof OpNode opNode) {
                if (opNode.left == null) {
                    opNode.left = inode;
                } else {
                    opNode.right = inode;
                }
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

        abstract int eval();
    }

    private static class OpNode extends Node {
        private char op;

        OpNode(char op, Node left) {
            super(left, null);
            this.op = op;
        }

        @Override
        int eval() {
            return switch (op) {
                case '+' -> left.eval() + (right != null ? right.eval() : 0);
                case '-' -> left.eval() - (right != null ? right.eval() : 0);
                case SENTINEL -> left.eval();
                default -> throw new IllegalArgumentException("unknown op: " + op);
            };
        }
    }

    private static class NegNode extends OpNode {
        NegNode() {
            super('-', null);
        }

        @Override
        int eval() {
            return -left.eval();
        }
    }

    private static class IntNode extends Node {
        private final int value;

        IntNode(int value) {
            super(null, null);
            this.value = value;
        }


        @Override
        int eval() {
            return value;
        }
    }
}

