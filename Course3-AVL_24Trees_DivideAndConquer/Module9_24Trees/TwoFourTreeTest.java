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
        ArrayList<Integer> exp = new ArrayList<>(Arrays.asList(10, 20, 30));
        TwoFourTree<Integer> tree = new TwoFourTree<>(exp);
        assertEquals(exp, tree.levelorder());
    }

    @Test
    public void testConstructInBulk() {
        LinkedList<List<Integer>> exp = new LinkedList<>(Arrays.asList(Arrays.asList(6), Arrays.asList(2), Arrays.asList(12, 13, 16)));
        TwoFourTree<Integer> tree = new TwoFourTree<>(exp);

        exp = new LinkedList<>(Arrays.asList(Arrays.asList(6), Arrays.asList(2), Arrays.asList(12, 13, 16)));
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
    public void testRemove() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
        tree.add(10);
        assertEquals(10, (int) tree.remove(10));
        assertFalse(tree.contains(10));
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

        tree.add(10);
        assertEquals(1, tree.size());

        tree.add(20);
        tree.add(30);
        tree.remove(20);
        assertEquals(2, tree.size());
    }

    /*
     * Full tree system tests
     */
    @Test
    public void testFullTree() {
        TwoFourTree<Integer> tree = new TwoFourTree<>();
    }
}
