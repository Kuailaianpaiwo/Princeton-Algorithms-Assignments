/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Node goal;



    private class Node implements Comparable<Node>
    {
        private Board val;
        private Node pre;
        private int move;

        private Node(Board board)
        {
            this.val = board;
        }

        private Node(Board val, Node pre)
        {
            this.val = val;
            this.pre = pre;
            this.move = pre.move + 1;
        }

        public int compareTo(Node o) {
            return val.manhattan() - o.val.manhattan() + move - o.move;
        }
    }
    public Solver(Board initial)
    {
        if (initial == null) throw  new IllegalArgumentException();

             Board twin = initial.twin();

             if(initial.isGoal())
             {
                 goal = new Node(initial);
                 return;
             }
             MinPQ<Node> boards = new MinPQ<>();
             MinPQ<Node> twinBoard = new MinPQ<>();
             boards.insert(new Node(initial));
             twinBoard.insert(new Node(twin));
             Node tGoal = null;

            while (goal == null && tGoal == null) {
               // System.out.println(trace.get(moves));
               Node b = boards.delMin();
                Node tb = twinBoard.delMin();

                for (Board board : b.val.neighbors())
                    if (b.pre == null || !board.equals(b.pre.val))
                    boards.insert(new Node(board,b));

                for (Board board : tb.val.neighbors())
                    if (tb.pre == null || !board.equals(tb.pre.val))
                    twinBoard.insert(new Node(board));

                if (boards.min().val.isGoal())
                    goal = boards.min();

                if (twinBoard.min().val.isGoal())
                    tGoal = twinBoard.min();


        }
    } // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable()
    {
        return goal != null;
    } // is the initial board solvable?

    public int moves()
    {
        if (isSolvable())
        {
           return goal.move;
        }
        return -1;
    } // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()
    {
        if (isSolvable())
        {
            Stack<Board> stack = new Stack<>();
            Node node = goal;
            while (node != null)
            {
            stack.push(node.val);
            node = node.pre;
            }
            return stack;
        }


        return null;
    } // sequence of boards in a shortest solution; null if unsolvable


    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)
}
