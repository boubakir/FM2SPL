package model;

import java.util.ArrayList;

public class Role {
	private String name;
	private int level;
	private int category;

	private ArrayList<Criterion> creteria = new ArrayList<Criterion>() ;
	
	public Role() {
	}

	public Role(String name, int level, int category) {
		super();
		this.name = name;
		this.level = level;
		this.category = category;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	
	public void addCriterion(Criterion ctr){
		this.creteria.add(ctr);
	}
	
	
	public ArrayList<Criterion> getCreteria() {
		return creteria;
	}

	public void disp() {
		System.out.print("     Role name: "+this.name);
		System.out.print("     level: "+this.level);
		System.out.println("   category: "+this.category);
		
		for(int i = 0; i < this.creteria.size(); i++){
			this.creteria.get(i).disp();
		}
	}
	
	public ArrayList<Role> getSubRole() {
		ArrayList<Role> res = new ArrayList<Role>();
		for(int i = 0; i < this.creteria.size(); i++){
			Role subRole = ModelConfiguration.getRole(this.creteria.get(i).getRoleName());
			res.add(subRole);
		}
		
		return res;
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
