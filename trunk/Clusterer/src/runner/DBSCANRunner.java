/**
 * 
 */
package runner;

import input.Dataset;
import input.InputReader;

import java.util.HashMap;
import java.util.Map;

import output.ValidationWriter;
import algorithms.DBSCAN;

/**
 * @author Markus
 *
 */
public class DBSCANRunner {
	
	public static void main(String[] args) {
		String inputFileName,outputFileName;
		float epsilon;
		int minNumOfPointsInCluster ;
		if (args.length ==4){
			//read input file path
			inputFileName = args[0];
			epsilon = Float.valueOf(args[1]);
			minNumOfPointsInCluster = Integer.valueOf(args[2]);
			outputFileName = args[3];
		}
		else{
			throw new IllegalArgumentException();
		}
		Dataset dataset = InputReader.readFromfile(inputFileName);

		DBSCAN dbscan = new DBSCAN();
		dbscan.setEpsilon(epsilon);
		dbscan.setMinPoints(minNumOfPointsInCluster);
		dbscan.doClustering(dataset);
		InputReader.writeDatasetToFile(outputFileName , dataset);
		Map<String,String> params = new HashMap<String,String>();
		params.put("Epsilon",String.valueOf(epsilon));
		params.put("minNumOfPoints", String.valueOf(minNumOfPointsInCluster));
		ValidationWriter.printValidationIndices("DBSCAN", params, dataset);
		ValidationWriter.writeValidationIndice(outputFileName, "DBSCAN", params, dataset);
	}

}
