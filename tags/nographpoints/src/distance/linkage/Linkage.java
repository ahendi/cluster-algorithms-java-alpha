/**
 * 
 */
package distance.linkage;

import input.FeatureVector;

import java.util.List;

import distance.DistanceMeasure;

import output.Cluster;

/**
 * @author Markus
 *
 */
public interface Linkage {
	
	
	/**
	 * Calculate distance between two clusters represented as Lists of FeatureVectors
	 * @param cluster1
	 * @param cluster2
	 * @return
	 */
	public float calculateClusterdistance (List<FeatureVector> cluster1, List<FeatureVector> cluster2);
	
	/**
	 * Calculate distance between two clusters represented as cluster objects
	 * @param cluster1
	 * @param cluster2
	 * @return distance
	 */
	public float calculateClusterdistance (Cluster cluster1, Cluster cluster2);
}
