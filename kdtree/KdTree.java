/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument to insert() is null\n");

        }
        root = insert(root, p, true, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insert(Node root, Point2D p, boolean color, double xmin, double ymin, double xmax,
                        double ymax) {
        if (root == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), color);
        }

        if (color) {
            if (root.p.x() > p.x()) {
                root.left = insert(root.left, p, false, xmin, ymin, root.p.x(), ymax);
            }
            else if (root.p.x() < p.x()) {
                root.right = insert(root.right, p, false, root.p.x(), ymin, xmax, ymax);
            }
            else if (root.p.y() != p.y()) {
                root.right = insert(root.right, p, false, root.p.x(), ymin, xmax, ymax);
            }
        }

        if (!color) {
            if (root.p.y() > p.y()) {
                root.left = insert(root.left, p, false, xmin, ymin, xmax, root.p.y());
            }
            else if (root.p.y() < p.y()) {
                root.right = insert(root.right, p, false, xmin, root.p.y(), xmax, ymax);
            }
            else if (root.p.x() != p.x()) {
                root.right = insert(root.right, p, false, xmin, root.p.y(), xmax, ymax);
            }
        }
        return root;
    }


    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument to contains() is null\n");
        }
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        while (x != null) {
            if (x.color) {
                if (x.p.x() > p.x()) x = x.left;
                else if (x.p.x() < p.x()) x = x.right;
                else if (x.p.y() != p.y()) x = x.right;
                else return true;
            }
            else {
                if (x.p.y() > p.y()) x = x.left;
                else if (x.p.y() < p.y()) x = x.right;
                else if (x.p.x() != p.x()) x = x.right;
                else return true;
            }
        }
        return false;
    }

    public void draw() {
        draw(root, 0.0, 0.0, 1.0, 1.0);
    }

    private void draw(Node x, double xmin, double ymin, double xmax, double ymax) {
        if (x == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();

        if (x.color) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            RectHV rect = new RectHV(x.p.x(), ymin, x.p.x(), ymax);
            rect.draw();
            draw(x.right, x.p.x(), ymin, xmax, ymax);
            draw(x.right, xmin, ymin, x.p.x(), ymax);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            RectHV rect = new RectHV(xmin, x.p.y(), xmax, x.p.y());
            rect.draw();
            draw(x.right, xmin, x.p.y(), xmax, ymax);
            draw(x.right, xmin, ymax, xmax, x.p.y());
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("the argument to range() is null\n");
        }

        Stack<Point2D> loop = new Stack<>();
        range(root, rect, loop);
        return loop;
    }

    private void range(Node groot, RectHV rect, Stack<Point2D> loop) {
        if (groot == null) return;
        if (!rect.intersects(groot.rectangle)) return;
        if (rect.contains(groot.p)) loop.push(groot.p);

        if (groot.color) {
            range(groot.right, rect, loop);
            range(groot.left, rect, loop);
        }

        if (!groot.color) {
            range(groot.right, rect, loop);
            range(groot.left, rect, loop);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("the argument to nearest() is null\n");
        }
        return nearest(root, p, null);
    }

    private Point2D nearest(Node groot, Point2D p, Point2D min) {
        if (groot == null || (min != null
                && min.distanceSquaredTo(p) <= groot.rectangle.distanceSquaredTo(p)))
            return min;
        if (min == null || groot.p.distanceSquaredTo(p) < min.distanceSquaredTo(p))
            min = groot.p;

        if (groot.right != null && groot.right.rectangle.contains(p)) {
            min = nearest(groot.right, p, min);
            min = nearest(groot.left, p, min);
        }
        else {
            min = nearest(groot.left, p, min);
            min = nearest(groot.right, p, min);
        }
        return min;
    }

    private class Node {
        private Point2D p;
        private RectHV rectangle;
        private boolean color;
        private Node left, right;

        public Node(Point2D p, RectHV rect, boolean color) {
            this.p = p;
            this.rectangle = rect;
            this.color = color;
        }
    }

    public static void main(String[] args) {

    }
}

