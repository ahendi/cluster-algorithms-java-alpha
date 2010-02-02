/**
 * 
 */
package ui.guipanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.ClusterCalculatorGUI;
import algorithms.GlobalKMeans;
import distance.DistanceMeasure;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class GlobalKMeansPanel extends AbstractAlgorithmPanel  implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2240485710349439513L;
	private JTextField numOfClust;
	private JComboBox distMeasure;


	/**
	 * @param clCalculatorGUI
	 */
	public GlobalKMeansPanel(ClusterCalculatorGUI clCalculatorGUI) {
		super(clCalculatorGUI);
		
		JPanel configPanel = new JPanel(new GridLayout(0,2,5,5));
		this.setLayout(new BorderLayout());

		JLabel numOfClustLabel = new JLabel("Please specify the number of clusters");
		configPanel.add(numOfClustLabel);
		
		numOfClust = new JTextField();
		configPanel.add(numOfClust);
		
		JLabel distMeasureLabel = new JLabel("Please Chosse your distance Measure");
		configPanel.add(distMeasureLabel);
		DistanceMeasure[] distanceMeasurement = new DistanceMeasure[3];
		distanceMeasurement[0] = new EuclideanDistance();


		distMeasure = new JComboBox (distanceMeasurement);
		configPanel.add(distMeasure);
		JButton start = new JButton ("start Clustering");
		start.addActionListener(this);
		configPanel.add(start);
		this.add(configPanel,BorderLayout.NORTH);
		


	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		GlobalKMeans gkmClusterer = new GlobalKMeans((DistanceMeasure) this.distMeasure.getSelectedItem());
	
		gkmClusterer.setHaltAtNumberOfCluster(Integer.valueOf(this.numOfClust.getText()));
		gkmClusterer.doClustering(this.clusCalculatorGUI.getDataSet());
		this.showResultPanel();

	}

}
