/******************************************************************************
 *  Name:    Reginald Almonte
 *  NetID:   N/A
 *  Precept: N/A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the 
 *                union-find data structure.
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final WeightedQuickUnionUF systemUF;
    private int nOpen;
    private int[][] system;
    
    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
            throw new IllegalArgumentException();
        
        size = n;
        nOpen = 0;
        system = new int[size][size];
        systemUF = new WeightedQuickUnionUF(size*size+2);
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException();
        
        if (isOpen(row, col))
            return;
        
        nOpen++;
        system[row - 1][col - 1] = 1;
        
        int upperRow = row - 1;
        int lowerRow = row + 1;
        int leftCol = col - 1;
        int rightCol = col + 1;        
        
        if (row == 1)
            systemUF.union(0, col);
        else if (isOpen(upperRow, col))
            systemUF.union(getUFIndex(upperRow, col), getUFIndex(row, col));
        
        // connect to the Left Cell
        if (col > 1 && isOpen(row, leftCol))
            systemUF.union(getUFIndex(row, col), getUFIndex(row, leftCol));
         
        // connect to the Right Cell
        if (col < size && isOpen(row, rightCol))
            systemUF.union(getUFIndex(row, col), getUFIndex(row, rightCol));
        
        // connect to the Lower Cell
        if (row == size)
        {
            systemUF.union(getUFIndex(row, col), size*size+1);
        }            
        else if (isOpen(lowerRow, col))
            systemUF.union(getUFIndex(row, col), getUFIndex(lowerRow, col));
    }
    
    private int getUFIndex(int row, int col)
    {
        return (row - 1)*size + col;
    }
        
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException();
        
        return system[row - 1][col - 1] == 1;
    }
    
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException();
        return systemUF.connected((row-1)*size+col, 0);
    }
    
    public int numberOfOpenSites()  // number of open sites
    {
        return nOpen;
    }
    
    public boolean percolates()  // does the system percolate?
    {
        return systemUF.connected(size*size+1, 0);
    }
    
//    public static void main(String[] args)   // test client (optional)
//    {
//    }
}