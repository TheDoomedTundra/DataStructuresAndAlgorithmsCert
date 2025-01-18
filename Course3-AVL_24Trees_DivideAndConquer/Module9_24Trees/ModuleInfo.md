# (2, 4) Trees
* A generalization of BSTs that allow multiple data to be stored in sorted order in a node, anywhere from 1 to 3 data.
* This is also a special case of **multiway search trees**, which allow multiple data to be stored in a node in general.
    * A node with $m$ data can have $m+1$ children. This leads to the name, since every node can have 2 to 4 children

* Type of balanced tree called a B-tree. 
    * B in B-Trees denotes the number of element sin a node or block
    * Maintains `O(log n)` operations
* Node Structure
    * 2 to 4 children
    * 1 to 3 data values
    * A node with m data values has m+1 children

## Node, Order and Shape Properties
* Node Property
    * $d_n - n^{th} \ data$
    * $c_n - n^{th} \ data \ in \ child$
    * A node with n children is called an **n-node**
* Order Property
    * All data is stored in ascending order. This extends to children, such that all data in the first child subtree is less than the 
    first data, etc.
    * $c_1 < d_1 < c_2 < d_2 < ... < c_n < d_n$
* Shape Property
    * All leaves must have the same depth. This shape scheme is what guarantees `O(log n)` operations.

**(2, 4) Trees prioritize properties to maintain this strictness** as follows:
1. Shape Property
2. Order Property
3. Node Property

With an AVL tree, perfect balancing can only occur for a certain $n$ of data. Allowing up to 3 data is a nice compromise to perfectly 
balance the tree **from a height perspective** efficiently.

## Note on B-Trees
The more data we have per node, the more we get to a sorted-list like structure, so what is the use? 

B-Trees can be useful for reading from SSDs, by reading an entire block of memory at a time, and organizing the nodes into blocks of 
memory. This allows us to look for data in the block, and not have to query different memory locations each time.

## Operations
### Searching in a (2, 4) Tree
Similar to a BST, but not a simple comparison with one value. Instead we add a linear search within a node until we find a value, or 
know which child to continue to.

### Adding and Overflow
Unlike BSTs, (2, 4) add operation palces data within the leaf node, rather than adding a new child to the leaf. When the add **overflows**
the node property, then a **promotion/split** occurs to resolve the violation. This promotes data upward until a valid tree is formed.

The nodes have **flexibility**, which is what allows us to add data that violates the node property. When a promotion occurs, one of two
things happens:
1. The middle data is pushed up to the parent
2. There is no parent, and a new root is created.

If after promotion, the order is violated, the leaf node where we added data is split to regain the order.

If after promotion, there is overflow in the parent, we :
1. split the leaf node, resulting in a parent with four data and five grandchildren.
2. A second promotion occurs, and leaves are distributed as appropriate

#### Aside: Splay Trees
A type of BST that uses rotations to keep popular data near the root of the BST (with rotations called **splaying**). That is, when data
in the BST is queried, we rotate it to the root. This results in often-queried data having faster look-up times, as infrequently-queried
data propagates down to the bottom of the tree.

Since this works for non-random data, the worst case remains `O(n)`, however, it has an amortized worst case of `O(log n)`.

### Removing and Underflow
This inverse of adding, we get underflow. This is remedied by either **transfer** or **fusion**. Transfer is easier and is always preferred
when possible.

#### Cases:
1. Removing when there is multiple data in a leaf node 
    * Remove data
2. Removing when there is multiple data in an internal node
    * Replace with either predecessor or successor
        * If there is underflow in a leaf node, the immediate siblings are checked for data to transfer (has multiple data)
            * If transfer is possible, send the parent data down to the underflow, and move data from leaf up to parent
3. Removing from leaf with a single data value
    * Perform a fusion with siblings.
        * Pull data down from the parent to the underflowed leaf
        * Fuse two siblings together
    * **Warning**: This can potentially flow the underflow up the tree to the root if there is not enough data to reconcile the structure.
        * This results in removing the root
4. Propagation of underflow with multiple fusions, the result of the **warning**.
    * When we get to a child of the root, we pull the root data down to the underflowed node, and fuse it with a sibling.

#### Aside: Red Black Trees
A BST that keeps track of the **color** of the node (red or black). All leaves of the tree are **sentinel/phantom nodes**,
which are nodes that are *null*.

The properties:
1. Each node must have a color, red or black
2. All leaf nodes (sentinels) are black nodes
3. If a node is red, then both of its children are black
4. Every path from a node to one of its descendent leaves must have the same number of black nodes

Since there is nothing about it being balanced, **Red Black trees derive logarithmic height directly from (2, 4) trees**.
For each (2, 4) tree there are multiple equivalent Red Black trees, and every Red Black tree has an equivalent (2, 4)
tree. Roughly, each black node and its red children form a node of a (2, 4) tree, guaranteeing a max height of `2log(n)`.