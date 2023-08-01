/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

public class RectHV {
    private double xmin, xmax, ymin, ymax;

    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
        if ((xmin > xmax) || (ymin > ymax)) {
            throw new IllegalArgumentException();
        }
    }

    public double xmin() {
        return this.xmin;
    }

    public double ymin() {
        return this.ymin;
    }

    public double xmax() {
        return this.xmax;
    }

    public double ymax() {
        return this.ymax;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (p.x() <= this.xmax && p.x() >= this.xmin && p.y() <= ymax && p.y() >= ymin) {
            return true;
        }
        return false;
    }

    public boolean intersects(RectHV that) {
        if (that == null) {
            throw new IllegalArgumentException();
        }
        if (this.xmax >= that.xmin && this.ymax >= that.ymin && that.xmax >= this.xmin
                && that.ymax >= this.ymin) {
            return true;
        }
        return false;
    }

    public double distanceTo(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return Math.sqrt(distanceSquaredTo(p));
    }

    public double distanceSquaredTo(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        double dx = 0.0, dy = 0.0;
        if (this.contains(p)) {
            return 0.0;
        }
        if (p.x() < this.xmin) {
            dx = this.xmin - p.x();
        }
        else if (p.x() > this.xmax) {
            dx = p.x() - this.xmax;
        }
        if (p.y() < this.ymin) {
            dy = this.ymin - p.y();
        }
        else if (p.y() > this.ymax) {
            dy = p.y() - this.ymax;
        }
        return dx * dx + dy * dy;
    }

    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }

        if (this == that) {
            return true;
        }

        if (that.getClass() != this.getClass()) {
            return false;
        }

        RectHV rect = (RectHV) that;
        if (this.xmax != rect.xmax) return false;
        if (this.xmin != rect.xmin) return false;
        if (this.ymax != rect.ymax) return false;
        if (this.ymin != rect.ymin) return false;

        return true;
    }

    public void draw() {
        StdDraw.line(xmin, ymin, xmax, ymax);
    }

    public String toString() {
        return "Rectangle: " + "[xmin: " + xmin + ", " + "xmax: " + xmax + "] x [ ymin: " + ymin
                + "," + "ymax: " + ymax + "]";
    }
}