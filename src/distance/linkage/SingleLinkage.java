/**
 * 
 */
package distance.linkage;

import input.FeatureVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import distance.DistanceMeasure;

/**
 * @author Markus
 *
 */
public class SingleLinkage extends AbstractLinkage {
	
	
	
	/**
	 * @param distanceMeasure
	 */
	public SingleLinkage(DistanceMeasure distanceMeasure) {
		super(distanceMeasure);

	}
	/**
	 * compares every point in the cluster with all the points in the other cluster.
	 * The two points with the lowest distance define the clusterdistance
	 */
	public float calculateClusterdistance (List<FeatureVector> cluster1, List<FeatureVector> cluster2){
		FeatureVector point1 , point2;

		ArrayList<Float> distances = new ArrayList<Float>();
		for (int i = 0; i < cluster1.size(); i++) {
			point1 = cluster1.get(i);
			for (int j = 0; j < cluster2.size(); j++){
				point2 = cluster2.get(j);
				
				float tempDist= distanceMeasure.calculate(point1.getFeatures(), point2.getFeatures());
				distances.add(tempDist);
				
			}
			
		}
		assert (distances.size() == cluster1.size()  * cluster2.size());
		return Collections.min(distances);
		
		
	}





}
