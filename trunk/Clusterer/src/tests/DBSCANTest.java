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

import algorithms.DBSCAN;

/**
 * @author Markus
 *
 */
public class DBSCANTest {
	
	private Dataset testset;
	@Before
	public void setUp(){
			InputReader inputReader = new InputReader();
			this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\easy.valid");
				
	}
	
	
	
	@Test
	public void testDBSCANClustering(){
		for (FeatureVector featureVector : testset) {
			assertEquals(FeatureVector.UNCLASSIFIED ,featureVector.getCalculatedClusternumber() );
		}
		DBSCAN dbscanClusterer = new DBSCAN();
		dbscanClusterer.setEpsilon(3f);
		dbscanClusterer.setMinPoints(2);
		dbscanClusterer.doClustering(testset);
		for (FeatureVector featureVector : testset) {
			 assertFalse(FeatureVector.UNCLASSIFIED == featureVector.getCalculatedClusternumber());
		}
		System.out.print(testset.toString());
	}
}
