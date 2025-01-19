package Module9_24Trees;

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
        size = root.size();
    }

    /**
     * Create a 2-4 Tree from a level-order sorted list (as a string) of data in the format:
     * "root, left, right, left-left, left-right, right-left, right-right, etc."
     * OR
     * "6|2,3|8|..."
     * 
     * Also, this is lazy and doesn't check that the data is actually valid
     * 
     * @param data A string of the tree in level-order format
     */
    public TwoFourTree(LinkedList<List<T>> levelOrderData) {
        root = new TwoFourNode<>(levelOrderData.removeFirst());
        size = root.size();

        Queue<TwoFourNode<T>> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        while (!levelOrderData.isEmpty()) {
            TwoFourNode<T> curr = nodeQueue.poll();
            for (int i = 0; i <= curr.size(); i++) {
                curr.setNode(i, new TwoFourNode<>(levelOrderData.removeFirst()));
                nodeQueue.add(curr.getNode(i));
                size += curr.getNode(i).size();
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
        assertDataNotNull(data);
        if (root == null) {
            root = new TwoFourNode<>(data);
        } else {
            root = addImpl(data, root);
            root = promote(null, root);
        }
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
        return found != null ? found.compareTo(data) == 0 : false;
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
     * Promote data from the sub-node to the current node if needed.
     * Lazily assumes input subNode is not null
     * 
     * @param curr The current node which data will be promoted to
     * @param subNode The sub-node that data will be promoted from
     * @return The current node, which is a new node if curr is null
     */
    public TwoFourNode<T> promote(TwoFourNode<T> curr, TwoFourNode<T> subNode) {
        if (!subNode.overflowed()) {
            // Curr is null for the root, in that case return subNode
            return curr != null ? curr : subNode;
        }

        // Handle the overflow
        T dataToPromote = subNode.removeAt(1);
        
        int addAt = 0;
        if (curr != null) {
            addAt = curr.addData(dataToPromote);
        } else {
            curr = new TwoFourNode<>(dataToPromote);
        }

        List<TwoFourNode<T>> newSubNodes = splitNode(subNode, 1);
        curr.setNode(addAt, newSubNodes.get(0));
        curr.setNode(addAt + 1, newSubNodes.get(1));

        return curr;
    }

    /** 
     * Remove the given data from the tree
     * 
     * @param data The data to remove
     * @throws java.lang.IllegalArgumentException If the data is null
     * @throws java.util.NoSuchElementException If the data is not in the tree
     */
    public T remove(T data) {
        assertDataNotNull(data);
        TwoFourNode<T> dummy = new TwoFourNode<>();
        root = removeImpl(data, root, dummy);
        size--;

        if (!root.underflowed()) {
            return data;
        }

        if (root.numSubNodes() <= 1) {
            root = root.getNode(0);
        }

        return data;
    }

    /**
     * Return the size of the tree, by number of elements in the tree
     * 
     * @return The size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Split the node
     * 
     * @param node The node to split
     * @param idx The index to split at
     * @return The new nodes
     * @throws java.lang.IllegalArgumentException If the index is not 1 or 2, or the node is null or overflowed
     */
    public List<TwoFourNode<T>> splitNode(TwoFourNode<T> node, int idx) {
        if (idx != 1 && idx != 2) {
            throw new IllegalArgumentException("Error: Invalid split index " + idx + ". It must be 1 or 2 (left or right middle).");
        } else if (node == null) {
            throw new IllegalArgumentException("Error: Node to split is null.");
        } else if (node.overflowed()) {
            throw new IllegalArgumentException("Error: Node to split is overflowed, fix the overflow first.");
        }
        TwoFourNode<T> first = new TwoFourNode<>(node.getData().subList(0, idx));
        for (int i = 0; i <= idx; i++) {
            first.setNode(i, node.getNode(i));
        }
        TwoFourNode<T> second = new TwoFourNode<>(node.getData().subList(idx, node.size()));
        for (int i = idx + 1; i <= node.size() + 1; i++) {
            second.setNode(i - idx - 1, node.getNode(i));
        }
        return Arrays.asList(first, second);
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
        // Base case: leaf node, node handles sorting and duplicates
        if (curr.numSubNodes() == 0) {
            curr.addData(data);
            return curr;
        }
        // Recursive case: find the correct sub-node to add the data
        int idxToCheck = curr.size();
        for (int i = 0; i < idxToCheck; i++) {
            int res = data.compareTo(curr.getDataAtIdx(i));
            if (res == 0) {
                throw new IllegalArgumentException("Error: " + data + " is already in the tree, no duplicates allowed");
            } else if (res < 0) {
                idxToCheck = i;
                break;
            }
        }

        curr.setNode(idxToCheck, addImpl(data, curr.getNode(idxToCheck)));
        return promote(curr, curr.getNode(idxToCheck));
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
                return findImpl(data, curr.getNode(i));
            }
        }

        return findImpl(data, curr.getNode(curr.size()));
    }

    /**
     * Perform a level order traversal of the 2-4 Tree starting at a given node
     * 
     * @param searchRoot The root of the tree OR sub-tree
     * @return List containing the level-order traversal of the tree
     */
    private LinkedList<List<T>> levelorderImpl(TwoFourNode<T> searchRoot) {
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

    /**
     * Remove implementation
     */
    private TwoFourNode<T> removeImpl(T data, TwoFourNode<T> curr, TwoFourNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The following data was not found in the tree: " + data);
        }

        int idxToCheck = curr.size();
        for (int i = 0; i < idxToCheck; i++) {
            int res = data.compareTo(curr.getDataAtIdx(i));
            if (res == 0) {
                // Found it!
                idxToCheck = -1;
                break;
            } else if (res < 0) {
                idxToCheck = i;
                break;
            }
        }

        if (idxToCheck != -1) {
            curr.setNode(idxToCheck, removeImpl(data, curr.getNode(idxToCheck), dummy));
        } else {
            int dataIdx = curr.getData().indexOf(data);
            dummy.addData(curr.remove(data));
            if (curr.numSubNodes() != 0) {
                TwoFourNode<T> dummy2 = new TwoFourNode<>();
                curr.setNode(dataIdx, removePredecessor(curr.getNode(dataIdx), dummy2));
                curr.addData(dummy2.getDataAtIdx(0));
            }
            return curr;
        }

        // Leaf with multiple data, return the node
        TwoFourNode<T> subNode = curr.getNode(idxToCheck);
        if (subNode.numSubNodes() == 0 && !subNode.underflowed()) {
            return curr;
        }
        return transferOrFuse(curr, idxToCheck);
    }

    private TwoFourNode<T> removePredecessor(TwoFourNode<T> curr, TwoFourNode<T> dummy) {
        // Need to handle underflow with this
        if (curr.numSubNodes() == 0) {
            T removed = curr.removeAt(curr.size() - 1);
            dummy.addData(removed);
            return curr;
        } else {
            curr.setNode(curr.size(), removePredecessor(curr.getNode(curr.size()), dummy));
        }

        return transferOrFuse(curr, curr.size());
    }

    private TwoFourNode<T> transferOrFuse(TwoFourNode<T> curr, int idx) {
        if (!curr.getNode(idx).underflowed()){
            return curr;
        }

        if (idx != 0 && curr.getNode(idx - 1).size() > 1) {
            transferLeftToRight(curr, idx);
        } else if (idx != curr.size() && curr.getNode(idx + 1).size() > 1) {
            transferRightToLeft(curr, idx);
        } else if (idx != 0 && curr.getNode(idx - 1).size() == 1) {
            fuseWithLeft(curr, idx);
        } else if (idx != curr.size() && curr.getNode(idx + 1).size() == 1) {
            fuseWithRight(curr, idx);
        }

        return curr;
    }

    private void fuseWithLeft(TwoFourNode<T> curr, int idx) {
        int lIdx = idx - 1;
        curr.getNode(lIdx).addData(curr.removeAt(lIdx));
        curr.removeNode(idx);
    }

    private void fuseWithRight(TwoFourNode<T> curr, int idx) {
        int rIdx = idx + 1;
        curr.getNode(rIdx).addData(curr.removeAt(idx));
        curr.removeNode(idx);
    }

    private void transferRightToLeft(TwoFourNode<T> curr, int idx) {
        int rIdx = idx + 1;
        curr.getNode(idx).addData(curr.removeAt(idx));
        curr.addData(curr.getNode(rIdx).removeAt(idx));
    }

    private void transferLeftToRight(TwoFourNode<T> curr, int idx) {
        int lIdx = idx - 1;
        curr.getNode(idx).addData(curr.removeAt(lIdx));
        curr.addData(curr.getNode(lIdx).removeAt(curr.getNode(lIdx).size() - 1));
    }
}










