/**
 * 
 */
package validationIndices;

import input.Dataset;
import input.GraphElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import output.Cluster;
import distance.EuclideanDistance;

/**
 * @author Markus
 * interner index,
 *Range [-1,1], The better the clustering the closer the index is to 1
 *If There is only 1  cluster in the dataset return NaN
 *See Paper Kaufman and Rousseeuw 
 */
public class SilhouetteIndex {
	
	public float calculateIndex(Dataset clusteredData) {
		Map<Integer, Cluster> clustermap = clusteredData.getClustermap();
		EuclideanDistance ed = new EuclideanDistance();
		float avgSilhouetteOverAllPoints = 0;
		if (clustermap.size() ==1){
			//Index is not defined for k=1. needs at least 2 clusters
			return Float.NaN;
		}
		for (GraphElement currentElement : clusteredData) {
			//for the current element get its cluster
			 Cluster contCluster = clustermap.get(currentElement.getCalculatedClusternumber());
			 //calculate average dist to other elements in the cluster
			
			 float avgInClusterDist = 0f, dist = 0f ;
			 int counter=0;
			 for (GraphElement clusterElement : contCluster.getClusterelements()){
				 // dist to every element except self
				 if (clusterElement != currentElement){

					 dist = currentElement.calculateDistance(clusterElement);
					 avgInClusterDist += dist;
					 counter++;
				 }
			 }
			 assert (counter == contCluster.size()-1):"Element with id "+currentElement.getId()+" could not be found in the cluster with its asigned clusternumber "+currentElement.getCalculatedClusternumber();
			 if (counter >0){
			 avgInClusterDist = avgInClusterDist /counter; //this is value ai
			 }
			 //for all clusters that the current element is not containded in
			 float avgOutClusterDist = 0f;
			 List<Float> avgOutClustDists = new ArrayList<Float>();
			 for (Integer currClusternumber : clustermap.keySet()){
				 if (currClusternumber != currentElement.getCalculatedClusternumber()){
					 Cluster otherCluster = clustermap.get(currClusternumber);
					 //calculate average dist to elements
					 for(GraphElement clusterElement : otherCluster.getClusterelements()){
						dist = clusterElement.calculateDistance(currentElement);
						avgOutClusterDist += dist;
					 }
					  avgOutClustDists.add(avgOutClusterDist/otherCluster.size());
				 }
			 }
			 assert (avgOutClustDists.size() == clustermap.size()-1);
			 //Find the min value of average distance to other clusters if there are any
			 //else return not a number
			 if (avgOutClustDists.isEmpty()){
				 return Float.NaN;
			 }
			 float bi = Collections.min(avgOutClustDists);
			 //si for current element:
			float si;
			// if we only have one element in our cluster it makes sense to set
			// si = 0
			if (contCluster.size() == 1) {
				si = 0.0f;
			} else {
				si = (bi - avgInClusterDist) / Math.max(bi, avgInClusterDist);
			}
			 avgSilhouetteOverAllPoints += si;
		}
		//average si over all points
		return  avgSilhouetteOverAllPoints / clusteredData.size();
	}
	
	

}
