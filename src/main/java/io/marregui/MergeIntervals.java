package io.marregui;

public class MergeIntervals {


    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return intervals;
        }
        int resultIdx = 1;
        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            int prevHi = intervals[resultIdx - 1][1];
            if (curr[0] <= prevHi) {
                if (curr[1] > prevHi) {
                    intervals[resultIdx - 1][1] = curr[1];
                }
            } else {
                intervals[resultIdx++] = curr;
            }
        }

        int[][] finalResult = new int[resultIdx][];
        System.arraycopy(intervals, 0, finalResult, 0, resultIdx);
        return finalResult;
    }

    public static void p(int[][] intervals) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < intervals.length; i++) {
            int[] range = intervals[i];
            sb.append('[').append(range[0]).append(',').append(range[1]).append(']');
            if (i + 1 < intervals.length) {
                sb.append(',');
            }
        }
        sb.append(']');
        System.out.println(sb);
    }

    private static void sort(int[][] array) {
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[][] array, int lo, int hi) {
        if (array == null || lo < 0 || hi > array.length || lo >= hi) {
            return;
        }

        int[] pivot = array[lo];
        int left = lo - 1;
        int right = hi + 1;
        while (left < right) {

            do {
                left++;
            } while (left < hi && array[left][0] < pivot[0]);

            do {
                right--;
            } while (right > lo && array[right][0] > pivot[0]);

            if (left < right) {
                int[] t = array[left];
                array[left] = array[right];
                array[right] = t;
            }
        }

        if (lo < right) {
            sort(array, lo, right);
        }
        if (right + 1 < hi) {
            sort(array, right + 1, hi);
        }
    }


    public static void main(String[] args) {
//        p(merge(new int[][]{{1, 3}, {15, 18}, {8, 10}, {2, 6}}));
//        p(merge(new int[][]{{1, 4}, {4, 5}}));
//        p(merge(new int[][]{{1, 4}, {2, 3}}));
//        p(merge(new int[][]{{1, 4}, {0, 2}, {3, 5}}));
        p(merge(new int[][]{{1, 4}, {0, 4}}));

        int[][] a = new int[][]{{1, 3}, {15, 18}, {8, 10}, {2, 6}};
        sort(a);
        p(a);
    }
}
