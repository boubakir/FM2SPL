package model;

import java.io.File;
import java.util.ArrayList;

import parser.ModelParser;
import util.MyString;

/**
 * represents a model
 * @author Boubakir
 *
 */
public class Model {
	
	
	private String id = "";	//L'identificateur du model
	private ArrayList<Element> elements;	//L'ensemble des éléments
	private String type = "";
	
	public Model(){
		//this.modelConf= new ModelConfiguration();
		this.elements = new ArrayList<Element>();
		//this.roles = new ArrayList<Role>();
	}
	
	public Model(String id){
		this();
		this.id = id;
	}
	
	
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	
	public String getType() {
		return type;
	}

	public void setType(String typ) {
		this.type = typ;
	}

	/*
	 * Retourne les éléments du modèle
	 * @return
	 */
	public ArrayList<Element> getElements() {
		return elements;
	}
	
	
	/*
	 * Retourner une liste d'élménents qui correspond à un role donné
	 * @param role
	 * @return
	 */
	public ArrayList<Element> getElements(String role) {
		
		ArrayList<Element> res = new ArrayList<Element>();
		
		for(int i = 0; i < this.elements.size(); i++){
			if(this.elements.get(i).getRole().equals(role)){
				res.add(this.elements.get(i));
			}
		}
		return res;
	
	}


public void removeElements(String role) {
		
		ArrayList<Element> liste = new ArrayList<Element>();
		
		
		for(int i = 0; i < this.elements.size(); i++){
			
			
			if(this.elements.get(i).getRole().equals(role)){
			}else{
				
				liste.add(this.elements.get(i));
			}
		}
		this.elements = liste;
		
		for(int i = 0; i < this.elements.size(); i++){
			this.elements.get(i).removeSubElements(role);
		}
			
		
	
	}
	


public void removeElement(Element elt) {
	
	this.elements.remove(elt);
	
	for(int i = 0; i < this.elements.size(); i++){
		this.elements.get(i).getSubElements().remove(elt);
	}
		
	

}


public void replaceOperationsByAttributes() {
	

	for(int i = 0; i < this.elements.size(); i++){
		
		Element elt = this.elements.get(i);
		if(elt.getType().equals("Operation")){
			
			
			elt.setType("Attribute");
			elt.setRole("ClassAttribute");
			
			for(int j = 0; j < elt.getSubElements().size(); j++){
				Element subElt = elt.getSubElements().get(j);
				if(subElt.getRole().equals("OperationName")){
					subElt.setRole("AttributeName");
				}
				
			}
		}
	}

	
	
		
	

}













	public void addElement(Element elt){
		this.elements.add(elt);
	}
	/*
	public void addElement(ArrayList<Element> elts, Element parentElement1, Element parentElement2){
		
		for(int i = 0; i < elts.size(); i++){
			this.addElement(elts.get(i), parentElement1, parentElement2);
		}
	}*/
	
	/*
public void addElement(ArrayList<Element> elts1, ArrayList<Element> elts2, Element parentElement1, Element parentElement2){
	

	
	ArrayList<Element> elts = new ArrayList<Element>();
	//elts.addAll(elts2);
	
	Element elt1 = null;
	Element elt2 = null;
	for(int i = 0; i < elts1.size(); i++){
		elt1 = elts1.get(i);
		boolean bool = false;
		int j = 0;
		while( (j < elts2.size()) && (!bool) ){
			elt2 = elts2.get(j);
			if(  elt1.getValue().equalsIgnoreCase(elt2.getValue())  ){
				bool = true;
				
			}else{
				j++;
			}
		}//while( (j < elts2.size()) && (bool) )
		
		if(bool){
			Element elt = new Element(elt1, elt2); 
			this.addElement(elt, parentElement1, parentElement2);
			//System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRR  "+elt.getId());
		}else{
			elts.add(elt1);
		}
	}//for(int i = 0; i < elts1.size(); i++)
	
	for(int i = 0; i < elts2.size(); i++){
		elt2 = elts2.get(i);
		boolean bool = false;
		int j = 0;
		while( (j < elts1.size()) && (!bool) ){
			elt1 = elts1.get(j);
			if(  elt1.getValue().equalsIgnoreCase(elt2.getValue())  ){
				bool = true;
			}else{
				j++;
			}
		}//while( (j < elts2.size()) && (bool) )
		
		if(bool){
		}else{
			elts.add(elt2);
		}
	}//for(int i = 0; i < elts1.size(); i++)
	
	//this.addElement(elts, parentElement1, parentElement2);
	
	//NEW 02/04/2018
	for(int i = 0; i < elts.size(); i++){
		Element elt = new Element(elts.get(i));
		this.addElement(elt, parentElement1, parentElement2);
	}
	//--NEW 02/04/2018
	
}
	
*/	
	
	/*
	 * Ajouter un élément
	 * @param elt
	 */

