package p1_percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author kaush
 *
 * @description
 * To model percolation system
 * Handled Backwash problem by using two copies of WeightedQuickUnionUF instance. One connects with the bottom 
 * virtual node and the other which doesn't connect is used in isFull()
 */
public class Percolation {

	private WeightedQuickUnionUF gridUF, gridUF1;
	private int[] grid;
	private int N;
	private int virtualNode1, virtualNode2;

	public Percolation(int N)              // create N-by-N grid, with all sites blocked
	{
		if (N <= 0)
			throw new IllegalArgumentException();

		gridUF = new WeightedQuickUnionUF(N*N + 2);
		gridUF1 = new WeightedQuickUnionUF(N*N + 2);
		grid = new int[N*N +2];
		this.N = N;
		virtualNode1 = 0;
		virtualNode2 = N*N + 1;
	}

	public void open(int i, int j)          // open site (row i, column j) if it is not open already
	{
		if (i < 1 || i > N || j < 1 || j > N)
			throw new IndexOutOfBoundsException();

		if (isOpen(i, j))
			return;

		int p = calculateIndex(i, j), q;
		grid[p] = 1;

		// Check top Site
		if (i != 1 && isOpen(i-1, j)) {
			q = calculateIndex(i-1, j);
			gridUF.union(p, q);
			gridUF1.union(p, q);
		}
		// Check bottom Site
		if (i != N && isOpen(i+1, j)) {
			q = calculateIndex(i+1, j);
			gridUF.union(p, q);
			gridUF1.union(p, q);
		}
		// Check top Site
		if (j != 1 && isOpen(i, j-1)) {
			q = calculateIndex(i, j-1);
			gridUF.union(p, q);
			gridUF1.union(p, q);
		}
		// Check top Site
		if (j != N && isOpen(i, j+1)) {
			q = calculateIndex(i, j+1);
			gridUF.union(p, q);
			gridUF1.union(p, q);
		}

		// Connect with top virtual node
		if (i == 1) {
			q = virtualNode1;
			gridUF.union(p, q);
			gridUF1.union(p, q);
		}
		// Connect with bottom virtual node
		if (i == N) {
			q = virtualNode2;
			gridUF.union(p, q);
		}
	}

	public boolean isOpen(int i, int j)     // is site (row i, column j) open?
	{
		if (i < 1 || i > N || j < 1 || j > N)
			throw new IndexOutOfBoundsException();

		int p = calculateIndex(i, j);
		return (grid[p] == 1);
	}

	public boolean isFull(int i, int j)     // is site (row i, column j) full?
	{
		if (i < 1 || i > N || j < 1 || j > N)
			throw new IndexOutOfBoundsException();

		int p = calculateIndex(i, j);
		return gridUF1.connected(virtualNode1, p);
	}

	public boolean percolates()             // does the system percolate?
	{
		return gridUF.connected(virtualNode1, virtualNode2);
	}

	private int calculateIndex(int i, int j) {
		return N*(i-1) + j;
	}

}
