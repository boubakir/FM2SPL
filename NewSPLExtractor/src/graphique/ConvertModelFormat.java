package graphique;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import model.Model;
import model.ModelConfiguration;
import parser.XMIParser;
import util.Directory;
import util.MyString;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ConvertModelFormat extends JFrame  implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSelect;
	
	private ArrayList<Model> models = new ArrayList<Model>() ;
	private JTextField tfModelID;
	private JButton btnClose;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConvertModelFormat frame = new ConvertModelFormat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static void run() {
		try {
			ConvertModelFormat frame = new ConvertModelFormat();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public ConvertModelFormat() {
		
		ModelConfiguration.initialize();
		
		
		setTitle("Convert");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 526, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnSelect = new JButton("Select a model");
		btnSelect.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("Model ID");
		
		tfModelID = new JTextField();
		tfModelID.setColumns(10);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(tfModelID, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnSelect, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addGap(75))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(97)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSelect)
						.addComponent(lblNewLabel)
						.addComponent(tfModelID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
					.addComponent(btnClose)
					.addGap(26))
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
			boolean error = false;
			models = new ArrayList<Model>() ;
			
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
			  		    	 
			  		    	 
				  		   	String id = "";
						  	if(this.tfModelID.getText().equals("")){
						  		id = MyString.readUntil(file.getName(), '.');
						  	}else{
						  		id  = this.tfModelID.getText()+(i+1);				  	
						  	}
						  	
						  	
						  	
							try{
						  		
						  		Model model  = XMIParser.getModel(file.getPath(), id);
								models.add(model);
						  		//model.disp();
							  /*	Directory.setCurrentDirectory(file.getParent());
							  	
							  	
							  	final JFileChooser chooser2 = new JFileChooser();
								
							  	chooser2.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
							  	chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							  	returnVal = chooser2.showSaveDialog(this);
								if (returnVal == JFileChooser.APPROVE_OPTION) {
									file = chooser2.getCurrentDirectory();
									
									model.save(file.getParent()+"\\"+model.getId()+".xmi");
									/*JOptionPane.showMessageDialog(this,
										    "The model has been successfully converted and saved.");
									Directory.setCurrentDirectory(file.getParent());
								}//if (returnVal == JFileChooser.APPROVE_OPTION) {
								*/						  		
						  	}catch(Exception e){
						  		error = true;
						  		JOptionPane.showMessageDialog(this,
									    "ERROR.",
									    "ERROR",
									    JOptionPane.ERROR_MESSAGE);
								Directory.setCurrentDirectory(file.getParent());
						  	
						  	}
						  	
						  	
						  	
						  	
						  	
						  	
						  	

					  	
			  		     }//if(file.isFile()){
			  	      }//if(file!=null){
			  		}// for(int i=0;i<list.length;i++){
			  	   
			  	
			  	   
			 
				  	   
			    
			    }//if (returnVal == JFileChooser.APPROVE_OPTION) {
			    
			    
	if(!error){  
					
				  	final JFileChooser chooser2 = new JFileChooser();
					String path = "";
					chooser2.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
					chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					returnVal = chooser2.showSaveDialog(this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						//File file = chooser2.getCurrentDirectory();
						File file = chooser2.getSelectedFile();
						path = file.getPath()+"\\"; //+model.getId()+".xmi");
						System.out.println(path+"    ****");

					}//if (returnVal == JFileChooser.APPROVE_OPTION) {
					for(int i =0; i < models.size(); i++ ){
						System.out.println(path+models.get(i).getId()+".xmi");
						models.get(i).save(path+models.get(i).getId()+".xmi");
					}
						
			    }//	if(!error){ 
		}//if(event.getSource() == this.btnSelect){ 
	
		/*
		
		if(event.getSource() == this.btnSelect){ 
			final JFileChooser chooser = new JFileChooser();
			//fc.setCurrentDirectory(new File("models"));
			chooser.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			 int returnVal = chooser.showOpenDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	File file = chooser.getSelectedFile();
			      	
				  	String id = "";
				  	if(this.tfModelID.getText().equals("")){
				  		id = MyString.readUntil(file.getName(), '.');
				  	}else{
				  		id  = this.tfModelID.getText();				  	
				  	}
				  	
				  	try{
				  		
				  		model  = XMIParser.getModel(file.getPath(), id);
						model.disp();
					  	Directory.setCurrentDirectory(file.getParent());
					  	
					  	
					  	final JFileChooser chooser2 = new JFileChooser();
						//fc.setCurrentDirectory(new File("models"));
					  	chooser2.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
						returnVal = chooser2.showSaveDialog(this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							file = chooser2.getSelectedFile();
							model.save(file.getPath()+".xmi");
							JOptionPane.showMessageDialog(this,
								    "The model has been successfully converted and saved.");
							Directory.setCurrentDirectory(file.getParent());
						}//if (returnVal == JFileChooser.APPROVE_OPTION) {
				  		
				  	}catch(Exception e){
				  		
				  	}

			    }
			
		}//if(event.getSource() == this.btnSelect){ 
		*/
		if(event.getSource() == this.btnClose){ 
			this.dispose();
		}
		
		
		
	}//public void actionPerformed(ActionEvent event) {
}
