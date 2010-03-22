/**
 * 
 */
package validationIndices;

import input.Dataset;
import input.FeatureVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import output.Cluster;
import util.CalculationUtil;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class DBIndex {
	
	/**
	 * This method calculates the Davis-Boudin index. As Input it requires a
	 * clustered Dataset
	 * @param clusteredDataset
	 * @return the value of the DB index between [0, infinity) with
	 * zero being a sign for a good cluster
	 */
	public float calculateIndex(Dataset clusteredDataset){
		
		Map<Integer,Cluster> clustermap = clusteredDataset.getClustermap();
		ArrayList<Integer> keys = new ArrayList<Integer>(clustermap.keySet());
		//float[][] centers = new float[keys.size()][];
		Map<Integer, float[] >centersMap = new HashMap<Integer,float[]>(); //REFACT
		//float[] averageDistance = new float[keys.size()];
		Map<Integer, Float >averageDistanceMap = new HashMap<Integer,Float>(); //REFACT
		
		// Calculate average Distance to Clustercenter
		EuclideanDistance euclDist = new EuclideanDistance();
		for (Integer clustNum : keys) {
			List <FeatureVector>clustermembers =clustermap.get(clustNum).getClusterelements();
		//	centers [clustNum] = this.calculateCenter(clustermembers);
			centersMap.put(clustNum, clustermap.get(clustNum).getCentroid());//REFACT
			//averageDistance[clustNum] = this.calculateAverageDist(clustermembers, 
			//		centers[clustNum], euclDist);
			averageDistanceMap.put(clustNum, this.calculateAverageDist(clustermembers, 
					centersMap.get(clustNum), euclDist));//REFACT
		}
		
		//Calculate compactness in relation do distance of every clusterpair
	//	float[][] rMatrix = new float[keys.size()][keys.size()];
		float[][] rMatrix2 = new float[keys.size()][keys.size()];//REFACT
		for (int i =0 ; i < keys.size()-1; i++){
			for (int j = i+1 ; j < keys.size(); j++){
			//	rMatrix[i][j] = (averageDistance[i] + averageDistance[j])/
			//					euclDist.calculate(centers[i], centers[j]);
				//Die Indizes müssten nochmal angeschaut werden. Werden wirklich alle clusternummmern korrekt
				//besucht
				rMatrix2[i][j] = (averageDistanceMap.get(keys.get(i)) + averageDistanceMap.get(keys.get(j)))/euclDist.calculate(centersMap.get(keys.get(i)), centersMap.get(keys.get(j)));		
			}
		}
//		assert Arrays.equals(rMatrix, rMatrix2);
		float[] maxR = new float[keys.size()];
		for (int i = 0; i < rMatrix2.length; i++) {
			maxR[i]= CalculationUtil.getMaxElement(rMatrix2[i]);
		}
		float result = 0;
		for (int i = 0; i < maxR.length; i++) {
			result += maxR[i];
		}	
		
		
		return result/keys.size();
		
	}

	/**
	 * calculates the center of a given list of FeatureVectors
	 * @param clustermembers not empty list of FeatureVectors
	 * @return
	 */
	private float[] calculateCenter(List<FeatureVector> clustermembers){
		float[] temp = clustermembers.get(0).getFeatures();
		float[] currentVector;
		//need at least 2 elements else we do'nt have to add
		for (int i = 1; i < clustermembers.size(); i++) {
			currentVector   = clustermembers.get(i).getFeatures();
			temp = CalculationUtil.vectorAddition(temp,currentVector );
		}
		return  CalculationUtil.scalarMultiplication(1.0f/clustermembers.size(), temp);
	}
	
	private float calculateAverageDist( List<FeatureVector> clustermembers, 
			float[] center, EuclideanDistance euclDist){		
		float temp = 0;
		for (FeatureVector featureVector : clustermembers) {
			temp += euclDist.calculate(center, featureVector.getFeatures());
		}
		return (temp / clustermembers.size());
	}
}
