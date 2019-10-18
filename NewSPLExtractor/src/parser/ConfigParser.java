package parser;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

import model.Criterion;
import model.ModelConfiguration;
import model.Role;
import util.MyString;

import java.util.List;
import java.util.Iterator;

/**
 * allows to parse an XMI configuration files using DOM
 * @author Boubakir
 *
 */
public class ConfigParser{
	
	private org.jdom2.Document document;
	private Element racine;
	private int curentElemntId = 0;
	private String modelName;
	
	private ModelConfiguration conf = new ModelConfiguration();


	public ModelConfiguration getModelConfiguration() {
		return conf;
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
	     
	   	List roles = racine.getChildren("role");
         Iterator r = roles.iterator();

         while(r.hasNext()){
        	 
        	 Element roleXMI = (Element)r.next();

        	 Role role = new  Role();
        	 conf.addRole(role);
        	  
 
       		 if(roleXMI.getAttributes().size()>0){
       			 String roleName = roleXMI.getAttributes().get(0).getValue();
       			 role.setName(roleName);
       		 }
       		 if(roleXMI.getAttributes().size()>1){
       			 String roleLevelStr = roleXMI.getAttributes().get(1).getValue();
       			try{
       				int level = Integer.parseInt(roleLevelStr);
       				role.setLevel(level);
       				if(level>conf.getLevelNumber()) conf.setLevelNumber(level);
    			}catch(java.lang.NumberFormatException e){
    				e.getMessage();
    			}
       		 }
       		if(roleXMI.getAttributes().size()>2){
      			 String category = roleXMI.getAttributes().get(2).getValue();
      			try{
      				role.setCategory(Integer.parseInt(category));
   			}catch(java.lang.NumberFormatException e){
   				e.getMessage();
   			}
      		 }
 
         }//while(f.hasNext()){ 
         
         
         
         List criteria = racine.getChildren("criteria");
         Iterator c = criteria.iterator();

         while(c.hasNext()){
        	 
        	 Element criteriaXMI = (Element)c.next();
        	 String roleName = "";
        	 if(criteriaXMI.getAttributes().size()>0){
       			 roleName = criteriaXMI.getAttributes().get(0).getValue();
       		 }
        	 
        	 
        	  
             List criterions = criteriaXMI.getChildren("criterion");
             Iterator ctr = criterions.iterator();

             while(ctr.hasNext()){
            	 
            	 Element criterionXMI = (Element)ctr.next();
            	 String subRoleName = "";
            	 float weight = -1;
            	 if(criterionXMI.getAttributes().size()>0){
            		 subRoleName = criterionXMI.getAttributes().get(0).getValue();
           		 }
            	 //String sup = "A";
            	 if(criterionXMI.getAttributes().size()>1){  		 
            		 try{
            			 //sup = criterionXMI.getAttributes().get(1).getValue();
            			 weight = Float.parseFloat(criterionXMI.getAttributes().get(1).getValue());
            		 }catch(java.lang.NumberFormatException e){
      					e.getMessage();
      				}
            	 }
            	 //sup
            	 Criterion  criterion  = new Criterion(subRoleName, weight); 
            	 conf.addCriterion(roleName, criterion);
            	//System.out.println(roleName+ "   "+criterion.getRoleName()+"   "+criterion.getWeight()+"   **********   "+sup);
            	 
             	 
             }//while(ctr.hasNext()){
        	 
        	 
        	 
         
         }//while(c.hasNext()){
         
         
         
         
        }//parse
   
   /*public static void main(String[] args) {
	   
   }*/
   public ConfigParser(String path){
	   
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
   
   public static ModelConfiguration getModelConfiguration(String path) {
	    
		ConfigParser configParser = new ConfigParser(path);
		ModelConfiguration conf = configParser.getModelConfiguration();
		//conf.disp();
		return conf;
		 
	
	
	}
   
  
   
	
	public static void main(String[] args) {
    	ModelConfiguration conf = getModelConfiguration("config/umlcd.xmi");
		conf.disp();
	}   
}