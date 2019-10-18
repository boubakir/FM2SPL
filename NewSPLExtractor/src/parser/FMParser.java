package parser;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import fm.Constraint;
import fm.Feature;
import fm.FeatureGroup;
import fm.FeatureModel;


import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * allows to parse an XMI files using DOM to obtain an SPL
 * @author Boubakir
 *
 */
public class FMParser{
	
	private org.jdom2.Document document;
	private Element racine;
	private int curentElemntId = 0;
	//private String modelName;
	
	private FeatureModel resultingModel = new FeatureModel();


	public FeatureModel getFeatureModel() {
		return resultingModel;
	}

	public org.jdom2.Document getDocument() {
		return document;
	}

	public void setDocument(org.jdom2.Document document) {
		this.document = document;
	}

	public Element getRacine() {
		return racine;
	}

	public void setRacine(Element racine) {
		this.racine = racine;
	}

	public int getCurentElemntId() {
		return curentElemntId;
	}

	public void setCurentElemntId(int curentElemntId) {
		this.curentElemntId = curentElemntId;
	}

	/*
	public String getEltId(){
		curentElemntId++;
		return this.modelName+curentElemntId;
	}*/

   /**
    * Parsse the XMI document
    * @throws DataConversionException 
    */
   @SuppressWarnings("rawtypes")
private void parse() throws DataConversionException{
	   
	   this.resultingModel.setId(racine.getAttributeValue("id"));
	   ArrayList<Feature> features = this.parseFeature(racine);
	   
	   
	   for(int i = 0; i< features.size(); i++){
		   Feature feature = features.get(i);
		   if(feature.getType().equals("mandatory")){
		  		 resultingModel.addMandatoryFeature(feature);
		   }else{
		  	 if(feature.getType().equals("optional")){
		  		 resultingModel.addOptionalFeature(feature);
		  	 }else{
		  		//New 02/12/2018
		  		/*if(feature.getType().equals("root")){
			  		 resultingModel.addRootFeature(feature);
			  	 }else{}*/
		  		//New 02/12/2018
		  	 }//if(feature.getType().equals("optional")){
		   }
			 
		   
	   }
			     
			     
	   
         List constraints = racine.getChildren("constraint");
         Iterator c = constraints.iterator();
         while(c.hasNext()){
        	 
        	 Element constraintXMI = (Element)c.next();
        	
        	 //Create a constraint
        	 Constraint constraint = new  Constraint();
        	 resultingModel.addConstraint(constraint);
        	  
        	//get the attributes of the constraints
       		 if(constraintXMI.getAttributes().size()>0){
       			 String left = constraintXMI.getAttributes().get(0).getValue();
       			 constraint.setLeft(left);
       		 }
       		 if(constraintXMI.getAttributes().size()>1){
       			 String right = constraintXMI.getAttributes().get(1).getValue();
       			 constraint.setRight(right);
       		 }
       		 if(constraintXMI.getAttributes().size()>2){
       			 String operator = constraintXMI.getAttributes().get(2).getValue();
       			 constraint.setOperator(operator);
       		 }
         }//while(c.hasNext()){ 
         
         
   
         List featureGroups = racine.getChildren("featureGroup");
         Iterator fg = featureGroups.iterator();
         while(fg.hasNext()){
        	 
        	 Element featureGroupXMI = (Element)fg.next();
        	
        	 //Create a feature group
        	 FeatureGroup featureGroup = new  FeatureGroup();
        	
        	//get the attributes of the feature group
       		 if(featureGroupXMI.getAttributes().size()>0){
       			 String id = featureGroupXMI.getAttributes().get(0).getValue();
       			featureGroup.setId(id);
       		 }
       		 if(featureGroupXMI.getAttributes().size()>1){
       			 String type = featureGroupXMI.getAttributes().get(1).getValue();
       			featureGroup.setType(type);
       		 }
       		 if(featureGroupXMI.getAttributes().size()>2){
       			 String parent = featureGroupXMI.getAttributes().get(2).getValue();
       			featureGroup.setParent(parent);
       		 }
       		 
       		 resultingModel.addFeatureGroup(featureGroup);
      
      	   features = this.parseFeature(featureGroupXMI);
      	   for(int i = 0; i< features.size(); i++){
      		   Feature feature = features.get(i);
      		 featureGroup.addFeature(feature);
      		  
      		   
      	   }
        
       	  
         
         
         }//while(fg.hasNext()){ 
       
   }//parse
   

private ArrayList<Feature> parseFeature(Element xmiFeature) throws DataConversionException{
	ArrayList<Feature> res = new ArrayList<Feature>();
	   List<Element> features = xmiFeature.getChildren("feature");
		Iterator<Element> f = features.iterator();
         
        //parcourir la liste des classes
         while(f.hasNext()){
        	 
        	 Element featureXMI = (Element)f.next();
        	
        	 //Create a feature
        	 Feature feature = new  Feature();
        	 
        	 //System.out.println(feature+ "  ---- "+features.s);
        	 
        	  
        	//get the attributes of the feature
       		 if(featureXMI.getAttributes().size()>0){
       			 String featureName = featureXMI.getAttributes().get(0).getValue();
   	        	 feature.setName(featureName);
       		 }
       		 if(featureXMI.getAttributes().size()>1){
       			 String featureType = featureXMI.getAttributes().get(1).getValue();
   	        	 feature.setType(featureType);
       		 }
       		 if(featureXMI.getAttributes().size()>2){
       			 String featureParent = featureXMI.getAttributes().get(2).getValue();
   	        	 feature.setParent(featureParent);
       		 }

       		 
        	//parcourir la liste des elements
        	 List<Element> attributs = featureXMI.getChildren("element");
             Iterator<Element> e = attributs.iterator();
             while(e.hasNext()){
            	Element elementXMI = (Element)e.next();
	            if(elementXMI.getAttributes().size()>0){
	            	String elementId = elementXMI.getAttributes().get(0).getValue();
	            	feature.addElemt(elementId);
	            }
             }//while(e.hasNext()){	 
             //resultingModel.addFeature(feature);
            
             res.add(feature);
             
         }//while(f.hasNext()){ 
		return res;
}
   
