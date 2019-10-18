package parser;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import model.Model;
import model.ModelConfiguration;
import util.MyString;

import java.util.List;
import java.util.Iterator;

/**
 * allows to parse the XMI files using DOM
 * @author Boubakir
 *
 */
public class ModelParser{
	
	private org.jdom2.Document document;
	private Element racine;
	private int curentElemntId = 0;
	private String modelName;
	
	private Model resultingModel = new Model();


	public Model getModel() {
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
   private void parse() throws DataConversionException{
	   //List classes = racine.getChildren("classes");
	   this.resultingModel.setId(racine.getAttributeValue("id"));
	   this.parse(racine, null);
       
 }
   
   @SuppressWarnings("rawtypes")
private void parse(Element parentXMI, model.Element parent) throws DataConversionException{
	   
	   
	

	List elements = parentXMI.getChildren();
	//level = level + 1;  
         Iterator c = elements.iterator();

         while(c.hasNext()){
        	 
        	 Element elementXMI = (Element)c.next();
        	 
        	 model.Element element = new model.Element();
        	 resultingModel.addElement(element);
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
        	 
        
        	 
             this.parse(elementXMI, element);
         
         
         }//while(c.hasNext()){
         

   
   }
   
   /*public static void main(String[] args) {
	   
   }*/
   public ModelParser(String path){
	   
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
    	  model.Element elt = model.getElements().get(i);
    		if(ModelConfiguration.getLevel(elt.getRole())==1){
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
 

   
   public static void save(Model model, String path){
	   org.jdom2.Document document = fromModelToXMI(model);
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
	public static Model getModel(String path){	
		ModelParser parser = new ModelParser(path);
		Model model  = parser.getModel();
		return model;
	}
   

   
   
   
	public static void main(String[] args) {
   
		ModelConfiguration.createModelConfig("config/umlcd.xmi");
		
		ModelParser parser = new ModelParser("models/argo/Original.xmi");
		Model model  = parser.getModel();
		/*String id = MyString.readUntil(new File("models/basic/M1.xmi").getName(), '.');
		model.setId(id);*/
		System.out.println("--------------------");
		//model.dispRoles();
		model.disp();
		System.out.println(model.getElements().get(0).getValue());
		System.out.println(model.getElements().size());
		
		
		
		
		
	
	
	}   
}