/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] board;
    private int boardLength;

    private int manhattanLength, hammingLength;

    public Board(int[][] tiles) {
        this.boardLength = tiles.length;
        this.board = new int[tiles.length][tiles.length];
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder reader = new StringBuilder();
        reader.append(boardLength + "\n");
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                reader.append(board[i][j] + "\t");
            }
            reader.append("\n");
        }
        return reader.toString();
    }

    // board dimension n
    public int dimension() {
        return boardLength;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 1;
        hammingLength = 0;
        for (int[] board1 : board) {
            for (int boardo : board1) {
                if (boardo != 0 && boardo != count) {
                    hammingLength++;
                }
                count++;
            }
        }
        return hammingLength;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        manhattanLength = 0;
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (board[i][j] != 0) {
                    int adjusted = board[i][j] - 1;
                    int x = adjusted % boardLength;
                    int y = (int) Math.floor(adjusted / boardLength);
                    manhattanLength += Math.abs(y - i) + Math.abs(x - j);
                }
            }
        }
        return manhattanLength;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) {
            return false;
        }
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> taken = new Queue<>();
        int count = 0, sunna = 0;
        for (int[] board1 : board) {
            for (int board2 : board1) {
                if (board2 == 0) {
                    sunna = count;
                    break;
                }
                count++;
            }
        }
        int n = sunna / boardLength;
        int m = sunna % boardLength;
        // up-side neighbour
        if (n - 1 >= 0) {
            Board up = CopyOfBoard(false);
            up.swap(sunna, this.to1D(n - 1, m));
            taken.enqueue(up);
        }
        // down-side neighbour
        if (n + 1 < boardLength) {
            Board down = CopyOfBoard(false);
            down.swap(sunna, this.to1D(n + 1, m));
            taken.enqueue(down);
        }
        // left-side neighbour
        if (m - 1 >= 0) {
            Board left = CopyOfBoard(false);
            left.swap(sunna, this.to1D(n, m - 1));
            taken.enqueue(left);
        }
        // right-side neighbour
        if (m + 1 < boardLength) {
            Board right = CopyOfBoard(false);
            right.swap(sunna, this.to1D(n, m + 1));
            taken.enqueue(right);
        }
        return taken;
    }

    private int to1D(int x, int y) {
        return ((boardLength * x) + y);
    }

    private int xvalue(int d) {
        int x = (d) / boardLength;
        return x;
    }

    private int yvalue(int d) {
        int y = (d) % boardLength;
        return y;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return CopyOfBoard(true);
    }

    private Board CopyOfBoard(boolean twin) {
        int[][] twinBoard = new int[boardLength][boardLength];
        int one = 0, two = 0, count = 0;
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                twinBoard[i][j] = board[i][j];
                if (board[i][j] != 0 && one == 0) {
                    one = count;
                }
                else if (board[i][j] != 0 && one != 0 && two == 0) {
                    two = count;
                }
                count++;
            }
        }
        Board twining = new Board(twinBoard);
        if (twin) {
            twining.swap(one, two);
        }
        return twining;
    }

    private void swap(int one, int two) {
        int oneRow = xvalue(one);
        int oneCol = yvalue(one);
        int twoRow = xvalue(two);
        int twoCol = yvalue(two);
        int yoyo = board[oneRow][oneCol];
        board[oneRow][oneCol] = board[twoRow][twoCol];
        board[twoRow][twoCol] = yoyo;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}