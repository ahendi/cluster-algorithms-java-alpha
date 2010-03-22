/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class EuclideanDistanceTest {

	/**
	 * Test method for {@link distance.EuclideanDistance#calculate(float[], float[])}.
	 */
	@Test
	public void testCalculate() {
		EuclideanDistance euDist = new EuclideanDistance();
		float[] a = {0,0};
		float[] b = {0,2};
		float[] c = {3,0};
		float[] d = {4,4};
		assertEquals(2,euDist.calculate(a, b));
		assertEquals (3, euDist.calculate(a, c));
		assertEquals (5.65, euDist.calculate(a, d));
		assertEquals (4.123,euDist.calculate(c, d));
	}

}
