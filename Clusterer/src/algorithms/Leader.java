package algorithms;

import input.Dataset;
import input.FeatureVector;

import java.util.ArrayList;
import java.util.List;

import util.CalculationUtil;

import distance.DistanceMeasure;
import distance.EuclideanDistance;


public class Leader implements ClusteringAlgorithm {
	private float epsilon;
	private List<FeatureVector> centers;
	private DistanceMeasure distMeasure = new EuclideanDistance();
	
	/**
	 * 
	 */
	public Leader() {
		// TODO Auto-generated constructor stub
	}
	
	public Leader(DistanceMeasure distMeasure, float epsilon){
		this.distMeasure = distMeasure;
		this.epsilon = epsilon;
		
	}
	
	
	public void doClustering(Dataset dataset){
		
		this.centers = new ArrayList <FeatureVector>();
		dataset.reset();
		dataset.get(0).setCalculatedClusternumber(0);
		centers.add(dataset.get(0));
		//for every vector in the dataset do
		for (int i = 1 ; i<dataset.size();i++){
			FeatureVector currentVector = dataset.get(i);
			assert(currentVector.getCalculatedClusternumber() == FeatureVector.UNCLASSIFIED);
			//calculate distances to centers
			Float[] distances = this.calculateDistance(currentVector, centers);
			//get number of cluster with smallest distance to current element
			int minIndex = CalculationUtil.getIndexOfMinElement(distances);
			float min = distances[minIndex];
			if (min <= epsilon){
				//if smallest distance to cluster smaller then epsilon add element to cluster
				currentVector.setCalculatedClusternumber(minIndex);
			} else{
				//else create new cluster
				centers.add(currentVector);
				//index of the newly added center is clusternumber
				int clusternumber = centers.size() -1;
				currentVector.setCalculatedClusternumber(clusternumber);
			}
			
		}
		
		
	}
	


	public void setEpsilon(float epsilon) {
		this.epsilon = epsilon;
	}

	private Float[] calculateDistance(FeatureVector currentFeature, List <FeatureVector> centers){
		float[] currentVector = currentFeature.getFeatures();
		Float[] distances = new Float[centers.size()];
		for (int k = 0; k < centers.size(); k++) {
			float[] centerVector = centers.get(k).getFeatures();
			distances[k] = this.distMeasure.calculate(centerVector, currentVector);
			
		}
		return distances;
				
	}
	
	public String toString(){
		return "Leader";
	}
	
	public void setDistMeasure(DistanceMeasure dist){
		this.distMeasure = dist;
	}

}
