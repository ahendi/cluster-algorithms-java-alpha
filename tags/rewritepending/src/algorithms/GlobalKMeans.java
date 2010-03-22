/**
 * 
 */
package algorithms;

import input.Dataset;
import input.Element;
import input.GraphElement;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import output.Cluster;
import distance.DistanceMeasure;

/**
 * @author Markus
 *
 */
public class GlobalKMeans extends KMedoids implements ClusteringAlgorithm{

	TreeMap <Float,GraphElement[]> errors = new TreeMap<Float, GraphElement[]>();
	private Integer haltAtNumberOfCluster;
	private ArrayList<GraphElement[]> calculatedCenters = new ArrayList<GraphElement[]>();

	public GlobalKMeans(){

	}
	
	public GlobalKMeans( int targetNumberOfClusters){

		this.haltAtNumberOfCluster = targetNumberOfClusters;
	}
	/* (non-Javadoc)
	 * @see algorithms.ClusteringAlgorithm#doClustering(input.Dataset)
	 */
	@Override
	public void doClustering(Dataset dataset) {
		if (this.haltAtNumberOfCluster == null){
			this.haltAtNumberOfCluster = dataset.size();
		}
		//We initialize the GKM by running KMeans with only one cluster
		this.setNumOfClusters(1);
		GraphElement[] center = this.chooseRandomElementsAsCenters(dataset);
		this.runKMedoids(dataset, center);
		// Now there is a single  Cluster and it always has number 0
		evaluateClusterResult(dataset);
		this.storeResultOfPrevIteration(this.findMinimalErrorClustering(),dataset);
		
		for ( this.numOfClusters =2; this.numOfClusters <= dataset.size() && this.numOfClusters <= this.haltAtNumberOfCluster; this.numOfClusters++){
			GraphElement[] medoids = this.findMinimalErrorClustering();
			errors = new TreeMap<Float, GraphElement[]>(); // reset map for next run
			GraphElement[] bestCenters = new GraphElement[this.numOfClusters];
			//get all the best centroids from the previous run and leave the last one empty
			for (int clusterID = 0; clusterID < bestCenters.length-1 ; clusterID++) {
				bestCenters[clusterID] = medoids[clusterID];
				bestCenters[clusterID].setCalculatedClusternumber(clusterID);
			}


			for (int i = 0; i < dataset.size(); i++){
				//we only recalculate if the candidate point is not already included in our set of best centers
				if (!centersContain(bestCenters,dataset.get(i))){
					bestCenters[this.numOfClusters-1]= dataset.get(i); //this is the one additional center we will try in this run
					this.runKMedoids(dataset, bestCenters); //carefull with the dataset reset
					this.evaluateClusterResult(dataset);
				}
			}	
			
			this.storeResultOfPrevIteration(this.findMinimalErrorClustering(),dataset);

			
		}
		
		//one final run to leave the dataset in the state where kmedoids ran with the
		//best medoid
		GraphElement[] finalmedoids = this.findMinimalErrorClustering();
		GraphElement[] bestCenters = new GraphElement[finalmedoids.length];
		//get all the best medoids from the previous run and leave the last one empty
		for (int clusterID = 0; clusterID < finalmedoids.length ; clusterID++) {
			bestCenters[clusterID] = finalmedoids[clusterID];
			bestCenters[clusterID].setCalculatedClusternumber(clusterID);
		}
		this.runKMedoids(dataset, bestCenters);
		
		
		
		
	}
	/**
	 * This method is used to store the result that had the least sum of squared errors
	 * for the given iteration. To recreate the results we can run kmeans on the dataset
	 * with the centroids as initial centers
	 * @param graphElements
	 * @param dataset
	 */
	private void storeResultOfPrevIteration(GraphElement[] graphElements, Dataset dataset) {
		this.calculatedCenters.add(graphElements);
		
	}

	/**
	 * Tests if the array of the best centers already contains the new candidate center
	 * @param bestCenters 
	 * @param candidatFeatureVectore
	 * @return true if the candidatFeatureVectore ist already contained in the bestCenters
	 * array as determined by equals
	 */
	private boolean centersContain(Element[] bestCenters, Element candidatFeatureVectore) {
		for (int i = 0; i < bestCenters.length -1 ; i++) {
			if (bestCenters[i].equals(candidatFeatureVectore)){
				return true;
			}
		}

		return false;
	}
	/**
	 * @return the centroids that minimized the squared error in the last run
	 * 
	 */
	private GraphElement[] findMinimalErrorClustering() {
		GraphElement[] result = this.errors.firstEntry().getValue();
		
		return result;
		
	}
	/**
	 * Calculate the sum of squared errors and put it in the error map for later comparison
	 * @param dataset
	 */
	private void evaluateClusterResult(Dataset dataset) {
		Map<Integer, Cluster> clusters = dataset.getClustermap();
		float overallSumOfSquaredErrors = 0;
		GraphElement[] medoids = new GraphElement[clusters.size()];
		for (Integer clusterID : clusters.keySet()) {
			Cluster currCluster = clusters.get(clusterID);
			overallSumOfSquaredErrors += currCluster.getSumOfSquaredError();
			medoids[clusterID] = currCluster.getMedoid();
		}
		
		errors.put(overallSumOfSquaredErrors, medoids);
	}

	public Integer getHaltAtNumberOfCluster() {
		return haltAtNumberOfCluster;
	}

	public void setHaltAtNumberOfCluster(Integer haltAtNumberOfCluster) {
		this.haltAtNumberOfCluster = haltAtNumberOfCluster;
	}
	
	/**
	 * This method can be used to recalculate the best result for a certain number of
	 * clusters provided the number of Clusters is smaller than the number of iterations
	 * the gkm has made. 
	 * @param dataset
	 * @param numberOfClusters
	 */
	public void getPartialResult(Dataset dataset, int numberOfClusters){
		GraphElement[] centers = this.calculatedCenters.get(numberOfClusters-1);
		if (centers == null){
			throw new IllegalStateException("You must run the gkm for a number of iterations that is biger than numberOfClusters");
		}
		this.runKMedoids(dataset, centers);
	}
	
	
	

}
