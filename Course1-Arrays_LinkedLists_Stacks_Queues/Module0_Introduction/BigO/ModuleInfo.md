# Big-O Concepts
## What is Big-O Notation?
**Big-O notation** is a way to analyze and describe the performance of an algorithm. It provides a mathematical expression that describes the upper bound of the time complexity of an algorithm. In other words, it tells us how the runtime of an algorithm grows as the input size increases.

The **complexity** is how efficient the algorithm is in terms size.

## Run-time analysis
* Start with primitive operations
    *   Assign value
    *   Arithmetic operation
    *   Comparison
    *   Method call or return
    *   Access element or follow a reference

Primitive operations execute in constant time. You can count the operations to correlate to the run time.

## Big-O Notation
* Denoted O(f(n)), where f(n) is a function that describes the upper bound of the algorithm's time complexity
* In this, typically represents an *upper bound*

# Conventions and Examples
1. Drop Constant Factors
    * O(2n) = O(n) - as we approach infinity the constant is insignificant, so drop it.
2. Drop Lower Order Terms
    * O(n^2 + n) = O(n^2) - the lower order term n is insignificant compared to n^2 as n gets larger.
3. O(1) - Constant Complexity
    * Always operates in the same amount of time. For example, returning the first element of an array or accessing a value in a dictionary.
4. O(n) - Linear Complexity
    * The time complexity grows linearly with the size of the input. For example, iterating through an array or linked list.
5. O(log(n)) - Logarithmic Complexity
    * The time complexity grows logarithmically with the size of the input. This is often seen in divide-and-conquer algorithms like binary search.

## Constant Factors - When are they important?
While from a worst-case performance analysis they may not matter, with small inputs constant factors come into play.

For example, consider one algorithm with O(n) complexity and another with O(n<sup>2</sup>) complexity. If the first performs 10000000000n operation and the second performs 10n<sup>2</sup> operations, for inputs sizes n $\leq$ 1 billion, the O(n<sup>2</sup>) algorithm will be faster.

[Algorithm Analysis Slides](https://courses.edx.org/asset-v1:GTx+CS1332xI+1T2024+type@asset+block/AnalysisOfAlgorithms.pdf)