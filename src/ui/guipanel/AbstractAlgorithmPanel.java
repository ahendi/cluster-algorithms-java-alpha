/**
 * 
 */
package ui.guipanel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ui.ClusterCalculatorGUI;

import algorithms.ClusteringAlgorithm;

/**
 * @author Markus
 *
 */
public  abstract class AbstractAlgorithmPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6295868393718603137L;
	protected ClusteringAlgorithm algorithm;
	protected ClusterCalculatorGUI clusCalculatorGUI;
	protected JTextArea resultArea;
	protected JScrollPane jsPane;
	
	public AbstractAlgorithmPanel(ClusterCalculatorGUI clCalculatorGUI){
		this.clusCalculatorGUI = clCalculatorGUI;
		//prepare result area
		this.setLayout(new BorderLayout());
		resultArea = new JTextArea();
		resultArea.setEditable(false);
		jsPane = new JScrollPane(resultArea);
		jsPane.setVisible(false);
		this.add(jsPane,BorderLayout.SOUTH);
		

	}
	

	/**
	 * shows the cluster summary string in a textarea
	 */
	protected void showResultPanel() {
		this.jsPane.setVisible(true);
		this.resultArea.setText(this.clusCalculatorGUI.getDataSet().toString());
		this.resultArea.setVisible(true);
		
		this.revalidate();
		this.clusCalculatorGUI.pack();
		
	}

	public ClusteringAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(ClusteringAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	

}
