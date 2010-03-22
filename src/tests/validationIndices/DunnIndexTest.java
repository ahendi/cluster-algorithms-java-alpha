/**
 * 
 */
package tests.validationIndices;

import static org.junit.Assert.assertEquals;
import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.DunnIndex;
import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class DunnIndexTest {
	private Dataset trivialTestset;
	
	@Before
	public void setup(){
		InputReader inputReader = new InputReader();
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		this.trivialTestset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\easy.valid");
		Leader leaderClusterer = new Leader();
		leaderClusterer.setEpsilon(4);
		leaderClusterer.doClustering(trivialTestset);
	}

	/**
	 * Test method for {@link validationIndices.CIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		DunnIndex index = new DunnIndex();
		float result = index.calculateIndex(trivialTestset);
		System.out.println(trivialTestset);
		assertEquals (1.118034, result); //TODO Stimmt das resultat?

	}

}
