
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    private int[][] board;
    private int manhaton;
    private int Bi;
    private int Bj;
    public Board(int[][] blocks)
    {
        board = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
            { board[i][j] = blocks[i][j];
            if (board[i][j] == 0)
            {
                Bi = i;
                Bj = j;
            }
            }
            manhaton = manhattan();
    }           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension()
    {
        return board.length;
    }                 // board dimension n
    public int hamming()
    {
        int result = 0;
        int N = dimension();
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (board[i][j] != i * N + j + 1 && board[i][j] !=0)
                    result++;
            }
        }
        return result;
    }                   // number of blocks out of place


    private int man(int i, int j, int target)
    {
        int N = dimension();
        int col = target % N - 1;
        if (col == -1)
            col = N - 1;
        int row = (target - col - 1) / N;

        return abs(i - row) + abs(j - col);
    }

    private int abs(int i)
    {
        if (i < 0)
            return  -i;
        return i;
    }
    public int manhattan()
    {
        int result = 0;
        int n = dimension();
        for (int i =0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (board[i][j] != 0)
                    result += man(i, j, board[i][j]);
            }
        }
        return result;
    }                 // sum of Manhattan distances between blocks and goal


    public boolean isGoal()
    {
        return hamming() == 0;
    }                // is this board the goal board?


    public Board twin() {
        int n = dimension();
        int[][] twinArray = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                twinArray[i][j] = board[i][j];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0)
                {
                    twinArray[i][j] = board[i][j + 1];
                    twinArray[i][j + 1] = board[i][j];
                    return new Board(twinArray);
                }
            }
        }
        throw new RuntimeException();// a board that is obtained by exchanging any pair of blocks
    }

    public boolean equals(Object y)
    {
        if (y == null) return false;
        if (this == y) return true;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board)y;
        if (that.dimension()!=dimension()) return false;
        for (int i = 0; i < dimension(); i ++)
            for (int j = 0; j < dimension(); j++)
                if (that.board[i][j] != this.board[i][j])
                    return false;

                return true;


    }        // does this board equal y?


    public Iterable<Board> neighbors()
    {
        List<Board> boards = new ArrayList<>();
        int[] index = { -1, 0, 1};
        for (int i = 0; i < index.length; i++)
        {
        for (int j = 0; j < index.length; j++)
        {
            if (abs(index[i]) == abs(index[j]))
                continue;
            int newX = Bi + index[i];
            int newY = Bj + index[j];
            if (onBoard(newX, newY)) {
                int[][] newBoard = new int[dimension()][dimension()];
                for (int m = 0; m < dimension(); m++)
                    for (int n = 0; n < dimension(); n++)
                        newBoard[m][n] = board[m][n];

                    newBoard[Bi][Bj] = board[newX][newY];
                    newBoard[newX][newY] = board[Bi][Bj];

                    Board newOne = new Board(newBoard);
                        boards.add(newOne);
                 //  else if (!pre.equals(newOne))
            }
        }
        }
        return boards;
    }     // all neighboring boards


    private boolean onBoard(int i, int j)
    {
        int n = dimension();
        return i < n && i >= 0 && j < n && j >= 0;
    }

    @Override
    public String toString()
    {
        String result = "";
        int n = dimension();
        for (int i = 0; i < n; i++)
        {
            if (result != "")
                result += "\n";
            for (int j = 0; j < n; j++)
            {
                if (j != 0) result += " ";
               if(board[i][j] >= 10) result += board[i][j];
               else result += " "+board[i][j];
            }
        }
        result = dimension()+"\n"+result;
        return result;
    }               // string representation of this board (in the output format specified below)

    public static void main(String[] args)
    {
        int[][] tem = {{1, 0}, {3, 2}};
        int[][] tem2 = {{1, 0},{2,3}};
        Board board = new Board(tem);
         // for (Board b : board.neighbors())
         //     System.out.println(b);
       Solver solver = new Solver(board);
      // System.out.println(solver.isSolvable());
        Stack<Board> stack = (Stack<Board>) solver.solution();
//        for (Board board1: stack)
           // System.out.println(board1);
        System.out.println(solver.isSolvable());


    } // unit tests (not graded)

}
