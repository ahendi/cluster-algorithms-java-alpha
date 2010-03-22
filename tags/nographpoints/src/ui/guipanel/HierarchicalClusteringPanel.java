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

import ui.ClusterCalculatorGUI;
import algorithms.HierarchicalClustering;
import distance.EuclideanDistance;
import distance.linkage.AverageLinkage;
import distance.linkage.CompleteLinkage;
import distance.linkage.Linkage;
import distance.linkage.SingleLinkage;

/**
 * @author Markus
 *
 */
public class HierarchicalClusteringPanel extends AbstractAlgorithmPanel implements ActionListener  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 483812108216291399L;
	private JComboBox linkage;

	/**
	 * 
	 */
	public HierarchicalClusteringPanel(ClusterCalculatorGUI clusterCalcGui) {
		super(clusterCalcGui);
		JPanel configPanel = new JPanel(new GridLayout(0,2,5,5));
		JLabel measurement = new JLabel("Please choose your distance linkage");
		configPanel.add(measurement);
		Linkage[] linkage = new Linkage[3];
		linkage[0] = new SingleLinkage(new EuclideanDistance());
		linkage[1]  = new AverageLinkage(new EuclideanDistance());
		linkage[2] = new CompleteLinkage(new EuclideanDistance());
		this.linkage = new JComboBox (linkage);
		configPanel.add(this.linkage);
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
		HierarchicalClustering hc = new HierarchicalClustering(new SingleLinkage(new EuclideanDistance()));
		this.setAlgorithm(hc);
		hc.setLinkage((Linkage) this.linkage.getSelectedItem());
		if (this.clusCalculatorGUI.getDataSet() != null){
			System.out.println("Started Cluster calculation");
		hc.doClustering(this.clusCalculatorGUI.getDataSet());
		}
		this.showResultPanel();
		
		
	}

}
