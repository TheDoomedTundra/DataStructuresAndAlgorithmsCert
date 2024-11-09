package Module0_Introduction.IteraCompara;

// Comparable
public class HDTVComparable implements Comparable<HDTVComparable> {
    private int size;
    private String brand;
    public int getSize() {return size;}
    public String getBrand() {return brand;}

    public int compareTo(HDTVComparable tv) {
        return size - tv.size;
    }
    
}