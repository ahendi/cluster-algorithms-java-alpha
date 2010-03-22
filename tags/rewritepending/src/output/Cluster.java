/**
 * 
 */
package output;


import input.Element;
import input.GraphElement;

import java.util.ArrayList;
import java.util.List;

import distance.DistanceMeasure;

/**
 * @author Markus
 *
 */
public class Cluster {
	private List <GraphElement> clusterelements;
	private int clusterid;
	private GraphElement medoid;
	
	public Cluster(int clusterId){
		this.clusterid =clusterId;
		this.clusterelements = new ArrayList<GraphElement>();
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
	public void add(GraphElement featureVector) {
		this.clusterelements.add(featureVector);
		this.medoid = null; // devalidate centroid because it changed

	}



	/**
	 * @return number of FeatureVectors in this Cluster
	 */
	public int size() {

		return this.clusterelements.size();
	}
	

	/**
	 * Calculates the Medoid of this cluster. The medoid is the element
	 * of the cluster with the minimal average distance to all other elements.
	 *  It will be recalculated when there
	 * was an element added to the cluster before @see add()
	 */
	public GraphElement getMedoid() {

		if (this.medoid == null) {
			float minAvgDist ,currElAvg;
			GraphElement currMedoid, currElement;
			currMedoid = this.clusterelements.get(0);
			minAvgDist = currMedoid.calculateAverageDistanceToOthers();
			
			for (int i = 1; i < this.clusterelements.size(); i++) {
				currElement = this.clusterelements.get(i);
				currElAvg = currElement.calculateAverageDistanceToOthers();
				if (minAvgDist > currElAvg ){
					minAvgDist = currElAvg;
					currMedoid = currElement;
				}
			}
			this.medoid = currMedoid;
		}
			return this.medoid;

	}
	//TODO is squaredErrorSum calculated this way?
	/**
	 * This method calculates the squaredErrorDistance for the cluster given a 
	 * passed distance measurement
	 */
	public float getSumOfSquaredError(){
		float squaredErrorSum = 0;
		float distToCenter;
		//distance of each element to clustercenter
		for (GraphElement element : clusterelements) {
			distToCenter = element.calculateDistance(this.getMedoid());
			//square because we do not want euclidean dist in this case
			squaredErrorSum += Math.pow(distToCenter,2);
		}
		return squaredErrorSum;
		
	}



	/**
	 * @return
	 */
	public List<GraphElement> getClusterelements() {
		return clusterelements;
	}



	/**
	 * this returns the maximum distance that exists between to feature vectors in 
	 * this cluster. 
	 * 
	 * 
	 */
	public float getMaxIntraClusterDistance() {
		
		GraphElement a;
		GraphElement b;

		float maxIntraClusterDistance =0.0f;
		for (int i= 0 ; i<this.clusterelements.size() -1 ; i++){
			a = this.clusterelements.get(i);
			for (int j = i+1 ; j<this.clusterelements.size(); j++){
				b = this.clusterelements.get(j);
				float currDist = a.calculateDistance(b);
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
