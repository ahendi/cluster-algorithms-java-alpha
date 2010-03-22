/**
 * 
 */
package algorithms;

import input.Dataset;
import input.GraphElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import output.ValidationWriter;

import distance.EuclideanDistance;

/**
 * @author Markus
 * 
 */
public class FastGlobalKMedoids extends KMedoids {
	
	/**
	 * The maximum of clusters to be created. execution stops when this number is reached
	 */
	private Integer limit;

	/**
	 * @param euclideanDistance
	 */
	public FastGlobalKMedoids() {
		
	}
	
	/**
	 * @param euclideanDistance
	 */
	public FastGlobalKMedoids(Integer limit) {
		
		setLimit(limit);
	}

	public void doClustering(Dataset dataset) {
		
		this.setNumOfClusters(1);
		this.centers = this.chooseRandomElementsAsCenters(dataset);
		this.runKMedoids(dataset, this.centers);
		if (this.limit == null){
			this.limit = dataset.size();
		}
		//---------------------------------------------

		for ( this.numOfClusters =2; this.numOfClusters <= limit ; this.numOfClusters++){
		TreeMap<Float, Integer> minimalErrorReduction = new TreeMap<Float, Integer>();

		for (int n = 0; n < dataset.size(); n++) {
			float total = 0.0f; // this is the lower bound for error reduction (b) 
			GraphElement xn = dataset.get(n);
			float d;
			GraphElement clostestCenter;
			for (int k = 0; k < dataset.size(); k++) {
				// calculate d
				GraphElement currentPoint = dataset.get(k);
				clostestCenter = this.centers[currentPoint
						.getCalculatedClusternumber()];
				assert (clostestCenter.getCalculatedClusternumber() == currentPoint
						.getCalculatedClusternumber());
				//d is distance of current point to its current clustermedoid
				d = clostestCenter.calculateDistance(currentPoint);
				d = d * d;
				float loss = xn.calculateDistance(currentPoint);
				loss = loss * loss;
				float errordelta = d - loss; //choosing xn as center would decrease sse by at
											//least this ammount
				total += Math.max(errordelta, 0);

			}
			minimalErrorReduction.put(total, n);
		}
		GraphElement bestPoint = dataset.get(minimalErrorReduction.lastEntry().getValue());
		
		this.centers = Arrays.copyOf(this.centers, this.centers.length+1);
		this.centers[this.centers.length -1] = bestPoint;
		this.runKMedoids(dataset, this.centers);
		Map<String, String> params = new HashMap<String, String>();
		ValidationWriter.writeToCSV("FGKMedoidResult.csv", Algorithms.FastGlobalKMedoids, dataset, params);
		}
	}

	/**
	 * @param this is the maximum ammount of Clusters that will be produced. If not set the
	 * algorithm runs till every element has its own cluster
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}
}
