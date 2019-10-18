package fm;

import java.util.ArrayList;

public class FeatureGroup {
	
	private String type = ""; //two values: alternative, or
	private String parent;
	private String id;
	private String role;
	private ArrayList<Feature> features = new ArrayList<Feature>();
	
	public FeatureGroup() {}
	
	public FeatureGroup(String id, String type, String parent) {
		this.id = id;
		this.type = type;
		this.parent = parent;
		
	}
	
	public FeatureGroup(String id, String type, String parent, String role) {
		this(id, type, parent);
		this.role = role;
		
		
	}

	public String toString(){
		return this.getId();
	}
	
	public String getDescription(){
		return this.toString()+": An "+this.type+" feature group "+ 
					System.getProperty("line.separator")+this.parent;
	}
	
	public String getType() {
		return type;
	}

	public boolean isAlternative() {
		return type.equals("alternative");
	}
	public boolean isOr() {
		return type.equals("or");
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<Feature> features) {
		this.features = features;
	}

	public void addFeature(Feature feature) {
		this.features.add(feature);
	}
	
	public boolean contains(Feature feature) {	
		return this.features.contains(feature); 
	}
	
	
	
	public String getRole() {
		return role;
	}

	public void disp(){
		System.out.println("id: "+this.id+", type: "+this.type+", parent: "+this.parent+", role: "+this.role);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}

}