	public void addUnmatchedElement(Element elt){
		Element parent  = null;
		Element newElement = new Element(elt);
		this.elements.add(newElement);
		
		//if(elt.getLevel() !=1){
		if(ModelConfiguration.getLevel(elt.getRole()) !=1){
			boolean bool = false;	int i = 0;
			while(i < this.elements.size() && !bool){
				
				Element elt2 = this.elements.get(i);
				Element e1 = elt2.getOriginal1();
				Element e2 = elt2.getOriginal2();	
				
				//SUPP
				if(e1!=null){
					if(e1.isParent(elt)) {  parent = elt2;  bool = true;  }	
				}
				
				if(e2!=null){
					if(e2.isParent(elt)) {  parent = elt2;  bool = true;  }	
				}
				
				
				i++;
			}//while+{	
			
			if(bool){	
				parent.addSubElement(newElement); 	
			}
				
		}//if(elt.getLayer() !=1){	
	}			
		
	
	public void addElement(Element elt, Element parentElement1, Element parentElement2){
		
		//02/04/2018
		
		this.elements.add(elt);
		
		//if(elt.getLevel() !=1){
		if(ModelConfiguration.getLevel(elt.getRole()) !=1){ 	
			
			
			for(int i = 0; i < this.elements.size(); i++){
				
				Element e1 = this.elements.get(i).getOriginal1();
				Element e2 = this.elements.get(i).getOriginal2(); 
				
				if(parentElement1 == e1 && parentElement2 == e2){
					this.elements.get(i).getSubElements().add(elt);
				}
				
			}//for(int i = 0; i < this.elements.size(); i++){
		}//if(elt.getLayer() !=1){
		
	}
	
	
	


/**
 * allows to display the model
 */
	public void disp(){
		 
		
		System.out.println("------------------------------------------- ");
		System.out.println("Displying a model ");
		System.out.println("------------------------------------------- ");
		System.out.println("Model id: "+this.getId());
		
		for(int i = 0; i < this.elements.size(); i++ ){

			//if(this.elements.get(i).getLevel()==1){
			if(ModelConfiguration.getLevel(this.elements.get(i).getRole()) ==1){ 	
				this.elements.get(i).dispAll("    ");
			}
		}
	}
	
public String toString(){
	String res = "Model id: ";
	res = res+this.getId();
		
		for(int i = 0; i < this.elements.size(); i++ ){

			//if(this.elements.get(i).getLevel()==1){
			if(ModelConfiguration.getLevel(this.elements.get(i).getRole()) ==1){ 	
				res = res + this.elements.get(i).toString("    ");
			}
		}
		return res;
	}

	
public void disp2(){
		
		for(int i = 0; i < this.elements.size(); i++ ){
			//if(this.elements.get(i).getLevel()==1){
			if(ModelConfiguration.getLevel(this.elements.get(i).getRole())==1){
				
				this.elements.get(i).dispAll3("    ");
			}
		}
	}
	
	
	/*
	 * return a direct element of a model
	 * @param role
	 * @return
	 */
	public Element getElement(String id){
		Element e = null;
		for(int i = 0; i < this.elements.size(); i++){
			if(this.elements.get(i).getId().equals(id)){
				e = this.elements.get(i);
			}
		}
		return e;
	}

/**
 * allows to save the model in a XMI file
 *  * @param path: the path of the XMI file 
 */
public void save(String path){
	ModelParser.save(this, path);	
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
	
	public static Model getModel(String path, String id){	
		Model model  = ModelParser.getModel(path);
		model.setId(id);
		return model;
	}
	
	
	
	public Element getParent(Element elt){
		Element res = null;
		boolean bool = false;
		
		int i = 0;
		while(i<this.elements.size() && !bool){
			if(this.elements.get(i).isParent(elt)){
				res = this.elements.get(i);
				bool = true;
			}else{
				i++;
			}
		}
		
		return res;
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
				if(files[i].isFile()){
					Model m = Model.getModel(files[i].getPath());
					//String id = MyString.readUntil(files[i].getName(), '.');
					//System.out.println(id);
					//m.setId(id);
					res.add(m);
				}
			}
					
		}
				return res;

	}

	
	
	
	
	
	/**
	 * returns the size of the model 
	 * @return
	 */
	public int getSize(){
		return this.getElements("Class").size();
	}
	
	public String getDescription(){
		return this.getId();
	}
	
	

	public static void main(String[] args) {
		/*
		 * read and display a set of models
		 */
		
		ModelConfiguration.createModelConfig("config/umlcd.xmi");
		Model model = Model.getModel("models/test/M2.xmi");
		String str = model.toString();
		System.out.println(str);
		
		/*
		ArrayList<Model> models = Model.getModelsFromRep("models/sup/");
		
		for(int i=0; i < models.size(); i++){
			models.get(i).disp();
			models.get(i).save("models/sup/s"+i+"xmi");
		}*/
		System.out.println("END");
		
		
		
	
	}
	
	

}
