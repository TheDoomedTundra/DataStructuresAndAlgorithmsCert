package Module11_DivideAndConquer;

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
    public void testMergeSort() {
        Integer[] act = {2,9,3,5,3,1};
        Integer[] exp = {1,2,3,3,5,9};
        IntegerComparator comp = new IntegerComparator();
        Sorting.mergeSort(act, comp);
        assertEquals(10, comp.comparisons());
        assertArrayEquals(exp, act);

        act = new Integer[] {4,6,13,7,7,2,4,11,2,9,13,11,2};
        exp = new Integer[] {2,2,2,4,4,6,7,7,9,11,11,13,13};

        comp.reset();
        Sorting.mergeSort(act, comp);
        assertEquals(35, comp.comparisons());
        assertArrayEquals(exp, act);

        act = new Integer[] {};
        exp = act;
        comp.reset();
        Sorting.mergeSort(act, comp);
        assertEquals(0, comp.comparisons());
        assertArrayEquals(exp, act);
    }

    @Test
    public void testLsdRadixSort() {
        int[] act = {190,83,460,387,394,179,20,94,52,143};
        int[] exp = {20,52,83,94,143,179,190,387,394,460};

        Sorting.lsdRadixSort(act);
        assertArrayEquals(exp, act);

        act = new int[] {141,118,83,414,405,478,386};
        exp = new int[] {83,118,141,386,405,414,478};

        Sorting.lsdRadixSort(act);
        assertArrayEquals(exp, act);

        act = new int[] {1, 0};
        exp = new int[] {0, 1};

        Sorting.lsdRadixSort(act);
        assertArrayEquals(exp, act);

        act = new int[] {3, 5, 10, 4, 2};
        exp = new int[] {2, 3, 4, 5, 10};

        Sorting.lsdRadixSort(act);
        assertArrayEquals(exp, act);

        act = new int[] {5};
        exp = act;
        Sorting.lsdRadixSort(exp);
        assertArrayEquals(exp, act);

        act = new int[] {};
        exp = act;
        Sorting.lsdRadixSort(exp);
        assertArrayEquals(exp, act);
    }

    @Test
    public void testQuickSort() {
        Integer[] act = {2,9,3,5,3,1};
        Integer[] exp = {1,2,3,3,5,9};
        IntegerComparator comp = new IntegerComparator();
        Sorting.quickSort(act, 0, act.length - 1, comp);
        assertArrayEquals(exp, act);

        act = new Integer[] {4,6,13,7,7,2,4,11,2,9,13,11,2};
        exp = new Integer[] {2,2,2,4,4,6,7,7,9,11,11,13,13};

        comp.reset();
        Sorting.quickSort(act, 0, act.length - 1, comp);
        assertArrayEquals(exp, act);

        act = new Integer[] {};
        exp = act;
        comp.reset();
        Sorting.quickSort(act, 0, act.length - 1, comp);
        assertArrayEquals(exp, act);
    }

    @Test
    public void testQuickSelect() {
        Integer[] data = {2,9,3,5,3,1};
        IntegerComparator comp = new IntegerComparator();
        int act = Sorting.quickSelect(data, 0, data.length - 1, 5, comp);
        assertEquals(5, act);

        data = new Integer[] {13,8,2,9,4,7,6,13,5,3,12};
        act = Sorting.quickSelect(data, 0, data.length - 1, 6, comp);
        assertEquals(7, act);
    }

}
