/**
 * 
 */
package validationIndices;

import java.util.Arrays;

import distance.EuclideanDistance;
import input.Dataset;
import input.GraphElement;

/**
 * @author Markus Hofstetter
 * The better the clustering the smaller the value of this index
 */
public class CIndex {

	public float calculateIndex(Dataset clusteredData) {
		EuclideanDistance edist = new EuclideanDistance();
		int numOfSameClustParis = 0; //number of pairs of elements in same cluster (a)
		float gamma = 0;  //sum of distance of elements in same cluster
		int datasetSize = clusteredData.size();
		GraphElement vectI;
		GraphElement vectJ;
		float dist;
		//create an array in the size of the number of pairs of elements
		float distances [] = new float[((datasetSize-1)*datasetSize)/2];
		int pairnumber = 0;
		//for every pair of points
		for (int i = 0; i < datasetSize - 1; i++) {
			for (int j = i+1; j < datasetSize; j++) {
				vectI = clusteredData.get(i);
				vectJ = clusteredData.get(j);
				dist = vectI.calculateDistance(vectJ);
				distances[pairnumber] = dist;
				if (vectI.isSameCluster(vectJ)){
					numOfSameClustParis ++; 
					//add distance to total sum of distance of elements in same cluster
					gamma += dist;
				}
				pairnumber ++;
				
			}
		}
		//sort ascending
		Arrays.sort(distances);
		float min = 0, max = 0;
		for (int i = 0; i < numOfSameClustParis ;i++) {
			min += distances[i]; //sum of the a smallest distances
			max += distances[(distances.length -1) -i]; //sum of the a largest dist
		}
		float cIndex = (gamma-min) / (max -min);
		return cIndex;
	}

}
