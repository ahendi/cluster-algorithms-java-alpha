/**
 * 
 */
package tests.validationIndices;

import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.GoodmanKruskal;
import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class GoodmanKruskalTest {

	private Dataset trivialTestset;
	@Before
	public void setUp(){
			InputReader inputReader = new InputReader();
			//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
			this.trivialTestset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
			Leader leaderClusterer = new Leader();
			leaderClusterer.setEpsilon(5);
			leaderClusterer.doClustering(trivialTestset);
	}
	
	/**
	 * Test method for {@link validationIndices.GoodmanKruskal#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		GoodmanKruskal gkCalculator = new GoodmanKruskal();
		System.out.println(gkCalculator.calculateIndex(trivialTestset));
	}

}
