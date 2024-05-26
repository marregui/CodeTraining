package io.marregui.dp;

public class WordSearch {

    public static boolean exist(char[][] board, String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }

        char firstChar = word.charAt(0);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == firstChar) {
                    if (exists(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static boolean exists(char[][] board, String word, int row, int col, int wordIdx) {
        if (word.length() == wordIdx) {
            return true;
        }
        if (row < 0 || col < 0 || row >= board.length || col >= board[row].length || word.charAt(wordIdx) != board[row][col]) {
            return false;
        }
        board[row][col] = '\0';
        boolean ans = exists(board, word, row - 1, col, wordIdx + 1)
                || exists(board, word, row + 1, col, wordIdx + 1)
                || exists(board, word, row, col - 1, wordIdx + 1)
                || exists(board, word, row, col + 1, wordIdx + 1);

        board[row][col] = word.charAt(wordIdx);
        return ans;
    }
}
