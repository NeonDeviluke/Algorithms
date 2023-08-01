/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

public class Point2D implements Comparable<Point2D> {

    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double distanceTo(Point2D that) {
        return Math.sqrt(distanceSquaredTo(that));
    }

    public double distanceSquaredTo(Point2D that) {
        return ((that.x - this.x) * (that.x - this.x)) + ((that.y - this.y) * (that.y - this.y));
    }

    public int compareTo(Point2D that) {
        if (that == null) {
            throw new IllegalArgumentException();
        }

        if (this.x < that.x) return -1;
        if (this.y < that.y) return -1;
        if (this.x > that.x) return 1;
        if (this.y > that.y) return 1;

        return 0;
    }

    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }

        if (that == null) {
            return false;
        }

        if (that.getClass() != this.getClass()) {
            return false;
        }

        Point2D obj = (Point2D) that;
        if (this.x != obj.x) return false;
        if (this.y != obj.y) return false;
        return true;

    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
