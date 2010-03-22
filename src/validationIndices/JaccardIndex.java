/**
 * 
 */
package validationIndices;

import input.Dataset;

/**
 * This class calculates the Jaccard similarity coefficient for a labeld dataset
 * The index produces a result in the range [0, 1], where a value of 1.0 indicates that C and K are identical.
 * @author Markus
 * 
 */
public class JaccardIndex extends AbstractCountingPairsIndex{

	public float calculateIndex(Dataset clusteredData) {
		this.countPairs(clusteredData);
		float jaccardIndex = ((float) a) / ((float)(a + b + c));
		return jaccardIndex;

	}
	

}
