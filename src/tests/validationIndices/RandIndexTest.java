/**
 * 
 */
package tests.validationIndices;

import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.RandIndex;
import algorithms.KMedoids;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class RandIndexTest {
	private Dataset trivialTestset;

	
	@Before
	public void setup(){
		InputReader inputReader = new InputReader();
	
		this.trivialTestset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		KMedoids kmeans = new KMedoids(new EuclideanDistance(),100) ;
		kmeans.doClustering(trivialTestset);

	}

	/**
	 * Test method for {@link validationIndices.DBIndex#calculateIndex(input.Dataset)}.
	 */
	@Test
	public void testCalculateIndex() {
		RandIndex ji = new RandIndex();
		float result = ji.calculateIndex(this.trivialTestset);
		System.out.println(this.trivialTestset);
		System.out.println( "Rand Index = "+ result);
	}


}
