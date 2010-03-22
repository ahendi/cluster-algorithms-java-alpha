/**
 * 
 */
package validationIndices;

import java.util.ArrayList;
import java.util.List;

import distance.EuclideanDistance;
import input.Dataset;
import input.GraphElement;

/**
 * @author Markus
 * 
 */
public class GoodmanKruskal {
	

	/**
	 * Berechnet den GoodmanKruska index eines bereits von einem
	 * Clusteringalgorithmus mit Clustereinteilungen versehenen Datensets
	 * 
	 * In the best Case this index is 1 in the worst it is -1
	 * 
	 * @param clusteredSet
	 * @return
	 */
	public float calculateIndex(Dataset clusteredSet) {
		EuclideanDistance ed = new EuclideanDistance();
		GraphElement vectI;
		GraphElement vectJ;
		float distanceIJ;
		float distanceRS;
		int differentClusterIJ;
		int differentClusterRS;
		float numOfConcordantQuadruples = 0; // # of concordant clusters
		float numOfDisconconcordantQuadruples = 0; // #of disconcordant clusters
		int numOfNeitherQuadruples = 0;
		int datasetSize = clusteredSet.size();
		List<int[]> pairs = new ArrayList<int[]>(
				(datasetSize * (datasetSize + 1)) / 2);
		float[][] distanceMatrix = new float[datasetSize][datasetSize];
		byte[][] clusterParity = new byte[datasetSize][datasetSize];
		for (int i = 0; i < datasetSize -1; i++) {
			for (int j = i + 1; j < datasetSize; j++) {
				pairs.add(new int[] { i, j });
				//we will need the distance of every pair later so let's
				//calculate it already now so we do it only once
				vectI = clusteredSet.get(i);
				vectJ = clusteredSet.get(j);
				distanceMatrix[i][j] = vectI.calculateDistance(vectJ);
				clusterParity[i][j] = (byte) ((vectI.getCalculatedClusternumber() == vectJ
						.getCalculatedClusternumber()) ? 0 : 1);
			}

		}
	//	System.out.println("second loop");
		for(int k = 0; k < pairs.size()-1 ; k++ ){
			for (int l = k+1; l < pairs.size(); l++){
				int[] ij = pairs.get(k);
				int[] rs = pairs.get(l);
				
			//	if (!(i == k && j == l)&& (!(i == l && j == k))){
					distanceIJ =distanceMatrix[ij[0]][ij[1]];
					distanceRS = distanceMatrix[rs[0]][rs[1]];
					differentClusterIJ = clusterParity[ij[0]][ij[1]];
					differentClusterRS = clusterParity[rs[0]][rs[1]];

					Boolean isConcordant = this.isConcordant(distanceIJ,
							differentClusterIJ, distanceRS, differentClusterRS);
					
				//	System.out.print("["+ij[0]+","+ij[1]+"] , ["+rs[0]+","+rs[1]+"]");
					if (isConcordant != null) {
						if (isConcordant) {
						//	System.out.println("concordnat");
							numOfConcordantQuadruples ++;
						} else{
						//	System.out.println("disconcordnat");
							numOfDisconconcordantQuadruples ++;
						}
					}else{
						numOfNeitherQuadruples ++;
						//System.out.println();
						}
//				//}
			}
		}
		
		float gkIndex = (numOfConcordantQuadruples-numOfDisconconcordantQuadruples)
				/ (numOfConcordantQuadruples + numOfDisconconcordantQuadruples);
		return gkIndex;
	}
	
	/**
	 * Calculate if a quadruple of points is concordant or disconcordant
	 * @param distIJ 
	 * @param diffClustIJ denotes if the points are in the same cluster
	 * @param distRS
	 * @param diffClustRS denotes if the points R and S are in the same cluster
	 * @return true if concordant, false if disconcordant, null otherwise
	 */
	private Boolean isConcordant (float distIJ,int diffClustIJ, float distRS,int diffClustRS  ){
		if((distIJ < distRS && diffClustIJ < diffClustRS) ||
		   (distIJ > distRS && diffClustIJ > diffClustRS)) {
			return true;
		} else if((distIJ < distRS && diffClustIJ > diffClustRS) ||
				  (distIJ > distRS && diffClustIJ < diffClustRS)){
			return false;
		} else {

			return null;
		}
		
	}

}
