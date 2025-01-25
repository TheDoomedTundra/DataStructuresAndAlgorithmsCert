package Module11_DivideAndConquer;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Your implementation of various divide & conquer sorting algorithms.
 */

public class Sorting {

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(n log n)
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays
     * back into the original T[] array. If two data are equal when merging,
     * think about which subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid
     * and will not be null.
     * 
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    @SuppressWarnings("unchecked")
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (arr.length <= 1) {
            return;
        }

        int length = arr.length;
        int midIdx = length / 2;

        T[] left = (T[]) new Object[midIdx];
        T[] right = (T[]) new Object[length - midIdx];

        for (int i = 0; i < midIdx; i++) {
            left[i] = arr[i];
        }

        for (int i = midIdx; i < length; i++) {
            right[i - midIdx] = arr[i];
        }

        // Stack overflow here
        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i+j] = left[i];
                i++;
            } else {
                arr[i+j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            arr[i+j] = left[i];
            i++;
        }

        while (j < right.length) {
            arr[i+j] = right[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(kn)
     * And a best case running time of: O(kn)
     *
     * Feel free to make an initial O(n) passthrough of the array to
     * determine k, the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * You may use an ArrayList or LinkedList if you wish, but it should only
     * be used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with merge sort. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     */
    public static void lsdRadixSort(int[] arr) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        @SuppressWarnings("unchecked")
        LinkedList<Integer>[] buckets = new LinkedList[19];

        int longest = 0;
        for (int num : arr) {
            longest = negAbs(num) < longest ? negAbs(num) : longest;
        }

        int k = 1;
        while (longest < -9) {
            longest = longest / 10;
            k++;
        }
        for (int i = 1; i <= k; i++) {
            for (int num : arr) {
                int digit = getDigit(num, i);
                if (buckets[digit+9] == null) {
                    buckets[digit+9] = new LinkedList<Integer>(); 
                }     
                buckets[digit+9].addLast(num);      
            }
            int idx = 0;
            for (Deque<Integer>bucket : buckets) {
                while (bucket != null && bucket.isEmpty() == false) {
                    arr[idx++] = bucket.poll();
                }
            }
        }
    }

    private static int getDigit(int num, int digit) {
        while (digit > 1) {
            num = num / 10;
            digit--;
        }
        return num % 10;
    }

    private static int negAbs(int val) {
        if (val < 0) {
            return val;
        }
        return -val;
    }

    public static <T> void quickSort(T[] arr, int start, int end, Comparator<T> comparator) {
        if ((end - start) < 1) {
            return;
        }

        int pivotIdx = (int) Math.random() * (end - start) + start;
        T pivotVal = arr[pivotIdx];
        swap(arr, start, pivotIdx);

        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(pivotVal, arr[j]) <= 0) {
                j--;
            }

            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        quickSort(arr, start, j-1, comparator);
        quickSort(arr, j+1, end, comparator);
    }

    public static <T> T quickSelect(T[] arr, int start, int end, int k, Comparator<T> comparator) {
        if (arr.length < 1) {
            throw new IllegalArgumentException();
        }

        int pivotIdx = (int) Math.random() * (end - start) + start;
        T pivotVal = arr[pivotIdx];
        swap(arr, start, pivotIdx);

        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(pivotVal, arr[j]) <= 0) {
                j--;
            }

            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        if (j == (k - 1)) {
            return arr[j];
        }
        if (j > (k - 1)) {
            return quickSelect(arr, start, j-1, k, comparator);
        } else {
            return quickSelect(arr, j+1, end, k, comparator);
        }
    }

    private static <T> void swap(T[] arr, int i1, int i2) {
        T temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

}
