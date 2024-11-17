### <p style="font-size:40px"> Arrays and ArrayLists </p>

**[1. Arrays](#1-arrays)**
* [1.1. Notes on Memory](#11-notes-on-memory)
* [1.2. Time Complexity](#12-time-complextiy)

**[2. ArrayLists](#2-arraylists)**
* [2.1. Abstract Data Types (ADTs)](#21-abstract-data-types-adts)
    * [2.1.1. List ADT](#211-list-adt)
* [2.2. The ArrayList](#22-the-arraylist)
    * [2.2.1 Terminology](#221-terminology)
    * [2.2.2 Functionality](#222-functionality)
    * [2.2.3 Time Complexity](#223-time-complextiy)
    * [2.2.3.1 Amortized Analysis of Adding to the Back](#2231-amortized-analysis-of-adding-to-the-back)

**[3. Amortized Analysis](#3-amortized-analysis)**
* [3.1. Soft v. Hard Removals](#31-soft-v-hard-removals)


<br>

# 1. Arrays
NOT a primitive data structure like primitve data, it is what is used to store data.<br>
Consists of a sequence of cells stored in a **contiguous** block of memory.
- Do not require contiguous data
- Good for fast lookup, but not for changing size needs

## 1.1. Notes on Memory
* Memory is static, have to allocate the bounds
* To resize, must move it to a larger chunk of memory

## 1.2. Time Complextiy
* Index acccess is O(1)
    * Computed by multiplying number of elements, by size of the data type, plus address of the first element
    ``` 
    ele = start + i * data_size;
    ```
* Element search for array of size n is O(n)
* Adding a new element is O(n) (copy all elements n to a new array)

<br>

# 2. ArrayLists
## 2.1. Abstract Data Types (ADTs)
* Model description of a data type that is defined by its behaviors and operations.
    * Basically an interface, where the implementation is the **data structure**

### 2.1.1. List ADT
<u>Definition</u>: A sequence of **contiguous** data values that are accessible via indexing. Requires the following operations:
1. `addAtIndex(int index, T data)` - Add data to list at index, shift all values to the right of index by +1 to make room
2. `removeAtIndex(int index)` - Remove data at the index, shift all values to the right of index by -1
3. `get(int index)` - Return data at index
4. `isEmpty()` - Whether or not the list is empty
5. `clear()` - Reset to default with no data
6. `size()` - Return the number of elements

The following methods are not requred for the List ADT, but are useful for other data structures, such as Stacks and Queues:
1. `addToFront(T data)`
2. `addToBack(T data)`
3. `removeFromFront(T data)`
4. `removeFromBack(T data)`

## 2.2. The ArrayList
* What is an array list?
    * A list backed by an array. (Still an ADT)
    * Only stores objects, thus in Java need the wrapper class for primitives
    * An abstraction/wrapper for an array
    * Resizing and dynamic allocation is handled by the implementation, not the user.

### 2.2.1. Terminology
* Size - Number of data store in the ArrayList
* Capacity - Maximum number of allowed data to be stored

### 2.2.2. Functionality
* Data must be contiguous, with no null spaces between data (starting from index 0)
* Storing the size is important for efficient operations
    * If we know the size is 3, `addToBack` can insert directly to index 3
* In most implementations, a resize doubles the capacity

### 2.2.3. Time Complextiy
* Rare O(n) Resize Case
    * When we hit capacity, have to copy all data over, an O(n) operation
* Adding at an arbitrary index (not the back) is O(n)
    * Due to the cost of shifting the data each time.
* Removing at the back is O(1) since we don't need to downsize
    * Removing at an arbitrary index is O(n) due to shifting


#### 2.2.3.1. Amortized Analysis of Adding to the Back
* In worst case, adding to the back is O(n)
* This case is rare, and when it does happen we have already done n*O(1) operations.
    * Distributing the cost, we have an amortized cost of O(1) for adding to the back

<br>

# 3. Amortized Analysis
* Amortized cost is really just average cost, where we sum the total cost of all operations and divide by the number of operations. That's how we get average time complexity.
    * Hence, adding to the back is worst case O(n), but average case O(1).
        * Avverage is O(1) since we can only hit the worse case once every n insertions, since we double the capacity.

## 3.1. Soft v. Hard Removals
* Hard Removal - ensure the data you removed is completely removed from the backing structure
    * Naturally, this is more secure than a soft removal
* Soft Removal - When you leave the data in the structure unless it is absolutely necessary to get rid of it.
    * For example, the end of ArrayList is controlled by size, so you don't need to remove elements at the end and can just change the value of `size`