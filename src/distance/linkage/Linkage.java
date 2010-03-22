/**
 * 
 */
package distance.linkage;

import input.GraphElement;

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
	public float calculateClusterdistance (List<GraphElement> cluster1, List<GraphElement> cluster2);
	
	/**
	 * Calculate distance between two clusters represented as cluster objects
	 * @param cluster1
	 * @param cluster2
	 * @return distance
	 */
	public float calculateClusterdistance (Cluster cluster1, Cluster cluster2);
}
