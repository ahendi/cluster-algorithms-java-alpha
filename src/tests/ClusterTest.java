/**
 * 
 */
package tests;

import input.FeatureVector;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import output.Cluster;

/**
 * @author Markus
 *
 */
public class ClusterTest {
	
	private Cluster cl1;
	private Cluster cl2;
	private Cluster cl3;
	
	@Before
	public void setUp(){
		
		FeatureVector f1 = new FeatureVector( new float[]{1.0f,2.0f, 3.0f},0);
		FeatureVector f2 = new FeatureVector( new float[]{3.0f,2.0f, 1.0f},0);
		FeatureVector f3 = new FeatureVector( new float[]{3.5f,2.2f, 1.1f},0);
		FeatureVector f4 = new FeatureVector( new float[]{4.0f,6.0f, 4.0f},0);
		FeatureVector f5 = new FeatureVector( new float[]{3.0f,3.3f, 3.0f},0);
		cl1 = new Cluster(0);
		cl2 = new Cluster(0);
		cl3 = new Cluster(0);
		cl1.add(f1);
		cl1.add(f2);
		cl1.add(f3);
		cl1.add(f4);
		cl1.add(f5);
		cl2.add(f1);
		cl2.add(f2);
		cl2.add(f5);
		cl2.add(f4);
		cl3.add(f3);
	}

	@Test
	public void testNumOfMutualElements(){
		cl1.countMutualElements(cl2);
		assertEquals(4, cl1.countMutualElements(cl2));
		assertEquals(0, cl2.countMutualElements(cl3));
		assertEquals(1,cl1.countMutualElements(cl3));
	}
}
