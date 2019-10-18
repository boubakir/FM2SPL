package model;

public class Criterion {

	String roleName;
	float weight;
	
	
	
	public Criterion(String role, float weight) {
		super();
		this.roleName = role;
		this.weight = weight;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String role) {
		this.roleName = role;
	}


	public float getWeight() {
		return weight;
	}


	public void setWeight(float weight) {
		this.weight = weight;
	}

	
	public void disp() {
		System.out.println("          role name: "+this.roleName + "  weight: "+this.weight);

	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
