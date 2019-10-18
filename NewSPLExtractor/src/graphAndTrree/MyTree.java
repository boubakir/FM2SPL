package graphAndTrree;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import fm.Feature;
import fm.FeatureGroup;
import fm.FeatureModel;


public class MyTree {
	

	
	public static FeatureModel fm;
	
	public static Color getColor(Feature feature){
		return fm.getColor(feature);
	}
	
	
	public static JTree getTree(FeatureModel fm){
		MyTree.fm = fm;
		

		ArrayList<DefaultMutableTreeNode> nodes =  new ArrayList<DefaultMutableTreeNode>();
		ArrayList<Object> objects =  new ArrayList<Object>();
		
		
		
		for(int i = 0; i < fm.getMandatoryFeatures().size(); i++){
			nodes.add(new DefaultMutableTreeNode(fm.getMandatoryFeatures().get(i)));
			objects.add(fm.getMandatoryFeatures().get(i));
		}
		for(int i = 0; i < fm.getOptionalFeatures().size(); i++){
			nodes.add(new DefaultMutableTreeNode(fm.getOptionalFeatures().get(i)));
			objects.add(fm.getOptionalFeatures().get(i));
		}
		
		for(int i = 0; i < fm.getAlternativeFeatureGroup().size(); i++){
			nodes.add(new DefaultMutableTreeNode(fm.getAlternativeFeatureGroup().get(i)));
			objects.add(fm.getAlternativeFeatureGroup().get(i));
		
			for(int j = 0; j < fm.getAlternativeFeatureGroup().get(i).getFeatures().size(); j++){
				nodes.add(new DefaultMutableTreeNode(fm.getAlternativeFeatureGroup().get(i).getFeatures().get(j)));
				objects.add(fm.getAlternativeFeatureGroup().get(i).getFeatures().get(j));
			}
		}
		
		for(int i = 0; i < fm.getOrFeatureGroup().size(); i++){
			nodes.add(new DefaultMutableTreeNode(fm.getOrFeatureGroup().get(i)));
			objects.add(fm.getOrFeatureGroup().get(i));
			
			for(int j = 0; j < fm.getOrFeatureGroup().get(i).getFeatures().size(); j++){
				nodes.add(new DefaultMutableTreeNode(fm.getOrFeatureGroup().get(i).getFeatures().get(j)));
				objects.add(fm.getOrFeatureGroup().get(i).getFeatures().get(j));
			}
		}
		
		/*
		
		System.out.println(nodes.size());
		System.out.println(objects.size());*/
		/*
		for(int j = 0; j < objects.size(); j++){
			System.out.println(objects.get(j).toString());
		}*/
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(fm);
		
		
		
		for(int i = 0; i < objects.size(); i++){
			
			if(objects.get(i) instanceof Feature){
				Object obj = objects.get(i);
				Feature feature = (Feature) obj;
				if(feature.hasParent()){
					//System.out.println(feature.getName());
					if(feature.getParent().equals("root")){
						//System.out.println(feature.getName());
						//New 02/12/2018
						rootNode.add(nodes.get(i));
						//New 02/12/2018
					}else{
						int parentIndex = -1;
						int j = 0;	boolean found = false;
						while(j < objects.size() && !found){
							Object parentObj = objects.get(j);
							String parentId = null;
							if(parentObj instanceof Feature){
								
								Feature parent = (Feature) parentObj;
								parentId = parent.getName();
							
							
							}else{
								if(parentObj instanceof FeatureGroup){
									FeatureGroup parent = (FeatureGroup) parentObj;
									parentId = parent.getId();
								}else{}
							}
							if(feature.getParent().equals(parentId)){
								found = true;	parentIndex = j;
							}else{
								j++;
							}
							
						
						}//while(j < objects.size() && !found){
						
					
						//System.out.println(parentIndex+" ------  ");
						nodes.get(parentIndex).add(nodes.get(i));
					}//if(feature.getParent().equals("root")){
				}else{
					//New 02/12/2018
					//System.out.println(feature.getName()+"  .  ");
					//rootNode.add(nodes.get(i));
					//-New 02/12/2018
				}//if(feature.hasParent()){
				
				
				for(int j = 0; j < feature.getElements().size(); j++){
					nodes.get(i).add(new DefaultMutableTreeNode(feature.getElements().get(j)));
				}
				
				
				
			}else{
				
				if(objects.get(i) instanceof FeatureGroup){
				
					
					Object obj = objects.get(i);
					FeatureGroup featureGroup = (FeatureGroup) obj;
					if(featureGroup.getParent().equals("root")){
						rootNode.add(nodes.get(i));
					}else{
						int parentIndex = -1;
						int j = 0;	boolean found = false;
						while(j < objects.size() && !found){
							Object parentObj = objects.get(j);
							String parentId = null;
							if(parentObj instanceof Feature){
								Feature parent = (Feature) parentObj;
								parentId = parent.getName();
							}else{
								if(parentObj instanceof FeatureGroup){
									FeatureGroup parent = (FeatureGroup) parentObj;
									parentId = parent.getId();
								}else{}
							}
							if(featureGroup.getParent().equals(parentId)){
								found = true;	parentIndex = j;
							}else{
								j++;
							}
							
						
						}//while(j < objects.size() && !found){
						
					
						//System.out.println(parentIndex+" ------  ");
						nodes.get(parentIndex).add(nodes.get(i));
					}//if(feature.hasParent()){
					
					/*
					for(j = 0; j < feature.getElements().size(); j++){
						nodes.get(i).add(new DefaultMutableTreeNode(feature.getElements().get(j)));
					}*/
					
					
				}else{
					
				}
				
				
			}//if(objects.get(i) instanceof Feature){
			
			
			
			
			
		}
	
		for(int i = 0; i < fm.getConstraints().size(); i++){
			
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fm.getConstraints().get(i));
			rootNode.add(node);
		}
		
		JTree tree = new JTree(rootNode);

		MyRenderer dtcr = new MyRenderer(); tree.setCellRenderer(dtcr);
		return tree; 
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
