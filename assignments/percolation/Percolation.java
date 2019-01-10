import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation {
      private int[][] grid;
       private WeightedQuickUnionUF QU;
       private WeightedQuickUnionUF Q;
       private int size;

        public Percolation(int n) {
            if (n<=0)throw new IllegalArgumentException();
            size=n;
            grid = new int[n][n];
            QU=new WeightedQuickUnionUF(n*n);
            Q=new WeightedQuickUnionUF(n*n);
            for (int i =0;i<n-1;i++){
                 QU.union(i,i+1);
                 QU.union(n*(n-1)+i,n*(n-1)+i+1);
                Q.union(i,i+1);
            }

        }                // create n-by-n grid, with all sites blocked

        public    void open(int row, int col) {
                if (row < 1 || row > size || col < 1 || col > size)throw new IllegalArgumentException();

                if (!isOpen(row,col)) grid[row-1][col-1] = 1;
                int site = (size)*(row-1)+col-1;
                int[][] neigh = { {row,col-1}, {row,col+1}, {row-1,col}, {row+1,col}};
                                for (int j = 0; j<neigh.length; j++) {
                                    int[] i  = neigh[j];
                                        int n = (i[0] - 1) * size + i[1] - 1;
                                        if (onBoeard(i[0], i[1])) {
                                                if (isOpen(i[0], i[1])) {
                                                        if (n<site) {
                                                        //    QU.union(n,site);
                                                            Q.union(n,site);
                                                        }

                                                        else {
                                                         //   QU.union(site, n);
                                                            Q.union(site,n);
                                                        }
                                                }
                                        }
                                }


        }    // open site (row, col) if it is not open already

        private boolean onBoeard(int row,int col) {
                if (row >= 1&&row <= size&&col >= 1&&col <= size) return true;
                return false;
        }



        public boolean isOpen(int row, int col) {
                if (row<1 || row>size || col<1 || col>size)throw new IllegalArgumentException();
                return grid[row-1][col-1]==1;
        }  // is site (row, col) open?


        public boolean isFull(int row, int col) {
                if (row<1 || row>size || col<1 || col>size)throw new IllegalArgumentException();

                if (isOpen(row,col)) {
                        int site = (size)*(row+-1)+col-1;
                        return Q.connected(site,0);
                }
                return false;
        } // is site (row, col) full?


        public     int numberOfOpenSites() {
                int result = 0;
                for (int i = 0; i<size; i++) {
                        for (int j = 0; j<size; j++) {
                                if (grid[i][j]==1)result++;
                        }
                }
                return result;
        }       // number of open sites
        public boolean percolates() {
            if (numberOfOpenSites()==0) return false;

                return QU.connected(0,size*size-1);
        }              // does the system percolate?


    public static void main(String[] args) {
        Percolation p =new Percolation(4);
        p.open(1,1);
        p.open(2,1);
        p.open(3,1);
     //   p.open(4,1);
        System.out.println(p.percolates());
    }

}
