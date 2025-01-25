# Divide and Conquer Algorithms
Break up the array to be sorted into smaller parts. Many use recursion to break up the halves to be sorted.

## Merge Sort
Break the arrays into subarrays. Sort the lowest sized subarrays, and merge them back together.


```
function merge(array)
    if array.length == base case (1)
        return
    else if (optional) length is 2
        swap
        return
    length = array.length
    midIndex = length/2
    left = array[0:midIndex-1]
    right = array[midIndex:length-1]
    merge(left)
    merge(right)
    init i, j
    while i && j are not at the end of left and right arrays, respectively:
        if left[i] <= right[j]
            array[i+j] = left[i]
            i++
        else
            array[i+j] = right[j]
            j++
    while i < left.length
        array[i+j] = left[i]
        i++
    while j < right.length
        array[i+j] = right[j]
        j++
```

### Time Complexity
* Best Case: $O(n \log(n))$ -> divide in half for log levels, with n data per level
* Average Case: $O(n \log(n))$
* Worst Case: $O(n \log(n))$ -> No way to detect fully sorted
* Stable? Yes due to non-strict inequality on sorted arrays taking from left
* Adaptive: No, cannot detect
* Out-of-place: Copies into sub arrays
    * Space: $O(n)$ due to split sub-arrays

Benefits: Consistency, better worse case run-time than iterative sorts. BUT, memory intensive.

## Quick Sort
There are many variations of quick sort, this one is **randomized inplace quick sort** using the **hoare partition scheme**. For this, instead of explicity splitting into haves, it uses a **pivot** element $p$. It is partitioned so that all data less than $p$ is left of the pivot, and vice versa for larger data.

Quick sort can be <u>either in-place or out-of-place</u>. This is an in-place quick sort.

```
quicksort(array, start, end)
    if end - start < 1
        return
    pivotIdx = random index between stard and end
    pivotVal = array[pivotIdx]
    swap(array[start], array[pivotIdx])

    // Pivot chose, partition
    i = start + i; j = end
    while i and j not crossed
        while i and j not crossed and array[i] <= pivotVal
            i++
        while i and j not crossed and array[j] >= pivotVal
            j--
        if i and j not crossed
            swap(array[i], array[j])
            i++, j--
    swap(array[start], array[j]) // Possbily non-adjacent, unstable
    quicksort(array, start, j-1)
    quicksort(array, j+1, end)
```

### Time Complexity
* Best Case: $O(n \log(n))$ -> relying on data being cut in half by the random index
* Average Case: $O(n \log(n))$
* Worst Case: $O(n^2)$ -> Pivot is the min or max value, becomes selection sort-ish
* Stable? No, long blind swap
* Adaptive: No, cannot detect
* In-place: This one anyways, defines subarrays by indices

### Aside: Space Complexity of Algorithms
Generally, talking about **auxiliary space complexity**. This is the space complexity of an algorithm that is **neither the space of the input not the space of the output**. That is, it's the working, non I/O memory.

Space complexity is the **maximum amount of space used at any given time during execution of the algorithm**. This is why merge sort is $O(n)$ and not $O(n\log(n))$, since the maximum at one time is equivalent to the entire array.

There are other issues, such as **recursion and methods calls not being free space-wise**. The call stack contributes to the overall space, but often is small enough (such as the $O(\log(n))$ space for merge sort) that we can ignore it.

However, for an algorithm like quick sort, the recurvise stack can become $O(n)$ in the worst case, giving it a linear space even though it is sorting in-place.

### Food for Thought: Heap Sort
Heap Sort is another $O(n \log(n))$ algorithm. We can take advantage of it being a min or max heap to sort the data.

1. Build a min heap
2. Dequeue from the heap until it is empty, giving us the smallest item remaining after each call

Building is $O(n)$ with build heap or $O(n\log(n))$ adding one-by-one. Removing from the heap is a time complexity of $O(n\log(n))$ for all cases. This is not adaptive, and out-of-place since we need another array fr the heap, and not stable since we don't have any information on duplicate ordering. 

We could also add to a BST/AVL, using an inorder traversal in $O(n)$ and the average build time compleixty of $O(n\log(n))$.


## The Limits of Comparison-Based Sorting
For any deterministic, comparison-based sorting algorithm, there is always an input that takes $\Omega(n\log(n))$ comparisons to execute.

