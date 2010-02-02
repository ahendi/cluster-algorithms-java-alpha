/**
 * 
 */
package util;

import input.Dataset;
import input.FeatureVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import output.Cluster;
import output.ValidationWriter;
import validationIndices.BPIndex;
import validationIndices.CIndex;
import validationIndices.CalinskiHarabasz;
import validationIndices.DBIndex;
import validationIndices.DunnIndex;
import validationIndices.FowlkesMallowsIndex;
import validationIndices.JaccardIndex;
import validationIndices.RandIndex;
import validationIndices.SilhouetteIndex;

/**
 * This class takes a list of clustered Datasets and calculates their indices.
 * Then it averages the results. This is usefull in cases where the algorithm
 * has to be started several times to compensate for random initialization
 * 
 * @author Markus Hofstetter
 * 
 */
public class Averager {

	public static Map<String, Float> calculateAverageIndices(
			List<Dataset> datasets) {

		if (datasets.isEmpty()) {
			throw new IllegalArgumentException(
					"Attempted to average indices but did "
							+ "not give list of clustered Datasets");
		}

		float numberOfDatasets = datasets.size();

		float cindex = 0;
		float db = 0;
		float dunnIndex = 0;
		float fowlkesMallows = 0;
		float ji = 0;
		float ri = 0;
		float si = 0;
		float bipartiteIndex = 0;
		float ch =0;

		CIndex ci = new CIndex();
		DBIndex dbindex = new DBIndex();
		DunnIndex di = new DunnIndex();
		FowlkesMallowsIndex fmIndex = new FowlkesMallowsIndex();
		JaccardIndex jaccardIndex = new JaccardIndex();
		RandIndex randIndex = new RandIndex();
		SilhouetteIndex siIndex = new SilhouetteIndex();
		BPIndex bpIndex = new BPIndex();
		CalinskiHarabasz chIndex= new CalinskiHarabasz();
		

		float numberOfFeatureVectors= 0;
		float dimensionOfVectors= 0;
		float numberOfUnclassified= 0;
		float numberOfClust = 0;


		// sum the indices
		for (Dataset dataset : datasets) {
			//indices
			cindex += ci.calculateIndex(dataset);
			db += dbindex.calculateIndex(dataset);
			dunnIndex += di.calculateIndex(dataset);
			fowlkesMallows += fmIndex.calculateIndex(dataset);
			ji += jaccardIndex.calculateIndex(dataset);
			ri += randIndex.calculateIndex(dataset);
			si += siIndex.calculateIndex(dataset);
			bipartiteIndex += bpIndex.calculateIndex(dataset);
			ch += chIndex.calculateIndex(dataset);
			
			//information values
			Map<Integer, Cluster> clustermap =dataset.getClustermap();
			numberOfFeatureVectors += dataset.size();
			dimensionOfVectors += dataset.get(0).getDimension();
			numberOfUnclassified += (clustermap.get(FeatureVector.UNCLASSIFIED) == null) ? 0 :clustermap.get(FeatureVector.UNCLASSIFIED).size();
			numberOfClust += clustermap.size();
		}
		// calculate average

		cindex = cindex / numberOfDatasets;
		db = db / numberOfDatasets;
		dunnIndex = dunnIndex / numberOfDatasets;
		fowlkesMallows = fowlkesMallows / numberOfDatasets;
		ji = ji / numberOfDatasets;
		ri = ri / numberOfDatasets;
		si = si / numberOfDatasets;
		bipartiteIndex = bipartiteIndex / numberOfDatasets;
		ch = ch / numberOfDatasets;
		
		numberOfFeatureVectors = numberOfFeatureVectors /numberOfDatasets;
		dimensionOfVectors = dimensionOfVectors/ numberOfDatasets;
		numberOfUnclassified = numberOfUnclassified / numberOfDatasets;
		numberOfClust = numberOfClust / numberOfDatasets;
		
		HashMap<String, Float> result = new HashMap<String,Float> ();
		result.put(CIndex.class.getName(), cindex);
		result.put(DBIndex.class.getName(), db);
		result.put(DunnIndex.class.getName(), dunnIndex);
		result.put(FowlkesMallowsIndex.class.getName(), fowlkesMallows);
		result.put(JaccardIndex.class.getName(), ji);
		result.put(RandIndex.class.getName(), ri);
		result.put(SilhouetteIndex.class.getName(), si);
		result.put(BPIndex.class.getName(), bipartiteIndex);
		result.put(CalinskiHarabasz.class.getName(), ch);
		
		result.put(ValidationWriter.NUM_OF_VECT, numberOfFeatureVectors);
		result.put(ValidationWriter.VECT_DIM, dimensionOfVectors);
		result.put(ValidationWriter.UNCLASSIFIED_VECTS, numberOfUnclassified);
		result.put(ValidationWriter.NUM_OF_CLUST, numberOfClust);

		return result;

	}

}
