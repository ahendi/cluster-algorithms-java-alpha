/**
 * 
 */
package runner;

import input.Dataset;
import input.InputReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import validationIndices.CIndex;
import validationIndices.GoodmanKruskal;
import algorithms.Leader;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputReader inputReader = new InputReader();
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		Dataset dataSet =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		Leader leaderClusterer = new Leader();
		//optimize parameters for leader
		List <Float> distances = new ArrayList<Float>((dataSet.size()* (dataSet.size()-1))/2);
		int iterationCount = 0;
		EuclideanDistance eDist = new EuclideanDistance();
		for (int i = 0; i < dataSet.size(); i++){
			for (int j = i+1; j < dataSet.size(); j++) {
				 float dist = eDist.calculate(dataSet.get(i).getFeatures(), dataSet.get(j).getFeatures());
				 distances.add(dist);
				 iterationCount++;
			}
		}
		float maxdist = Collections.max(distances); 
		System.out.println("Maxdistance between elements is" + maxdist);
		CIndex cIndex = new CIndex();
		GoodmanKruskal kruski = new GoodmanKruskal();
		List <Float> indexvalues = new ArrayList();
		for (int i = 2; i <=  maxdist; i++  ){
		//	System.out.println("Iteration " +i+" of "+ maxdist);
			leaderClusterer.setEpsilon(i);
			leaderClusterer.doClustering(dataSet);
			float index = cIndex.calculateIndex(dataSet);
			indexvalues.add(index);
			System.out.println ("The C index for epsilon = "+i + " is: "+index +" and there are "+ dataSet.getClustermap().size() + "clusters");

		}
		System.out.println("The best value for epsilon is " + Collections.min(indexvalues));

	}

}
