package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;


import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import fm.Constraint;
import fm.Feature;
import fm.FeatureGroup;
import fm.FeatureModel;
import graphAndTrree.MyGraph;
import graphAndTrree.MyTree;
import graphique.AddModels;
import graphique.ConvertModelFormat;
import graphique.CreateSPL;
import graphique.Help;
import graphique.MergeManyModels;
import graphique.ModifyEltsID;
import graphique.SimpleMerge;
import model.Element;
import model.Model;
import model.ModelConfiguration;
import spl.SPL;
import util.Directory;

import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;

@SuppressWarnings("serial")
public class Main extends JFrame implements ViewerListener , MouseListener, ActionListener {
//public class Split extends JFrame implements ActionListener {
	
	private static Main frame ;
	
	private String displayed = ""; //has three values: MODEL or SPL or FM
	private SPL displayedSPL;
	//private String displayedSPLModelPath;
	//private String displayedFMPath;
	private JTabbedPane tabbedPane_graph;
	private JTabbedPane tabbedPane_Text;
	
	private Model displayedModel;

	private JPanel contentPane;
	private JMenuItem mntmDisplayModel;
	private JMenuItem mntmMergeManyModel;
	private JMenuItem mntmSimpleMerge;
	
	private JMenuItem mntmConvertFormat;
	
	
	private ViewerPipe fromViewer;
	private ViewPanel viewPanel;
	private Graph graph;
	//private JPanel panel;
	private JPanel panel_graph1;
	private JTextArea textArea1;
	private JTextArea textArea2;
	
	private JMenuItem mntmNewSPL;
	private JMenuItem mntmDisplaySPL;
	private JMenuItem mntmAddModels;
	private JMenu modifySPL;
	private JMenuItem modifyEltsId;
	
	
	private JMenu mnFM;
	private JMenuItem mntmOpenFM;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;
	private JTree tree;
	private JScrollPane scrollPane_Tree;
	private JTextArea textAreaDescription;
	
	private JPopupMenu popupElement;

	private Object selecctedNodeInTneGraph;
	
