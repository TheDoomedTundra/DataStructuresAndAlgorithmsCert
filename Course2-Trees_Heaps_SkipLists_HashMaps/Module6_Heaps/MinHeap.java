package Module6_Heaps;

import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    @SuppressWarnings("unchecked")
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        if (needsResize()) {
            resize();
        }
        // Add data and upheap
        size++;
        backingArray[size] = upheap(data, size);
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (size == 0) {
            throw new NoSuchElementException("The heap is already empty!");
        }
        T removed = backingArray[1];

        // Move the last element to the root and delete the end
        backingArray[1] = downheap(backingArray[size], 1);
        backingArray[size] = null;
        size--;

        return removed;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    private boolean needsResize() {
        return size == (backingArray.length - 1);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] tmpArray = (T[]) new Comparable[2*backingArray.length];
        for (int i = 0; i <= size; i++) {
            tmpArray[i] = backingArray[i];
        }
        backingArray = tmpArray;
    }

    private T upheap(T data, int idx) {
        T parent = backingArray[idx/2];

        if (parent != null && data.compareTo(parent) < 0) {
            // Swap
            backingArray[idx/2] = upheap(data, idx/2);
            return parent;
        }
        return data;
    }

    private T downheap(T data, int idx) {
        if (2*idx < size) {
            int lIdx = 2*idx;
            int rIdx = lIdx + 1;    

            T left = backingArray[lIdx];
            T right = backingArray[rIdx];

            if (right == null || left.compareTo(right) <= 0) {
                // If right child doesn't exist or left < right, check for swap with left
                if (data.compareTo(left) > 0) {
                    backingArray[2*idx] = downheap(data, 2*idx);
                    return left;
                }
            } else if (data.compareTo(right) > 0) {
                // Check for swap with right
                backingArray[2*idx+1] = downheap(data, 2*idx+1);
                return right;
            }
        }
        return data;
    }

    public static void main(String[] args)
    {
        MinHeap<Integer> heap = new MinHeap<>();
        
        heap.add(1);
        heap.add(3);
        heap.add(9);
        heap.remove();
        heap.remove();
        heap.remove();
    }
}