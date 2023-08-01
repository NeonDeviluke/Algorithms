import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkValue(v);
        checkValue(w);
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(graph, w);
        return Calculate(vpath, wpath, true);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkValue(v);
        checkValue(w);
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(graph, w);
        return Calculate(vpath, wpath, false);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        checkValue(v);
        checkValue(w);
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(graph, w);
        return Calculate(vpath, wpath, true);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        checkValue(v);
        checkValue(w);
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(graph, w);
        return Calculate(vpath, wpath, false);
    }

    private void checkValue(Integer val) {
        if (val < 0 || val > graph.V() - 1 || val == null) throw new IllegalArgumentException();
    }

    private void checkValue(Iterable<Integer> val) {
        for (int i : val) {
            checkValue(i);
        }
    }

    private int Calculate(BreadthFirstDirectedPaths v, BreadthFirstDirectedPaths w, boolean value) {
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (v.hasPathTo(i) && w.hasPathTo(i)) {
                int vdist = v.distTo(i);
                int wdist = w.distTo(i);
                if (vdist + wdist < min) {
                    min = vdist + wdist;
                    ancestor = i;
                }
            }
        }
        if (value) return min < Integer.MAX_VALUE ? min : -1;
        else return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("D:\\Algorithms\\wordnet\\digraph2.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}