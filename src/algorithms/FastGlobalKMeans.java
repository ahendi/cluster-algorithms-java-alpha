/**
 * 
 */
package algorithms;

import input.Dataset;
import input.FeatureVector;

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
public class FastGlobalKMeans extends KMeans {
	
	private Integer limit;

	/**
	 * @param euclideanDistance
	 */
	public FastGlobalKMeans(EuclideanDistance euclideanDistance) {
		super(euclideanDistance);
	}
	
	/**
	 * @param euclideanDistance
	 */
	public FastGlobalKMeans(EuclideanDistance euclideanDistance, Integer limit) {
		super(euclideanDistance);
		setLimit(limit);
	}

	public void doClustering(Dataset dataset) {
		
		this.setNumOfClusters(1);
		this.centers = this.chooseRandomElementsAsCenters(dataset);
		this.runKMeans(dataset, this.centers);
		if (this.limit == null){
			this.limit = dataset.size();
		}
		//---------------------------------------------

		for ( this.numOfClusters =2; this.numOfClusters <= limit ; this.numOfClusters++){
		TreeMap<Float, Integer> minimalErrorReduction = new TreeMap<Float, Integer>();

		for (int n = 0; n < dataset.size(); n++) {
			float total = 0.0f; // this is the lower bound for error reduction (b) 
			FeatureVector xn = dataset.get(n);
			float d;
			FeatureVector clostestCenter;
			for (int k = 0; k < dataset.size(); k++) {
				// calculate d
				FeatureVector currentPoint = dataset.get(k);
				clostestCenter = this.centers[currentPoint
						.getCalculatedClusternumber()];
				assert (clostestCenter.getCalculatedClusternumber() == currentPoint
						.getCalculatedClusternumber());
				d = this.distanceMeasure.calculate(
						clostestCenter.getFeatures(), currentPoint
								.getFeatures());
				d = d * d;
				float loss = this.distanceMeasure.calculate(xn.getFeatures(),
						currentPoint.getFeatures());
				loss = loss * loss;
				float errordelta = d - loss; //choosing xn as center would decrease sse by at
											//least this ammount
				total += Math.max(errordelta, 0);

			}
			minimalErrorReduction.put(total, n);
		}
		FeatureVector bestPoint = dataset.get(minimalErrorReduction.lastEntry().getValue());
		
		this.centers = Arrays.copyOf(this.centers, this.centers.length+1);
		this.centers[this.centers.length -1] = bestPoint;
		this.runKMeans(dataset, this.centers);
		Map<String, String> params = new HashMap<String, String>();
		ValidationWriter.writeToCSV("FGKMResult.csv", Algorithms.FastGlobalKMeans, dataset, params);
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
