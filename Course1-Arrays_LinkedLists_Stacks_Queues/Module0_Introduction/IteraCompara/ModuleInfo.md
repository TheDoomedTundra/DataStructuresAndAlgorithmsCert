# Iterable and Iterator
## The Iterable Interface
* Abstraction that allows implementing a class to be iterated through in a for-each
* Has abstract method `iterator` that returns an `Iterator` object
* Handles the task of iterating through data
* Need to implement "next" and "hasNext" methods
* `next` returns a cursor to the next object to be iterated over

[Iterable Documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html)

[Iterator Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)

# Comparable and Comparator
* Unlike Iterable and Iterator, they are completely independent.
## The Comparable Interface
* An object that implements the Comparable interface can be compared to other objects of the same type using the `compareTo()` method.

## The Comparator Interface
* The Comparator interface provides a way to define custom sorting orders for objects.
* It has a `compare(T x, T y)` method that returns a negative integer, zero, or a positive integer depending on whether `x` is less than, equal to, or greater than `y`.
* Can use a comparator with a collection's `sort()` method to sort the collection based on the custom comparison logic.

## Comparable/Comparator
[Comparable Documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)

[Comparator Documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)

The results do NOT need to be [-1, 0, 1], but rather [-, 0, +]

[Iterators Slides](https://courses.edx.org/asset-v1:GTx+CS1332xI+1T2024+type@asset+block/Iterators.pdf)