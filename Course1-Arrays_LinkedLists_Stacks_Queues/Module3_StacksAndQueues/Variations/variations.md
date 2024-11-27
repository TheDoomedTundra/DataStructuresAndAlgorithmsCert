# Priority Queue ADT
* Stack ADT
    <br>-> Remove  most recently added
* Queue ADT
    <br>-> Remove first added
* Priority Queue ADT
    <br>-> Remove element with highest priority

Typically used to retrieve minimum or maximum value from a data set, depending on the implementation.
* Uses comparable data

## Implementations
Problem: Efficiently ranking by priority, constantly searching for each add and remove is very inefficient
Solution: Heaps

# Deque
* Short for "double-ended queue"
* Does not have a single expected removal order, you can add/remove first and last

## Unuspported Ops
* Searching for data
* Arbitrary index access and add/remove ops

## Backing Data Types
* Circular wraparound array
    * Must wrap around the front to the back, and the back to the front
* Doubly-linked list