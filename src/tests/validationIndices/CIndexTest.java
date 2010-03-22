/**
 * 
 */
package tests.validationIndices;

import static org.junit.Assert.*;
import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.CIndex;
import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class CIndexTest {
	private Dataset trivialTestset;
	
	@Before
	public void setup(){
		InputReader inputReader = new InputReader();
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		this.trivialTestset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		Leader leaderClusterer = new Leader();
		leaderClusterer.setEpsilon(3);
		leaderClusterer.doClustering(trivialTestset);
	}

	/**
	 * Test method for {@link validationIndices.CIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		CIndex index = new CIndex();
		float result = index.calculateIndex(trivialTestset);
		assertEquals (0, result);
		System.out.println(result);
	}

}
