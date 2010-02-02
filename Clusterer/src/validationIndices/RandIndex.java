/**
 * 
 */
package validationIndices;

import input.Dataset;

/**
 * @author Markus
 * 
 */
public class RandIndex  extends AbstractCountingPairsIndex{

	/**
	 * The index produces a result in the range [0,1], where a value of 1.0
	 * indicates that the labels and the calculated clusters are identical. 
	 * A high value for this measure
	 * generally indicates a high level of agreement between a clustering and
	 * the annotated natural classes.
	 * 
	 * @param clusteredData
	 * @return
	 */
	public float calculateIndex(Dataset clusteredData) {
		
		this.countPairs(clusteredData);
		float randIndex = ((float) (a + d)) / ((float) (a + b + c + d));
		return randIndex;

	}

}
