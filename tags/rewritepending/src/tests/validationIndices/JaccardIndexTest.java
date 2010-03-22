/**
 * 
 */
package tests.validationIndices;

import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import distance.EuclideanDistance;

import validationIndices.DBIndex;
import validationIndices.JaccardIndex;
import algorithms.KMedoids;
import algorithms.Leader;

/**
 * @author Markus
 *
 */
public class JaccardIndexTest {
	private Dataset trivialTestset;

	
	@Before
	public void setup(){
		InputReader inputReader = new InputReader();
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		this.trivialTestset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\easy.valid");
		KMedoids kmeans = new KMedoids(new EuclideanDistance(),3) ;
		kmeans.doClustering(trivialTestset);

	}

	/**
	 * Test method for {@link validationIndices.DBIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		JaccardIndex ji = new JaccardIndex();
		float result = ji.calculateIndex(this.trivialTestset);
		System.out.println( "Jaccard Index = "+ result);
	}


}