	protected boolean loop = true;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Initialization();
					frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void Initialization(){
		ModelConfiguration.createModelConfig("config/umlcd.xmi");
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("  File  ");
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("  Exit  ");
				mntmExit.addActionListener(this);
				mnFile.add(mntmExit);
		
		
		JMenu mnModel = new JMenu("  Model  ");
		menuBar.add(mnModel);
		
		mntmDisplayModel = new JMenuItem("  Display  ");
		mntmDisplayModel.addActionListener(this);
		mnModel.add(mntmDisplayModel);
		

		mntmMergeManyModel = new JMenuItem("  Merge a set of models  ");
		mntmMergeManyModel.addActionListener(this);
		mnModel.add(mntmMergeManyModel);
		
		mntmSimpleMerge = new JMenuItem("  Simple merge of a set of models  ");
		mntmSimpleMerge.addActionListener(this);
		mnModel.add(mntmSimpleMerge);
		
		
		
		
		mntmConvertFormat = new JMenuItem("  Convert to addoted xmi format (only me)  ");
		mntmConvertFormat.addActionListener(this);
		mnModel.add(mntmConvertFormat);
		
		
		JMenu mnSPL = new JMenu("  SPL  ");
		menuBar.add(mnSPL);
		
		mntmNewSPL = new JMenuItem("  New SPL  ");
		mntmNewSPL.addActionListener(this);
		mnSPL.add(mntmNewSPL);
		
		
		mntmDisplaySPL = new JMenuItem("  Display  ");
		mntmDisplaySPL.addActionListener(this);
		mnSPL.add(mntmDisplaySPL);
		
		
		mntmAddModels = new JMenuItem("  Add models  ");
		mntmAddModels.addActionListener(this);
		mnSPL.add(mntmAddModels);
		
		modifySPL = new JMenu("  Modify the SPL  ");
		//modifySPL.addActionListener(this);
		mnSPL.add(modifySPL);
		
		modifyEltsId = new JMenuItem("  Modify element identifiers  ");
		modifyEltsId.addActionListener(this);
		modifySPL.add(modifyEltsId);
		
		
		
		
		mnFM = new JMenu("  Feature model  ");
		menuBar.add(mnFM);
		
		mntmOpenFM = new JMenuItem("  Display  ");
		mntmOpenFM.addActionListener(this);
		mnFM.add(mntmOpenFM);
		
		
		
		
		JMenu mnHelp = new JMenu("  Help  ");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("  About SPLExtractor  ");
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(this);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		popupElement = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("New Project...");
		popupElement.add(menuItem);
	    menuItem = new JMenuItem("New File...");
	    popupElement.add(menuItem);
	    
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//tabbedPane.setBackground(Color.BLUE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1164, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
		);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tabbedPane, popupMenu);
		
		tabbedPane_graph = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Graph", null, tabbedPane_graph, null);
		
		JSplitPane splitPaneGraph = new JSplitPane();
		splitPaneGraph.setDividerLocation(150);
		tabbedPane_graph.addTab("", null, splitPaneGraph, null);
		tabbedPane_graph.setTitleAt(0, "SPL");
	
		
		//tabbedPane_graph.getTabComponentAt(0).setName("--------------------");
		
		//panel_graph1 = new JPanel();
		
		
		
		panel_graph1 = new JPanel(new GridLayout()){
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        };
        panel_graph1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		
		
		
		JSplitPane splitPaneDescription = new JSplitPane();
		splitPaneDescription.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneDescription.setDividerLocation(300);
		splitPaneGraph.setLeftComponent(splitPaneDescription);
		
		

		JTabbedPane tabbedPaneGraphModel = new JTabbedPane(JTabbedPane.TOP);
		splitPaneGraph.setRightComponent(tabbedPaneGraphModel);
		
		//JScrollPane scrollPaneDescription = new JScrollPane();
		tabbedPaneGraphModel.addTab("Model ", null, panel_graph1, null);
		
		
		
		
		//splitPaneGraph.setRightComponent(panel_graph1);
		
		
		
		scrollPane_Tree = new JScrollPane();
		
		
		
		
		
		JTabbedPane tabbedPaneDescriptionTree = new JTabbedPane(JTabbedPane.TOP);
		splitPaneDescription.setLeftComponent(tabbedPaneDescriptionTree);
		
		//JScrollPane scrollPaneDescription = new JScrollPane();
		tabbedPaneDescriptionTree.addTab("Feature Model ", null, scrollPane_Tree, null);
		
		
		
		
		splitPaneDescription.setLeftComponent(tabbedPaneDescriptionTree);
		
		
		//splitPaneDescription.setLeftComponent(scrollPane_Tree);
		
		
		
		
		tree = MyTree.getTree(new FeatureModel());
		tree.addMouseListener(this);
		//scrollPane_Tree.setViewportView(tree);
		
		
		tabbedPane_Text = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Text", null, tabbedPane_Text, null);
		
		JSplitPane splitPaneText = new JSplitPane();
		splitPaneText.setDividerLocation(300);
		splitPaneText.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane_Text.addTab("", null, splitPaneText, null);
		tabbedPane_Text.setTitleAt(0, "SPL");
		
		JPanel panel = new JPanel();
		splitPaneText.setRightComponent(panel);
		
		JLabel lblFeatureModel = new JLabel("Feature Model");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
						.addComponent(lblFeatureModel))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblFeatureModel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
		);
		
		textArea2 = new JTextArea();
		textArea2.setMargin( new Insets(20,40, 20, 20));
		textArea2.setBackground(new Color(255,255,255));
		textArea2.setLineWrap(true);
		textArea2.setWrapStyleWord(true);
		textArea2.setCaretPosition(0); 
		
		scrollPane.setViewportView(textArea2);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		splitPaneText.setLeftComponent(panel_1);
		
		JLabel lblModel = new JLabel("Model");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
						.addComponent(lblModel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblModel)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		textArea1 = new JTextArea();
		textArea1.setMargin( new Insets(20,40, 20, 20));
		textArea1.setBackground(new Color(255,255,255));
		textArea1.setLineWrap(true);
		textArea1.setWrapStyleWord(true);
		textArea1.setCaretPosition(0); 
		scrollPane_1.setViewportView(textArea1);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
		//******************************
		
		
		
		/*JLabel lblDescription = new JLabel("Description");
		
		splitPaneDescription.setRightComponent(lblDescription);*/
		
		JTabbedPane tabbedPaneDescription = new JTabbedPane(JTabbedPane.TOP);
		splitPaneDescription.setRightComponent(tabbedPaneDescription);
		
		JScrollPane scrollPaneDescription = new JScrollPane();
		tabbedPaneDescription.addTab("Description ", null, scrollPaneDescription, null);
		
		textAreaDescription = new JTextArea();
		textAreaDescription.setMargin( new Insets(10,20, 10, 10));
		textAreaDescription.setBackground(new Color(255,255,255));
		textAreaDescription.setLineWrap(true);
		textAreaDescription.setWrapStyleWord(true);
		textAreaDescription.setCaretPosition(0); 
		
		scrollPaneDescription.setViewportView(textAreaDescription);
		
		 this.setSize(1200, 730);
		
	        
	        // Get the size of the screen
	           Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	           // Determine the new location of the window
	           int w = this.getSize().width;
	           int h = this.getSize().height;
	           int x = (dim.width-w)/2;
	           int y = (dim.height-h)/2;

	           // Move the window
	           this.setLocation(x, y);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.mntmDisplayModel){
			
			
			Model model = null;
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	File file = fc.getSelectedFile();
			    	model = Model.getModel(file.getPath());
			    	displayModel(model, null);
			    	this.displayedModel = model;
			    	this.displayed = "MODEL";
			    	this.tabbedPane_graph.setTitleAt(0, "Model : "+model.getId());
			    	this.tabbedPane_Text.setTitleAt(0, "Model : "+model.getId());
			    	
			    	Directory.setCurrentDirectory(file.getParent());
			    	
				}//if (returnVal == JFileChooser.APPROVE_OPTION) {
		 }//if(e.getSource() == this.mntmDisplayModel){
		
		
		if(e.getSource() == this.mntmMergeManyModel){
			MergeManyModels.run();
		}
		
		if(e.getSource() == this.mntmSimpleMerge){
			SimpleMerge.run();
		}
		
		
		if(e.getSource() == this.mntmConvertFormat){
			ConvertModelFormat.run();
		}//if(e.getSource() == this.mntmConvertFormat){
		
		
		if(e.getSource() == this.mntmNewSPL){ 
			CreateSPL.run();
		}
			 
		if(e.getSource() == this.mntmAddModels){ 
			if(this.displayed.equals("SPL")){
					AddModels.run(this.displayedSPL);
			}else{
				AddModels.run(null);
			}
			
		}
		if(e.getSource() == this.modifyEltsId){ 
			if(this.displayed.equals("SPL")){
				ModifyEltsID.run(this.displayedSPL);
			}else{
				//AddModels.run(null);
			}
			
		}
		
		
		
		if(e.getSource() == this.mntmExit){ 
			this.dispose();
		}
		
		if(e.getSource() == this.mntmOpenFM){ 
			this.displayed = "FM";
			
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			 int returnVal = fc.showOpenDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	File file = fc.getSelectedFile();
			    	FeatureModel fm = FeatureModel.getFM(file.getPath());
			    	this.displayFM(fm);
			    	
			    	Directory.setCurrentDirectory(file.getParent());
			    }
			
			
			
			
		}
		
		if(e.getSource() == this.mntmAbout){ 
			Help.run();
		}
		
		
			
		
		
		if(e.getSource() == this.mntmDisplaySPL){ 
			
			
			/*
			JOptionPane.showMessageDialog(this,
				    "Select the SPL model then the Feature model.");*/
			
			//Model splModel = null;
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			fc.setDialogTitle("Select the SPL");
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = fc.getSelectedFile();
		    	//String splModelPath = file.getPath();
		    	SPL spl = SPL.getSPL(file.getPath());
		    	spl.setSplPth(file.getPath());
		    	displaySPL(spl);
		    	Directory.setCurrentDirectory(file.getParent());
		    	
			   
				
			}//if (returnVal == JFileChooser.APPROVE_OPTION) {
		}//if(e.getSource() == this.mntmDisplaySPL){ 
	
	
	
	}//public void actionPerformed(ActionEvent e) {



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		/*if(event.getSource() == viewPanel){
			 this.selecctedNodeInTneGraph = null;			 
			 fromViewer.pump();
			 if(SwingUtilities.isRightMouseButton(event)){
				 
				 if(this.selecctedNodeInTneGraph==null){
					 System.out.println("XXXXXX");
				 }else{
					 this.showPopupForGraph(event);
				 }
				 
			 }
				
		 }*/
		
	}
