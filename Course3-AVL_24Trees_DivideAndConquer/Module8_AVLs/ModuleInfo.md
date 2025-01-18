# AVLs (Adelson-Velsky Landis Trees)
Sub-classification of BSTs that inherit all the order and shape properties. They have an 
additional shape proprerty to restrict the tree height.

**AVL**: A self-balacing BST used to eliminate `O(n)` worst case -> becomes `O(log n)`
* Rotations are performed on "unbalanced" nodes
* **Height(node)** $= max(Height(left), Height(right)) + 1$
    * Base case of 0 or null height (-l)
* **BalanceFactor(node)** $= Height(left) - Height(right)$
* AVLs store heights and bfs to make calculations `O(1)` instead of `O(n)`

### Updating a Height/BF in a single AVL node
* Update it in `O(1)` time
    * use the store height of children in update calculation
    * If child is null, use -1 as height
* Importance of update order:
    * Height and BF require correct children heights
    * Whenever a change is made to the tree, the only nodes that need to be updated are nodes on the path from the changed node to the root

### Definition of Unbalance
* Node is unbalanced if $|BF| > 1$
* Node is balanced if $BF = -1, 0, 1$

This is lenient, because it is too difficult to have a perfectly balanced BST. For example, if it is perfectly balanced you cannot insert a node and keep the balance.

If $BF < 0$, right heavy, if $BF > 0$, left heavy

## What is "Balanced" for an AVL Tree?
Due to leniency, a **balanced AVL tree** adheres to $|node.balanceFactor| \leq 1$ for every node.

In addition, this means the height of children differ by AT MOST 1.

The height is at max $1.44\log(n)$

## Rotations
Rotation operations are used to adjust the height of nodes without disrupting the order to balance the tree.

Rotations are left or right. Used when adding or removing if the tree becomes unbalanced.

### What rotation to perform
#### Single Rotations
Used for simple imbalances.
* If the BF of a right child is -1, a single left rotation is all that is required.
```
Node B = A's right child
A's right child = B's left child
B's left child = A
Update height & BF of A
Update height & BF of B
return B
```
* If the BF of the left child os 1 or 0, one single right rotation is required
```
Node B =  C's left child
C's left child = B's right child
B's right child = C
Update height and BF of C
Update height and BF of B
return B
```

#### Removal
Same as the BST, until node is removed. When you remove and move back up to the root, then rebalances are needed for out of balance nodes. 

#### Double Rotations
If a node is side-heavy, and that side's node is the opposite-side heavy, the tree has a bend and requires a double rotation.

For example, node is -2 and right child is 1, it is right heavy then left heavy, and we need a right rotation on the grandchild, and then a left rotation on it. A **Right-Left Rotation**

Conversely, if the node BF is 2 and the left child is -1, we do a left- then right- rotation on the grandchild. A **Left-Right Rotation**

#### Simplest Rotation Cases:
Parent BF 2: (POSITIVE MEANS TALLER LEFT)
* Heavier Child Left, Child BF (0, 1), Rotate right
* Heavier Child Left, Child BF (-1), Left-Right

Parent BF -2: (NEGATIVE MEANS TALLER RIGHT)
* Heavier Child Right, Child BF (-1, 0), Left
* Heavier Child Right, Child BF (1), Right-Left

## Logistics
When we add/remove, we go down to node and then recurse back up to the root. We update the height and BF at each node, but don't require a rotation at every node. Each rotation and update is `O(1)`, and occurs at most `O(log n)`

Adding has **at most one rotation** since one rotation will reset the unbalanced tree to the original height. Any remove operation **can trigger `O(log n)` rotations**.

## Complexity
Since the max height is `O(log n)`, add remove and search are `O(log n)` worst case instead of `O(n)` for a BST.

However, traversal operations that mess with the structure (such as removig all leaves), can be worse due to the need to rebalance.