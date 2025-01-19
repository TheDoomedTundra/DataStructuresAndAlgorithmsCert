package Module9_24Trees;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class TwoFourTreeTest {
    @Test
    public void testAdd() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertTrue(tree.contains(10));
    }

    @Test
    public void testAddDuplicate() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertThrows(IllegalArgumentException.class, () -> tree.add(10));
    }

    @Test
    public void testAddNull() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        assertThrows(IllegalArgumentException.class, () -> tree.add(null));
    }

    @Test
    public void testConstructFromNode() {
        ArrayList<Integer> act = new ArrayList<>(Arrays.asList(10, 20, 30));
        LinkedList<List<Integer>> exp = new LinkedList<>(Arrays.asList(act));
        TwoFourTree<Integer> tree = new TwoFourTree<>(act);
        assertEquals(exp, tree.levelorder());
    }

    @Test
    public void testConstructInBulk() {
        LinkedList<List<Integer>> act = new LinkedList<>(Arrays.asList(Arrays.asList(6), Arrays.asList(2), Arrays.asList(12, 13, 16)));

        // Deep copy the input since the constructor will modify the input
        LinkedList<List<Integer>> exp = deepCopyLinkedList(act);

        TwoFourTree<Integer> tree = new TwoFourTree<>(act);
        assertEquals(exp, tree.levelorder());
    }

    @Test
    public void testContainsNotInTree() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertFalse(tree.contains(20));
    }

    @Test
    public void testContainsNull() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        assertThrows(IllegalArgumentException.class, () -> tree.contains(null));
    }

    @Test
    public void testGet() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertEquals(10, (int) tree.get(10));
    }

    @Test
    public void testGetNotInTree() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertThrows(NoSuchElementException.class, () -> tree.get(20));
    }

    @Test
    public void testGetNull() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        assertThrows(IllegalArgumentException.class, () -> tree.get(null));
    }

    @Test
    public void testPromote() {
        TwoFourNode<Integer> top = new TwoFourNode<>(3);
        top.setNode(0, new TwoFourNode<>(Arrays.asList(1, 2)));
        TwoFourNode<Integer> bot = new TwoFourNode<>(Arrays.asList(4, 5, 6));
        bot.addData(7);
        top.setNode(1, bot);
        
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        top = tree.promote(top, bot);
        assertEquals(2, top.size());
        assertEquals(3, top.numSubNodes());
        assertEquals(Arrays.asList(3, 5), top.getData());
        assertEquals(Arrays.asList(1, 2), top.getNode(0).getData());
        assertEquals(Arrays.asList(4), top.getNode(1).getData());
        assertEquals(Arrays.asList(6, 7), top.getNode(2).getData());
    }

    @Test
    public void testPromoteAtRoot() {
        TwoFourNode<Integer> root = new TwoFourNode<>(Arrays.asList(3, 4, 5));
        root.addData(2);

        TwoFourTree<Integer> tree = new TwoFourTree<>();
        root = tree.promote(null, root);
        assertTrue(root != null);
        assertEquals(1, root.size());
        assertEquals(2, root.numSubNodes());

        assertEquals(3, (int) root.getDataAtIdx(0));
        assertEquals(2, (int) root.getNode(0).getDataAtIdx(0));
        assertEquals(Arrays.asList(4, 5), root.getNode(1).getData());
    }

    @Test
    public void testRemove() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertEquals(10, (int) tree.remove(10));
        assertFalse(tree.contains(10));
    }

    @Test
    public void testRemoveFusion() {
        TwoFourTree<Integer> tree = new TwoFourTree<>(Arrays.asList(1, 2, 3));
        tree.add(4);
        tree.remove(1);
        tree.remove(2);

        ArrayList<Integer> exp = new ArrayList<>(Arrays.asList(3, 4));
        assertEquals(exp, linkedToArrayList(tree.levelorder()));
    }

    @Test
    public void testRemoveLeaf() {
        TwoFourTree<Integer> tree = new TwoFourTree<>(Arrays.asList(1, 2, 3));
        tree.add(4);
        tree.remove(3);

        ArrayList<Integer> act = new ArrayList<>(Arrays.asList(2, 1, 4));
        assertEquals(act, linkedToArrayList(tree.levelorder()));
    }

    @Test
    public void testRemovePredecessorAtRoot() {
        LinkedList<List<Integer>> act = new LinkedList<>(Arrays.asList(
            Arrays.asList(3, 5, 8), 
            Arrays.asList(0, 1, 2), 
            Arrays.asList(4), 
            Arrays.asList(6, 7),
            Arrays.asList(9, 11, 12)));

        TwoFourTree<Integer> tree = new TwoFourTree<>(act);
        tree.remove(3);

        List<Integer> exp = new ArrayList<>(Arrays.asList(2,5,8,0,1,4,6,7,9,11,12));
        assertEquals(exp, linkedToArrayList(tree.levelorder()));
    }

    @Test
    public void testRemovePredecessorAtRootWithUnderflow() {
        TwoFourTree<Integer> tree = new TwoFourTree<>(Arrays.asList(1, 3, 4));
        tree.add(5);
        tree.add(2);
        tree.remove(3);

        ArrayList<Integer> act = new ArrayList<>(Arrays.asList(2, 1, 4, 5));
        assertEquals(act, linkedToArrayList(tree.levelorder()));
    }

    @Test
    public void testRemovePredecessorInTree() {
        LinkedList<List<Integer>> act = new LinkedList<>(Arrays.asList(
            Arrays.asList(5), 
            Arrays.asList(2), 
            Arrays.asList(10, 13), 
            Arrays.asList(0, 1),
            Arrays.asList(4),
            Arrays.asList(7, 8, 9),
            Arrays.asList(11),
            Arrays.asList(14, 16)));

        TwoFourTree<Integer> tree = new TwoFourTree<>(act);
        tree.remove(10);

        List<Integer> exp = new ArrayList<>(Arrays.asList(5,2,9,13,0,1,4,7,8,11,14,16));
        assertEquals(exp, linkedToArrayList(tree.levelorder()));
    }

    @Test
    public void testRemoveTransfer() {
        TwoFourTree<Integer> tree = new TwoFourTree<>(Arrays.asList(1, 2, 3));
        tree.add(4);
        tree.remove(1);

        ArrayList<Integer> exp = new ArrayList<>(Arrays.asList(3, 2, 4));
        assertEquals(exp, linkedToArrayList(tree.levelorder()));
    }

    @Test
    public void testRemoveNotInTree() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertThrows(NoSuchElementException.class, () -> tree.remove(20));
    }

    @Test
    public void testRemoveNull() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        assertThrows(IllegalArgumentException.class, () -> tree.remove(null));
    }

    @Test
    public void testSize() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        assertEquals(0, tree.size());
    }

    @Test
    public void testSizeForDataConstructor() {
        TwoFourTree<Integer> tree = new TwoFourTree<>(Arrays.asList(10, 20, 30));
        assertEquals(3, tree.size());
    }

    @Test
    public void testSizeForLevelOrderConstructor() {
        LinkedList<List<Integer>> data = new LinkedList<>(Arrays.asList(Arrays.asList(6), Arrays.asList(2), Arrays.asList(12, 13, 16)));
        TwoFourTree<Integer> tree = new TwoFourTree<>(data);
        assertEquals(5, tree.size());
    }

    @Test
    public void testSizeOnAdd() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        tree.add(20);
        tree.add(30);
        assertEquals(3, tree.size());
    }

    @Test
    public void testSizeOnRemove() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        tree.add(20);
        tree.add(30);
        tree.remove(20);
        assertEquals(2, tree.size());
    }

    @Test
    public void testSplitNode() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(3, 4, 5));

        TwoFourTree<Integer> tree = new TwoFourTree<>();
        List<TwoFourNode<Integer>> nodes = tree.splitNode(node, 1);

        assertEquals(1, nodes.get(0).size());
        assertEquals(2, nodes.get(1).size());

        assertEquals(3, (int) nodes.get(0).getDataAtIdx(0));
        assertEquals(Arrays.asList(4, 5), nodes.get(1).getData());
    }

    @Test
    public void testSplitNodeMultiple() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(3, 8, 10));
        node.setNode(0, new TwoFourNode<>(Arrays.asList(0, 1, 2)));
        node.setNode(1, new TwoFourNode<>(Arrays.asList(4)));
        node.setNode(2, new TwoFourNode<>(Arrays.asList(6, 7)));
        node.setNode(3, new TwoFourNode<>(9));
        node.setNode(4, new TwoFourNode<>(Arrays.asList(11, 12)));

        TwoFourTree<Integer> tree = new TwoFourTree<>();
        List<TwoFourNode<Integer>> splits = tree.splitNode(node, 1);
        assertEquals(2, splits.size());

        // Test LHS
        TwoFourNode<Integer> left = splits.get(0);
        assertEquals(Arrays.asList(3), left.getData());
        assertEquals(2, left.numSubNodes());
        assertEquals(Arrays.asList(0, 1, 2), left.getNode(0).getData());
        assertEquals(Arrays.asList(4), left.getNode(1).getData());

        // Test RHS
        TwoFourNode<Integer> right = splits.get(1);
        assertEquals(Arrays.asList(8, 10), right.getData());
        assertEquals(3, right.numSubNodes());
        assertEquals(Arrays.asList(6, 7), right.getNode(0).getData());
        assertEquals(Arrays.asList(9), right.getNode(1).getData());
        assertEquals(Arrays.asList(11, 12), right.getNode(2).getData());
    }

    @Test
    public void testSplitNodeNegative() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        assertThrows(IllegalArgumentException.class, () -> tree.splitNode(null, 1));
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(3, 4, 5));
        assertThrows(IllegalArgumentException.class, () -> tree.splitNode(node, 0));
        node.addData(6);
        assertThrows(IllegalArgumentException.class, () -> tree.splitNode(node, 1));
    }

    /*
     * Full tree system tests
     */
    @Test
    public void testPromoteMultipleLevels() {
        LinkedList<List<Integer>> act = new LinkedList<>(Arrays.asList(
            Arrays.asList(3, 5, 8), 
            Arrays.asList(0, 1, 2), 
            Arrays.asList(4), 
            Arrays.asList(6, 7),
            Arrays.asList(9, 11, 12)));
        LinkedList<List<Integer>> exp = deepCopyLinkedList(act);

        TwoFourTree<Integer> tree = new TwoFourTree<>(act);
        assertEquals(exp, tree.levelorder());

        tree.add(10);
        exp = new LinkedList<>(Arrays.asList(
            Arrays.asList(5), 
            Arrays.asList(3), 
            Arrays.asList(8, 10), 
            Arrays.asList(0, 1, 2),
            Arrays.asList(4),
            Arrays.asList(6, 7),
            Arrays.asList(9),
            Arrays.asList(11, 12)));
        assertEquals(exp, tree.levelorder());
    }

    public static <T> LinkedList<T> deepCopyLinkedList(LinkedList<T> original) {
        LinkedList<T> copy = new LinkedList<>();
        for (T item : original) {
            // Assuming T has a copy constructor or a method to clone the object
            copy.add(item); // Replace with item.clone() or new T(item) if necessary
        }
        return copy;
    }

    public static <T> ArrayList<T> linkedToArrayList(LinkedList<List<T>> orig) {
        ArrayList<T> data = new ArrayList<T>(orig.size());
        for (List<T> aList : orig) {
            data.addAll(aList);
        }
        return data;
    }
}
