package runner;

import input.Dataset;
import input.InputReader;
import validationIndices.CIndex;
import validationIndices.RandIndex;
import algorithms.GlobalKMeans;
import distance.EuclideanDistance;

	/**
	 * @author Markus
	 *
	 */
	public class GlobalKMeansRunner {

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			InputReader inputReader = new InputReader();
			//this.testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
			
			//todo import distance matrix
			Dataset dataSet =InputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\easy.valid");
			GlobalKMeans gkmeansClusterer = new GlobalKMeans();
		
			CIndex cIndex = new CIndex();
			RandIndex randIndex = new RandIndex();
			float bestIndex = 0.0f;
			int best_NumClust = 1;
			gkmeansClusterer.doClustering(dataSet);
			for (int i = 1; i <= dataSet.size(); i++) {
				gkmeansClusterer.getPartialResult(dataSet, i)	;			
				float index = randIndex.calculateIndex(dataSet);
				System.out.println("The Rand index for "+ i+" clusters is "+ index);
				if (index >= bestIndex){
					bestIndex = index;
					best_NumClust = i;
					
				}
					
			}
			System.out.println("The best number of cluster is " + best_NumClust +" with a rand index of "+ bestIndex);
		

	}


}