/*
	private void showPopupForGraph(MouseEvent event) {

            this.popupElement.show(event.getComponent(),
            		event.getX(), event.getY());
        }
    }*/


	
	@Override
	
	public void viewClosed(String id) {
		loop = false;
	}

	public void buttonPushed(String id) {
		//System.out.println("Button pushed on node "+id);
		if(this.displayed.equals("MODEL")){
			Element elt =this.displayedModel.getElement(id);
			if(elt==null){
				this.selecctedNodeInTneGraph = displayedModel;
				this.textAreaDescription.setText(displayedModel.getDescription());
			}else{
				this.selecctedNodeInTneGraph = elt;
				this.textAreaDescription.setText("Element :"+System.getProperty("line.separator")+elt.toStr());
			}
			
		}else{
			if(this.displayed.equals("SPL")){
				Element elt =this.displayedSPL.getSplModel().getElement(id);
				if(elt==null){
					this.selecctedNodeInTneGraph = displayedSPL.getSplModel();
					this.textAreaDescription.setText(displayedSPL.getDescription(displayedSPL.getSplModel()));
				}else{
					this.selecctedNodeInTneGraph = elt;
					this.textAreaDescription.setText(displayedSPL.getDescription(elt) );
				}
			}
		}
	}

	public void buttonReleased(String id) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		 if(e.getSource() == viewPanel){
			 this.selecctedNodeInTneGraph = null;			 
			 fromViewer.pump();
			 if(this.selecctedNodeInTneGraph==null){
				 
			 }else{
				 //this.showPopupForGraph(e);
			 }

		 }
		 
		 /**
		  * When se select a node of FM by the Jtree
		  */
		 if(e.getSource() == this.tree){
				
				DefaultMutableTreeNode selectedNode = 
					       (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(selectedNode==null){
					
				}else{
				
					if(selectedNode.getUserObject() instanceof FeatureModel ){
						FeatureModel fm = (FeatureModel) selectedNode.getUserObject();
						this.textAreaDescription.setText(fm.getDescription());
					}else{
						if(selectedNode.getUserObject() instanceof Feature ){
							Feature feature = (Feature) selectedNode.getUserObject();
							this.textAreaDescription.setText(feature.getDescription());
						}else{
							
							if(selectedNode.getUserObject() instanceof Constraint ){
								Constraint constraint = (Constraint) selectedNode.getUserObject();
								this.textAreaDescription.setText(constraint.getDescription());
							}else{

								if(selectedNode.getUserObject() instanceof FeatureGroup ){
									FeatureGroup featureGroup = (FeatureGroup) selectedNode.getUserObject();
									this.textAreaDescription.setText(featureGroup.getDescription());
								}else{
									
									/*if(selectedNode.getUserObject() instanceof Element ){
										Element elt = (Element) selectedNode.getUserObject();
										this.textAreaDescription.setText(elt.getDescription());
									}else{*/
								
										if(this.displayed.equals("SPL")){
											Model model  = this.displayedSPL.getSplModel();
											Element element = model.getElement((String) selectedNode.getUserObject());
											/*
											for(int i = 0; i < model.getElements().size(); i++ ){
												System.out.println(model.getElements().get(i).getId());
											}
											System.out.println((String) selectedNode.getUserObject()+"   *");
										*/
											this.textAreaDescription.setText(displayedSPL.getDescription(element));
										}else{
											this.textAreaDescription.setText("No model"+System.getProperty("line.separator")+
													"No sofware product line is diplyaed");
										}
									//}
								}//
								
							}//if(selectedNode.getUserObject() instanceof Constraint ){
						
						}//if(selectedNode.getUserObject() instanceof Feature ){
						
					}//if(selectedNode.getUserObject() instanceof FeatureModel ){
					
					//this.textAreaDescription.setText(selectedNode.getUserObject().toString());
				}//if(selectedNode.getUserObject()==null){
				
			}//if(e.getSource() == this.tree){
		
	}
	
	public void displayTextModel(Model model){
		this.textArea1.setText(model.toString());
		
	}
	
	public static void refreshSPL(){
		frame.displayModel(frame.displayedSPL.getSplModel(), frame.displayedSPL.getFeatureModel());
		frame.displayFM(frame.displayedSPL.getFeatureModel());
	}
	
	public static void displayModel(Model model){
		frame.displayed = "MODEL";
		frame.displayedModel = model;
		frame.displayModel(model, null);

		
		frame.tabbedPane_graph.setTitleAt(0, "MODEL: "+model.getId());
		frame.tabbedPane_Text.setTitleAt(0, "MODEL : "+model.getId());
		
		
		
		//spl.getFeatureModel().disp();
		
	}
	public static void displaySPL(SPL spl){
		frame.displayed = "SPL";
		frame.displayedSPL = spl;
		/*frame.displayedSPLModelPath = splPath;
		frame.displayedFMPath = fmPath;*/
		frame.displayModel(spl.getSplModel(), spl.getFeatureModel());
		frame.displayFM(spl.getFeatureModel());
		
		frame.tabbedPane_graph.setTitleAt(0, "SPL : "+spl.getId());
		frame.tabbedPane_Text.setTitleAt(0, "SPL : "+spl.getId());
		
		
		
		//spl.getFeatureModel().disp();
		
	}
	
	public void displayTextFM(FeatureModel fm){
		this.textArea2.setText(fm.toStr());
		
	}
	
	public void displayModel(Model model, FeatureModel fm){
		try{
			MyGraph myGraph = new MyGraph(model.getId(), model, fm); 
		    graph = myGraph.getGraph(); 
			Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
			viewer.enableAutoLayout();
			viewPanel = viewer.addDefaultView(false);
			viewPanel.addMouseListener(this);
			panel_graph1.removeAll();
			panel_graph1.add(viewPanel);
			//this.add(panel);
			this.pack();
		       
		    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
		    fromViewer = viewer.newViewerPipe();
			fromViewer.addViewerListener(this);
			fromViewer.addSink(graph);
			this.setSize(1200, 730);
			        
			// Get the size of the screen
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			// Determine the new location of the window
			int w = this.getSize().width;
			int h = this.getSize().height;
			int x = (dim.width-w)/2;
			int y = (dim.height-h)/2;
	       // Move the window
	       this.setLocation(x, y);
	       
	       displayTextModel(model);
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
	
		}
		
	}
	
	public void displayFM(FeatureModel fm){
		displayTextFM(fm);
		this.tree = MyTree.getTree(fm);
		tree.addMouseListener(this);
		scrollPane_Tree.setViewportView(tree);

	}
	
	
	public static void display(){
		frame.setState(JFrame.NORMAL);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
