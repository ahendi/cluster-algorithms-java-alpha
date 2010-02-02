/**
 * 
 */
package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import output.Cluster;
import util.CalculationUtil;


import distance.DistanceMeasure;

import input.Dataset;
import input.FeatureVector;

/**
 * @author Markus
 * 
 */
public class KMeans implements ClusteringAlgorithm {

	protected DistanceMeasure distanceMeasure;
	protected Integer numOfClusters;
	protected FeatureVector[] centers;

	//Constructors -------------------------------
	public KMeans(){
	}
	public KMeans (DistanceMeasure dm, int numOfClusters){
		this.distanceMeasure = dm;
		this.numOfClusters = numOfClusters;
	}
	
	public KMeans (DistanceMeasure dm){
		this.distanceMeasure = dm;
	}
	
	//Getters and Setters -----------------------
	public DistanceMeasure getDistanceMeasure() {
		return distanceMeasure;
	}

	public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}

	public int getNumOfClusters() {
		return numOfClusters;
	}

	public void setNumOfClusters(Integer numOfClusters) {
		this.numOfClusters = numOfClusters;
	}
	
	//Methods ------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.ClusteringAlgorithm#doClustering(input.Dataset)
	 */
	@Override
	public void doClustering(Dataset dataset) {
		dataset.reset();
		FeatureVector[] randomCentroids = this.chooseRandomElementsAsCenters(dataset);
		this.runKMeans(dataset,randomCentroids );
	

	}

	/**
	 * @param centers
	 * @param recalcCenters
	 * @return true if the point on position x of the centers array is equal to
	 *         the the point at position x in the racalcCenters array.
	 *         Also returns true if one or both parameters are <code> null</code>
	 */
	private boolean centersChanged(FeatureVector[] centers,
			FeatureVector[] recalcCenters) {
		if (centers == null || recalcCenters == null){
			return true;
		}
		for (int i = 0; i < centers.length; i++) {
			boolean isEqualCenter;
			isEqualCenter = Arrays.equals(centers[i].getFeatures(),
					recalcCenters[i].getFeatures());
			if (!isEqualCenter) {
				return true; // center has changed
			}
		}
		return false; // all centers are the same as before
	}

	protected FeatureVector[] chooseRandomElementsAsCenters(Dataset dataset) {
		List<FeatureVector> candidatPoints = new ArrayList<FeatureVector>();
		candidatPoints.addAll(dataset.getAllPoints());
		FeatureVector[] centers = new FeatureVector[this.numOfClusters];
		for (int i = 0; i < this.numOfClusters; i++) {
			int randElement = (int) Math.floor(Math.random()
					* candidatPoints.size());
			FeatureVector center = candidatPoints.get(randElement);
			center.setCalculatedClusternumber(i);
			centers[i] = candidatPoints.get(randElement);
			candidatPoints.remove(randElement);
		}
		return centers;
	}

	private float[] calculateCenter(Cluster cluster) {
		return cluster.getCentroid();
	}

	private void assingPointsToClosestCenter(Dataset dataset,
			FeatureVector[] centers) {
		assert (centers[centers.length-1] != null);
		for (FeatureVector currFeatureVector : dataset) {
			Float[] distances = new Float[centers.length];
			for (int i = 0; i < distances.length; i++) {
				FeatureVector currCenter = centers[i];
				distances[i] = distanceMeasure.calculate(currCenter
						.getFeatures(), currFeatureVector.getFeatures());

			}
			int closestId = CalculationUtil.getIndexOfMinElement(distances);
			currFeatureVector.setCalculatedClusternumber(closestId);
			assert (centers[closestId].getCalculatedClusternumber() == closestId);
		}
		assert (dataset.getClustermap().keySet().size() == this.numOfClusters);
	}
	
	/**
	 * For every cluster in the dataset calculate its center
	 * @param dataset
	 * @return
	 */
	private  FeatureVector[] calculateCenters(Dataset dataset){
		FeatureVector[] recalcCenters = new FeatureVector[this.numOfClusters];
		Map<Integer, Cluster> clusters = dataset.getClustermap();
		for (Integer clusterID : clusters.keySet()) {

			float[] newCenter = this.calculateCenter(clusters
					.get(clusterID));

			recalcCenters[clusterID] = new FeatureVector(newCenter,
					clusterID);
		}
		return recalcCenters;
		
	}
	
	/**
	 * Starts a run of the KMeans algortihm. It runs as long as there is 
	 * a change in the positioning of the centers. The initial centers 
	 * are stored in the field centers and are recalculated every iteration.
	 * @param dataset
	 * @param initialCentroids
	 */
	public void runKMeans (Dataset dataset, FeatureVector[] initialCentroids){
	dataset.reset();
	FeatureVector[] oldCenters = null;
	this.centers = initialCentroids;
		while (centersChanged(oldCenters, this.centers)) {
			oldCenters = this.centers;
			this.assingPointsToClosestCenter(dataset, oldCenters);
			this.centers = this.calculateCenters(dataset);
		}
	}
	
	/**
	 * Overloaded method to run it without wraping the centers in a feature vector first
	 * @param dataset 
	 * @param initialCentroids two dimensional array. The position of the float array
	 * in the enclosing array determines the number of the cluster the float[] is the
	 * centerpoint of
	 */
	
	public void runKMeans(Dataset dataset, float[][] initialCentroids) {
		FeatureVector[] bestCenters = new FeatureVector[initialCentroids.length];
		for (int clusterID = 0; clusterID < initialCentroids.length; clusterID++) {
			bestCenters[clusterID] = new FeatureVector(
					initialCentroids[clusterID], clusterID);
		}
		this.runKMeans(dataset, bestCenters);
	}

}
