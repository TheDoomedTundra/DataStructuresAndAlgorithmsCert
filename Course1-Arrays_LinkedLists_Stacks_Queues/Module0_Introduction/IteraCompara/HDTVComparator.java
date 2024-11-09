package Module0_Introduction.IteraCompara;

import java.util.Comparator;

// Comparator
public class HDTVComparator implements Comparator<HDTVComparable> {
    public int compare(HDTVComparable tv1, HDTVComparable tv2) {
        return tv1.compareTo(tv2);
    }
}