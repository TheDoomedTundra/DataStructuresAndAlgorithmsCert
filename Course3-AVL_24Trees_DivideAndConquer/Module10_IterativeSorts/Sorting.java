package Module10_IterativeSorts;

import java.util.Comparator;

/**
 * Your implementation of various iterative sorting algorithms.
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * NOTE: You should implement bubble sort with the last swap optimization.
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        int stopIndex = arr.length - 1;
        while (stopIndex != 0) {
            int lastSwapped = 0;
            int i = 0;
            while (i < stopIndex) {
                if (comparator.compare(arr[i], arr[i+1]) > 0) {
                    swap(arr, i, i+1);
                    lastSwapped = i;
                }
                i++;
            }
            stopIndex = lastSwapped;
        }
    }

    /**
     * Cocktail shaker sort just for fun
     * 
     * @param <T>
     * @param arr
     * @param comparator
     */
    public static <T> void cocktailShakerSort(T[] arr, Comparator<T> comparator) {
        int startIdx = 0;
        int stopIdx = arr.length - 1;
        while (startIdx != stopIdx) {
            int lastSwapped = 0;
            for (int i = startIdx; i < stopIdx; i++) {
                if (comparator.compare(arr[i], arr[i+1]) > 0) {
                    swap(arr, i, i+1);
                    lastSwapped = i;
                }
            }
            stopIdx = lastSwapped;

            for (int j = stopIdx; j > startIdx; j--) {
                if (comparator.compare(arr[j-1], arr[j]) > 0) {
                    swap(arr, j - 1, j);
                    lastSwapped = j;
                }
            }
            startIdx = lastSwapped;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n^2)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        for (int n = arr.length - 1; n > 0; n--) {
            int maxIdx = n;
            for (int i = 0; i < n; i++) {
                if (comparator.compare(arr[i], arr[maxIdx]) > 0) {
                    maxIdx = i;
                }
            }
            swap(arr, n, maxIdx);
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i != 0 && comparator.compare(arr[i], arr[i-1]) < 0) {
                swap(arr, i, i-1);
                i--;
            }
        }
    }

    private static <T> void swap(T[] arr, int i1, int i2) {
        T temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
}