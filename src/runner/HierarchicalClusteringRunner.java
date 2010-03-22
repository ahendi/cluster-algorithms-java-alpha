package runner;

import input.Dataset;
import input.InputReader;

import java.util.HashMap;
import java.util.Map;

import distance.EuclideanDistance;
import distance.linkage.SingleLinkage;

import output.ValidationWriter;
import algorithms.HierarchicalClustering;

/**
 * Starts a run of the kmeans algorithm with specifeid parameters and saves
 * results to a file
 * 
 * @author Markus
 * 
 */
public class HierarchicalClusteringRunner {

	public static void main(String[] args) {
		String inputFileName, outputFileName;
		Integer numOfClusters = null;
		if (args.length == 3) {
			// read input file path
			inputFileName = args[0];
			outputFileName = args[1];
			numOfClusters = Integer.valueOf(args[2]);
		} else if (args.length == 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}

		else {
			throw new IllegalArgumentException();
		}
		Dataset dataset = InputReader.readFromfile(inputFileName);
		boolean printSteps = true;
		HierarchicalClustering hc = new HierarchicalClustering(
				new SingleLinkage(new EuclideanDistance()),printSteps);
		hc.setLimit(numOfClusters);
		hc.doClustering(dataset);
		InputReader.writeDatasetToFile(outputFileName, dataset);
		Map<String, String> params = new HashMap<String, String>();
		params.put(ValidationWriter.KMEANS_K_LABEL, String.valueOf(numOfClusters));
		//useless since we should write out every step
//		ValidationWriter
//				.printValidationIndices("Hierarchical", params, dataset);
//		ValidationWriter.writeValidationIndice(outputFileName, "Hierarchical",
//				params, dataset);

	}

}
