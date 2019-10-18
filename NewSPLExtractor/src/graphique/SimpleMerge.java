package graphique;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Main;
import merge.Merger;
import model.Model;
import model.ModelConfiguration;
import util.Directory;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class SimpleMerge extends JFrame  implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSelect;
	private JButton btnSave;
	private JButton btnClose;
	private JButton btnDisply;
	private JLabel lblProcessingPlease;
	
	private ArrayList<Model> models = new ArrayList<Model>() ;
	
	private Model model;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleMerge frame = new SimpleMerge();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static void run() {
		try {
			SimpleMerge frame = new SimpleMerge();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public SimpleMerge() {
		
		ModelConfiguration.initialize();
		
		
		setTitle("Simple merging of a set of models, one after another");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 526, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnSelect = new JButton("Select a set of models");
		btnSelect.addActionListener(this);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		
		btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.addActionListener(this);
		
		btnDisply = new JButton("Disply");
		btnDisply.addActionListener(this);
		btnDisply.setEnabled(false);
		
		lblProcessingPlease = new JLabel("");

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(198)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSelect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDisply, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(165, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(281, Short.MAX_VALUE)
					.addComponent(lblProcessingPlease)
					.addGap(173))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addComponent(lblProcessingPlease)
					.addGap(18)
					.addComponent(btnSelect)
					.addGap(18)
					.addComponent(btnSave)
					.addGap(18)
					.addComponent(btnDisply)
					.addGap(18)
					.addComponent(btnClose)
					.addContainerGap(50, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
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
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == this.btnSelect){ 
			
			this.lblProcessingPlease.setText("Processing.....Please waite");
			this.btnClose.setEnabled(false);
			
			ModelConfiguration.createModelConfig("config/umlcd.xmi");
	  		models  = new  ArrayList<Model>();
			

			
			final JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			 chooser.setMultiSelectionEnabled(true);
			 int returnVal = chooser.showOpenDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	File file;
			  	    File[] list = chooser.getSelectedFiles();
			  	   
			  	 	
				  	 
			  	    
			  	   //String[][] str = new String[list.length][1];
			  	   //System.out.println(list.length+" ******************");
			  	   
			  	   for(int i=0;i<list.length;i++){
			  		  file = list[i];      
			  	      if(file!=null){
			  		     if(file.isFile()){
			  		    	 
			  		    	 
				  		  
							try{
								
								Model model = Model.getModel(file.getPath());
								models.add(model);
						  		
						  	
								
								
						  	}catch(Exception e){
						  		System.out.println(e.getMessage());

						  		JOptionPane.showMessageDialog(this,
									    "ERROR.",
									    "ERROR",
									    JOptionPane.ERROR_MESSAGE);
								
						  	
						  	}
						  	
						  	
						  	
							Directory.setCurrentDirectory(file.getParent());
						  	
						  	
						  	

					  	
			  		     }//if(file.isFile()){
			  	      }//if(file!=null){
			  		}// for(int i=0;i<list.length;i++){
			  	   
			 

				
			  		merge();
			  		this.lblProcessingPlease.setText("");
			  		this.btnSave.setEnabled(true);
			  		this.btnDisply.setEnabled(true);
			  		this.btnClose.setEnabled(true);
			  		//this.lblProcessingPlease.setVisible(false);

					
					
				  	   
			    
			    }//if (returnVal == JFileChooser.APPROVE_OPTION) {
			    

		}//if(event.getSource() == this.btnSelect){ 
	
		
		if(event.getSource() == this.btnSave){ 
			
			this.btnSave.setEnabled(false);
			
			final JFileChooser chooser = new JFileChooser();
			String path = "";
			chooser.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			//chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			int returnVal = chooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				path = file.getPath()+".xmi";
				model.save(path);


				 Directory.setCurrentDirectory(file.getParent());
			}//if (returnVal == JFileChooser.APPROVE_OPTION) {
			/*for(int i =0; i < models.size(); i++ ){
				System.out.println(path+models.get(i).getId()+".xmi");
				models.get(i).save(path+models.get(i).getId()+".xmi");
			}*/
			
		}
		
if(event.getSource() == this.btnDisply){ 
			
			this.btnDisply.setEnabled(false);
			Main.displayModel(model);
			this.dispose();
		}
		
		if(event.getSource() == this.btnClose){ 
			this.dispose();
		}
		
		
		
	}//public void actionPerformed(ActionEvent event) {
	
	private void merge(){
		
		
		long startTime = System.nanoTime();
		System.out.println(startTime);
		model = Merger.simpleMerge(models);
		long endTime   = System.nanoTime();
		System.out.println(endTime);
		long seconds = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
		System.out.println(seconds);
		
		
  		
  		//m.disp();
		
	}
}
