package input;

import java.util.Arrays;

import distance.DistanceMeasure;
import distance.EuclideanDistance;

public class GraphElement extends Element{

	/**
	 * Creates a new FeatureVector by reading the contents of the passed array
	 * @param fileLine
	 */
	public GraphElement(int id, String[] fileLine, boolean isResult) {
		super(id);
		
		this.label = (Integer.parseInt(fileLine[0]));

		if (isResult)	{
			//the line contains a result that was already calculated in
			//addition to the label (*.result usually)
			this.calculatedClusternumber = (Integer.parseInt(fileLine[1]));

		} else {
			//the line is not the result of a clustering process (*test,*.valid)
			this.calculatedClusternumber = Element.UNCLASSIFIED;

		}
		
	}

	public GraphElement(int id ,String[] fileLine) {
		//in the default case we read in FeatureVectors that do not
		//have a calculated result yet
		this(id,fileLine, false);
	}


	/**
	 *Creates a FV with the specified Features and the specified CalculatedClusterNumber
	 *Used for the KMEans algorithm. Do not use to create real FV
	 
	 * @param clusterID the calculatedClusternumber
	 */
	public GraphElement(int elementid ,Integer clusterID) {
		super(elementid);
		this.calculatedClusternumber = clusterID;
	}



	
	/**
	 * Determines if the point vect is calculated to be in the same cluster as
	 * this point.
	 * @param vect
	 * @return false if not in same cluster or clusters are not yet calculated
	 */
	public boolean isSameCluster(Element vect) {
		if (this.calculatedClusternumber == Element.UNCLASSIFIED
				&& vect.getCalculatedClusternumber() == Element.UNCLASSIFIED) {
			//clusternumbers have not been calculated
			return false;
		} else if (vect.getCalculatedClusternumber() == this.calculatedClusternumber) {
			//clusternumber has been calculated to be the same
			return true;
		} else {
			//clusternumbers have been calculated but are different
			return false;
		}
	}



	/**
	 * Calculates the dist to another Element using the distance matrix
	 */
	@Override
	public float calculateDistance(Element other) {
		if (other instanceof GraphElement){
			return Dataset.getDistanceMatrix()[this.getId()][other.getId()];
			
		} else{
			throw new RuntimeException("could not calculate distance between FeatureVector and other element");
		}

	}

	/**
	 * calculates the average distance to all other elements by using the imported
	 * Distance Matrix which is a square matrix
	 */
	public float calculateAverageDistanceToOthers() {
		float[] distances = Dataset.getDistanceMatrix()[this.getId()];
		float sum = 0.0f;
		for (int i = 0; i < distances.length; i++) {
			sum += distances[i];
		}
		return (sum /distances.length); 
		
	}

	/**
	 * @return
	 */
	public float getDimension() {
		// TODO Auto-generated method stub
		return Float.NaN;
	}
	

	


}