A sorting algorithm can be thought of as a **decision tree**. This algorithm has $n!$ permutations (resulting in $n!$ leaves), where the worst case is the longest path to a leaf of the decision tree. When deciding to go left or right at a node, the worst case means we have our search space, and the path will have $\log(n!) \approx n\log(n)$ comparisons.

The same statement is true for the average case of a potentially-randomized comparison-based sorting algorithm.

## LSD Radix Sort
The lower bound mentioned above refers to comparison-based sorts. We can avoid comparisons if we assume there is some structure to the data, such as being all integers in the case of **LSD Radix Sort**.

Least-Significant-Digit Radix Sort: Repeatedly sorts integers digit by digit moving from the *least significant digit* to the most significant.
* Disadvantage: Can only sort integer-like data types (requires an alphabet of characters and an ordering system)
* Advantage: Doesn't rely on comparisons, which has the strict lower-bound of $\Omega(n\log(n))$ 

**radix** refers to the size of the alphabet. So 10 for positive digits, 19 for positive and negative digits, 26 for the alphabet, etc.

```
radix(array)
    buckets = LinkedList[19] // For -9 to 9
    for iteration = 0:k(number of digits in longest number)
        for i = 0:array.length
            calc current digit of array[i]
            add array[i] to buckets[digit+9]
        idx = 0
        for bucket in buckets
            while bucket is not emoty
                array[idx++] = value removed from bucket
```

### Time Complexity
* Best Case: $O(kn)$
* Average Case: $O(kn)$
* Worst Case: $O(kn)$
* Stable
* Adaptive: no since cannot detect sorted array
* Out-of-place: copies data into buckets

On average, works well on large data sets where k is relatively small compared to n.

Can be done for the MSD, but more complicated due to recursion

### Food for Thought: MSD Radix Sort
Needs to be done recursively instead. For each iteration $i$:

1. From left to right of the array, move integers into buckets based on the digit value of the $i^{th}$ position form the left.
2. For each bucket, recursively call MSD radix sort on that bucket for iteration $i+1$.
3. Take the sorted data in each bucket and dequeue all items like in LSD from left to right

Recursively creating the buckets creates a much larger memory footprint. However, there are some interesting properties:
1. MSD radix sort is somewhat adaptive as it can stop early if the numbers are of varying lengths
2. Can give you an "almost sorted" array after a few iterations. Helpful if sorting on order of magnitude rather than precise sorting
3. This version is stablem though there are variants which use less memory at the cost of stability.

Part of a broader class of algorithms called Bucket Sorts.

## Quick Select
Not a sorting algirithm, but solves the **Kth Selection** problem, and is heavily based on quick sort.

The Kth selection problem is:

For an array of comparable data of length $n$, output the $k^{th}$ smallest data in the array. If duplicates are present, they count in the numbering. 

We do not need to sort the entire array, just sort up through $k-1$ as this will be the $k^{th}$ smallest element in a sorted arrat. For a fully sorted array, this would be $O(n\log(n))$.

```
quickselect(array, start, end, k)
    perform quick sort up to recursive calls
    swap(array[start], array[j])
    if j == k - 1
        return array[j]
    if j > k - 1
        quickselect(array, start, j-1, k)
    else
        quickselect(array, j+1, end, k)
```

### Time Compleixty
* Best Case: $O(n)$ -> similar to skip list, recursively looking at halves when pivot is the median
* Average Case: $O(n)$
* Worst Case: $O(n^2)$ -> pivot is min or max
* Not stable -> long swaps
* In-place


### Food for Thought: Deterministic Pivot Selection
Why not make it deterministic?
* Choosing the first or last element to eliminate pivot swap: if sorted it is worst case
* Heuristic choice, median of 3: Not hard to come up with options where this is bad, making it exploitable

There is a way to guarantee good pivots deterministically so that the overall worst case of quick sort and quick select are $O(n\log(n))$ and $O(n)$.  This is called the **median of medians**. The general idea is as follows:
1. Group the $n$ elements of the subarray into groups of size 5
2. Find the median of each of these group
3. Compuete the median of these medians recursively
4. Use the computed median of medians as the pivot for the next iteration

In practice, it is not typically used due to the high overhead costs and the risk of the worst case of randomized pivots is so low.