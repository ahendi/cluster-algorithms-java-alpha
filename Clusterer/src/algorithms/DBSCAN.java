/**
 * 
 */
package algorithms;

import input.Dataset;
import input.FeatureVector;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Markus
 *
 */
public class DBSCAN implements ClusteringAlgorithm {

	float epsilon;
	int minPoints;
	
	
	
	public float getEpsilon() {
		return epsilon;
	}
	public void setEpsilon(float epsilon) {
		this.epsilon = epsilon;
	}
	public int getMinPoints() {
		return minPoints;
	}
	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}
	/* (non-Javadoc)
	 * @see algorithms.ClusteringAlgorithm#doClustering(input.Dataset)
	 */
	@Override
	public void doClustering(Dataset dataset) {
		int currentClusterId = 0;
		dataset.reset();
		for (int i = 0; i < dataset.size(); i++) {
			FeatureVector featureVector = dataset.get(i);
			if(featureVector.getCalculatedClusternumber() == FeatureVector.UNCLASSIFIED){
				if(this.expandCluster(i,currentClusterId, this.epsilon, this.minPoints,  dataset ));
				currentClusterId ++;
			}
			
		}

	}
	/**
	 * @param featureVector
	 * @param currentClusterId
	 * @param gamma2
	 * @param minPoints2
	 */
	private boolean expandCluster(int pointnumber, int currentClusterId, float gamma2, int minPoints2, Dataset dataset) {
		LinkedList<Integer> seeds = (LinkedList<Integer>) this.getNeighbours(dataset,  pointnumber);
		if (seeds.size() < this.minPoints){ //no core point
			dataset.get(pointnumber).setCalculatedClusternumber(FeatureVector.NOISE);
			return false;
		}
		else{
			for (Integer i : seeds) {
				dataset.get(i).setCalculatedClusternumber(currentClusterId);
			}
			seeds.remove((Integer)pointnumber);
			while (!seeds.isEmpty()){
				int currentPoint = seeds.getFirst();
				Collection<Integer> result = this.getNeighbours(dataset, currentPoint);
			
				if (result.size() >= this.minPoints){
					for (Integer resultPId : result) {
						FeatureVector resultP = dataset.get(resultPId);
						if (resultP.getCalculatedClusternumber() == FeatureVector.UNCLASSIFIED){
							seeds.addLast(resultPId);
							resultP.setCalculatedClusternumber(currentClusterId);
						}
						if (resultP.getCalculatedClusternumber() == FeatureVector.NOISE){
							resultP.setCalculatedClusternumber(currentClusterId);
						}
					}
				}
				seeds.remove((Integer)currentPoint);
			}
			return true;
		}
		
	}
	/**
	 * This method queries the dataset for the neighbours of the passed element (id) that
	 * satisfie  [dist (id, x) <= epsilon for x element of dataset] .
	 * The method returns the ids of the points that fullfill the condition
	 * @param dataset
	 * @param featureVector
	 * @param id
	 * @return list of ids that are in the epsilon neighborhood
	 */
	private Collection<Integer> getNeighbours(Dataset dataset, int id) {
		SortedMap<Float, List<Integer>> neigbourList = dataset.getNeighbourMatrix()[id];
		List<Integer> result = new LinkedList<Integer>();
		//because the api returns strictly smaller we add  a small value. 
		Collection <List<Integer>>closepoints = neigbourList.headMap(this.epsilon +0.0000000000000001f).values();
		for (List<Integer> list : closepoints) {
			result.addAll(list);
		}
		return result;													  
		
	}
	
	public String toString(){
		return "DBSCAN";
	}
	
	

}
