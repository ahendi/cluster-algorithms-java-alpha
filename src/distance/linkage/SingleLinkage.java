/**
 * 
 */
package distance.linkage;

import input.GraphElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Markus
 *
 */
public class SingleLinkage extends AbstractLinkage {
	
	
	
	/**
	 * @param distanceMeasure
	 */
	public SingleLinkage() {


	}
	/**
	 * compares every point in the cluster with all the points in the other cluster.
	 * The two points with the lowest distance define the clusterdistance
	 */
	public float calculateClusterdistance (List<GraphElement> cluster1, List<GraphElement> cluster2){
		GraphElement point1 , point2;

		ArrayList<Float> distances = new ArrayList<Float>();
		for (int i = 0; i < cluster1.size(); i++) {
			point1 = cluster1.get(i);
			for (int j = 0; j < cluster2.size(); j++){
				point2 = cluster2.get(j);
				
				float tempDist = point1.calculateDistance(point2);
				distances.add(tempDist);
				
			}
			
		}
		assert (distances.size() == cluster1.size()  * cluster2.size());
		return Collections.min(distances);
		
		
	}





}
