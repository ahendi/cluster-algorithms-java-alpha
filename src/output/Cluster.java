/**
 * 
 */
package output;


import input.Element;
import input.FeatureVector;

import java.util.ArrayList;
import java.util.List;

import util.CalculationUtil;

import distance.DistanceMeasure;

/**
 * @author Markus
 *
 */
public class Cluster {
	private List <FeatureVector> clusterelements;
	private int clusterid;
	private float[] centroid;
	
	public Cluster(int clusterId){
		this.clusterid =clusterId;
		this.clusterelements = new ArrayList<FeatureVector>();
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clusterelements == null) ? 0 : clusterelements.hashCode());
		result = prime * result + clusterid;
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cluster other = (Cluster) obj;
		if (clusterelements == null) {
			if (other.clusterelements != null)
				return false;
		} else if (!clusterelements.equals(other.clusterelements))
			return false;
		if (clusterid != other.clusterid)
			return false;
		return true;
	}

	/**
	 * Adds a FeatureVector to a Cluster. 
	 * 
	 * @param featureVector
	 */
	public void add(FeatureVector featureVector) {
		this.clusterelements.add(featureVector);
		this.centroid = null; // devalidate centroid because it changed

	}



	/**
	 * @return number of FeatureVectors in this Cluster
	 */
	public int size() {

		return this.clusterelements.size();
	}
	

	/**
	 * Calculates the Centroid of this cluster
	 */
	public float[] getCentroid() {

		if (this.centroid == null || this.centroid.length == 0) {

			// start with the first element
			float[] sum = this.clusterelements.get(0).getFeatures();
			for (int i = 1; i < this.clusterelements.size(); i++) {
				FeatureVector currVect = this.clusterelements.get(i);
				sum = CalculationUtil.vectorAddition(sum, currVect
						.getFeatures());
			}
			// calculate center of Cluster by dividing sum by number of
			// clusterelements
			this.centroid = CalculationUtil.scalarMultiplication(
					1.0f / this.clusterelements.size(), sum);
		}
		return centroid;
	}
	//TODO is squaredErrorSum calculated this way?
	/**
	 * This method calculates the squaredErrorDistance for the cluster given a 
	 * passed distance measurement
	 */
	public float getSumOfSquaredError(DistanceMeasure dm){
		float squaredErrorSum = 0;
		float distToCenter;
		//distance of each element to clustercenter
		for (FeatureVector element : clusterelements) {
			distToCenter = dm.calculate(this.getCentroid(), element.getFeatures());
			//square because we do not want euclidean dist in this case
			squaredErrorSum += Math.pow(distToCenter,2);
		}
		return squaredErrorSum;
		
	}



	/**
	 * @return
	 */
	public List<FeatureVector> getClusterelements() {
		return clusterelements;
	}



	/**
	 * this returns the maximum distance that exists between to feature vectors in 
	 * this cluster. 
	 * @param dist This is the symetric distance measure used to calulate the distance between
	 * feature vectors
	 * 
	 * 
	 */
	public float getMaxIntraClusterDistance(DistanceMeasure dist) {
		
		FeatureVector a;
		FeatureVector b;

		float maxIntraClusterDistance =0.0f;
		for (int i= 0 ; i<this.clusterelements.size() -1 ; i++){
			a = this.clusterelements.get(i);
			for (int j = i+1 ; j<this.clusterelements.size(); j++){
				b = this.clusterelements.get(j);
				float currDist = dist.calculate(a.getFeatures(), b.getFeatures());
				if (currDist > maxIntraClusterDistance){
					maxIntraClusterDistance = currDist;
				}
				
			}
		}
		return maxIntraClusterDistance;
	}
	
	public int getClusterid() {
		return clusterid;
	}



	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}



	/**
	 * This method counts the number of Elements that are in this cluster and
	 * also in the cluster that is passed as a parameter. In other words this
	 * counts the number of elements in the intersection of the clusters
	 * @param c  the cluster that we compare with
	 */
	public int countMutualElements(Cluster c) {
		int numberOfMutualElements = 0;
		for (Element fv : this.clusterelements) {
			for(Element otherFV : c.getClusterelements()){
				if (fv.equals(otherFV)){
					numberOfMutualElements++;
				}
			}
		}
		return numberOfMutualElements;
	}
	
	

}
