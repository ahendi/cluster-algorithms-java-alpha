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
import algorithms.Leader;
import distance.DistanceMeasure;
import distance.EuclideanDistance;

/**
 * @author Markus
 *
 */
public class LeaderPanel extends AbstractAlgorithmPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3342690430234855328L;

	private JComboBox distanceMeasures;
	private JTextField epsilonInput ;
	/**
	 * 
	 */
	public LeaderPanel(ClusterCalculatorGUI clusterCalculatorGUI) {
		super(clusterCalculatorGUI);
		this.setAlgorithm(new Leader());
		JPanel configPanel = new JPanel(new GridLayout(0,2,5,5));
		JLabel measurement = new JLabel("Pleast choose your distanc measurement");
		configPanel.add(measurement);
		DistanceMeasure[] dm = new DistanceMeasure[2];
		dm[0] = new EuclideanDistance();
		distanceMeasures = new JComboBox (dm);
		configPanel.add(distanceMeasures);
		JLabel epsilon = new JLabel("Please choose the value for epsilon");
		configPanel.add(epsilon);
		epsilonInput = new JTextField();
		configPanel.add(epsilonInput);
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
		Leader leaderClusterer = new Leader();
		leaderClusterer.setDistMeasure((DistanceMeasure) this.distanceMeasures.getSelectedItem());
		leaderClusterer.setEpsilon(Float.parseFloat(this.epsilonInput.getText()));
		leaderClusterer.doClustering(this.clusCalculatorGUI.getDataSet());
		this.showResultPanel();
	}

}
