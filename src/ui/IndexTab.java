/**
 * 
 */
package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import input.Dataset;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import validationIndices.RandIndex;

/**
 * @author Markus
 *
 */
public class IndexTab extends JPanel implements ActionListener{

	private ClusterCalculatorGUI parentGUI;
	private  final String[] indizes = {"C-Index", "DB-Index", "GK Index" , "Rand Index", "Jaccard Index","Fowlkes-Mallows"};
	private JLabel result;
	/**
	 * 
	 */
	public IndexTab(ClusterCalculatorGUI gui) {
		this.parentGUI = gui;
		this.setLayout(new GridLayout(0,1,10,10));

		
		this.add(new JLabel("If you already clustered the data with one of " +
				"the clusteringalgorithms provided you can calculate an index."), BorderLayout.NORTH);
		
		JComboBox indizes = new JComboBox(this.indizes);
		this.add(indizes);
		
		JButton calculateIndex = new JButton("Calculate");
		calculateIndex.addActionListener(this);
		this.add(calculateIndex);
		
		result = new JLabel();
		this.add(result);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		this.result.setText(String.valueOf((new RandIndex().calculateIndex(this.parentGUI.getDataSet()))));
	}

}
