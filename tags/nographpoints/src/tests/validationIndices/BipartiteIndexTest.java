/**
 * 
 */
package tests.validationIndices;

import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.BPIndex;
import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class BipartiteIndexTest {
	private Dataset trivialTestset;
	
	@Before
	public void setup(){

		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		this.trivialTestset =InputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		Leader leaderClusterer = new Leader();
		leaderClusterer.setEpsilon(10);
		leaderClusterer.doClustering(trivialTestset);
	}

	/**
	 * Test method for {@link validationIndices.DBIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		BPIndex bpIndex = new BPIndex();
		float result = bpIndex.calculateIndex(this.trivialTestset);
		System.out.println("Bipartite-Index for this Clustering is = "+ result);
	}

}
