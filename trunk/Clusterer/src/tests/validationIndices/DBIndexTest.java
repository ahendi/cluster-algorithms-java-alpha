/**
 * 
 */
package tests.validationIndices;

import static org.junit.Assert.*;
import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.DBIndex;

import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class DBIndexTest {
	private Dataset trivialTestset;
	
	@Before
	public void setup(){
		InputReader inputReader = new InputReader();
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		this.trivialTestset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		Leader leaderClusterer = new Leader();
		leaderClusterer.setEpsilon(7);
		leaderClusterer.doClustering(trivialTestset);
	}

	/**
	 * Test method for {@link validationIndices.DBIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		DBIndex dbIndex = new DBIndex();
		float result = dbIndex.calculateIndex(this.trivialTestset);
		System.out.println("DBIndex = "+ result);
	}

}
