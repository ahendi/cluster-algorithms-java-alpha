/**
 * 
 */
package output;

import input.Dataset;
import input.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import validationIndices.BPIndex;
import validationIndices.CIndex;
import validationIndices.CalinskiHarabasz;
import validationIndices.DBIndex;
import validationIndices.DunnIndex;
import validationIndices.FowlkesMallowsIndex;
import validationIndices.JaccardIndex;
import validationIndices.RandIndex;
import validationIndices.SilhouetteIndex;
import algorithms.Algorithms;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * This is a util class that writes out the result of several validation indices
 * to an output
 * 
 * @author Markus
 * 
 */
public class ValidationWriter {
	
	//Index Labels
	public static final String ALGORITHM_LABEL ="Algorithm";
	public static final String CINDEX_LABEL ="CIndex";
	public static final String DBINDEX_LABEL ="DBIndex";
	public static final String DUNNINDEX_LABEL ="DunnIndex";
	public static final String FM_LABEL ="FowlkesMallowsIndex";
	public static final String JACCARD_LABEL ="JaccardIndex";
	public static final String RAND_LABEL ="RandIndex";
	public static final String SIHLOUETTE_LABEL="SilhouetteIndex";
	public static final String BIPARTIE_LABEL="BipartiteIndex";
	private static final String CH_LABEL = "CHIndex";
	//Info Labels
	public static final String NUM_OF_VECT ="Number of Feature Vectors";
	public static final String VECT_DIM ="Feature Vector Dimension";
	public static final String NUM_OF_CLUST = "Number Of Clusters calculated";
	public static final String UNCLASSIFIED_VECTS = "Unclassified FeatureVectors";
	//Param Labels
	public static final String LEADER_EPSILON_LABEL ="epsilon for leader";
	public static final String KMEANS_K_LABEL ="k";
	
	//File Names
	public static final String RESULT_FILENAME ="result.csv";
	
	

