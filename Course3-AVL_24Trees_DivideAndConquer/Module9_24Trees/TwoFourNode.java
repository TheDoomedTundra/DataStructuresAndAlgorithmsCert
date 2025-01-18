package Module9_24Trees;

import java.util.NoSuchElementException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class TwoFourNode<T extends Comparable<? super T>> {
    
    public static final int MAX_CAPACITY = 3;

    private List<TwoFourNode<T>> children;
    private int height;
    private List<T> nodeData;    

    /**
     * Create an empty TwoFourNode
     */
    public TwoFourNode() {
        this.children = new ArrayList<>(MAX_CAPACITY + 1);
        this.nodeData = new ArrayList<>(MAX_CAPACITY);
    }

    /**
     * Create a TwoFourNode for a given data
     * 
     * @param data The data stored in the node
     */
    public TwoFourNode(T data) {
        this();
        this.addData(data);
    }

    /** 
     * Create a TwoFourNode for a given collection of data. This does not allow for an overflowed node.
     * 
     * @param c The collection to create the node from
     * @throws java.lang.IllegalArgumentException If there is more data in the collection than the node supports
     */
    public TwoFourNode(Collection<? extends T> c) {
        this();
        if (c.size() > MAX_CAPACITY) {
            throw new IllegalArgumentException("Error: Cannot add more data than the max allowed size of " + MAX_CAPACITY);
        }
        this.nodeData.addAll(c);
        enforceOrder();
    }

    /**
     * Add a non-duplicate data to the node
     * 
     * @param data
     * @throws java.lang.IllegalArgumentException If the data is null or a duplicate
     */
    public void addData(T data) {
        if (contains(data)) {
            throw new IllegalArgumentException("Error: " + data + " is already in the node, no duplicates allowed");
        }

        nodeData.add(data);
        enforceOrder();
    }

    /**
     * Check if the node contains the data
     * 
     * @param data
     * @return If the node contains it
     * @throws java.lang.IllegalArgumentException If the data is null
     */
    public boolean contains(T data) {
        assertDataNotNull(data);
        return nodeData.contains(data);
    }

    /**
     * Gets all the data
     * 
     * @return all contained data
     */
    public List<T> getData() {
        return nodeData;
    }

    /**
     * Get data at a specific index
     * 
     * @param idx Index of desired data
     * @throws java.lang.IndexOutOfBoundsException If the index is larger than the capacity
     */
    public T getDataAtIdx(int idx) {
        return nodeData.get(idx);
    }

    /**
     * Gets the height.
     *
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get all sub-nodes of this node
     * 
     * @return All of the child nodes
     */
    public List<TwoFourNode<T>> getSubNodes() {
        return children;
    }

    /**
     * Get sub-node at a specific index. Passes the null checking onto
     * the caller
     * 
     * @return The idx-th child node
     * @throws java.lang.IndexOutOfBoundsException If the index is larger than the number of nodes
     */
    public TwoFourNode<T> getSubNodeAt(int idx) {
        return children.get(idx);
    }

    /**
     * Check if the node is overflowed
     * 
     * @return Whether or not the node is overflowed
     */
    public boolean overflowed() {
        if (nodeData.size() > MAX_CAPACITY) {
            return true;
        }
        return false;
    }

    /**
     * Remove the given data from the node
     *
     * @param data The data to remove 
     * @return The removed data
     * @throws java.lang.IllegalArgumentException If the data is null
     * @throws java.util.NoSuchElementException If the data is not in the node
     */
    public T remove(T data) {
        if (contains(data)) {
            nodeData.remove(data);
        } else {
            throw new NoSuchElementException("The data was not found in the node: " + data);
        }
        return data;
    }

    /** 
     * Remove the data at a given index
     * 
     * @param idx The index to remove data from
     * @return The removed data
     * @throws java.lang.IndexOutOfBoundsException If the index is too large
     */
    public T removeAt(int idx) {
        T removed = getDataAtIdx(idx);
        nodeData.remove(idx);
        return removed;
    }

    /**
     * Sets the height.
     *
     * @param height The new height.
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Set the n-th sub-node
     * 
     * @param idx The index to add the node at
     * @param node The node to add
     * @throws java.lang.IndexOutOfBoundsException If the index is too large
     * 
     */
    public void setNode(int idx, TwoFourNode<T> node) {
        children.add(idx, node);
    }

    /**
     * Get the number of data stored in the node
     *
     * @return The size of the node
     */
    public int size() {
        return nodeData.size();
    }
 
    /**
     * Check if the node is underflowed
     * 
     * @return Whether or not the node is underflowed
     */
    public boolean underflowed() {
        if (nodeData.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Assert if the data is null
     * 
     * @param data
     * @throws java.lang.IllegalArgumentException If the data is null
     */
    private void assertDataNotNull(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error: data is null.");
        }
    }

    /**
     * Enforce the sorted order of the node, called during adds
     */
    private void enforceOrder() {
        Collections.sort(nodeData);
    }    
}
