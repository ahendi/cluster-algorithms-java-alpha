package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

public class InputReader {
	
	
	public static Dataset  readFromfile(String path, boolean isResult){
		Dataset dataset = new Dataset();
		FeatureVector featureVector;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			while (line != null){
				String[] splitLine = line.split(" ");
				featureVector = new FeatureVector(splitLine,isResult);
				dataset.add(featureVector);
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dataset;
	}
	
	public static Dataset readFromfile(String path){
		//as default we do not read in results
		return InputReader.readFromfile(path , false);
	}
	
	public static void writeDatasetToFile(String outputPathAndName, Dataset dataset){

		try {
			 File file = new File(outputPathAndName);
			    
		        // Create file if it does not exist
		        boolean success = file.createNewFile();
		        if (success) {
		            // File did not exist and was created
		        } else {
		            // File already exists
		        }

			FileWriter  bw =  new FileWriter(file);
			for (FeatureVector featureVector : dataset) {
				StringBuilder line = new StringBuilder();
				line.append(featureVector.getLabel());
				line.append(" ");
				line.append(featureVector.getCalculatedClusternumber());
				line.append(" ");
				float[] features = featureVector.getFeatures();
				for (int i = 0; i < features.length; i++) {
					line.append(i+1+":"+features[i]+" ");
					
				}
				line.append("\n");
				bw.write(line.toString());
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static float[][] importDistances(String pathToXMLFileWithDistances) {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactoryImpl
				.newInstance();
		float[][] distanceMatrix =null;
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document doc = documentBuilder
					.parse(new File(
							"C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\tete.dxl"));

			NodeList content = doc.getElementsByTagName("content");
			Node element = content.item(0);
			NamedNodeMap attributes = element.getAttributes();
			Node cols = attributes.getNamedItem("cols");
			Node rows = attributes.getNamedItem("rows");
			int numberOfCols = Integer.parseInt(cols.getNodeValue());
			int numberOfRows = Integer.parseInt(rows.getNodeValue());
			distanceMatrix = new float[numberOfRows][numberOfCols];
			String values = element.getTextContent();
			String[] distances = values.split(";");
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfCols; j++) {
					float currentValue = Float.parseFloat(distances[i
							* numberOfCols + j]);
					distanceMatrix[i][j] = currentValue;
				}

			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//cant really do much on this one
			e.printStackTrace();
		}
		return distanceMatrix;
	} 
	

}
