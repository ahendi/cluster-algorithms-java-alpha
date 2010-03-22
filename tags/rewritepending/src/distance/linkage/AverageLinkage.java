/**
 * 
 */
package distance.linkage;

import input.FeatureVector;

import java.util.List;

import distance.DistanceMeasure;

/**
 * @author Markus
 * 
 */
public class AverageLinkage extends AbstractLinkage {

	/**
	 * @param distanceMeasure
	 */
	public AverageLinkage(DistanceMeasure distanceMeasure) {
		super(distanceMeasure);

	}

	private DistanceMeasure distanceMeasure;

	public float calculateClusterdistance(List<FeatureVector> cluster1,
			List<FeatureVector> cluster2) {
		FeatureVector point1, point2;

		float distance = 0;
		float count = 0;

		for (int i = 0; i < cluster1.size(); i++) {
			point1 = cluster1.get(i);
			for (int j = 0; j < cluster2.size(); j++) {
				point2 = cluster2.get(j);

				float tempDist = distanceMeasure.calculate(point1.getFeatures(),
						point2.getFeatures());
				distance += tempDist;
				count++;

			}

		}
		if (count == 0) {
			throw new NoElementInClusterException();
		} else {
			return distance / count;
		}

	}
	


}
