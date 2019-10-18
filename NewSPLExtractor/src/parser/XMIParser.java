package parser;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;


import model.Model;
import model.ModelConfiguration;
import util.MyString;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * allows to parse the XMI files using DOM
 * @author Boubakir
 *
 */
public class XMIParser{
	
	private org.jdom2.Document document;
	private Element racine;
	private int curentElemntId = 0;
	private String modelName;
	
	private Model resultingModel = new Model();


	private Model getModel() {
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

	public String getEltId(){
		curentElemntId++;
		return this.modelName+curentElemntId;
	}

   /**
    * Parsser le document XMI
 * @throws DataConversionException 
    */
	
	
	/**
	 * Parcer le document
	 * @throws DataConversionException
	 */
   @SuppressWarnings("rawtypes")
private void parse() throws DataConversionException{
	   

      
         List classes = racine.getChildren("classes");
         

         //List classes = packagee.getChildren("classes");
         
         Iterator c = classes.iterator();
         
        //parcourir la liste des classes
         while(c.hasNext()){
        	 
        	 
        	 Element classeXMI = (Element)c.next();
        	
        	 //Créer un élément de type classe
        	 model.Element classe = new model.Element(this.getEltId(),"Class","Class");
        	 resultingModel.addElement(classe);
        	 //resultingModel.addRole(new Role(elementXMI.getName(), level));
        	 
        	//Créer un élément de type nom de classe
        	 for(int i = 0; i < classeXMI.getAttributes().size(); i++){
	        	 //String classeNameName = classeXMI.getAttributes().get(i).getName();
	        	 String classeNameValue = classeXMI.getAttributes().get(i).getValue();      	 
	        	 model.Element classeName = new model.Element(this.getEltId(),"String","ClassName", classeNameValue);
	        	 
	        	 classe.addSubElement(classeName);
	        	 resultingModel.addElement(classeName);
        	 }
        	 
        	 
        	//parcourir la liste des attributs
        	 List attributs = classeXMI.getChildren("attributes");
             Iterator a = attributs.iterator();
             while(a.hasNext()){
            	Element attXMI = (Element)a.next();
            	
            	 //Créer un élément de type attribut
           	 	model.Element attribute = new model.Element(this.getEltId(),"Attribute","ClassAttribute");
           	 	classe.addSubElement(attribute);
           	 	resultingModel.addElement(attribute);
            	
           	 	for(int i = 0; i < attXMI.getAttributes().size(); i++){
           	 	
	            	 String attName = attXMI.getAttributes().get(i).getName();
	            	 String attValue = attXMI.getAttributes().get(i).getValue();      	 
	            	 
	            	 
	            	 if(attName.equals("name")){
	            		 model.Element attNameElement = new model.Element(this.getEltId(),"String","AttributeName", attValue);
	            		 attribute.addSubElement(attNameElement);
	            		 this.resultingModel.addElement(attNameElement);
	            	 }
	            	 if(attName.equals("type")){
	            		 model.Element attTypeElement = new model.Element(this.getEltId(),"String","AttributeType", attValue);
	            		 attribute.addSubElement(attTypeElement);
	            		 this.resultingModel.addElement(attTypeElement);
	            	 }
	       	 	}
             }//while(a.hasNext()){	 
        	 
        	 
           //parcourir la liste des -OPERATIONS--------------------
//List operations = classeXMI.getChildren("operations");
  List operations = classeXMI.getChildren();      	 
        	 
  			Iterator o = operations.iterator();
             while(o.hasNext()){
            	 
            	Element opXMI = (Element)o.next();

            if(opXMI.getName().equals("operations") || opXMI.getName().equals("methods") ){	
            	//Créer un élément de type operation
            	 
           	 	model.Element operation = new model.Element(this.getEltId(),"Operation","ClassOperation");
           	 	classe.addSubElement(operation);
           	 	this.resultingModel.addElement(operation);
            	
           	 	for(int i = 0; i < opXMI.getAttributes().size(); i++){
	            	 
           	 		String attName = opXMI.getAttributes().get(i).getName();
	            	String attValue = opXMI.getAttributes().get(i).getValue();      	 
	                 	 
	            	if(attName.equals("name")){
	            		model.Element opNameElement = new model.Element(this.getEltId(),"String","OperationName", attValue);
	            		operation.addSubElement(opNameElement);
		            	this.resultingModel.addElement(opNameElement);
	            	}
	            	
	            	if(attName.equalsIgnoreCase("returnType")){
	            		model.Element opTypeElement = new model.Element(this.getEltId(),"String","OperationType", attValue);
	            		operation.addSubElement(opTypeElement);
		            	this.resultingModel.addElement(opTypeElement);
	            	}
	            	
	            	
	            	 //model.Element opType = new model.Element(this.getEltId(),"String","OperationType", opTypeValue);
	            	 
	            	 
             }
            	 
            	//parcourir la liste des parametres PARAMETER --------------------------
            	 List parameters = opXMI.getChildren("parameters");
                 Iterator par = parameters.iterator();
                 int order = 0;
                 while(par.hasNext()){
                	Element parXMI = (Element)par.next();
                	
                	 //Créer un élément de type parametre
                	 
               	 	model.Element parameter = new model.Element(this.getEltId(),"Parameter","Parameter");
               	 	parameter.setOrder(order);
               	 	order++;
               	 	operation.addSubElement(parameter);
               	 	this.resultingModel.addElement(parameter);
                	
               	 for(int i = 0; i < parXMI.getAttributes().size(); i++){
	                	 
	                	 String attName = parXMI.getAttributes().get(i).getName();
		            	 String attValue = parXMI.getAttributes().get(i).getValue();      	 
		         	 
		            	 if(attName.equals("name")){
		            		 model.Element parNameElement = new model.Element(this.getEltId(),"String","ParameterName", attValue);
		                	 parameter.addSubElement(parNameElement);
			                 this.resultingModel.addElement(parNameElement);
	            		 
		            	 }
		            	 if(attName.equals("type")){
		            		 model.Element parTypeElement = new model.Element(this.getEltId(),"String","ParameterType", attValue);
		                	 parameter.addSubElement(parTypeElement);
			                 this.resultingModel.addElement(parTypeElement);
	            		 
		            	 }
		            	 
                 	}// for(int i = 0; i < parXMI.getAttributes().size(); i++){	 
                	 
                 }//while(par.hasNext()){	   --------------PARAMETER 
            	 
            	 
            	 
            	 
            }//if(opXMI.getName().equals("operations") || opXMI.getName().equals("methods") ){	 
 }//while(o.hasNext()){	------------------------OPERATION 
        	
             
        	 
        	
         
         
         
         
         
         }//while(c.hasNext()){
         
         //On affiche le nom de l’élément courant
         //System.out.println(courant.getChild("attributes").getText());
      
      
      
      //}//while(p.hasNext()){
   
     
   
   }
   
   /*public static void main(String[] args) {
	   
   }*/
   public XMIParser(String path){
	   
	   	File f = new File(path);
		this.modelName = f.getName();
		this.modelName = MyString.readUntil(this.modelName, '.');
		//System.out.println(modelName);
	   
	   SAXBuilder sxb = new SAXBuilder();
	      try{
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé ;)
	       
	    	  this.document = sxb.build(new File(path));
	    	  //On initialise un nouvel élément racine avec l'élément racine du document.
	    	  this.racine = document.getRootElement();

	      //Méthode définie dans la partie 3.2. de cet article
	    	  
	    	 /* ModelConfiguration conf = ConfigParser.getModelConfiguration("config/umlcd.xmi");
	    	  this.resultingModel.setRoles(conf.getRoles());*/
	      
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

 /*
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
 */  
   /*
   public static org.jdom2.Document fromModelToXMI(Model model) {
   
	    
	   
      //Nous allons commencer notre arborescence en créant la racine XML
      //qui sera ici "personnes".
      Element racine = new Element("xmi");
      racine.setAttribute(new Attribute("id",model.getId()));
      
      //On crée un nouveau Document JDOM basé sur la racine que l'on vient de créer
      org.jdom2.Document document = new Document(racine);
      //org.jdom2.Document document = new Document();

      
      //for(int i = 0; i < 1; i++ ){
      for(int i = 0; i < model.getElements().size(); i++ ){
			if(model.getElements().get(i).getLevel()==1){
				model.Element elt = model.getElements().get(i);
				addElement(elt, racine);
			}
		}
      
          
     //document.addContent(racine);
      
      //Element etl1 = new Element("preseonnes");
      //Element etl2 = new Element("preseonnes");
      
      //racine.addContent(etl1);
      //racine.addContent(etl2);
      
      
      
         //On crée un nouvel Element etudiant et on l'ajoute
         //en tant qu'Element de racine
         //Element etudiant = new Element("etudiant");
        //racine.addContent(etudiant);

         //On crée un nouvel Attribut classe et on l'ajoute à etudiant
        //grâce à la méthode setAttribute
        // Attribute classe = new Attribute("classe","P2");
         //etudiant.setAttribute(classe);

         //On crée un nouvel Element nom, on lui assigne du texte
         //et on l'ajoute en tant qu'Element de etudiant
        // Element nom = new Element("nom");
        // nom.setText("CynO");
        // etudiant.addContent(nom);

         return document;
         //save(document, "Exercice1.xml");
      
   }
 
*/
   /*
   public static void save(Model model, String path){
	   org.jdom2.Document document = fromModelToXMI(model);
      try{
         XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
         out.output(document, new FileOutputStream(path));
      }
      catch (java.io.IOException e){}
   }
   */
//------------------------------------------------------------------------------
	

   /**
	 * Allows to obtain a model from a XMI file
	 * @param path : path of the XMI file
	 * @return return a model
	 */
	public static Model getModel(String path, String id){	
		XMIParser parser = new XMIParser(path);
		Model model  = parser.getModel();
		model.setId(id);
		createReferenceType(model);
		
		
		return model;
	}
	
	
	
	public static void createReferenceType(Model model){
		ArrayList<model.Element> classes = new ArrayList<model.Element>();
		for(int i = 0; i < model.getElements().size(); i++){
			if(model.getElements().get(i).getRole().equals("Class")){
				classes.add(model.getElements().get(i));
			}//if(model.getElements().get(i).getRole().equals("Class")){
		}//for(int i = 0; i < model.getElements().size(); i++){
		
		
		for(int i = 0; i < model.getElements().size(); i++){
			model.Element element = model.getElements().get(i);
			if(element.getRole().equals("AttributeType") || element.getRole().equals("OperationType") || element.getRole().equals("ParameterType")  ){
				
				for(int j = 0; j < classes.size(); j++){
					
					if(element.getValue().equals(classes.get(j).getSubElements("ClassName").get(0).getValue())){
						//System.out.println(element.getValue()+"  "+" ******");
						element.setValue(classes.get(j).getId());
						element.setType("Reference");
					}
					
					
				}//for(int j = 0; j < classes.size(); j++){
				
				
			}//if
			
		}//for(int i = 0; i < model.getElements(); i++){
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Allows to obtain a model from a XMI file
	 * @param path : path of the XMI file
	 * @return return a model
	 */
	public static Model getModel(String path){	
		
		String id = MyString.readUntil(new File(path).getName(), '.');
		return getModel(path, id);

	}
	
   
	
	
	/**
	 * Allows to read a set of models stored in a folder as XMI files
	 * @param parent : the path of the folder containing the XMI files
	 * @return
	 */
	public static ArrayList<Model> getModelsFromRep(String parent){
		ArrayList<Model> res = new ArrayList<Model>();
		
		
		File rep = new File(parent);
		
		if(rep.isDirectory()){
			File[] files = rep.listFiles();
			for(int i = 0; i< files.length; i++){
				Model m = getModel(files[i].getPath());
				res.add(m);
			}
					
		}
				return res;

	}
   
   
		
  /* 
	public static void main(String[] args) {
    

		
			ArrayList<Model> models = getModelsFromRep("models/sup/");
			
			for(int i=0; i < models.size(); i++){
				models.get(i).disp();
				models.get(i).save("models/sup/s"+i+".xmi");
			}
			System.out.println("END");
			
			
			

		 
	
	
	}   
*/
	


	public static void main(String[] args) {
   
		ModelConfiguration.initialize();
		//ModelConfiguration.createModelConfig("config/umlcd.xmi");
		String id = MyString.readUntil(new File("models/test/org/M1.xmi").getName(), '.');
		Model model  = XMIParser.getModel("models/test/org/M1.xmi", id);
		model.disp();
		model.save("models/test/M1.xmi");	
		
		id = MyString.readUntil(new File("models/test/org/M2.xmi").getName(), '.');
		model  = XMIParser.getModel("models/test/org/M2.xmi", id);
		model.disp();
		model.save("models/test/M2.xmi");	
		
		
		
	} 

}