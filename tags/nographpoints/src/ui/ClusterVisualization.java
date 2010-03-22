/**
 * 
 */
package ui;

import input.Dataset;
import input.FeatureVector;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import output.Cluster;

/**
 * @author Markus
 *Primitive visualization for two dimensional vectors
 */
public class ClusterVisualization extends Canvas{
	
	private Color[] colors;
	private Dataset clusteredDataset;

	
	/**
	 * 
	 */
	public ClusterVisualization(Dataset clusteredDataset) {
		
		this.clusteredDataset = clusteredDataset;
		this.setBackground(Color.WHITE);

		
	}

	/**
	 * @param size
	 */
	private void generateRandomColors(int size) {
		this.colors = new Color[size];
		float hue = 0.0f;
		for (int i = 0; i<size; i++){
			colors[i] = Color.getHSBColor( hue, 1.0F, 1.0F );
			hue += 1.0f/size;
		}
		
	}
	
	public void paint(Graphics g){
		Set<Integer> keys = this.clusteredDataset.getClustermap().keySet();
		generateRandomColors(keys.size());
		for (Integer clusterId : keys) {
			this.drawCluster(clusterId, clusteredDataset.getClustermap().get(clusterId),g);
		}
	}

	/**
	 * @param clusternumber
	 * @param cluster
	 * @param g 
	 */
	private void drawCluster(Integer clusternumber, Cluster cluster, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(this.colors[clusternumber]);
		List<FeatureVector> clusterelements = cluster.getClusterelements();
		for (FeatureVector featureVector : clusterelements) {
			float x = featureVector.getFeatures()[0] *10;
			float y = featureVector.getFeatures()[1]* 10;
			Rectangle2D.Float rect = new Rectangle2D.Float(x,y,2,2);
			g2.draw(rect);
		}
	}

}
