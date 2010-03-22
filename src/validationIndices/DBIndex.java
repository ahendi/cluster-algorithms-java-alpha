/**
 * 
 */
package validationIndices;

import input.Dataset;
import input.GraphElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import output.Cluster;
import util.CalculationUtil;

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
		Map<Integer, GraphElement >centersMap = new HashMap<Integer,GraphElement>(); //REFACT
		//float[] averageDistance = new float[keys.size()];
		Map<Integer, Float >averageDistanceMap = new HashMap<Integer,Float>(); //REFACT
		
		// Calculate average Distance to Clustercenter
		for (Integer clustNum : keys) {
			List <GraphElement>clustermembers =clustermap.get(clustNum).getClusterelements();
		//	centers [clustNum] = this.calculateCenter(clustermembers);
			centersMap.put(clustNum, clustermap.get(clustNum).getMedoid());//REFACT
			//averageDistance[clustNum] = this.calculateAverageDist(clustermembers, 
			//		centers[clustNum], euclDist);
			averageDistanceMap.put(clustNum, this.calculateAverageDist(clustermembers, 
					centersMap.get(clustNum)));//REFACT
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
				rMatrix2[i][j] = (averageDistanceMap.get(keys.get(i)) + averageDistanceMap.get(keys.get(j)))/(centersMap.get(keys.get(i)).calculateDistance( centersMap.get(keys.get(j))));		
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


	
	private float calculateAverageDist( List<GraphElement> clustermembers, 
			GraphElement medoid){		
		float temp = 0;
		for (GraphElement currElement : clustermembers) {
			temp += medoid.calculateDistance(currElement);
		}
		return (temp / clustermembers.size());
	}
}