   /*public static void main(String[] args) {
	   
   }*/
   public FMParser(String path){
	   
	   	//File f = new File(path);
		/*this.modelName = f.getName();
		this.modelName = MyString.readUntil(this.modelName, '.');*/
		//System.out.println(modelName);
	   
	   SAXBuilder sxb = new SAXBuilder();
	      try{
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé ;)
	       
	    	  this.document = sxb.build(new File(path));
	    	  //On initialise un nouvel élément racine avec l'élément racine du document.
	    	  this.racine = document.getRootElement();

	      //Méthode définie dans la partie 3.2. de cet article
	      
			parse();
		} catch (DataConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

 
   private static void addFeature(Feature feature, org.jdom2.Element root){

	   Element xmiFeature = new Element("feature");
	   root.addContent(xmiFeature);
	   
	  Attribute name = new Attribute("name", feature.getName());
	  xmiFeature.setAttribute(name);
	  
	  Attribute type = new Attribute("type", feature.getType());
	  xmiFeature.setAttribute(type);
	  
	   Attribute parent = new Attribute("parent", feature.getParent());
	  xmiFeature.setAttribute(parent);
	
	   
	   for(int i = 0; i < feature.getElements().size(); i++ ){
		   addElementToAFeature(feature.getElements().get(i), xmiFeature); 
		}

   }
   
   
   private static void addFeatureGroup(FeatureGroup featureGroup, org.jdom2.Element root){

	   Element xmiFeatureGroup= new Element("featureGroup");
	   root.addContent(xmiFeatureGroup);
	   
	  Attribute id = new Attribute("id", featureGroup.getId());
	  xmiFeatureGroup.setAttribute(id);
	  
	  Attribute type = new Attribute("type", featureGroup.getType());
	  xmiFeatureGroup.setAttribute(type);
	  
	  Attribute parent = new Attribute("parent", featureGroup.getParent());
	  xmiFeatureGroup.setAttribute(parent);
	
	   
	   for(int i = 0; i < featureGroup.getFeatures().size(); i++ ){
		   addFeature(featureGroup.getFeatures().get(i), xmiFeatureGroup); 
		}

   }

   
   
   private static void addConstraint(Constraint constraint, org.jdom2.Element root){

	   Element xmiConstraint= new Element("constraint");
	   root.addContent(xmiConstraint);
	   
	  Attribute left = new Attribute("left", constraint.getLeft());
	  xmiConstraint.setAttribute(left);
	  
	  Attribute right = new Attribute("right", constraint.getRight());
	  xmiConstraint.setAttribute(right);
	  
	  Attribute operator = new Attribute("operator", constraint.getOperator());
	  xmiConstraint.setAttribute(operator);


   }
   
   private static void addElementToAFeature(String elt, org.jdom2.Element xmiFeature){

	   Element xmiElement = new Element("element");
	   xmiFeature.addContent(xmiElement);
	   Attribute id = new Attribute("id", elt);
	   xmiElement.setAttribute(id);


   }
  
   
   
   public static org.jdom2.Document fromFMToXMI(FeatureModel fm) {
   
      Element root = new Element("xmi");
      root.setAttribute(new Attribute("id",fm.getId()));
      org.jdom2.Document document = new Document(root);
      
      for(int i = 0; i < fm.getMandatoryFeatures().size(); i++ ){
    	  Feature feature = fm.getMandatoryFeatures().get(i);
    	  addFeature(feature, root);
      }
      
      for(int i = 0; i < fm.getOptionalFeatures().size(); i++ ){
    	  Feature feature = fm.getOptionalFeatures().get(i);
    	  addFeature(feature, root);
      }
      
      for(int i = 0; i < fm.getAlternativeFeatureGroup().size(); i++ ){
    	  FeatureGroup featureGroup = fm.getAlternativeFeatureGroup().get(i);
    	  addFeatureGroup(featureGroup, root);
      }
      
      for(int i = 0; i < fm.getOrFeatureGroup().size(); i++ ){
    	  FeatureGroup featureGroup = fm.getOrFeatureGroup().get(i);
    	  addFeatureGroup(featureGroup, root);
      }
      
      for(int i = 0; i < fm.getConstraints().size(); i++ ){
    	  Constraint constraint = fm.getConstraints().get(i);
    	  addConstraint(constraint, root);
      }
      return document;
   }
 

   
   public static void save(FeatureModel fm, String path){
	   
	   org.jdom2.Document document = FMParser.fromFMToXMI(fm);
	   
	   
      try{
         XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
         out.output(document, new FileOutputStream(path));
      }
      catch (java.io.IOException e){}
   }
   
   /**
	 * Allows to obtain a feature model from an XMI file
	 * @param path : path of the XMI file
	 * @return return a feature model
	 */
   public static FeatureModel getFM(String path){	
		FMParser parser = new FMParser(path);
		return parser.getFeatureModel();
	}
   
//------------------------------------------------------------------------------
	
	public static void main(String[] args) {
    
		FeatureModel fm = getFM("models/test3/FM1.xmi");
		fm.disp();
		fm.setId("FFFFFFFFF");
		fm.save("models/test3/FM2.xmi" );
		System.out.println(fm.toString());
	
	
	}   
}