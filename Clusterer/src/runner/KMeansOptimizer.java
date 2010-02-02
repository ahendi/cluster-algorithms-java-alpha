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
import algorithms.KMeans;
import algorithms.Leader;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class KMeansOptimizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputReader inputReader = new InputReader();
		//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		Dataset dataSet =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		KMeans kmeansClusterer = new KMeans();
		kmeansClusterer.setDistanceMeasure(new EuclideanDistance());
		

		CIndex cIndex = new CIndex();
		float globalMinIndex =1.0f ;
		int globalMinNumberclust = 0;
		GoodmanKruskal kruski = new GoodmanKruskal();
		List <Float> indexvalues = new ArrayList();
		for (int i = 2; i <=  40; i++  ){
			System.out.println("Iteration " +i+" of 40");
			kmeansClusterer.setNumOfClusters(i);
			for (int j = 0; j <5; j++){
				kmeansClusterer.doClustering(dataSet);
				float index = cIndex.calculateIndex(dataSet);
				if (index < globalMinIndex){
					globalMinIndex = index;
					globalMinNumberclust = i;
				}
				indexvalues.add(index);
				System.out.println ("The C index for K-Means with "+i + " clusters is: "+index );
			}

			

		}
		System.out.println("The best value for numOfClust is " + globalMinNumberclust + " and the smallest value of the cIndex was "+globalMinIndex);

	}

}
