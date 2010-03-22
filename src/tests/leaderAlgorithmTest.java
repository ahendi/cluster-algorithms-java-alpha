/**
 * 
 */
package tests;

import input.Dataset;
import input.Element;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import algorithms.Leader;
import static org.junit.Assert.*;

/**
 * @author Markus
 *
 */
public class leaderAlgorithmTest {
	
	private Dataset testset;
	
	@Before
	public void setUp(){
			InputReader inputReader = new InputReader();
			this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
				
	}
	
	
	
	@Test
	public void testleaderClustering(){
		for (Element featureVector : testset) {
			assertEquals(-1 ,featureVector.getCalculatedClusternumber() );
		}
		Leader leaderClusterer = new Leader();
		leaderClusterer.setEpsilon(14.6f);
		leaderClusterer.doClustering(testset);
		for (Element featureVector : testset) {
			 assertFalse(-1 == featureVector.getCalculatedClusternumber());
		}
		System.out.print(testset.toString());
	}

}
