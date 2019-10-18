package fm;

import java.util.ArrayList;
public class Feature {
	
	
	private String name ="";
	private String type ="";
	private String parent =""; //optional, mandatory, variant, alternative
	private ArrayList<String> elements = new ArrayList<String>();

	
	public Feature(String name, String type, String parent) {
		super();
		this.name = name;
		this.type = type;
		this.parent = parent;
	}

	public Feature(String name, String type, String parent, ArrayList<String> elements) {
		this(name, type, parent);
		this.elements = elements;
	}


	
	
	

	public Feature() {
		//super();
	}



	public Feature(String name) {
		super();
		this.name = name;
	}



	public ArrayList<String> getElements() {
		return elements;
	}



	public String getName() {
		return name;
	}



	public String getType() {
		return type;
	}



	
	public String getParent() {
		return this.parent;
	}



	public void setParent(String parent) {
		this.parent = parent;
	}



	public void setElements(ArrayList<String> elements) {
		this.elements = elements;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setType(String type) {
		this.type = type;
	}

	public void addElemt(String elt) {
		this.elements.add(elt);
	}
	
	public void removeElement(String elt) {
		int index  = elements.indexOf(elt);
		this.elements.remove(index);
	}
	
	public boolean isEmpty() {
		return  elements.size()==0;
	}
	
	
	/**
	 * allows to display the feature
	 */
	public void disp(){
		System.out.println("   name = "+this.name);
		System.out.println("   type = "+this.type);
		System.out.println("   parent = "+this.parent);
		System.out.println("   elements");
		System.out.print("     ");
		for(int i = 0; i < this.elements.size(); i++ ){
			System.out.print("   "+this.elements.get(i));
		}
		System.out.println();
		
	}
	
	public String toString(){
		return this.name;
	}
	
	public String getDescription(){
		String res = "A feature"+System.getProperty("line.separator")+
				"type: "+this.type;
		return res;
		
	}
	
	public boolean hasParent(){
		return !parent.equals("");
		
	}
	
	public boolean contains(String elt){
		return this.elements.contains(elt);
		
	}
	
	public String toStr(){
		String parent = "";
		if(this.parent.equals("")){
		}else{
			parent =",  parent: "+this.parent;
		}
		
		
		String res = "name: "+this.name +", type: "+this.type+ parent +
		",  elements: ";
		for(int i = 0; i < this.elements.size(); i++ ){
			res = res + "     "+this.elements.get(i);
		}
		return res = res + System.getProperty("line.separator");
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
