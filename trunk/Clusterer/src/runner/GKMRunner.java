/**
 * 
 */
package runner;

import input.Dataset;
import input.InputReader;

import java.util.HashMap;
import java.util.Map;

import output.ValidationWriter;
import algorithms.GlobalKMeans;
import algorithms.KMeans;
import distance.EuclideanDistance;

/**
 * Starts a run of the kmeans algorithm with specifeid parameters and
 * saves results to a file
 * @author Markus
 *
 */
public class GKMRunner {
	
	public static void main(String[] args) {
		String inputFileName,outputFileName;
		int numOfClusters;
		if (args.length ==3){
			//read input file path
			inputFileName = args[0];
			outputFileName = args[1];
			numOfClusters = Integer.valueOf(args[2]);
		}
		else{
			throw new IllegalArgumentException();
		}
		Dataset dataset = InputReader.readFromfile(inputFileName);

		GlobalKMeans kmeans = new GlobalKMeans(new EuclideanDistance(),numOfClusters);
		kmeans.doClustering(dataset);
		InputReader.writeDatasetToFile(outputFileName , dataset);
		Map<String,String> params = new HashMap<String,String>();
		params.put("Fixed Numer Of Clusters",String.valueOf(numOfClusters));
		ValidationWriter.printValidationIndices("KMEANS", params, dataset);
		ValidationWriter.writeValidationIndice(outputFileName, "KMeans", params, dataset);

	}

}
