package input;

import java.util.Arrays;

public class FeatureVector extends Element{
	public static final int UNCLASSIFIED = -2;
	public static final int NOISE = -1;

	private int calculatedClusternumber;
	private float[] features;

	/**
	 * Creates a new FeatureVector by reading the contents of the passed array
	 * @param fileLine
	 */
	public FeatureVector(String[] fileLine, boolean isResult) {
		this.label = (Integer.parseInt(fileLine[0]));
		int startIndexOfFeatures;
		if (isResult)	{
			//the line contains a result that was already calculated in
			//addition to the label (*.result usually)
			this.calculatedClusternumber = (Integer.parseInt(fileLine[1]));
			startIndexOfFeatures = 2;
		} else {
			//the line is not the result of a clustering process (*test,*.valid)
			this.calculatedClusternumber = FeatureVector.UNCLASSIFIED;
			startIndexOfFeatures = 1;
		}
		
		this.features = new float[fileLine.length - startIndexOfFeatures];
		for (int i = startIndexOfFeatures; i < fileLine.length; i++) {
			String[] temp = fileLine[i].split(":");
			this.features[i - startIndexOfFeatures] = Float.parseFloat(temp[1]);

		}
	}

	public FeatureVector(String[] fileLine) {
		//in the default case we read in FeatureVectors that do not
		//have a calculated result yet
		this(fileLine, false);
	}


	/**
	 *Creates a FV with the specified Features and the specified CalculatedClusterNumber
	 *Used for the KMEans algorithm. Do not use to create real FV
	 * @param center
	 * @param clusterID the calculatedClusternumber
	 */
	public FeatureVector(float[]features, Integer clusterID) {
		this.features = features;
		this.calculatedClusternumber = clusterID;
	}

	public int getDimension() {
		return this.features.length;
	}




	private int getClusternumber() {
		return label;
	}

	private void setClusternumber(int clusternumber) {
		this.label = clusternumber;
	}

	public float[] getFeatures() {
		return this.features;
	}
	
	public int getCalculatedClusternumber() {
		return calculatedClusternumber;
	}

	public void setCalculatedClusternumber(int calculatedClusternumber) {
		this.calculatedClusternumber = calculatedClusternumber;
	}

	/**
	 * Determines if the point vect is calculated to be in the same cluster as
	 * this point.
	 * @param vect
	 * @return false if not in same cluster or clusters are not yet calculated
	 */
	public boolean isSameCluster(FeatureVector vect) {
		if (this.calculatedClusternumber == FeatureVector.UNCLASSIFIED
				&& vect.getCalculatedClusternumber() == FeatureVector.UNCLASSIFIED) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(features);
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
		FeatureVector other = (FeatureVector) obj;
		if (!Arrays.equals(features, other.features))
			return false;
		return true;
	}
	


}
