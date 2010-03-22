/**
 * 
 */
package ui.guipanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.ClusterCalculatorGUI;

import algorithms.DBSCAN;

import distance.DistanceMeasure;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class DBSCANPanel extends AbstractAlgorithmPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5050728033547488126L;
	private JTextField epsilonInput;
	private JTextField numPointsInput;

	/**
	 *  creates a configuration panel for teh dbscan algorithm
	 */
	public DBSCANPanel(ClusterCalculatorGUI clusterCalculatorGUI) {
		super(clusterCalculatorGUI);
		this.setAlgorithm(new DBSCAN());
		JPanel configPanel = new JPanel(new GridLayout(0,2,5,5));

		JLabel measurement = new JLabel("Pleast choose your distanc measurement");
		configPanel.add(measurement);
		DistanceMeasure[] dm = new DistanceMeasure[2];
		dm[0] = new EuclideanDistance();
		JComboBox distanceMeasures = new JComboBox (dm);
		configPanel.add(distanceMeasures);
		JLabel epsilon  = new JLabel("Please choose the value for epsilon");
		configPanel.add(epsilon);
		epsilonInput = new JTextField();
		configPanel.add(epsilonInput);
		JLabel minPoints = new JLabel ("Please choose the minimal number of points in a cluster");
		configPanel.add(minPoints);
		numPointsInput = new JTextField();
		configPanel.add(numPointsInput);
		JButton start = new JButton ("start Clustering");

		start.addActionListener(this);
		configPanel.add(start);
		this.add(configPanel,BorderLayout.NORTH);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		DBSCAN dbscanClusterer = new DBSCAN();
		dbscanClusterer.setEpsilon(Float.valueOf(epsilonInput.getText()));
		dbscanClusterer.setMinPoints(Integer.valueOf(this.numPointsInput.getText()));
		this.showResultPanel();
	}

}
