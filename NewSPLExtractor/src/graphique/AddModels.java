package graphique;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import spl.SPL;
import util.Directory;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import main.Main;
import model.Model;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class AddModels extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnClose;
	private JButton btnSelect;
	private JButton btnAdd;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnDelete;
	private JTextField tfSPLPath;
	private JLabel lblSplModel;
	private JButton btnSelectSPL;
	

	private SPL spl;

	
	
	private JTextField tfSPLName;
	
	
	public static void run(SPL spl) {
		try {
			AddModels frame = new AddModels(spl);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SPL spl = new SPL();
					AddModels frame = new AddModels(spl);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddModels(SPL spl ) {
	
		

		tfSPLName = new JTextField();
		tfSPLName.setColumns(10);
		
		tfSPLPath = new JTextField();
		tfSPLPath.setColumns(10);
		
		if(spl==null){
			this.spl = new SPL();
		}else{
			this.spl = spl;
			/*this.SPLModelPath = SPLModelPath;
			this.FMPath = FMPath;*/
			
			this.tfSPLName.setText(this.spl.getId());
			this.tfSPLPath.setText(this.spl.getSplPth());
		
			
		}
		
		setTitle("Add new models to the SPL");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 632, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//if(this.spl!=null) {  tf2.setText(this.spl.getName()); tf3.setText(this.spl.getName());  }
		
		
		
		btnSelect = new JButton("Select models");
		btnSelect.addActionListener(this);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		
		btnAdd = new JButton("Add to the SPL");
		btnAdd.addActionListener(this);
		
		scrollPane = new JScrollPane();
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this);
		
		
		lblSplModel = new JLabel("SPL path");
		
		btnSelectSPL = new JButton("Browse");
		btnSelectSPL.addActionListener(this);
		
		
		
		JLabel lblSplName = new JLabel("SPL name");
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnSelect, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(62)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnClose)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
								.addComponent(btnDelete)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblSplModel)
											.addGap(18)
											.addComponent(tfSPLPath, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblSplName, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(tfSPLName, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
											.addGap(128)))
									.addGap(18)
									.addComponent(btnSelectSPL, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
									.addGap(2)))))
					.addGap(24))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(34, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfSPLName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSplName))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfSPLPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelectSPL)
						.addComponent(lblSplModel))
					.addGap(42)
					.addComponent(btnSelect)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnDelete)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAdd)
						.addComponent(btnClose))
					.addGap(6))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Model"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(623);
		scrollPane.setViewportView(table);
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
				
		if(event.getSource() == this.btnSelect){ 
			final JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			 chooser.setMultiSelectionEnabled(true);
			 int returnVal = chooser.showOpenDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	 File file;
			  	   File[] list = chooser.getSelectedFiles();
			  	   
			  	
			  	   String[][] str = new String[list.length][1];
			  	   //System.out.println(list.length+" ******************");
			  	   
			  	   for(int i=0;i<list.length;i++){
			  		  file = list[i];      
			  	      if(file!=null){
			  		     if(file.isFile()){
				  			    str[i][0]  =  list[i].getPath();
				  		     //String extension = FileUtil.getExtension(file);
			  		     }//if(file.isFile()){
			  	      }//if(file!=null){
			  		}
			  	   
			  	table.setModel(new DefaultTableModel(
				 			str,
				 			new String[] {
				 				"Model"
				 			}
				 		));
	
			  	table.getColumnModel().getColumn(0).setPreferredWidth(623);
				//scrollPane.setViewportView(table);
				//contentPane.setLayout(gl_contentPane);
			    
			  	Directory.setCurrentDirectory(chooser.getSelectedFile().getPath());
			    
			    }//if (returnVal == JFileChooser.APPROVE_OPTION) {
			
		}//if(event.getSource() == this.btnSelect){ 
		
		if(event.getSource() == this.btnClose){
			this.dispose();
			Main.display();	
		}
		
		if(event.getSource() == this.btnDelete){ 
			//System.out.println(this.table.getSelectedRow());
			DefaultTableModel dm = (DefaultTableModel) this.table.getModel();
			dm.removeRow(this.table.getSelectedRow());
		}
		//this.table.getSelectedRow()
		
		if(event.getSource() == this.btnAdd){ 
			
			DefaultTableModel dm = (DefaultTableModel) this.table.getModel();
			
			
			try{
				for(int i = 0; i < dm.getRowCount(); i++){
					String modelPath = (String) dm.getValueAt(i, 0);
					Model model = Model.getModel(modelPath);
					
					/**
					 * Calculate the execution time
					 */
					long startTime = System.currentTimeMillis();
					
					this.spl.addModel(model);
					
					long endTime = System.currentTimeMillis();
					long timesSec = (endTime-startTime)/1000;
					long timesMin = timesSec/60;
					System.out.println("Execution time: "+timesSec +"  second");
					System.out.println("Execution time: "+timesMin +"  menute");
					
				}
				
				
				this.spl.save();
				Main.display();	
				Main.refreshSPL();
				
				this.dispose();
		}catch(Exception e){
				/*System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(this,
					    "Please select at least one model.",
					    "warning",
					    JOptionPane.WARNING_MESSAGE);*/
			JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
			}

			
		}
		
		
			
		if(event.getSource() == this.btnSelectSPL){ 
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			 int returnVal = fc.showOpenDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	File file = fc.getSelectedFile();
			    	this.spl = SPL.getSPL(file.getPath());
			    	this.spl.setSplPth(file.getPath());
			    	this.tfSPLName.setText(this.spl.getId());
			    	this.tfSPLPath.setText(this.spl.getSplPth());
			    	Main.displaySPL(spl);
			    	
			    	Directory.setCurrentDirectory(file.getParent());
			    }
			
		}
		
		
		if(event.getSource() == this.btnClose){ 
			this.dispose();
			
		}
		
	}
}