	/**
	 * Calculates and writes out the validation indices for a given clustered
	 * Dataset. A clustered Dataset is one that consists of elements which have
	 * their calculatedClusterNumber set. An den pfad des outputfiles wird .indice angehängt
	 * 
	 * @param path
	 * @param dataset
	 */
	public static void writeValidationIndice(String path, String title, Map<String,String> params, Dataset dataset) {
		path = path.concat(".indice");
		File file = new File(path);
		Map<Integer, Cluster> clustermap = dataset.getClustermap();
		String unclassified = (clustermap.get(Element.UNCLASSIFIED) == null) ? "none" : String.valueOf(clustermap.get(Element.UNCLASSIFIED).size());

		// Create file if it does not exist
		try {
			file.createNewFile();
			PrintWriter  pw = new PrintWriter(file);
			pw.println();
			pw.println("*******************"+title+"************************");
			pw.println("Number of Feature Vectors: "+ dataset.size());
			pw.println("Feature Vector Dimension: "+ dataset.get(0).getDimension());
			for (String name : params.keySet()) {
				pw.println(name+": "+ params.get(name));
			}
			pw.println("Number Of Clusters calculated: "+ clustermap.size());
			pw.println("Unclassified FeatureVectors: "+ unclassified);
			
			pw.println("-----------------------------------------------------");
			CIndex ci = new CIndex();
			float cindex = ci.calculateIndex(dataset);
			pw.println("CIndex: "+ cindex);
			
			DBIndex dbindex= new  DBIndex();
			float db = dbindex.calculateIndex(dataset);
			pw.println("DBIndex: "+ db);
			
			DunnIndex di = new DunnIndex();
			float dunnIndex = di.calculateIndex(dataset);
			pw.println("DunnIndex: "+ dunnIndex);
			
			FowlkesMallowsIndex fmiIndex = new FowlkesMallowsIndex();
			float fowlkesMallows = fmiIndex.calculateIndex(dataset);
			pw.println("FowlkesMallowsIndex: "+ fowlkesMallows);
			
			JaccardIndex jaccardIndex = new JaccardIndex();
			float ji = jaccardIndex.calculateIndex(dataset);
			pw.println("JaccardIndex: "+ ji);
			
			
			RandIndex randIndex = new RandIndex();
			float ri = randIndex.calculateIndex(dataset);
			pw.println("RandIndex: "+ ri);
			
			SilhouetteIndex siIndex = new SilhouetteIndex();
			float si = siIndex.calculateIndex(dataset);
			pw.println("Silhouette-Index: "+si);
			
			BPIndex bpIndex = new BPIndex();
			float bipartiteIndex = bpIndex.calculateIndex(dataset);
			pw.println("Bipartite-Index: "+ bipartiteIndex);
			
			pw.flush();
			pw.close();
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static synchronized void writeToCSV(String file, Algorithms algorithm ,Dataset result ,Map<String,String> params){
		
		CIndex ci = new CIndex();
		float cindex = ci.calculateIndex(result);
		
		DBIndex dbindex= new  DBIndex();
		float db = dbindex.calculateIndex(result);

		DunnIndex di = new DunnIndex();
		float dunnIndex = di.calculateIndex(result);

		
		FowlkesMallowsIndex fmIndex = new FowlkesMallowsIndex();
		float fowlkesMallows = fmIndex.calculateIndex(result);

		
		JaccardIndex jaccardIndex = new JaccardIndex();
		float ji = jaccardIndex.calculateIndex(result);

		RandIndex randIndex = new RandIndex();
		float ri = randIndex.calculateIndex(result);

		
		SilhouetteIndex siIndex = new SilhouetteIndex();
		float si = siIndex.calculateIndex(result);

		BPIndex bpIndex = new BPIndex();
		float bipartiteIndex = bpIndex.calculateIndex(result);
		
		CalinskiHarabasz chIndex = new CalinskiHarabasz();
		float ch  = chIndex.calculateIndex(result);

		String indices = cindex+"#"+db+"#"
						+dunnIndex+"#"+fowlkesMallows+"#"+ji+"#"+ri+"#"+si+"#"+bipartiteIndex+"#"+ch;
		
		
		Map<Integer, Cluster> clustermap = result.getClustermap();
		String numberOfFeatureVectors = String.valueOf(result.size());
		String dimensionOfVectors = String.valueOf(result.get(0).getDimension());
		String unclassified = (clustermap.get(Element.UNCLASSIFIED) == null) ? "0" : String.valueOf(clustermap.get(Element.UNCLASSIFIED).size());
		String numberOfClust =String.valueOf(result.getClustermap().size());
		String basicInfos = numberOfFeatureVectors+"#"+dimensionOfVectors+"#"+numberOfClust+"#"+unclassified+"#";
		
		String algorithmInfos = ValidationWriter.getAlgorithmInfos(algorithm,params);
		String line = algorithmInfos + basicInfos + indices;
		
		CSVWriter writer =null;
		String header = null;
		File output = new File(file);
		if (!output.exists()){
			 header = ALGORITHM_LABEL+"#"+ LEADER_EPSILON_LABEL +"#"+KMEANS_K_LABEL +"#"+NUM_OF_VECT+"#"+VECT_DIM+"#"+NUM_OF_CLUST+"#"+UNCLASSIFIED_VECTS+"#"+CINDEX_LABEL +"#"+DBINDEX_LABEL +"#"+DUNNINDEX_LABEL +"#"+FM_LABEL +"#"+JACCARD_LABEL +"#"+RAND_LABEL +"#"+SIHLOUETTE_LABEL +"#"+BIPARTIE_LABEL+"#"+CH_LABEL;
		
		}
		try {
			
			FileWriter fw = new FileWriter(file,true);
			 // feed in your array (or convert your data to an array)
		     String[] entries = line.split("#");
			 writer = new CSVWriter(fw, '\t');
			 if (header != null){
				 
				 writer.writeNext(header.split("#"));
			 }
			 writer.writeNext(entries);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}	

	}
	
	/**
	 * @param algorithm
	 * @param params
	 * @return
	 */
	private static String getAlgorithmInfos(Algorithms algorithm,
			Map<String, String> params) {
		
		String paramInfos;
		String epsl =params.get(ValidationWriter.LEADER_EPSILON_LABEL) == null ? "" :params.get(ValidationWriter.LEADER_EPSILON_LABEL);
		String k = params.get(ValidationWriter.KMEANS_K_LABEL) == null ? "" :params.get(ValidationWriter.KMEANS_K_LABEL);
		
		paramInfos = algorithm.toString() +"#"+epsl+"#"+k+"#";
		return paramInfos;
	}
	/**
	 * calculates and prints validation indices to system out
	 * @param title
	 * @param params
	 * @param dataset
	 */
	public static void printValidationIndices(String title, Map<String,String> params,Dataset dataset){
		float dim = dataset.get(0).getDimension();
		Map<Integer, Cluster> clustermap = dataset.getClustermap();
		String unclassified = (clustermap.get(Element.UNCLASSIFIED) == null) ? "none" : String.valueOf(clustermap.get(Element.UNCLASSIFIED).size());
		System.out.println();
		System.out.println("*******************"+title+"************************");
		System.out.println("Feature Vector Dimension: "+ dim);
		System.out.println("Number of Feature Vectors: "+ dataset.size());
		

		for (String name : params.keySet()) {
			System.out.println(name+": "+ params.get(name));
		}
		System.out.println("Number Of Clusters calculated: "+ dataset.getClustermap().size());
		System.out.println("Unclassified FeatureVectors: "+ unclassified);
		System.out.println("--------------------------------------");
		CIndex ci = new CIndex();
		float cindex = ci.calculateIndex(dataset);
		System.out.println("CIndex: "+ cindex);
		
		DBIndex dbindex= new  DBIndex();
		float db = dbindex.calculateIndex(dataset);
		System.out.println("DBIndex: "+ db);
		
		DunnIndex di = new DunnIndex();
		float dunnIndex = di.calculateIndex(dataset);
		System.out.println("DunnIndex: "+ dunnIndex);
		
		FowlkesMallowsIndex fmiIndex = new FowlkesMallowsIndex();
		float fowlkesMallows = fmiIndex.calculateIndex(dataset);
		System.out.println("FowlkesMallowsIndex: "+ fowlkesMallows);
		
		JaccardIndex jaccardIndex = new JaccardIndex();
		float ji = jaccardIndex.calculateIndex(dataset);
		System.out.println("JaccardIndex: "+ ji);
		
		
		RandIndex randIndex = new RandIndex();
		float ri = randIndex.calculateIndex(dataset);
		System.out.println("RandIndex: "+ ri);
		
		SilhouetteIndex siIndex = new SilhouetteIndex();
		float si = siIndex.calculateIndex(dataset);
		System.out.println("Silhouette-Index: "+si);
		
		BPIndex bpIndex = new BPIndex();
		float bipartiteIndex = bpIndex.calculateIndex(dataset);
		System.out.println("Bipartite-Index: "+ bipartiteIndex);
		
		CalinskiHarabasz chIndex = new CalinskiHarabasz();
		float ch = chIndex.calculateIndex(dataset);
		System.out.println("Calinski-Harabasz: "+ch);
		
//		GoodmanKruskal gkIndex = new GoodmanKruskal();
//		float gki = gkIndex.calculateIndex(dataset);
//		System.out.println("RandIndex: "+ gki);
		System.out.println("----------------------------------------------------");
	}

	public static synchronized void writeToCSV(String file, Algorithms algorithm ,Map<String,Float> indiceResults ,Map<String,String> params){
		
		float cindex = indiceResults.get(CIndex.class.getName());
		float db = indiceResults.get(DBIndex.class.getName());
		float dunnIndex = indiceResults.get(DunnIndex.class.getName());
		float fowlkesMallows = indiceResults.get(FowlkesMallowsIndex.class
				.getName());
		float ji = indiceResults.get(JaccardIndex.class.getName());
		float ri = indiceResults.get(RandIndex.class.getName());
		float si = indiceResults.get(SilhouetteIndex.class.getName());
		float bipartiteIndex = indiceResults.get(BPIndex.class.getName());
		float ch = indiceResults.get(CalinskiHarabasz.class.getName());

		String indices = cindex+"#"+db+"#"
						+dunnIndex+"#"+fowlkesMallows+"#"+ji+"#"+ri+"#"+si+"#"+bipartiteIndex+"#"+ch;
		
		

		String numberOfFeatureVectors = String.valueOf(indiceResults.get(NUM_OF_VECT));
		String dimensionOfVectors = String.valueOf(indiceResults.get(VECT_DIM));
		String unclassified = String.valueOf(indiceResults.get(UNCLASSIFIED_VECTS));
		String numberOfClust =String.valueOf(indiceResults.get(NUM_OF_CLUST));
		String basicInfos = numberOfFeatureVectors+"#"+dimensionOfVectors+"#"+numberOfClust+"#"+unclassified+"#";
		
		String algorithmInfos = ValidationWriter.getAlgorithmInfos(algorithm,params);
		String line = algorithmInfos + basicInfos + indices;
		
		CSVWriter writer =null;
		String header = null;
		File output = new File(file);
		if (!output.exists()){
			 header = ALGORITHM_LABEL+"#"+ LEADER_EPSILON_LABEL +"#"+KMEANS_K_LABEL +"#"+NUM_OF_VECT+"#"+VECT_DIM+"#"+NUM_OF_CLUST+"#"+UNCLASSIFIED_VECTS+"#"+CINDEX_LABEL +"#"+DBINDEX_LABEL +"#"+DUNNINDEX_LABEL +"#"+FM_LABEL +"#"+JACCARD_LABEL +"#"+RAND_LABEL +"#"+SIHLOUETTE_LABEL +"#"+BIPARTIE_LABEL;
		
		}
		try {
			
			FileWriter fw = new FileWriter(file,true);
			 // feed in your array (or convert your data to an array)
		     String[] entries = line.split("#");
			 writer = new CSVWriter(fw, '\t');
			 if (header != null){
				 
				 writer.writeNext(header.split("#"));
			 }
			 writer.writeNext(entries);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}	

	}
}
