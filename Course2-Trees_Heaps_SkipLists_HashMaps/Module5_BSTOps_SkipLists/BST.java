package Module5_BSTOps_SkipLists;

import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This should be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to search for. You may assume data is never null.
     * @return The data in the tree equal to the parameter.
     * @throws java.util.NoSuchElementException If the data is not in the tree.
     */
    public T get(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        return getImpl(data, root);
    }

    private T getImpl(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException();
        }

        if (data.compareTo(curr.getData()) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            return getImpl(data, curr.getLeft());
        } else {
            return getImpl(data, curr.getRight());
        }
    }


    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The new data should become a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Should be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to add to the tree.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        assertNotNull(data);
        root = addImpl(root, data);
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the SUCCESSOR to
     * replace the data. You should use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If data is null.
     * @throws java.util.NoSuchElementException   If the data is not in the tree.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        assertNotNull(data);
        // Create tmp (dummy) to keep track of node that gets removed
        BSTNode<T> tmp = new BSTNode<>(data);
        root = removeImpl(root, data, tmp);
        return tmp.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This should be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to search for. You may assume data is never null.
     * @return true if the parameter is contained within the tree, false otherwise.
     */
    public boolean contains(T data) {
        assertNotNull(data);
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        return containsImpl(data, root);        
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    private void assertNotNull(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
    }
    private BSTNode<T> addImpl(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addImpl(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addImpl(curr.getRight(), data));
        }
        return curr;
    }

    private BSTNode<T> removeImpl(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The data does not exist in the BST");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeImpl(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeImpl(curr.getRight(), data, dummy));
        } else {
            // Found it, set dummy data to the data being removed
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                // No children, set the parent's pointer to null
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                // Set the parent's pointer to left child
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                // Set the parent's pointer to right child
                return curr.getRight();
            } else {
                // Set to successor, create second dummy to keep track of successor data
                BSTNode<T> dummy2 = new BSTNode<>(data);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            // This is the successor, update dummy and return the right node
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            // Recurse to the left
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    private boolean containsImpl(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        }
        int result = data.compareTo(curr.getData());
        if (result == 0) {
            return true;
        } else if (result < 0) {
            return containsImpl(data, curr.getLeft());
        } else {
            return containsImpl(data, curr.getRight());
        }
    }
}
