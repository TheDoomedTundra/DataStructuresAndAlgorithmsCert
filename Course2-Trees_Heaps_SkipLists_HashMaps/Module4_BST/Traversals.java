package Module4_BST;

import java.util.List;
import java.util.LinkedList;

/**
 * Your implementation of the pre-order, in-order, and post-order
 * traversals of a tree.
 */
public class Traversals<T extends Comparable<? super T>> {

    /**
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */

    /**
     * Given the root of a binary search tree, generate a
     * pre-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the pre-order traversal of the tree.
     */
    public List<T> preorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> data = new LinkedList<>();
        return preorderImpl(root, data);
    }

    /**
     * Given the root of a binary search tree, generate an
     * in-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the in-order traversal of the tree.
     */
    public List<T> inorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> data = new LinkedList<>();
        return inorderImpl(root, data);
    }

    /**
     * Given the root of a binary search tree, generate a
     * post-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the post-order traversal of the tree.
     */
    public List<T> postorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> data = new LinkedList<>();
        return postorderImpl(root, data);
    }

    private List<T> preorderImpl(TreeNode<T> node, List<T> data) {
        if (node == null) {
            return data;
        }
        data.add(node.getData());
        data = preorderImpl(node.getLeft(), data);
        data = preorderImpl(node.getRight(), data);

        return data;
    }

    private List<T> postorderImpl(TreeNode<T> node, List<T> data) {
        if (node == null) {
            return data;
        }
        data = postorderImpl(node.getLeft(), data);
        data = postorderImpl(node.getRight(), data);
        data.add(node.getData());

        return data;
    }

    private List<T> inorderImpl(TreeNode<T> node, List<T> data) {
        if (node == null) {
            return data;
        }
        data = inorderImpl(node.getLeft(), data);
        data.add(node.getData());
        data = inorderImpl(node.getRight(), data);

        return data;
    }

    public static void main(String[] args)
    {
        TreeNode<Integer> root = new TreeNode<>(5);
        root.setLeft(new TreeNode<Integer>(3));
        root.setRight(new TreeNode<Integer>(8));

        TreeNode<Integer> curr = root.getLeft();
        curr.setLeft(new TreeNode<Integer>(1));
        curr.setRight(new TreeNode<Integer>(4));

        curr = root.getRight();
        curr.setLeft(new TreeNode<Integer>(7));
        curr.setRight(new TreeNode<Integer>(9));

        Traversals<Integer> traverse = new Traversals<>();
        List<Integer> results = traverse.inorder(root);
        System.out.println(results);
    }
}