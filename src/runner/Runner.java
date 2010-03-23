/**
 * 
 */
package runner;

import input.Dataset;
import input.InputReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import output.ValidationWriter;
import util.Averager;
import algorithms.Algorithms;
import algorithms.FastGlobalKMedoids;
import algorithms.HierarchicalClustering;
import algorithms.KMedoids;
import algorithms.Leader;
import distance.EuclideanDistance;
import distance.linkage.SingleLinkage;

/**
 * @author Markus
 *
 */
public class Runner {

	/**
	 * used to run one clustering algorithm from command line
	 * @param args first argument is the algorithm second is the input file and third is the output file
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		if (args.length < 3){
			throw new IllegalArgumentException("Incorrect number of Argumetns");
		} else{
			String algorithm = args[0];
			// read input file path
			String inputFileName = args[1];
			String outputFileName = args[2];
			//translate the string to an enum
			Algorithms alg = Enum.valueOf(Algorithms.class, algorithm);
			Dataset dataset = InputReader.readFromfile(inputFileName);
			Integer upperClusterLimit,numOfClusters;
			Float epsilon; 
			float[][] distanceMatrix;
			String distancematrixfile;
			Map<String, String> params = new HashMap<String, String>();
			
			switch (alg) {
			case Leader:
				Leader leader = new Leader();
				epsilon = Float.valueOf(args[3]); //leader needs an eps value
				leader.setEpsilon(epsilon);
				leader.setDistMeasure(new EuclideanDistance());
				List<Dataset> culmulatedResults = new ArrayList<Dataset>();
				for (int i = 0; i < 10; i++) {
					dataset = InputReader.readFromfile(inputFileName);
					dataset.randomizeOrder();
					leader.doClustering(dataset);
					InputReader.writeDatasetToFile(outputFileName+"_"+i , dataset);
					culmulatedResults.add(dataset);
					
				}
				Map<String, Float> indices = Averager.calculateAverageIndices(culmulatedResults);
				
				params = new HashMap<String,String>();
				params.put(ValidationWriter.LEADER_EPSILON_LABEL,String.valueOf(epsilon));
				//TODO Fix this to calc average index too
				ValidationWriter.writeValidationIndice(outputFileName, "Leader", params, dataset);
				//write result to csv because it is not done within the algorithm itself
				ValidationWriter.writeToCSV("result.csv", Algorithms.Leader, indices, params);
				
				break;
			case KMedoids:
				distancematrixfile = args[3];
				distanceMatrix = InputReader.importDistances(distancematrixfile);
				Dataset.setDistanceMatrix(distanceMatrix);
				numOfClusters = Integer.valueOf(args[4]);//Kmeans needs a number of clusters 
				KMedoids kmeans = new KMedoids(numOfClusters);
				kmeans.doClustering(dataset);
				InputReader.writeDatasetToFile(outputFileName , dataset);
				params = new HashMap<String,String>();
				params.put(ValidationWriter.KMEANS_K_LABEL,String.valueOf(numOfClusters));
				ValidationWriter.printValidationIndices("KMedoids", params, dataset);
				ValidationWriter.writeToCSV("kmedoidsResults.csv", Algorithms.KMedoids, dataset, params);
				ValidationWriter.writeValidationIndice(outputFileName, "KMedoids", params, dataset);
				break;
			case FastGlobalKMedoids:
				//Integer upperClusterLimit = (args.length ==4) ? Integer.valueOf(args[3]) : null;
				FastGlobalKMedoids fgkm = new FastGlobalKMedoids();
				distancematrixfile = args[3];
				distanceMatrix = InputReader.importDistances(distancematrixfile);
				Dataset.setDistanceMatrix(distanceMatrix);
				upperClusterLimit = (args.length ==5) ? Integer.valueOf(args[4]) : null;
				fgkm.setLimit(upperClusterLimit);
				fgkm.doClustering(dataset);
				InputReader.writeDatasetToFile(outputFileName, dataset);
				ValidationWriter.writeValidationIndice(outputFileName, "FGKMedoid", params, dataset);
				break;
			case GlobalKMeans:
				
				break;
			case DBSCAN:
				
				break;
			case Hierarchical:
				
				HierarchicalClustering hc = new HierarchicalClustering(
						new SingleLinkage(),true);
				upperClusterLimit = (args.length ==4) ? Integer.valueOf(args[3]) : null;
				hc.setLimit(upperClusterLimit);
				hc.doClustering(dataset);
				params = new HashMap<String, String>();
				params.put(ValidationWriter.KMEANS_K_LABEL, String.valueOf(upperClusterLimit));
				InputReader.writeDatasetToFile(outputFileName, dataset);
				break;
				
			default:
	
				break;
			}
		}
		

	}

}
