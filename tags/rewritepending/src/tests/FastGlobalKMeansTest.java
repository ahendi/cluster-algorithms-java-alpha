
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import input.Dataset;
import input.Element;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import algorithms.FastGlobalKMedoids;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class FastGlobalKMeansTest {
	
	private Dataset testset;
	@Before
	public void setUp(){
			InputReader inputReader = new InputReader();
			this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
			this.testset.reset();
	}
	
	
	
	@Test
	public void testFastGlobalKMeans(){
		for (Element featureVector : testset) {
			assertEquals(Element.UNCLASSIFIED ,featureVector.getCalculatedClusternumber() );
		}
		FastGlobalKMedoids fgkm = new FastGlobalKMedoids();
		fgkm.doClustering(testset);

		for (Element featureVector : testset) {
			 assertFalse(Element.UNCLASSIFIED == featureVector.getCalculatedClusternumber());
		}
		System.out.print(testset.toString());
	}
}
