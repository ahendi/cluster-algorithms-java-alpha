
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import input.Dataset;
import input.FeatureVector;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import algorithms.FastGlobalKMeans;
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
		for (FeatureVector featureVector : testset) {
			assertEquals(FeatureVector.UNCLASSIFIED ,featureVector.getCalculatedClusternumber() );
		}
		FastGlobalKMeans fgkm = new FastGlobalKMeans( new EuclideanDistance());
		fgkm.doClustering(testset);

		for (FeatureVector featureVector : testset) {
			 assertFalse(FeatureVector.UNCLASSIFIED == featureVector.getCalculatedClusternumber());
		}
		System.out.print(testset.toString());
	}
}
