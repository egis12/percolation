/******************************************************************************
 *  Name:    Reginald Almonte
 *  NetID:   N/A
 *  Precept: N/A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Perform a series of computational experiments that can 
 *                return the following statistics results:
 *                  - mean. Use mean() function
 *                  - stddev: Use stddev() funtion
 *                  - 95% confidence interval: Use confidenceLo()
 *                    and confidenceHi() functions
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private Percolation system;
    private final double[] percolationThresh;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        percolationThresh = new double[trials];
        
        for (int i = 0; i < trials; i++)
        {
            system = new Percolation(n);
            int nOpenSites = esimatePercolationThresh(n);
            percolationThresh[i] = (double) nOpenSites/(n*n);
        }
    }
    
    private int esimatePercolationThresh(int n)
    {
        int i;
        for (i = 1; i <= n*n; i++)
        {
            openOneRandomSite(n);
            if (system.percolates())
                break;
        }
        return i;
    }
    
    private void openOneRandomSite(int n)
    {
        if (system == null || system.numberOfOpenSites() == n*n)
            return; // all sites are open
        
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;
        while (system.isOpen(row, col))
        {
            row = StdRandom.uniform(n) + 1;
            col = StdRandom.uniform(n) + 1;
        }
        system.open(row, col);
    }
    
    private void printSummary()
    {        
        System.out.println("mean                    = " + mean());
        System.out.println("stddev                  = " + stddev());
        System.out.println("95% confidence interval = [" +
                           confidenceLo() + ", " + confidenceHi() + "]");
    }
    
    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(percolationThresh);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() 
    {
        return StdStats.stddev(percolationThresh);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        double mean = mean();
        double s = stddev();
        return mean - (1.96 * s)/sqrt(percolationThresh.length);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        double mean = mean();
        double s = stddev();
        return mean + (1.96 * s)/sqrt(percolationThresh.length);
    }
    
    private static double sqrt(int number) {
        double t;        
        double squareRoot = (double) number / 2;        
        do {
            t = squareRoot;
            squareRoot = (t + (number / t)) / 2;
        } while ((t - squareRoot) != 0);
        
        return squareRoot;
    }
    
    public static void main(String[] args)
    {        
        if (args.length < 2)
            throw new IllegalArgumentException();
        
        int n, t = 0;
        
        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);
                
        if (n <= 0 || t <= 0)
            throw new IllegalArgumentException();
        
        PercolationStats ps = new PercolationStats(n, t);
        ps.printSummary();
        
    }
}