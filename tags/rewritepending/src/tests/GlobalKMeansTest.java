/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import input.Dataset;
import input.Element;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import algorithms.GlobalKMeans;
import algorithms.KMedoids;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class GlobalKMeansTest {
	
	private Dataset testset;
	@Before
	public void setUp(){
			InputReader inputReader = new InputReader();
			this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\trivial.valid");
			this.testset.reset();
			//TODO import matrix
	}
	
	
	
	@Test
	public void testGlobalKMeans(){
		for (Element featureVector : testset) {
			assertEquals(Element.UNCLASSIFIED ,featureVector.getCalculatedClusternumber() );
		}
		GlobalKMeans gkm = new GlobalKMeans();
		gkm.doClustering(testset);

		for (Element featureVector : testset) {
			 assertFalse(Element.UNCLASSIFIED == featureVector.getCalculatedClusternumber());
		}
		System.out.print(testset.toString());
	}
}