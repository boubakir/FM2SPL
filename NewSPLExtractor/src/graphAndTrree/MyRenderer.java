package graphAndTrree;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import fm.Constraint;
import fm.Feature;
import fm.FeatureGroup;
import fm.FeatureModel;

public class MyRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean selected,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus) {
				// Allow the original renderer to set up the label
			Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus); 

 
DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;



//setIcon(new ImageIcon("util/icons/alternative.png"));
		if(node.getUserObject() instanceof Feature ){
			Feature f = (Feature) node.getUserObject();
			Color color = MyTree.getColor(f);
			/*System.out.println(MyTree.getIndex(f));
			System.out.println(MyTree.fm.getFeatures().size());*/
			this.setBackgroundNonSelectionColor(color);
			
			/*if(color.equals(Color.black)){
				this.setTextNonSelectionColor(Color.white);
			}else{
				this.setTextNonSelectionColor(Color.black);
			}
				*/
				this.setBackgroundSelectionColor(Color.white);
				this.setTextSelectionColor(Color.black);
			//}
			
			if(f.getType().equals("mandatory")){
				setIcon(new ImageIcon("util/icons/mandatory.png"));
				//this.setBackground(Color.BLUE);
				this.setBackgroundNonSelectionColor(Color.white);
			}else{
				if(f.getType().equals("optional")){
					setIcon(new ImageIcon("util/icons/optional.png"));
					//this.setBackgroundNonSelectionColor(Color.RED);
				}else{
					setIcon(new ImageIcon("util/icons/feature.png"));
				}
			}
			
			/*
			
			if(f.getType().equals("alternative")){
				this.setIcon(new ImageIcon("util/icons/alternative.png"));
				//this.setBackgroundNonSelectionColor(Color.YELLOW);
			}*/
			
			//System.out.println(f.getType());
			
		}else{
			if(node.getUserObject() instanceof FeatureModel ){
				setIcon(new ImageIcon("util/icons/fm.png"));
				this.setBackgroundNonSelectionColor(Color.white);
			}else{
				
				
				if(node.getUserObject() instanceof Constraint ){
					setIcon(new ImageIcon("util/icons/constraint.png"));
					this.setBackgroundNonSelectionColor(Color.white);
				}else{
					
					if(node.getUserObject() instanceof FeatureGroup ){
						
						this.setBackgroundNonSelectionColor(Color.white);
						FeatureGroup featureGroup = (fm.FeatureGroup) node.getUserObject();
						if(featureGroup.getType().equals("alternative")){
							setIcon(new ImageIcon("util/icons/alternative.png"));
						}else{
							setIcon(new ImageIcon("util/icons/or.png"));
						}
						//this.setBackgroundNonSelectionColor(Color.white);
					}else{
					
						setIcon(new ImageIcon("util/icons/element.png"));
						this.setBackgroundNonSelectionColor(Color.white);
					}
					
				}
				
			}
			
		}//if(node.getUserObject() instanceof Feature ){

return c;
}
} 