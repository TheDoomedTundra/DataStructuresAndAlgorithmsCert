package Module9_24Trees;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.NoSuchElementException;


public class TwoFourNodeTest {

    @Test
    public void testAddData() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        node.addData(10);
        assertTrue(node.contains(10));
    }

    @Test
    public void testAddDuplicateData() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        node.addData(10);
        assertThrows(IllegalArgumentException.class, () -> node.addData(10));
    }

    @Test
    public void testAddMultipleData() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        node.addData(10);
        node.addData(20);
        node.addData(30);
        assertEquals(Arrays.asList(10, 20, 30), node.getData());
    }

    @Test
    public void testAddNullData() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        assertThrows(IllegalArgumentException.class, () -> node.addData(null));
    }

    @Test
    public void testAddOutOfOrderData() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        node.addData(30);
        node.addData(10);
        node.addData(20);
        assertEquals(Arrays.asList(10, 20, 30), node.getData());
    }

    @Test
    public void testConstructFromData() {
        TwoFourNode<Integer> node = new TwoFourNode<>(10);
        assertTrue(node.contains(10));
    }

    @Test
    public void testConstructFromMultipleData() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20, 30));
        assertEquals(Arrays.asList(10, 20, 30), node.getData());
    }

    @Test
    public void testConstructFromOutOfOrderData() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(30, 10, 20));
        assertEquals(Arrays.asList(10, 20, 30), node.getData());
    }

    @Test
    public void testConstructFromTooManyData() {
        assertThrows(IllegalArgumentException.class, () -> new TwoFourNode<>(Arrays.asList(10, 20, 30, 40)));
    }

    @Test
    public void testContainsNull() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        assertThrows(IllegalArgumentException.class, () -> node.contains(null));
    }

    @Test
    public void testGetDataAtIdx() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20));
        Integer exp = 10;
        assertEquals(exp, node.getDataAtIdx(0));
    }

    @Test
    public void testGetDataAtIdxNegative() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20));
        assertThrows(IndexOutOfBoundsException.class, () -> node.getDataAtIdx(3));
    }

    @Test
    public void testGetSetHeight() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        node.setHeight(5);
        assertEquals(5, node.getHeight());
    }

    @Test
    public void testGetSetSubNodes() {
        TwoFourNode<Integer> parent = new TwoFourNode<>();
        TwoFourNode<Integer> child = new TwoFourNode<>(20);
        parent.setNode(0, child);
        assertEquals(Arrays.asList(child), parent.getSubNodes());

        parent.setNode(0, new TwoFourNode<>(20));
        assertEquals(child, parent.getSubNodeAt(1));
    }

    @Test
    public void testOverflowed() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20, 30));
        node.addData(40);
        assertTrue(node.overflowed());
    }

    @Test
    public void testOverflowedNegative() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20, 30));
        assertFalse(node.overflowed());
    }

    @Test
    public void testRemoveData() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20, 30));
        node.remove(10);
        assertFalse(node.contains(10));
    }

    @Test
    public void testRemoveIndex() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20, 30));
        node.removeAt(0);
        assertFalse(node.contains(10));
    }

    @Test
    public void testRemoveNonExistentData() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        assertThrows(NoSuchElementException.class, () -> node.remove(10));
    }

    @Test
    public void testRemoveNonExistentIndex() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        assertThrows(IndexOutOfBoundsException.class, () -> node.removeAt(4));
    }

    @Test
    public void testRemoveNull() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        assertThrows(IllegalArgumentException.class, () -> node.remove(null));
    }

    @Test
    public void testSize() {
        TwoFourNode<Integer> node = new TwoFourNode<>(Arrays.asList(10, 20, 30));
        assertEquals(3, node.size());
    }

    @Test
    public void testUnderflowed() {
        TwoFourNode<Integer> node = new TwoFourNode<>();
        assertTrue(node.underflowed());
    }

    @Test
    public void testUnderflowedNegative() {
        TwoFourNode<Integer> node = new TwoFourNode<>(10);
        assertFalse(node.underflowed());
    }


}