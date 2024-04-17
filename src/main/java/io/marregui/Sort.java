package io.marregui;

import java.util.Arrays;

public class Sort {


    private static void quickSort(int low, int high, long[] array) {
        if (array == null || high <= low) {
            return;
        }
        long pivot = array[low];
        int left = low - 1;
        int right = high + 1;
        while (left < right) {
            do {
                left++;
            }
            while (left < high && array[left] < pivot);

            do {
                right--;
            }
            while (right > low && array[right] > pivot);

            if (left < right) {
                long tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
            }
        }
        if (low < right) {
            quickSort(low, right, array);
        }
        if (right + 1 < high) {
            quickSort(right + 1, high, array);
        }
    }

    public static void quickSort(long[] array) {
        quickSort(0, array.length - 1, array);
    }

    /*
     * worst-case time complexity: O(N^2)
     * average-case time complexity: O(N^2)
     * best-case time complexity: O(N)
     *
     * efficient for small data sets, appropriate for data sets that are already partially sorted
     */
    public static void insertionSort(long[] arr) {
        for (int i = 1; i < arr.length; i++) {
            long key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        long[] array = {11, 1, 2, -291, 11, 14, 2, 0, -291, 9, 7, 11, -1};
        System.out.printf("Array: %s%n", Arrays.toString(array));
        quickSort(array);
        System.out.printf("Array: %s%n", Arrays.toString(array));

        array = new long[]{11, 1, 2, 14, 2, 9, 7, 11, -1};
        System.out.printf("Array: %s%n", Arrays.toString(array));
        insertionSort(array);
        System.out.printf("Array: %s%n", Arrays.toString(array));
    }
}