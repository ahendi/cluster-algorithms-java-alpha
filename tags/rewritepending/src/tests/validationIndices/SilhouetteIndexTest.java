/**
 * 
 */
package tests.validationIndices;

import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.SilhouetteIndex;
import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class SilhouetteIndexTest {
	private Dataset trivialTestset;
	
	@Before
	public void setup(){
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		this.trivialTestset =InputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\easy.valid");
		Leader leaderClusterer = new Leader();
		leaderClusterer.setEpsilon(1);
		leaderClusterer.doClustering(trivialTestset);
	}

	/**
	 * Test method for {@link validationIndices.DBIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		System.out.println(trivialTestset);
		SilhouetteIndex siIndex = new SilhouetteIndex();
		float result = siIndex.calculateIndex(this.trivialTestset);
		System.out.println("Average Silhouette for this Cluster is = "+ result);
	}

}
