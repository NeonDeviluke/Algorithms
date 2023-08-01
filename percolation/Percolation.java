import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private static final int VIRTUAL_TOP = 0;
    private int[][] grid;
    private int n;
    private boolean[][] values;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufFull;
    private int openSites;
    private int virtualBottom;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.openSites = 0;
        this.n = n;
        int squareSize = n * n;
        this.uf = new WeightedQuickUnionUF(squareSize + 2);
        this.ufFull = new WeightedQuickUnionUF(squareSize + 1);
        this.grid = new int[n + 1][n + 1];
        this.values = new boolean[n + 1][n + 1];
        int count = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                this.grid[i][j] = count;
                count++;
            }
        }
        this.virtualBottom = squareSize + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.validation(row, col);
        if (this.isOpen(row, col)) return;
        this.values[row][col] = true;
        this.openSites++;
        this.connection(row, col);
        if (row == 1) {
            uf.union(this.grid[1][col], VIRTUAL_TOP);
            ufFull.union(this.grid[1][col], VIRTUAL_TOP);
        }
        if (row == n) {
            uf.union(this.grid[n][col], this.virtualBottom);
        }
    }

    private void connection(int row, int col) {
        if (row + 1 <= n) {
            if (this.values[row + 1][col]) {
                uf.union(this.grid[row][col], this.grid[row + 1][col]);
                ufFull.union(this.grid[row][col], this.grid[row + 1][col]);
            }
        }
        if (row - 1 >= 1) {
            if (this.values[row - 1][col]) {
                uf.union(this.grid[row][col], this.grid[row - 1][col]);
                ufFull.union(this.grid[row][col], this.grid[row - 1][col]);
            }
        }
        if (col + 1 <= n) {
            if (this.values[row][col + 1]) {
                uf.union(this.grid[row][col], this.grid[row][col + 1]);
                ufFull.union(this.grid[row][col], this.grid[row][col + 1]);

            }
        }
        if (col - 1 >= 1) {
            if (this.values[row][col - 1]) {
                uf.union(this.grid[row][col], this.grid[row][col - 1]);
                ufFull.union(this.grid[row][col], this.grid[row][col - 1]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        this.validation(row, col);
        return this.values[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        this.validation(row, col);

        if (!this.values[row][col]) {
            return false;
        }
        return ufFull.find(this.grid[row][col]) == ufFull.find(VIRTUAL_TOP);
    }

    private void validation(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException();
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(0) == uf.find(virtualBottom)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(6);
        perc.open(1, 6);
        perc.open(2, 6);
        perc.open(3, 6);
        perc.open(4, 6);
        perc.open(5, 6);
        perc.open(5, 5);
        perc.open(4, 4);
        System.out.println(perc.isFull(4, 4));
        System.out.println(perc.percolates());
    }
}