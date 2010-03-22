/**
 * 
 */
package algorithms;

import input.Dataset;
import input.Element;
import input.GraphElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import output.Cluster;
import util.CalculationUtil;

/**
 * @author Markus
 * 
 */
public class KMedoids implements ClusteringAlgorithm {

	protected Integer numOfClusters;
	protected GraphElement[] centers;

	//Constructors -------------------------------
	public KMedoids(){
	}
	public KMedoids (int numOfClusters){

		this.numOfClusters = numOfClusters;
	}
	

	//Getters and Setters -----------------------


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
		GraphElement[] randomCentroids = this.chooseRandomElementsAsCenters(dataset);
		this.runKMedoids(dataset,randomCentroids );
	

	}

	/**
	 * @param centers
	 * @param recalcCenters
	 * @return true if the Element on position x of the centers array is equal to
	 *         the the Elements at position x in the racalcCenters array.
	 *         Also returns true if one or both parameters are <code> null</code>
	 */
	private boolean centersChanged(GraphElement[] centers,
			GraphElement[] recalcCenters) {
		if (centers == null || recalcCenters == null){
			return true;
		}
		for (int i = 0; i < centers.length; i++) {
			boolean isEqualCenter;
			
			isEqualCenter = centers[i].equals(recalcCenters[i]);
			if (!isEqualCenter) {
				return true; // center has changed
			}
		}
		return false; // all centers are the same as before
	}

	protected GraphElement[] chooseRandomElementsAsCenters(Dataset dataset) {
		List<GraphElement> candidatPoints = new ArrayList<GraphElement>();
		candidatPoints.addAll(dataset.getAllPoints());
		GraphElement[] centers = new GraphElement[this.numOfClusters];
		for (int i = 0; i < this.numOfClusters; i++) {
			int randElement = (int) Math.floor(Math.random()
					* candidatPoints.size());
			Element center = candidatPoints.get(randElement);
			center.setCalculatedClusternumber(i);
			centers[i] = candidatPoints.get(randElement);
			candidatPoints.remove(randElement);
		}
		return centers;
	}



	private void assingPointsToClosestCenter(Dataset dataset,
			GraphElement[] centers) {
		assert (centers[centers.length-1] != null);
		for (GraphElement currFeatureVector : dataset) {
			Float[] distances = new Float[centers.length];
			for (int i = 0; i < distances.length; i++) {
				GraphElement currCenter = centers[i];
				distances[i] = currCenter.calculateDistance(currFeatureVector);
				

			}
			int closestId = CalculationUtil.getIndexOfMinElement(distances);
			currFeatureVector.setCalculatedClusternumber(closestId);
			assert (centers[closestId].getCalculatedClusternumber() == closestId);
		}
		assert (dataset.getClustermap().keySet().size() == this.numOfClusters);
	}
	
	/**
	 * For every cluster in the dataset calculate its Medoid
	 * @param dataset
	 * @return
	 */
	private  GraphElement[] calculateCenters(Dataset dataset){
		GraphElement[] recalcCenters = new GraphElement[this.numOfClusters];
		Map<Integer, Cluster> clusters = dataset.getClustermap();
		for (Integer clusterID : clusters.keySet()) {

			GraphElement newMedoid = (clusters
					.get(clusterID).getMedoid());
			assert (newMedoid.getCalculatedClusternumber() == clusterID);
			recalcCenters[clusterID] = newMedoid;
		}
		return recalcCenters;
		
	}
	
	/**
	 * Starts a run of the KMeans algortihm. It runs as long as there is 
	 * a change in the positioning of the centers. The initial centers 
	 * are stored in the field centers and are recalculated every iteration.
	 * @param dataset
	 * @param initialMedoid
	 */
	public void runKMedoids (Dataset dataset, GraphElement[] initialMedoid){
	dataset.reset();
	GraphElement[] oldCenters = null;
	this.centers = initialMedoid;
		while (centersChanged(oldCenters, this.centers)) {
			oldCenters = this.centers;
			this.assingPointsToClosestCenter(dataset, oldCenters);
			this.centers = this.calculateCenters(dataset);
		}
	}
	


}
