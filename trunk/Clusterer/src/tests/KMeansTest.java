/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import input.Dataset;
import input.FeatureVector;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import algorithms.KMeans;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class KMeansTest {
	
	private Dataset testset;
	@Before
	public void setUp(){
			InputReader inputReader = new InputReader();
			this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\trivial.valid");
			this.testset.reset();
	}
	
	
	
	@Test
	public void testKMeansClustering(){
		for (FeatureVector featureVector : testset) {
			assertEquals(FeatureVector.UNCLASSIFIED ,featureVector.getCalculatedClusternumber() );
		}
		KMeans kmenasClusterer = new KMeans();
		kmenasClusterer.setNumOfClusters(2);
		kmenasClusterer.setDistanceMeasure(new EuclideanDistance());
		kmenasClusterer.doClustering(testset);
		for (FeatureVector featureVector : testset) {
			 assertFalse(FeatureVector.UNCLASSIFIED == featureVector.getCalculatedClusternumber());
		}
		System.out.print(testset.toString());
	}
}