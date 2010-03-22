package input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import output.Cluster;

import distance.DistanceMeasure;
import distance.EuclideanDistance;

public class Dataset implements Iterable<FeatureVector>{
	
	private List<FeatureVector> vectors = new ArrayList<FeatureVector>();
	private DistanceMeasure distanceMeasure = new EuclideanDistance();
	private Map <Integer,Cluster> clusterMap;
	private Map <Integer,Cluster> labelMap;
	private SortedMap<Float, List<Integer>>[] neighbourMatrix;
	private float[][] distanceMatrix = null;
	
	public void add(FeatureVector fv){
		this.vectors.add(fv);
		}
	
	public int size(){
		return this.vectors.size();
		}

	public FeatureVector get(int i){
		return this.vectors.get(i);
	}


	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<FeatureVector> iterator() {
		// TODO Auto-generated method stub
		return this.vectors.iterator();
	}
	
	public String toString(){
		this.createClusterMap();
		
		StringBuilder stringBuilder = new StringBuilder("Clusternumber \t \t \t Number of Elements \n");
		int numOfPoints = 0;
		int totalNumOfPoints =0;
		Set<Integer> keys = this.clusterMap.keySet();
		for (Integer number : keys) {
			numOfPoints =  this.clusterMap.get(number).size();
			totalNumOfPoints += numOfPoints;
			stringBuilder.append(number + " \t \t \t \t \t "+ numOfPoints +"\n");
		}
		stringBuilder.append("\n");
		stringBuilder.append("Total Number Of Clusters: \t "+keys.size()+ "\n");
		stringBuilder.append("Total Number of Points in Clusters: \t "+totalNumOfPoints);
		return stringBuilder.toString();
	}
	
	private void createClusterMap(){
		HashMap<Integer, Cluster> clusters = new HashMap<Integer, Cluster>();
		for (FeatureVector featureVector : this.vectors) {
			Integer currentClusternumber =featureVector.getCalculatedClusternumber();
			Cluster temp  = clusters.get(currentClusternumber);
			assert (currentClusternumber != Element.UNCLASSIFIED);
			if (temp != null){
				temp.add(featureVector);
			} else {
				temp =  new Cluster(currentClusternumber);
				temp.add(featureVector);
				clusters.put(currentClusternumber,temp);
			}
			
		}
		this.clusterMap = clusters;
	}
	
	/**
	 * @return a map that maps the label to its cluster
	 */
	public Map<Integer, Cluster> getLabelMap() {
		if (this.labelMap == null) {
			HashMap<Integer, Cluster> actualClusters = new HashMap<Integer, Cluster>();
			for (FeatureVector current : this.vectors) {
				Integer currentLabelNumber = current.getLabel();
				Cluster temp = actualClusters.get(currentLabelNumber);
				if (temp != null) {
					temp.add(current);
				} else {
					temp = new Cluster(currentLabelNumber);
					temp.add(current);
					actualClusters.put(currentLabelNumber, temp);
				}

			}
			this.labelMap = actualClusters;
		}
		return this.labelMap;
	}
	
	public Map<Integer, Cluster> getClustermap() {
		this.createClusterMap();
		return this.clusterMap;
	}
	
	/**
	 * This method reorderes the Data in a random way. This can be usefull if the
	 * results of a algorithm that is applied to the dataset depend on the
	 * order of it's elements.
	 */
	public void randomizeOrder(){
		int originalSize = this.vectors.size();
		List<FeatureVector> randomizedList = new ArrayList<FeatureVector>(originalSize);
		for(int i = 0; i < originalSize; i++){
			int rand = (int) Math.floor( Math.random() * this.vectors.size());
			randomizedList.add(this.vectors.remove(rand));
		} 
		if (this.vectors.size() == 0 && randomizedList.size() ==originalSize){
			this.vectors = randomizedList;
			this.clusterMap = null;
			this.neighbourMatrix = null;
			
		} else {
			throw new IllegalStateException();
		}
		
	}
	/**
	 * For every element in the dataset this method calculates the distance to all
	 * the other elements. It is assumed that the used distance measurement is symetric.
	 * Therefore only an upper diagonal matrix is created. The distances are
	 * then stored in a sorted map so that it is clear which distance belongs 
	 * to which element (via the index).
	 * This method is useful for finding the n-nearest neighbours of an element
	 * of finding all elements that are within a certain radius to the element.
	 * 
	 * @return
	 */
	private void createSortedDistanceList() {
		float dist;
		if (this.distanceMatrix == null) {
			this.createDistanceMatrix();
		}
		SortedMap<Float, List<Integer>>[] sortedMap = new SortedMap[this.size()];
		for (int i = 0; i < this.size(); i++) {
			sortedMap[i] = new TreeMap<Float,List<Integer>>();
			for (int j = 0; j < this.size(); j++) {
				if (i != j) {
					dist = this.getDistance(i, j);
					List <Integer >l = sortedMap[i].get(dist);
					if (l == null){
						sortedMap[i].put(dist, l = new ArrayList<Integer>());
					}
					l.add(j);
				}
			}

		}
		this.neighbourMatrix = sortedMap;

	}

	/**
	 * @param neighbourMatrix the neighbourMatrix to set
	 */
	private void setNeighbourMatrix(SortedMap<Float, List<Integer>>[] neighbourMatrix) {
		this.neighbourMatrix = neighbourMatrix;
	}

	/**
	 * @return the neighbourMatrix
	 */
	public SortedMap<Float, List<Integer>>[] getNeighbourMatrix() {
		if (this.neighbourMatrix == null){
			this.createSortedDistanceList();
		}
		return this.neighbourMatrix;
	}
//Distance Matrix and Funcionallity 
	
	private float[][] getDistanceMatrix() {
		if (this.distanceMatrix == null){
			this.createDistanceMatrix();
		}
		return distanceMatrix;
	}

	/**
	 * Diese Methode erstellt eine Distanzmatrix aller Elemente in dem Datenset
	 */
	private void createDistanceMatrix() {
		float distance;
		this.distanceMatrix = new float[this.vectors.size()][this.vectors.size()];
		for (int i = 0; i < this.vectors.size() - 1; i++) {
			for (int j = i + 1; j < this.vectors.size(); j++) {

				distance = this.distanceMeasure.calculate(this.vectors.get(i)
						.getFeatures(), this.vectors.get(j).getFeatures());
				this.distanceMatrix[i][j] = distance;
			}
		}

	}

	private void setDistanceMatrix(float[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}
	
	/**
	 * This method is used to normalize querys on the distance Matirx. That Way only
	 * the upper half of the matrix has to be stored since we expect the
	 * distance measurement to be symetrical.
	 * @param pointId1 index of one of the points that we are interested in in the dataset
	 * @param pointId2 index of the other of the points that we are interested in in the dataset
	 * @return distance between the two points
	 */
	private float getDistance (int pointId1, int pointId2){
		if (pointId1 < pointId2){
			return this.distanceMatrix[pointId1][pointId2];
		} else {
			return this.distanceMatrix[pointId2][pointId1];
		}
	}
	
	public void reset(){
		for (Element fv : vectors) {
			fv.setCalculatedClusternumber(Element.UNCLASSIFIED);
			
		}
		this.clusterMap = null;
		this.distanceMatrix = null;
		this.neighbourMatrix = null;
	}
	/**
	 * 
	 * @returna a list of all the points in this dataset
	 */
	public List <FeatureVector> getAllPoints(){
		return this.vectors;
	}
	
}
