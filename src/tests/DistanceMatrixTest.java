/**
 * 
 */
package tests;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

/**
 * @author Markus
 *
 */
public class DistanceMatrixTest {
	
	@Test
	public float[][] testImportMatrix(){
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactoryImpl.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(new File ("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\tete.dxl"));

			NodeList content = doc.getElementsByTagName("content");
			Node element = content.item(0);
			NamedNodeMap attributes = element.getAttributes();
			Node cols = attributes.getNamedItem("cols");
			Node rows = attributes.getNamedItem("rows");
			int numberOfCols = Integer.parseInt(cols.getNodeValue());
			int numberOfRows = Integer.parseInt(rows.getNodeValue());
			float[][] distanceMatrix = new float [numberOfRows][numberOfCols];
			String values = element.getTextContent();
			String[] distances = values.split(";");
			for (int i = 0; i < numberOfRows ; i++) {
				for (int j = 0; j < numberOfCols; j++) {
					float currentValue = Float.parseFloat(distances[i*numberOfCols+j]);
					distanceMatrix[i][j] = currentValue;
				}
				
			}
			

			assertEquals (distances.length , numberOfCols*numberOfRows);
			return distanceMatrix;
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
