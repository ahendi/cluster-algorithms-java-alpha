/**
 * 
 */
package tests;

import input.GraphElement;

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
		
		GraphElement f1 = new GraphElement(1, 0);
		GraphElement f2 = new GraphElement(2,0);
		GraphElement f3 = new GraphElement( 3,0);
		GraphElement f4 = new GraphElement( 4,0);
		GraphElement f5 = new GraphElement( 5,0);
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
