/**
 * 
 */
package tests.validationIndices;

import static org.junit.Assert.assertEquals;
import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import distance.EuclideanDistance;

import validationIndices.CalinskiHarabasz;
import algorithms.KMeans;
import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class CHIndexTest {
	private Dataset trivialTestset;
	
	@Before
	public void setup(){
		InputReader inputReader = new InputReader();
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		this.trivialTestset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\easy.valid");
//		Leader leaderClusterer = new Leader();
//		leaderClusterer.setEpsilon(2);
//		leaderClusterer.doClustering(trivialTestset);
		KMeans kmeans = new KMeans(new EuclideanDistance());
		kmeans.setNumOfClusters(10);
		kmeans.doClustering(this.trivialTestset);
	}

	/**
	 * Test method for {@link validationIndices.CIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		System.out.println(trivialTestset);
		CalinskiHarabasz index = new CalinskiHarabasz();
		float result = index.calculateIndex(trivialTestset);
		assertEquals (77.109, result); //this is the result for this clustering with 100% accuracy
		
	}

}
