import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE95 = 1.96;
    private int trials;
    private double[] values;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.trials = trials;
        this.values = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            boolean[][] checkList = new boolean[n + 1][n + 1];
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (checkList[row][col]) {
                    continue;
                }
                perc.open(row, col);
                checkList[row][col] = true;
            }
            double treshold = (double) perc.numberOfOpenSites() / (n * n);
            values[i] = treshold;
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(values);
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (CONFIDENCE95 * this.stddev()) / Math.sqrt(this.trials);
    }


    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (CONFIDENCE95 * this.stddev()) / Math.sqrt(this.trials);
    }


    // test client (see below)
    public static void main(String[] args) {
        int gridSize = 10;
        int totalTrials = 10;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            totalTrials = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(gridSize, totalTrials);

        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}