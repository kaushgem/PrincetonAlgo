package p1_percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author kaush
 *
 */
public class PercolationStats {

	private double[] fractionOpenSites;
	private int T;
	private int openSites;

	public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
	{
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException();
		
		this.T = T;
		
		fractionOpenSites = new double[T];
		for (int k = 0; k < T; k++) {
			openSites = 0;
			Percolation p = new Percolation(N);
			while (!p.percolates()) {
				int i = StdRandom.uniform(1, N+1);
				int j = StdRandom.uniform(1, N+1);
				if (!p.isOpen(i, j)) {
					p.open(i, j);
					openSites++;
				}
			}
			fractionOpenSites[k] = (double) openSites/(N*N);
		}
	}

	public double mean()                      // sample mean of percolation threshold
	{
		return StdStats.mean(fractionOpenSites);
	}

	public double stddev()                    // sample standard deviation of percolation threshold
	{
		return StdStats.stddev(fractionOpenSites);
	}
	
	public double confidenceLo()              // low  endpoint of 95% confidence interval
	{
		return mean() - (1.96 * stddev()) / Math.sqrt(T);
	}

	public double confidenceHi()              // high endpoint of 95% confidence interval
	{
		return mean() + (1.96 * stddev()) / Math.sqrt(T);
	}

	public static void main(String[] args)    // test client (described below)
	{
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);

		PercolationStats pStat = new PercolationStats(N, T);

		System.out.println("mean                    = " + pStat.mean());
		System.out.println("stddev                  = " + pStat.stddev());
		System.out.println("95% confidence interval = " + pStat.confidenceLo() + ", " + pStat.confidenceHi());
	}

}
