/**
 * 
 */
package runner;

import input.Dataset;
import input.InputReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import output.ValidationWriter;
import algorithms.Algorithms;
import algorithms.KMedoids;

/**
 * Starts a run of the kmeans algorithm with specifeid parameters and
 * saves results to a file
 * @author Markus
 *
 */
public class KMeansRunner {
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		String inputFileName,outputFileName,distanceMatrixFileName;
		int numOfClusters;
		if (args.length ==4){
			//read input file path
			inputFileName = args[0];
			outputFileName = args[1];
			numOfClusters = Integer.valueOf(args[2]);
			distanceMatrixFileName = args[3];
		}
		else{
			throw new IllegalArgumentException();
		}
		Dataset dataset = InputReader.readFromfile(inputFileName);
		float[][] distanceMatrix = InputReader.importDistances(distanceMatrixFileName);
		Dataset.setDistanceMatrix(distanceMatrix);

		KMedoids kmeans = new KMedoids(numOfClusters);
		kmeans.doClustering(dataset);
		InputReader.writeDatasetToFile(outputFileName , dataset);
		Map<String,String> params = new HashMap<String,String>();
		params.put(ValidationWriter.KMEANS_K_LABEL,String.valueOf(numOfClusters));
		ValidationWriter.printValidationIndices("KMEANS", params, dataset);
		ValidationWriter.writeToCSV("kmeansResults.csv", Algorithms.KMedoids, dataset, params);
		ValidationWriter.writeValidationIndice(outputFileName, "KMeans", params, dataset);

	}

}
