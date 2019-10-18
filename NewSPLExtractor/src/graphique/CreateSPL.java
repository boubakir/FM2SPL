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

import main.Main;
import model.Model;
import spl.SPL;
import util.Directory;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CreateSPL extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfSPLName;
	private JTextField tf2;
	
	private JButton btnSelect;
	private JButton btnClose;
	private JButton btnsave;
	private Model model = null;
	
	private SPL spl;
	
	
	
	
	public static void run() {
		try {
			CreateSPL frame = new CreateSPL();
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
					CreateSPL frame = new CreateSPL();
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
	public CreateSPL() {
		setTitle("Create a new SPL and initialize it");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 632, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		tfSPLName = new JTextField();
		tfSPLName.setColumns(10);
		
		btnSelect = new JButton("Browse");
		btnSelect.addActionListener(this);
		
		JLabel lblSpl = new JLabel(" SPL name");
		
		JLabel lblInitializationModel = new JLabel("Initialization model");
		
		tf2 = new JTextField();
		tf2.setColumns(10);
		
		btnsave = new JButton("Save");
		btnsave.addActionListener(this);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(65)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSpl)
						.addComponent(lblInitializationModel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(tfSPLName, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnsave, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(tf2, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnSelect))))
					.addContainerGap(143, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(67)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSpl)
						.addComponent(tfSPLName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInitializationModel)
						.addComponent(tf2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelect))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnsave))
					.addContainerGap(89, Short.MAX_VALUE))
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
			final JFileChooser fc = new JFileChooser();
			//fc.setCurrentDirectory(new File("models"));
			fc.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
			 int returnVal = fc.showOpenDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	File file = fc.getSelectedFile();
			    	this.tf2.setText(file.getAbsolutePath());
			      	//System.out.println(file.getPath());
				  	model = Model.getModel(file.getPath());
				  	
				  	Directory.setCurrentDirectory(file.getParent());
			    }
			
		}
		
		
		if(event.getSource() == this.btnsave){ 
			if(this.tfSPLName.getText().equals("")){
				JOptionPane.showMessageDialog(this,
					    "The SPL name can not be empty.",
					    "warning",
					    JOptionPane.WARNING_MESSAGE);
			}else{
				spl = null;
				if(this.model==null){
					spl = new SPL(this.tfSPLName.getText());
				}else{
	
					spl = new SPL(this.tfSPLName.getText(), this.model);
				}
				this.save();
				this.dispose();
				/*SaveNewSPL.run(spl);
				this.dispose();*/
			
			
			
			}//if(this.tfSPLName.getText().equals("")){
				
		}
		
		if(event.getSource() == this.btnClose){ 
			this.dispose();
			Main.display();	


			
		}
		
	}
	
	
private void save(){
	
	final JFileChooser chooser = new JFileChooser();
	//fc.setCurrentDirectory(new File("models"));
	chooser.setCurrentDirectory(new File(Directory.getCurrentDirectory()));
	 int returnVal = chooser.showSaveDialog(this);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	
	    	
	    	File file = chooser.getSelectedFile();
	    	
	    	chooser.setDialogTitle("Select a path for saving the SPL");
	    	
	    	String path = file.getPath()+".xmi"; 
	    	spl.setSplPth(path);
	    	this.spl.saveAs(path);
		  	Main.displaySPL(this.spl);
		  	
		  	SaveConfirmation.run(this.spl);
		  	
			
		  	
		  	Directory.setCurrentDirectory(file.getParent());

		  	
	    }
	
}



}
