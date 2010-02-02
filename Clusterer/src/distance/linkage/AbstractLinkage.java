/**
 * 
 */
package distance.linkage;

import output.Cluster;
import distance.DistanceMeasure;

/**
 * @author Markus
 *
 */
public abstract class AbstractLinkage implements Linkage  {
	
	protected DistanceMeasure distanceMeasure;
	
	public AbstractLinkage(DistanceMeasure distanceMeasure){
		this.distanceMeasure = distanceMeasure;
		
	}
	
	/* (non-Javadoc)
	 * @see distance.linkage.Linkage#calculateClusterdistance(output.Cluster, output.Cluster)
	 */
	@Override
	public float calculateClusterdistance(Cluster cluster1, Cluster cluster2) {
		return this.calculateClusterdistance(cluster1.getClusterelements(),
				cluster2.getClusterelements());
		
	}
	
	public String toString(){
		return this.getClass().getSimpleName();
	}

}
