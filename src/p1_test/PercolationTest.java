/**
 * 
 */
package p1_test;

import static org.junit.Assert.*;

import org.junit.Test;

import p1_percolation.Percolation;

/**
 * @author kaush
 *
 */
public class PercolationTest {

	@Test
	public void test() {
		Percolation p = new Percolation(3);
		p.open(1,1);
		p.open(2,1);
		p.open(3,1);
		assertEquals("Should be true", true, p.percolates());
		//fail("Not yet implemented");
	}

}
