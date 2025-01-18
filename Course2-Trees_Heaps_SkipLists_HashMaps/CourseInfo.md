### <p style="font-size:40px"> Trees, Heaps, SkipLists and HashMaps </p>

**[Trees](#trees)**
* [Classification](#classification)
* [Binary Trees](#binary-trees)
    * [Definition of a Binary Search Tree](#definition-of-a-binary-search-tree)
    * [Implementation and Efficiency of BSTs](#implementatiopn-and-efficiency-of-bsts)
    * [Traversals](#traversals)
    * [BST Operations](#bst-operations)
        * [Searching a BST](#searching-a-bst)
        * [Best and Average Case Complexity Analysis](#best-and-average-case-complexity-analysis)
        * [Adding to a BST](#adding-to-a-bst)
        * [Removing from a BST](#removing-from-a-bst)
* [Tree Traversals](#tree-traversals)
    * [Depth (Recursive) Traversals](#depth-recursive-traversals)
        * [Preorder](#preorder)
        * [Postorder](#postorder)
        * [Inorder](#inorder)
    * [Breadth (Iterative) Traversals](#breadth-iterative-traversals)
    * [Choosing a Traversal](#choosing-a-traversal)

* [SkipLists](#skiplists)
    * [Structure](#structure)
    * [Use of Probability](#use-of-probability)
    * [Searching Algorithm](#searcing-algorithm)
    * [Efficiency](#efficiency)
    * [Purpose of SkipLists](#purpose-of-skiplists)

**[Heaps](#heaps)**
* [Heap Operations](#heap-operations)
* [The BuildHeap Algorithm](#the-buildheap-algorithm)

**[HashMaps](#hashmaps)**
* [Introduction](#introduction)
* [Collision Handling](#collision-handling)
    * [External Chaining](#external-chaining)
    * [Probing Strategies](#probing-strategies)
    * [Double Hashing](#double-hashing)

# Trees
A linked data structure with a parent-child relationship (LinkedLists are very simple trees)
* Internal Node -> Node that has children
* Leaf nodes -> No children

**Sub-Trees**: Any child-node, can have children or no children or be null
<br>**Depth**: How many steps away from the root a node is
<br>**Height**: Distance from furthest leaf node (always 0 for leaf nodes)
<br>**Trees**: Connected linked structures with no cycles (Singly-linked list is. circularly-linked list is not)
* Can be implemented in multiple way
* Can furhter be defined by shape (structure of the nodes) and order (arrangement of the data)

## Classification
1. Binary Trees
    * Heaps
    * Binary Search Trees
        * AVLs
        * Splay Trees
2. 2-4 Trees

## Binary Trees
Defined by three properties:
1. Every node can have at least two children
2. Child nodes are labeled left and right child
3. Left child always precedes the right order in the order of the node
<br>^ This is the shape of the tree, there is no order for the data in a node

Potential Node Information:
1. Parent reference
2. Height
3. Depth

Shape Properties of Binary Trees
1. **Full** if every node except for the leaf has two children
2. **Complete** if all the levels are completely filled except for the bottom-most level (nodes can have 0, 1 or 2 children, but the levels of the tree must be filled top-down, left-right. All nodes must be as far left as possible)
3. **Degenerate** if all nodes only have one child (except for the leaf)
    * The worst case binary tree, since it is essentially a linked list

Node References
1. References to child nodes
2. Can reference an actual node or child node

Iterating Through a Binary Tree
* Traverse until the node is `null`

### Definition of a Binary Search Tree
1. Inherit all properties of binary trees
2. Enforce a data order property where the left child data is less than the right child data
3. All of the data in a node's left sub tree must be less than the data in the node. All data in the right sub-tree must be greater than the node

### Implementatiopn and Efficiency of BSTs
* In Java, need to implement `Comparable` to compare data efficienty
* Motivation: **Binary Search**
    * Each comparison tells you if data is to the left or right, splitting the search space in half per comparison
        * Average O(log n) runtimes
* Performance depends on the shape of the tree
    * Runtime complexity decays to O(n) with a degenerate tree

### Traversals
Since it is not a linear-data structure, we need traversal algorithms
* **Depth Traversals**(Stack-based) -> Follows one path or branch until it hits a null child
    * Preorder Traversal
    * Inorder Traversal
    * Postorder Traversal
* **Breadth Traversals**(Queue-based) -> Goes one level at a time (looks all all children of all nodes at a depth)
    * Levelorder Traversal

### BST Operations
#### Searching a BST
Drawback to linear data structures is the O(n) search time. BSTs aim to optimize searching using binary search, producing an `O(log n)` search time.
<br>-> Decays to `O(n)` since that is a linear data structure. `O(1)` when in the root.

#### Best and Average Case Complexity Analysis
**Best Case Analysis**
<br> The most favorable configuration, often a trivial `O(1)`

**Average Case Analysis**
<br> The probabilisitc *expected performance*, not the amortized cost. Given all possible trees normally distributed, it is the expected performance.

**Logarithmic Time: How good is it?**
<br>`O(log n)` is much closer to `O(1)` then it is to `O(n)`

#### Adding to a BST
* Recursive cases:
    1. `data < node.data`
    2. `data > node.data`
* Base cases:
    1. `data == node.data` -> do not add since no duplicates in the course
    2. `node == null` -> data does not exist, add

**Pointer Reinforcement**: For each call on a current node, use return to help restructure the tree -> Return the node that should be in the direction of traversal. If traversing left, return what should be to the left. To set the changes in place, set the left/right pointer to what is returned.
* The responsibility of restructuring is moved from the child's recursive call to the parent.
* There is a constrasting method called **Look-ahead** where you keep track of parent instead of the current node. Instead, you check if the child is null before traversing in that direction. Becomes less effective for more complex operations than `add`
```
public void add(int data):
    root = rAdd(root, data);

private Node rAdd(Node curr, int data):
    if curr == null:
        size++;
        return new Node(data);
    else if data < curr.data:
        curr.left = rAdd(curr.left, data);
    else
        curr.right = rAdd(curr.right, data);
    // This return statement is "reinforcing" what is already there, with the only change being at the leaf. In a sense this is less efficient than look-ahead, but becomes more useful for more complicated operations.
    return curr
```

Worst-case analysis of adding *n* elements to an empty BST in arbitrary order: It is `O(n^2)` since we iterate over the entire length n times.

#### Removing from a BST
One of the hardest operations to implement for a BST.
* Recursive cases:
    1. `data < node.data`
    2. `data > node.data`
* Base cases:
    1. `data == node.data` -> data found remove it
    2. `node == null` -> data does not exist,  throw an exception
* Three removal types:
    1. No children: Parent's pointer set to null
    2. Node has one child: Point parent's pointer at child
    3. Node has two children: Remove the data, but keep the node intact
        * Predecessor removal: Traverse to left once, then to right until you heat a no right child. Replace node data with found data, remove found node
        * Success removal: Traverse to right once, then to left until you hit a no left child. Replace node data with found data, remove found node
Removal also uses pointer reinforcement.

```
public int remove(int data):
    Node dummy = new Node(-1)
    root = rRemove(root, data, dummy)
    return dummy.data

private Node rRemove(Node curr, int data, Node dummy):
    if curr == null:
        // Throw
    else if data < curr.data:
        curr.left = rRemove(curr.left, data, dummy)
    else if data > curr.data:
        curr.right = rRemove(curr.right, data, dummy)
    else:
        // Data found case
        dummy.data = curr.data
        size--
        if 0 children:
            return null
        else if left child !null:
            // Return child to parent
            return left child
        else if right child !null:
            return right child
        else:
            Node dummy2 = new Node(-1)
            curr.right = removeSuccessor(curr.right, dumm2)
            curr.data = dummy2.data
    return curr

private Node removeSuccessor(Node curr, Node dummy):
    if curr.left == null:
        dummy.data = curr.data
        return curr.right
    else:
        curr.left = removeSuccessor(curr.left, dummy)
```

Why is pointer reinforcement superior to look-ahead for removal?
<br>Using look-ahead you need to change what the root points to if the root had zero or one children, otherwise you need to alter the data. Further down in the tree this distinction doe snot exist. With pointer reinforcement there is no special treatment.

Look-ahead also requires null-checking ops as a function of required number of look-aheads

## Tree Traversals
### Depth (Recursive) Traversals
There are three approaches for recursive traversals. They all go down to a null node, but differ in the order in which they perform operations.
#### Preorder
Recursive method that takes in a node, and perhaps a structure to hold data
```
preorder(Node node):
    if node is not null:
        look at data in node
        recurse left
        recurse right
```
Useful for copying a BST, as preorder traversal defines the structure of the BST with the order data is presented (from root, through inner nodes to leaf, from left to right)

#### Postorder
Uniquely identifies the BST (must be a BST, not a Binary Tree)
```
postorder(Node node):
    if node is not null:
        recurse left
        recurse right
        look at data in the node
```
Useful for removing data. Data is presented in order by leaves (from left to right)

#### Inorder
Unique for BSTs isnce it yeilds the data in sorted order (defines the data order, all others define shape)
```
inorder(Node node):
    if node is not null:
        recurse left
        look at data in the node
        recurse right
```
Given a BST, the left nodes are the lowest, so going to the left and coming back to center yields data in ascending order

### Breadth (Iterative) Traversals
Unlike depth traversals that utilize a stack, the `levelorder` traversal utilizes a queue to access data in an order sorted by closeness to the root (left node being closer than the right)
```
levelorder():
    Create Queue q
    Add root to q
    While q is not empty:
        Node curr = q.dequeue()
        if curr is not null:
            q.enqueue(curr.left)
            q.enqueue(curr.right)
```
Starting from the root, the algorithm adds the root's children to the queue. Then it dequeues each child and adds their children to the queue from left to right. This lets it traverse each level from left to right

### Choosing a Traversal
The procedures for these traversals are dfined for Binary Trees. However, there properties are specific to BSTs.

What makes each traversal unique?
* **Preorder**: Uniquely identifies a BST, when adding data to an empty tree in the order given by the traversal. It is a hybrid depth approach that biases towards giving data closer to the root faster than leaf nodes
* **Inorder**: Returns BST data in sorted order. Does *not* uniquely identify the BST, it simply returns data in a left-to-right order (given a properly spaced out tree)
* **Postorder**: Like preorder, uniquely identifies the BST, and removes leaf nodes first. Opposite hybird depthj approach to preorder, in that it biases to giving leaf nodes faster than nodes close to the root.
* **Levelorder**: Gives data in sorted order based on the depth, thus is fully realized preorder in that it gives full control of returning internal nodes before leaf nodes. Uniquely identifies a BST

## SkipLists
A probability based data structure. 

### Structure
* Levels of LinkedLists
* Each level has a -inf head node, and +inf tail node
* Each level contains some subset of data from the level below
* The lowest level contains all the data

### Use of Probability:
* Number of times a node is duplicated depends on coin flips (heads promotes, tails terminates)
* Probability of raching level `i` is $(1/2)^i$
* Each level on averages halves the amount of data
    * Most implementations cap at log(n) levels in-case there is a high-run of promotions

### Searcing Algorithm
(gives the name, goal is to skip a lot of data)
* Begin at head, the top left infinity node
* Look at the right node if it exists and compare:
    * if data > right, go to right
    * if data < right, go down
    * if data == right, data found
* If at bottom level, data isn't in SkipList

### Efficiency
* Dicated by the distribution of the data.
* Average and best case is always `O(log n)`, the worst case is `O(n)`
* Space best and average is `O(n)` since sum of all halvings is 2n, worst is `O(n log n)` when all data is promoted all the way with a cap at log n levels

### Purpose of SkipLists
1. In concurrent operations, if there is a remove and search/add at the same  time, there won't be incorrect ops assuming pointers are updated in the correct order
2. They are simple to implement as they don't rely on complex balancing operations
3. A nice introduction to randomization in computing

# Heaps
Binary heaps are the most common type of heaps. They are a binary tree type of data structure, with the additional shape property of **completeness**. Completeness is a property that lends itself to an array-backed implementation, which tends to be faster at a low-level compared to trees. The order properrty is also different from BSTs.

A heap is a binary tree, but NOT a BST. Complete means filled left to right with no gaps.

Types of heap:
* **Min Heap**: Smallest element in the dataset lives at the root, no direct relationship between siblings or children
    * All data in the children are greater than the parent
* **Max Heap**: Stores largest element in the root, inverse of min

Heaps omit index 0 when implementing with a backing array, placed into array based on level order.
* Given data at index left, left child at `2*n` and right child at `2*n+1`, parent at `n/2`
* `size` gives the last element

Uses Cases:
* Designed for accessing the root
* Not designed for arbitrary searching
* Often used to back priority queues, as largest or smallest data is always at the root
    * `O(1)` to get highest priority data

## Heap Operations
Only operations to consider are `add` or `remove` since not designed for arbitrary search and access.

### Add
* Add to next spot in array to mantain completeness
* Up-heap/heapify starting from the new data to fix order property
    * Compare with parent data
        * Swap data if necessary
    * Continue until top of heap is reached or no swap is necessary
* Overall time complexity is `O(log n)` because of up-heaping, adding to the end is `O(1)`
* Resize is the worst cause, resulting in `O(n)`
* Worst case to <u>build</u> a heap is `O(n log n)` since we need to add `n` times (more complex than that, but approximates out)

### Remove
* Remove from the root and downheap
* Move the last element of the heap to replace the root
* Delete the last element
* Down-Heap starting from root to fix the order
    * If two children, compare data with higher priority child
    * If one child (must be left), compare data with child
    * Swap as necessary
    * Continue down the heap until no swap is made or a leaf is reached.
* Overall time compleixty is `O(log n)`, due to down-heaping. Removal itself is `O(1)`
* Worst case is the average at `O(log n)` to fix the heap

### Other Operations
* `union`: Fuse two heaps together into one larger heap of the same type
* `increasePriority`: Boost a given item's priority level. Small caveat is it may be hard to find this item

Binary heaps aren't suited to these operations, so other heaps like **binomial heaps** and **fibonacci heaps** are used, which maintain forests to store the data. They aren't often used in practice due to increase complexity and overhead.

## The BuildHeap Algorithm
An algorithm for building a heap in linear time instead of `O(n log n)`
* **Input**: Unsorted, unordered array
* **Problem**: Construct a heap from input
* **Naive Solution**: Add each element, resulting in `n` adds for `O(n log n)` complexity
* **Build Heap**:
    * Shape property - put the data in as presented
    * Order property 
        * Look at sub-heaps
        * Down heap through valid sub-heaps
            * From bottom to top down heap

**The Time Complexity**<br>
* Down-Heap: `O(log n)`
* Number of Down Heaps: `n / 2`
-> Looks like it would be `O(n log n)` but it is not
* Some intuition:
    * Down heap is really `O(1)` at the bottom, and increases linearly up the tree to `O(log n)`. Thus, in total it is `O(n)` since most of the data is towards the bottom.

Worst AND Best Case are `O(n)`, making it a tight algorithm

# HashMaps
## Introduction
**What are maps?** Collection of searchable, unsorted K-V pairs.
* Keys must be unique so no two entires have the same key.
    * Keys are immutable so we don't change how we search for the key
* Methods:
    * `V put(<K, V>)` -> Adds entries, or replaces existing entry and returns old value (if it exists)
    * `V get(K)`
    * `V remove(K)`
    * `int size()`
* Backing Structure
    * Array of map entries for `O(1)` access
        * Backing length often prime numbers to avoid collision during compression
* Hash function
    * Represent our key object as an integer (hashcode)
    * Map hashcode to an integer value
    * To fit hashes into smaller backing array, we mod the hash code to get an index (this is a **compression function**)
        * A collision is when two hashcodes have the same compressed index
* Avoiding Collisions
    * Resize to a larger table periodically
    * Resize when maximum **load factor** is hit
        * Load factor typically 60-70%, computed by size/capacity
    * Use prime numbers for the capacity -> minimizes collisions when data exhibits some particular patterns

**What is a set?** Basically, a map with only keys.

* Most ineffeciencies in a hasp map come from collisions, but picking a good hash function goes a long way to reducing the need to resolve collisions. 
Also of importance is picking a good compression function, table size, and load factor.


## Collision Handling
### External Chaining
An example of **Closed Addressing**: A policy where all keys that collide into the same location are stored at the location by some means.

* Backing Structure
    * An array of LinkedLists -> each index stores multiple entries
    * For the most part, duplicate entries are added at the front of the LinkedLists
* Resizing
    * When using external chaining, we don't run out of space
        * However, long chains increase runtimes
    * For each index in the old backing array
        * For each entry at that index
            * Calculate where to put entry in the resized array

Time Complexity:
* Worst case resize is `O(n)`
* Worst case search/removal is `O(n)` if all in one chain and at the back
* Duplicate add worst case is `O(n)`, non-duplicate is `O(1)`
* Average removal case is `O(1)` as for a good implentation the expectation is `O(1)`

### Probing Strategies
Examples of **Open Addressing**: A policy where additional, colliding keys can be stored at a different location based on the original location.

#### Linear Probing
* Backing Structure
    * Array -> Each index only stores one entry
* **Linear Probing**: Simplest open addressing strategy. If collision occurs at a given index, increment the index by one and check again until
you find a null node or have probed `N` times.

#### Soft Removal
Removing normally from an array does not work for a HashMap, so we perform a **soft removal**. In a soft removal, we leave the entry but mark it with
a flag to indicate it will be removed, called **DEL Markers** or **Tombstones**. They entries to assit HashMap operations, but are invisibile to the user.

To remove, we probe at the index. If the keys are equal, we have found the K-V pair and can remove. Otherwise we keep moving.

* Probing Scenarios:
    1. Valid (not null or deleted) and unequal key
    2. Valid and equal key
    3. Deleted
        * With a `put`, assuming no duplicate key, we'll come back to the first deleted entry if found before a null
    4. Null
* Resizing Backing Array
    * When surpasses the threshold
    * Loop through and rehash into new backing array
    * Skip deleted markers
* Efficiency of DEL Markers
    * Since they keep the array full, even if we remove all elements you can still have `O(n)` ops by looping over all the DEL Markers. 
        * This can be mitigated by including them in load factor calculations or triggering an intentional resize

Time Complexity:
* Worst case resize is $O(n^2)$ if everything rehashes to the same index.
* Worst case search is `O(n)`

#### Quadratic Probing
* If a collision occurs, add $h^2$ to the original index and check again.
    * Goal is to mitigate **primary clustering/turtling**, when there are contiguous indices occupied with keys or DEL markers than need to be processed.

* Complexities of Quadratic Probing
    * Infinite probing can be caused by a cyclic pattern in the data where we keep probing occupied indices, so we stop probing after `backingArray.length` probes.
        * Solution 1: Resize until a spot is found
            * This could have to occur multiple times before finding a spot.
        * Solution 2: Impose a set of conditions on the table to ensure this never happens
            * Some hasing schemes can do this. One example is a prime number table length with a max load factor of 0.5
    * While it solves primary clustering, it suffers from **secondary clustering**, where keys belonging to different indices collide in quadratic rather than linear steps.

### Double Hashing
A probing strategy that combines linear and quadratic probing.
* Breaks up clusters created by linear probing. For example:
* `index = (c * h + origIndex) % backingArray.length`
* `h = number of times we've probed`
* `c = result of second has function (linear when c = 1)`

* **First hash**: H(k), used to calc origIndex
* **Second Hash**: D(k), used to calc probing constrasting. For example:
* `D(k) = q - H(k) % q`
    * `q = some prime number < n`

If original index from hash function is occupied, index using the second hash.

Notes:
* Delete flags are still necessary for removals
* Infinite probing can still happen

## Collision Wrap-up
Chaining strategies were presented in order of popularity.

The primary reason to not use *external chaining* is **locality of reference**, which allows open addressing to be sped up by faster
access of adjacent memory.

Drawbacks to quadratic probing and double hashing:
1. Their implementations are complex, leading to hard use in practice.
2. The primary goal is to avoid collisions, **not** figuring out how to fix them.
3. Due to using modulo and skipping around, the speed up for them is not as significant with linear probing.
4. Configuration of the table can be constrained to avoid infinite probing.