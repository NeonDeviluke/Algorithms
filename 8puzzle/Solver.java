import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Solver {
    private searchNode node, twining, finalSolution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        int moves = 0;
        node = new searchNode(initial, moves);
        twining = new searchNode(initial.twin(), moves);

        MinPQ<searchNode> pQueue = new MinPQ<>();
        MinPQ<searchNode> tQueue = new MinPQ<>();

        pQueue.insert(node);
        tQueue.insert(twining);

        while (true) {
            if (node.board.isGoal()) {
                this.finalSolution = node;
                return;
            }
            else if (twining.board.isGoal()) {
                this.finalSolution = null;
                return;
            }

            moves++;

            int moves1 = node.moves + 1;

            for (Board b : node.board.neighbors()) {
                Board bprevious = null;

                if (node.previous != null) {
                    bprevious = node.previous.getBoard();
                }

                if (!b.equals(bprevious)) {
                    searchNode node1 = new searchNode(b, moves1);
                    node1.previous = node;
                    pQueue.insert(node1);
                }
            }
            node = pQueue.delMin();


            int moves2 = twining.moves + 1;

            for (Board b : twining.board.neighbors()) {
                Board tprevious = null;

                if (twining.previous != null) {
                    tprevious = twining.previous.getBoard();
                }

                if (!b.equals(tprevious)) {
                    searchNode node2 = new searchNode(b, moves2);
                    node2.previous = twining;
                    tQueue.insert(node2);
                }
            }

            twining = tQueue.delMin();
        }

    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return finalSolution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return node.getMoves();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> solutionStack = new Stack<>();
        searchNode solutionNode = node;
        while (solutionNode != null) {
            solutionStack.push(solutionNode.getBoard());
            solutionNode = solutionNode.previous;
        }
        return solutionStack;
    }

    private class searchNode implements Comparable<searchNode> {
        private Board board;
        private int moves;
        private searchNode previous;

        public searchNode(Board board, int moves) {
            this.board = board;
            this.moves = moves;
            previous = null;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public int heuristic() {
            return board.manhattan() + this.moves;
        }

        public int compareTo(searchNode that) {
            return this.heuristic() - that.heuristic();
        }

        public String toString() {
            return board.toString();
        }
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
