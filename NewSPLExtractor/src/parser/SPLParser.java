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
import model.Model;
import model.ModelConfiguration;
import spl.SPL;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * allows to parse the XMI files using DOM
 * @author Boubakir
 *
 */
public class SPLParser{
	
	private org.jdom2.Document document;
	private Element racine;
	private SPL spl = new SPL();


	public SPL getSPL() {
		return spl;
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

	
	   @SuppressWarnings("rawtypes")
   private void parse() throws DataConversionException{
		   /*Model model = new Model();
		   FeatureModel fm = new FeatureModel();*/
		   this.spl.setId(racine.getAttributeValue("id"));
		  
		   //parsing the spl model
		   List models = racine.getChildren("model");

		   Iterator modelsIterator = models.iterator();
		   while(modelsIterator.hasNext()){     	 
			   Element modelXMI = (Element)modelsIterator.next();
			   this.spl.getSplModel().setType(modelXMI.getAttributeValue("type"));
			   this.spl.getSplModel().setId(this.spl.getId());
			   this.parseModel(modelXMI, null);
		   }

		   
		   
		   //parsing the spl model
		   List fms = racine.getChildren("fm");

		   Iterator fmsIterator = fms.iterator();
		   while(fmsIterator.hasNext()){     	 
			   Element fmXMI = (Element)fmsIterator.next();
			   this.spl.getFeatureModel().setId(this.spl.getId());
			   this.parseFM(fmXMI);
		   }

 
 }

	   
	   @SuppressWarnings("rawtypes")
	   private void parseFM(Element fmXMI) throws DataConversionException{
		   
		   ArrayList<Feature> features = this.parseFeature(fmXMI);
		   for(int i = 0; i< features.size(); i++){
			   Feature feature = features.get(i);
	     		if(feature.getType().equals("mandatory")){
	     			this.spl.getFeatureModel().addMandatoryFeature(feature);
	     		}else{
	     		  	 if(feature.getType().equals("optional")){
	     		  		this.spl.getFeatureModel().addOptionalFeature(feature);
	     		  	 }else{}
	     		   }

	     	   }
	            
	            
	            
	            List constraints = fmXMI.getChildren("constraint");
	            Iterator c = constraints.iterator();
	            while(c.hasNext()){
	           	 
	           	 Element constraintXMI = (Element)c.next();
	           	
	           	 //Create a constraint
	           	 Constraint constraint = new  Constraint();
	           	 this.spl.getFeatureModel().addConstraint(constraint);
	           	  
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
	            
	            List featureGroups = fmXMI.getChildren("featureGroup");
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
	          		 
	          		 this.spl.getFeatureModel().addFeatureGroup(featureGroup);
	         
	         	   features = this.parseFeature(featureGroupXMI);
	         	   for(int i = 0; i< features.size(); i++){
	         		   Feature feature = features.get(i);
	         		  feature.setParent(featureGroup.getId());
	         		  feature.setType(featureGroup.getType());
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
		 
	   
   
   @SuppressWarnings("rawtypes")
private void parseModel(Element parentXMI, model.Element parent) throws DataConversionException{

	List elements = parentXMI.getChildren();
	//level = level + 1;  
         Iterator c = elements.iterator();

         while(c.hasNext()){
        	 
        	 Element elementXMI = (Element)c.next();
        	 
        	 model.Element element = new model.Element();
        	 this.spl.getSplModel().addElement(element);
        	 if(parent !=null) parent.addSubElement(element);
        	 //resultingModel.addRole(new Role(elementXMI.getName(), level));
        	 
        	  // System.out.println(elementXMI.getName()+"    *****    "+level);
        	 element.setRole(elementXMI.getName());
        	
        	 for(int i = 0; i < elementXMI.getAttributes().size(); i++){
	        	 String attributeName = elementXMI.getAttributes().get(i).getName();
	        	 String attributeValue = elementXMI.getAttributes().get(i).getValue(); 
	        	 element.setAttribute(attributeName, attributeValue);
	        	 //if(attributeName.equals("order")) System.out.println("------------------------------------  "+attributeName+"  "+attributeValue);
	        	 //System.out.println(attributeName+"      "+attributeValue);
        	 }// for(int i = 0; i < elementXMI.getAttributes().size(); i++){
        	 
        
        	 
             this.parseModel(elementXMI, element);
         
         
         }//while(c.hasNext()){
         

   
   }
   
   /*public static void main(String[] args) {
	   
   }*/
   public SPLParser(String path){

	   
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

   
   

   public static org.jdom2.Document fromSPLToXMI(SPL spl) {
   

      Element racine = new Element("xmi");
      racine.setAttribute(new Attribute("id",spl.getId()));

      
      org.jdom2.Document document = new Document(racine);
      addModel(spl.getSplModel(),racine);
      addFM(spl.getFeatureModel(),racine);
         return document;
   }
   
   
   private static void addFM(FeatureModel fm, org.jdom2.Element element){
	   
	   Element XMIFM = new Element("fm");
	   element.addContent(XMIFM);
 
	   for(int i = 0; i < fm.getMandatoryFeatures().size(); i++ ){
		   Feature feature = fm.getMandatoryFeatures().get(i);
		   addFeature(feature, XMIFM);
	   }
	   
	   for(int i = 0; i < fm.getOptionalFeatures().size(); i++ ){
		   Feature feature = fm.getOptionalFeatures().get(i);
		   addFeature(feature, XMIFM);
	   }
	   
	   for(int i = 0; i < fm.getAlternativeFeatureGroup().size(); i++ ){
		   FeatureGroup featureGroup = fm.getAlternativeFeatureGroup().get(i);
		   addFeatureGroup(featureGroup, XMIFM);
	   }
	   
	   for(int i = 0; i < fm.getOrFeatureGroup().size(); i++ ){
		   FeatureGroup featureGroup = fm.getOrFeatureGroup().get(i);
		   addFeatureGroup(featureGroup, XMIFM);
	   }
	   
	   
	   
	   for(int i = 0; i < fm.getConstraints().size(); i++ ){
		   Constraint constraint = fm.getConstraints().get(i);
	    	addConstraint(constraint, XMIFM);
	    }

   }
   

   
   private static void addFeature(Feature feature, org.jdom2.Element XMIFM){

	   Element xmiFeature = new Element("feature");
	   XMIFM.addContent(xmiFeature);
	   
	  Attribute name = new Attribute("name", feature.getName());
	  xmiFeature.setAttribute(name);
	  
	  Attribute type = new Attribute("type", feature.getType());
	  xmiFeature.setAttribute(type);
	  
	   Attribute parent = new Attribute("parent", feature.getParent());
	  xmiFeature.setAttribute(parent);
	
	   
	   for(int i = 0; i < feature.getElements().size(); i++ ){
		   Element xmiElement = new Element("element");
		   xmiFeature.addContent(xmiElement);
		   Attribute id = new Attribute("id", feature.getElements().get(i));
		   xmiElement.setAttribute(id);
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

   
   
   private static void addConstraint(Constraint constraint, org.jdom2.Element XMIFM){

	   Element xmiConstraint= new Element("constraint");
	   XMIFM.addContent(xmiConstraint);
	   
	  Attribute left = new Attribute("left", constraint.getLeft());
	  xmiConstraint.setAttribute(left);
	  
	  Attribute right = new Attribute("right", constraint.getRight());
	  xmiConstraint.setAttribute(right);
	  
	  Attribute operator = new Attribute("operator", constraint.getOperator());
	  xmiConstraint.setAttribute(operator);


   }
   
   
   
   private static void addModel(Model model, org.jdom2.Element element){
	   
	   Element XMIModel = new Element("model");
	   XMIModel.setAttribute(new Attribute("type", model.getType()));
	   element.addContent(XMIModel);
	   
	   for(int i = 0; i < model.getElements().size(); i++ ){
		 	  model.Element elt = model.getElements().get(i);
		 		if(ModelConfiguration.getLevel(elt.getRole())==1){
						addElement(elt, XMIModel);
					}
				}
	   
   }


private static void addElement(model.Element elt, org.jdom2.Element element){
	
	   
	   
	   
	   Element XMIElt = new Element(elt.getRole());
	   element.addContent(XMIElt);
	   
	   //System.out.println(elt.getType()+"   >>>>>>>>>>>>>>>>>>>>>   "+elt.getId());
	   Attribute id = new Attribute("id",elt.getId());
	   
	   XMIElt.setAttribute(id);
	   
	   
	   
	   Attribute type = new Attribute("type",elt.getType());
	   XMIElt.setAttribute(type);
	   
	   
	   
	   
	   if(elt.getOrder()!=-1){  Attribute order = new Attribute("order",""+elt.getOrder());
	   XMIElt.setAttribute(order);  }
	   
	   if(elt.getValue()!=""){  Attribute value = new Attribute("value",elt.getValue());
	   XMIElt.setAttribute(value);  }
	   
	   
	   
	   for(int i = 0; i < elt.getSubElements().size(); i++ ){
		   addElement(elt.getSubElements().get(i), XMIElt); 
		}
	   
	   
   }
   
   
 

   
   public static void save(SPL spl, String path){
	   org.jdom2.Document document = fromSPLToXMI(spl);
      try{
         XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
         out.output(document, new FileOutputStream(path));
      }
      catch (java.io.IOException e){}
   }
//------------------------------------------------------------------------------
	

   /**
	 * Allows to obtain a model from a XMI file
	 * @param path : path of the XMI file
	 * @return return a model
	 */
	public static SPL getSPL(String path){	
		SPLParser parser = new SPLParser(path);
		SPL spl  = parser.getSPL();
		return spl;
	}
   

   
   
   
	public static void main(String[] args) {
   
		ModelConfiguration.initialize();
		
		SPLParser parser = new SPLParser("models/spl/spl.xmi");
		SPL spl  = parser.getSPL();
		
		spl.getSplModel().disp();
		System.out.println();
		spl.getFeatureModel().disp();
		
		System.out.println();
		
		save(spl, "models/spl/ceatedSpl.xmi" );
		
		
		
		
		
	
	
	}   
}