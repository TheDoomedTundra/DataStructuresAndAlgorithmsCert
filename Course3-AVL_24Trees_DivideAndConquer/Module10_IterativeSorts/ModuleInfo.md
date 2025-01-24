# Introduction to Sorting
## Algorithmic Problems and Solutions
Given an input data structure, wanting to put it in a specific output.

**What is sorting?**
* **Sorting**: Arranging data based on defined order
* Order established based on comparisons

### Types of Sorting Algorithms
1. **Iterative**
    * Iterate through the data several times to sort the data
2. **Divide and Conquer**
    * Break the data into subsets, sort the smaller subsets (often recursively)
3. **Non-Comparison**
    * Sort certain types of data without performing any comparisons

### Analyzing Sorting Algorithms
1. Time Complexity
2. Stability: Stable or unstable?
    * Stable - maintains relative order of duplicates throughout algorithm
        * Comes from strict comparisons between adjacent elements (but can be satisfied in other ways)
    * Instability caused by making "non-adjacent" swaps
    * Stability allows multiple sorts to be applied and maintain meaningful order among duplicates
        * Important with multiple properties, like sorting by first or last name when the other name is the same
3. Adaptivity: Adaptive or not adaptive?
    * Adaptive - takes advantage of preexisting order to improve performance
    * Achieved by design or optims that detect presorted data
4. Place - In-place or out-of-place?
    * In-place - no more than `O(1)` extra (non-recursive) memory required
        * Essentially, don't copy data out of the original memory
    * Achieved by sorting in the original structure
    * In-place sorts require a lot of swaps
    * If we have an out of place with `O(n)` additional memory, we can satisfy stability in quadratic time by doing
    all of the work in the copy array, preserving original order at all times.

## Bubble Sort
Iterative sort with the premise that it "bubbles" the largest element to the end of the array for each iteration.
* Guarantees that after `i` iterations, the last `i` elements are sorted.

The algorithm:
```
stopIndex = array.length - 1
while stopIndex != 0:
    int i
    while i < stopIndex:
        if array[i] > array[i+1]
            swap values
        i++;
    stopIndex--;
```

### Optimizations
1. **Adaptivity**
    * If no swaps are made during a loop (the outer loop), terminate the algorithm

```
swapped = true
while stopIndex != 0 and swapped
    ...
    swapped = false
    while i < stopIndex
        if array[i] > array[i+1]
            ...
            swapped = true
    ...
```

2. **Adaptivty 2.0, more natural adaptation**
```
while stopIndex != 0
    ...
    int lastSwapped
    while i < stopIndex
        if array[i] > array[i+1]
            ...
            lastSwapped = i
    stopIndex = lastSwapped
```

### Time Complexity
* Best Case: $O(n)$ to iterate once (n-1 comparisons w/ optims, (n)(n-1)/2 w/o optims)
* Average Case: $O(n^2)$ since approximately go to end of inner loop
* Worst Case: $O(n^2)$ reverse sorted array ((n)(n-1)/2 w & w/o optims)
* Stable - Only swap adjacent elements when not-equal
* Adaptive - given you use one of the two optimizations
* In-place

Note: With almost sorted arrays, tends to decay to the max even with the optimizations.
For example, sort `[2, 3, 4, 5, 1]` with and without optimizations. Need to get to the swap of (5, 1)

## Insertion Sort
Iterative sort with the premise that the elements before the current element are presorted. Then insert 
an unsorted element in its correct location in the sorted array. 
```
for n = 1 : array.length - 1
    int i, behind presorted array (length of presorted)
    while i is not at front of array and array[i] < array[i - 1]
        swap values
        decrement i
```

**Note**: This is the only one of the iterative algorithms that sorts by **relative** and not *final* positions.

### Time Complexity
* Best Case $O(n)$ for a sorted array
* Average Case $O(n^2)$
* Worst Case $O(n^2)$ for a reverse sorted array
* Stable -  strict comparisons, swaps adjacent
* Adaptive - Yes, stops when no swap needed
* In-place

### Food for Thought -- Streaming Algorithms
If we want to know if a large dataset that is too large to store is sorted, we look at the largest element seen so far, and see if new data is less then. This is similar to insertion sort, which is the only algorithm (in this course)
that can process data as a stream progresses.

## Selection Sort
Passes through array searching for the largest item, then swaps it to the end of the array. Keeps doing this, looking
for the nth largest until the array is sorted.
* There are multiple implementations, you could do the minimum as well

```
loop from n = array.length-1 to 1
    init i for max value
    loop from 0 to n
        if array[i] > array[maxIdx]
            maxIdx = i
    swap(n, maxIdx)
```

* What is the advantage? Minimizes swaps and data movement in the array

### Time Complexity
* Best Case $O(n^2)$
* Average Case $O(n^2)$
* Worst Case $O(n^2)$
* Unstable - long blind swap with last element
    * For example, it can swap duplicates around, and depending on the inequality they become out of order
    * If we copy to a new array and make it out-of-place, it can become stable
* Not adapative - cannot detect if already sorted
* In-place

### Food for Thought -- Sublinear Algorithms
What if our dataset is too expensive to even read the whole thing? Algorithms that have time complexity smaller than $n$

For example, a **property tester**
* An algorithm that checks if the input satisfies some property
* Have parameter $\varepsilon$ to quantify what inputs it works well for, and a metric of how "far" the input is from satisfying the property.
    * For example, if the distance function is "How many swaps are needed at a minimum to sort the array?", then $\varepsilon = 1/2$ says we should distinguish a sorted array from one that requires half of all possible swaps with a high probability.

## Cocktail Shaker Sort
A modification of bubble sort. Two iterations of bubble sort each iteration, one to bubble the maximum and one to bubble the minimum. ("shaken back and forth")

```
init swapsMade, startInd, endInd
while swapsMade
    swapsMade = false
    loop from startInd to endInd
        if array[i] > array[i+1]
            swap(i, i+1)
            swapsMade = true, endInd = i
    if swapsMade
        swapsMade = false
        loop from endInd to startInd
            if array[i-1] > array[i]
                swap(i-1, i)
                swapsMade = true, startInd = i
```
**Note**: `swapsMade` flag is not needed since last swapped optimization with make the search space empty if no swaps are made. This requires an additional variable to set endInd to 0 to startInd if no swaps are made

### Time Complexity
Same reasons as bubble sort
* Best Case: $O(n)$
* Average Case: $O(n^2)$
* Worst Case: $O(n^2)$
* Stable
* Adaptive
* In-place

## Usage of Iterative Sorts
Rearely used in practice because quadratic performance on average is expensive for sorting. Two cases where they may be used:
1. We need a simple implementation of a sorting algorithm, and the data is small enough that quadratic isn't too expensive
2. The size of the data is small enough that the overhead of implementing more complex algorithms is not worth the asymptotic performance gain

**REMEMBER** the first solution you come up with is typically simple, inefficient, and naive (like iterative sorts).