import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private final double[] result;
   private static final double constant = 1.96;
   private double s;
    public PercolationStats(int n, int trials){
        if (n<=0||trials<=0) throw new IllegalArgumentException();
        result=new double[trials];
        for (int i=0;i<trials;i++){
            Percolation p=new Percolation(n);

           while (!p.percolates()){
               int row=StdRandom.uniform(1,n+1);
               int col=StdRandom.uniform(1,n+1);
               p.open(row,col);

           }
           double a=(double)( p.numberOfOpenSites())/n;

           result[i]=a/n;
        }
        s=stddev();
    }    // perform trials independent experiments on an n-by-n grid
    public double mean(){

        if(result!=null)
        return StdStats.mean(result);

        return 0;
    }              // sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(result);
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo(){
        double mean=mean();

        return mean-constant*s/Math.sqrt(result.length);
    }         // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        double mean=mean();

        return mean+constant*s/Math.sqrt(result.length);
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args)   {

        int n=StdRandom.uniform(0,100);
        int trail=StdRandom.uniform(0,100);
        PercolationStats p=new PercolationStats(5,10000);
        System.out.println("% java-algs4 PercolationStats "+n+" "+trail);
        System.out.println("mean                   ="+p.mean());
        System.out.println("stddev                 ="+p.stddev());
        System.out.println("95% confidence interval=["+p.confidenceLo()+", "+p.confidenceHi()+"]");
    }    // test client (described below)
}
