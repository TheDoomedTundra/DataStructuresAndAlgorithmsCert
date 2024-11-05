// Simple point class
public class Point {
    private double x;
    private double y;

    public double getX() { return x; }

    public void setX(double x) { this.x = x; }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double a) {
        // Constructor chaining
        this(a, 0);
    }
}