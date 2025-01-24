package Module10_IterativeSorts;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;


public class SortingTest {
    public class IntegerComparator implements Comparator<Integer> {
        int comparisons = 0;
        public int comparisons() {
            return comparisons;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            comparisons++;
            return Integer.compare(o1, o2);
        }

        public void reset() {
            comparisons = 0;
        }
    }
    
    @Test
    public void testBubbleSort() {
        Integer[] act = {5, 4, 3, 2, 1};
        IntegerComparator comp = new IntegerComparator();
        Sorting.bubbleSort(act, comp);

        Integer[] exp = {1, 2, 3, 4, 5};
        assertEquals(10, comp.comparisons());
        assertArrayEquals(exp, act);

        Integer[] act2 = {5, 1, 2, 3, 4};
        comp.reset();
        Sorting.bubbleSort(act2, comp);
        assertEquals(7, comp.comparisons());
        assertArrayEquals(exp, act2);

        Integer[] act3 = {2, 3, 4, 5, 1};
        comp.reset();
        Sorting.bubbleSort(act3, comp);
        assertEquals(10, comp.comparisons());
        assertArrayEquals(exp, act3);
    }

    @Test
    public void testCocktailShakerSort() {
        Integer[] act = {2, 3, 4, 5, 1};
        IntegerComparator comp = new IntegerComparator();
        Sorting.cocktailShakerSort(act, comp);

        Integer[] exp = {1, 2, 3, 4, 5};
        assertEquals(9, comp.comparisons());
        assertArrayEquals(exp, act);

    }

    @Test
    public void testInsertionSort() {
        Integer[] act = {5, 4, 3, 2, 1};
        IntegerComparator comp = new IntegerComparator();
        Sorting.insertionSort(act, comp);

        Integer[] exp = {1, 2, 3, 4, 5};
        assertEquals(10, comp.comparisons());
        assertArrayEquals(exp, act);

        Integer[] act2 = {5, 6, 7, 8, 1, 2, 3, 4};
        Integer[] exp2 = {1, 2, 3, 4, 5, 6, 7, 8};
        comp.reset();
        Sorting.insertionSort(act2, comp);
        assertEquals(22, comp.comparisons());
        assertArrayEquals(exp2, act2);
    }

    @Test
    public void testSelectionSort() {
        Integer[] act = {5, 4, 3, 2, 1};
        IntegerComparator comp = new IntegerComparator();
        Sorting.selectionSort(act, comp);

        Integer[] exp = {1, 2, 3, 4, 5};
        assertEquals(10, comp.comparisons());
        assertArrayEquals(exp, act);

        Integer[] act2 = {1, 2, 3, 4, 5, 6, 7, 8};
        comp.reset();
        Sorting.selectionSort(act2, comp);
        assertEquals(28, comp.comparisons());
    }
}
