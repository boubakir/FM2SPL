package graphique;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Main;
import model.ModelConfiguration;
import spl.SPL;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ModifyEltsID extends JFrame  implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnOk;
	
	private JTextField tfStr;
	private JButton btnClose;
	private JLabel lblNumber;
	private JTextField tfNum;

	
	private SPL spl;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SPL spl = new SPL();
					ModifyEltsID frame = new ModifyEltsID(spl);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static void run(SPL spl) {
		try {
			ModifyEltsID frame = new ModifyEltsID(spl);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public ModifyEltsID(SPL spl) {
		
		this.spl = spl;
		
		ModelConfiguration.initialize();
		
		
		setTitle("Modify the elements ID");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("Prefix");
		
		tfStr = new JTextField();
		tfStr.setText("M");
		tfStr.setColumns(10);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		
		lblNumber = new JLabel("Number");
		
		tfNum = new JTextField();
		tfNum.setText("1");
		tfNum.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(tfStr))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(lblNumber, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(tfNum, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(49)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(tfStr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumber)
						.addComponent(tfNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOk))
					.addGap(18)
					.addComponent(btnClose)
					.addContainerGap(107, Short.MAX_VALUE))
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
		
		if(event.getSource() == this.btnOk){
			try{
				int number = Integer.parseInt(this.tfNum.getText());
				this.spl.modifyIDs(this.tfStr.getText(), number);
				System.out.println(this.tfStr.getText()+"  "+number);
				//this.spl.disp();
				
				this.spl.save();
				//System.out.println(" ---- "+this.spl.getSplPth()+"  "+this.spl.getId());
				Main.display();	
				Main.refreshSPL();
				
				this.dispose();
				
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Dialog",
				        JOptionPane.ERROR_MESSAGE);
			}
			
			
		}//if(event.getSource() == this.btnOk){ 
		
		if(event.getSource() == this.btnClose){ 
			this.dispose();
		}
		
		
		
	}//public void actionPerformed(ActionEvent event) {
}
