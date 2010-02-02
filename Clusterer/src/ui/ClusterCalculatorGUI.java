/**
 * 
 */
package ui;

import input.Dataset;
import input.InputReader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ui.guipanel.DBSCANPanel;
import ui.guipanel.GlobalKMeansPanel;
import ui.guipanel.HierarchicalClusteringPanel;
import ui.guipanel.KMeansPanel;
import ui.guipanel.LeaderPanel;

/**
 * @author Markus
 *
 */
public class ClusterCalculatorGUI extends JFrame{

	private static final long serialVersionUID = 6663543863030801968L;


	private JPanel mainPanel;
	private JLabel fileLabel = new JLabel("Please choose a File");
	private JPanel	algorithmOptions = new JPanel();
	
	//model stuff
	private Dataset dataSet;
	
	public Dataset getDataSet() {
		return dataSet;
	}

	public void setDataSet(Dataset dataSet) {
		this.dataSet = dataSet;
	}

	public ClusterCalculatorGUI(){
		super("ClusterCalculator");
		
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane();
		this.mainPanel = new JPanel();
		tabbedPane.addTab("Run Cluster Algorithm", this.mainPanel);
		JComponent panel2 = new IndexTab(this);
		tabbedPane.addTab("Validation Indices", panel2);

		
		mainPanel.setLayout(new BorderLayout(6,4));
		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		this.mainPanel.add(this.fileLabel,BorderLayout.NORTH);
		this.createMenu();
		this.createAlgorithmCombobox();

		this.mainPanel.add(this.algorithmOptions,BorderLayout.SOUTH);
		//this.add(mainPanel);
		this.add(tabbedPane);
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Creates the drop down (JComboBox) where the user can select the desired algorithm
	 */
	private void createAlgorithmCombobox() {
		String[] algorithms = {"DBSCAN","Leader","HierarchicalClustering","K-Means","Global K-Means"};
		
		
		JComboBox algoChooser   = new JComboBox(algorithms);
		algoChooser.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				
				
				JComboBox cb = (JComboBox)event.getSource();
				String choosenAlgorithm  = (String) cb.getSelectedItem();
				if (choosenAlgorithm.equals("Leader")){
					ClusterCalculatorGUI.this.mainPanel.remove(ClusterCalculatorGUI.this.algorithmOptions);
					ClusterCalculatorGUI.this.algorithmOptions =new LeaderPanel(ClusterCalculatorGUI.this);
					ClusterCalculatorGUI.this.mainPanel.revalidate();
					
					
				}
				if (choosenAlgorithm.equals("DBSCAN")){
					ClusterCalculatorGUI.this.mainPanel.remove(ClusterCalculatorGUI.this.algorithmOptions);
					ClusterCalculatorGUI.this.algorithmOptions =new DBSCANPanel(ClusterCalculatorGUI.this);
					ClusterCalculatorGUI.this.mainPanel.revalidate();
				}
				if (choosenAlgorithm.equals("HierarchicalClustering")){
					ClusterCalculatorGUI.this.mainPanel.remove(ClusterCalculatorGUI.this.algorithmOptions);
					ClusterCalculatorGUI.this.algorithmOptions =new HierarchicalClusteringPanel(ClusterCalculatorGUI.this);
					ClusterCalculatorGUI.this.mainPanel.revalidate();
					
				}
				
				if (choosenAlgorithm.equals("K-Means")){
					ClusterCalculatorGUI.this.mainPanel.remove(ClusterCalculatorGUI.this.algorithmOptions);
					ClusterCalculatorGUI.this.algorithmOptions =new KMeansPanel(ClusterCalculatorGUI.this);
					ClusterCalculatorGUI.this.mainPanel.revalidate();
							
				}
				if (choosenAlgorithm.equals("Global K-Means")){
					ClusterCalculatorGUI.this.mainPanel.remove(ClusterCalculatorGUI.this.algorithmOptions);
					ClusterCalculatorGUI.this.algorithmOptions =new GlobalKMeansPanel(ClusterCalculatorGUI.this);
					ClusterCalculatorGUI.this.mainPanel.revalidate();
							
				}
				
				
				ClusterCalculatorGUI.this.mainPanel.add(ClusterCalculatorGUI.this.algorithmOptions,BorderLayout.SOUTH);
				ClusterCalculatorGUI.this.pack();
			}
			
		});
		
		algoChooser.setPreferredSize(new Dimension(80,25));
		algoChooser.setMaximumSize(new Dimension(80,25));
		this.mainPanel.add(algoChooser,BorderLayout.CENTER);
		this.algorithmOptions = new DBSCANPanel(ClusterCalculatorGUI.this);
		
		
	}

	/**
	 * Creates the menu bar for the application
	 */
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu edit = new JMenu("Edit");
		JMenuItem load = new JMenuItem("Load Dataset");
		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();
				int returnValue = filechooser.showOpenDialog(ClusterCalculatorGUI.this);
				
				if (returnValue == JFileChooser.APPROVE_OPTION){
					File dataFile = filechooser.getSelectedFile();
					ClusterCalculatorGUI.this.dataSet = InputReader.readFromfile(dataFile.getAbsolutePath());
					ClusterCalculatorGUI.this.fileLabel.setText("loaded " + dataFile.getName());
				}
				
			}});
		edit.add(load);
		

		JMenuItem visualization = new JMenuItem("simple visualization");
		visualization.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ClusterCalculatorGUI.this.dataSet.get(0).getDimension() == 2) {
					JFrame vf = new JFrame("visualization");
					vf.add(new ClusterVisualization(
							ClusterCalculatorGUI.this.dataSet));
					vf.setSize(200, 300);
					vf.setVisible(true);
				}

			}
		});
		edit.add(visualization);
		menuBar.add(edit);
		JMenu  help = new JMenu("Help");
		menuBar.add(help);
		this.setJMenuBar(menuBar);
		
	}

	public static void main(String[] args) {
		ClusterCalculatorGUI calc = new ClusterCalculatorGUI();
	}
	

}
