package Module9_24Trees;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TwoFourTree<T extends Comparable<? super T>> {

    private TwoFourNode<T> root;
    private int size;

    /**
     * Create an empty 2-4 Tree
     */
    public TwoFourTree() {
        root = null;
        size = 0;
    }

    /**
     * Create a 2-4 Tree with the given data for one node
     * 
     * @param data The data for the root node
     * @throws java.lang.IllegalArgumentException If the data is too large for one node
     */
    public TwoFourTree(Collection<? extends T> c) {
        root = new TwoFourNode<>(c);
        size = 1;
    }

    /**
     * Create a 2-4 Tree from a level-order sorted list (as a string) of data in the format:
     * "root, left, right, left-left, left-right, right-left, right-right, etc."
     * OR
     * "6|2,3|8|..."
     * @param data A string of the tree in level-order format
     */
    public TwoFourTree(LinkedList<List<T>> levelOrderData) {
        root = new TwoFourNode<>(levelOrderData.removeFirst());
        size++;

        Queue<TwoFourNode<T>> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        while (!levelOrderData.isEmpty()) {
            TwoFourNode<T> curr = nodeQueue.poll();
            for (int i = 0; i <= curr.size(); i++) {
                curr.setNode(i, new TwoFourNode<>(levelOrderData.removeFirst()));
                nodeQueue.add(curr.getSubNodeAt(i));
            }
        }
    }

    /**
     * Add the data to the tree
     * 
     * @param data The data to add
     * @throws java.lang.IllegalArgumentException If the data is null or duplicate
     */
    public void add(T data) {
        root = addImpl(data, root);
        size++;
    }

    /**
     * Check if the tree contains the given data
     * 
     * @param data The data to search for
     * @return True if the data is in the tree
     */
    public boolean contains(T data) {
        T found = find(data);
        return found == null;
    }

    /**
     * Traverse the tree to find the given data
     * 
     * @param data The data to search for
     * @throws java.lang.IllegalArgumentException If the request data is null
     * @throws java.util.NoSuchElementException If the data is not in the tree
     */
    public T get(T data) {
        T found = find(data);
        if (found == null) {
            throw new NoSuchElementException("The following data was not found in the tree: " + data);
        }
        return found;
    }

    /**
     * Perform a level order traversal of the 2-4 Tree
     * 
     * @param root The root of the tree
     * @return List containing the level-order traversal of the tree
     */
    public LinkedList<List<T>> levelorder() {
        return levelorderImpl(root);
    }
    
    /** 
     * Remove the given data from the tree
     * 
     * @param data The data to remove
     * @throws java.lang.IllegalArgumentException If the data is null
     * @throws java.util.NoSuchElementException If the data is not in the tree
     */
    public T remove(T data) {

    }

    /**
     * Return the size of the tree
     * 
     * @return The size of the tree
     */
    public int size() {
        return size;
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
     * Add the data to the tree
     * 
     * @param data The data to add
     * @throws java.lang.IllegalArgumentException If the data is null or duplicate
     */
    private TwoFourNode<T> addImpl(T data, TwoFourNode<T> curr) {
        if (curr == null) {
            return new TwoFourNode<>(data);
        }
    }

    /**
     * Traverse the tree to find the given data
     * 
     * @param data The data to search for
     * @throws java.lang.IllegalArgumentException If the request data is null
     */
    private T find(T data) {
        assertDataNotNull(data);
        T found = findImpl(data, root);
        return found;
    }

    private T findImpl(T data, TwoFourNode<T> curr) {
        if (curr == null || curr.size() == 0) {
            return null;
        }

        for (int i = 0; i < curr.size(); i++) {
            int res = data.compareTo(curr.getDataAtIdx(i));
            if (res == 0) {
                // Found it
                return data;
            } else if (res < 0) {
                return findImpl(data, curr.getSubNodeAt(i));
            }
        }

        return findImpl(data, curr.getSubNodeAt(curr.size()));
    }

    /**
     * Perform a level order traversal of the 2-4 Tree starting at a given node
     * 
     * @param searchRoot The root of the tree OR sub-tree
     * @return List containing the level-order traversal of the tree
     */
    public LinkedList<List<T>> levelorderImpl(TwoFourNode<T> searchRoot) {
        LinkedList<List<T>> data = new LinkedList<>();
        Queue<TwoFourNode<T>> nodeQueue = new LinkedList<>();

        nodeQueue.add(searchRoot);
        while (!nodeQueue.isEmpty()) {
            TwoFourNode<T> curr = nodeQueue.poll();
            data.addLast(curr.getData());
            for (TwoFourNode<T> aSubNode : curr.getSubNodes()) {
                if (aSubNode != null) {
                    nodeQueue.add(aSubNode);
                }
            }
        }

        return data;
    }

    public static void main(String[] args) {
        LinkedList<List<Integer>> exp = new LinkedList<>(Arrays.asList(Arrays.asList(6), Arrays.asList(2), Arrays.asList(12, 13, 16)));
        TwoFourTree<Integer> tree = new TwoFourTree<>(exp);
        System.out.println(tree.levelorder());
    }
}
