package model;

import java.util.ArrayList;

import parser.ConfigParser;
import util.MyString;
import util.TextFile;


public class ModelConfiguration {
	
	private ArrayList<Role>  roles = new ArrayList<Role>() ;
	private int levelNumber;
	
	
	public int getLevelNumber() {
		return levelNumber;
	}





	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}




	private static ModelConfiguration modelConf;  
	
	


	public static ModelConfiguration getModelConf() {
		return modelConf;
	}





	public static void setModelConf(ModelConfiguration modelConf) {
		ModelConfiguration.modelConf = modelConf;
	}





	public ArrayList<Role> getRoles() {
		return roles;
	}



	public static Role getRole(String name) {
		
		int i = 0; Boolean found = false;	Role role = null;
		while(i < modelConf.roles.size() && !found){
			if(modelConf.roles.get(i).getName().equalsIgnoreCase(name)){
				found = true;	role = modelConf.roles.get(i);
			}else i++;
		}//while(i < this.roles.size() && !found){
		
		return role;
	}
	


	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}


	public void addRole(Role role) {
		this.roles.add(role);
	}


	public void disp() {

		System.out.println();
		for(int i = 0; i < this.roles.size(); i++){
			this.roles.get(i).disp();
		
		}

	}
	
	/*
	public void disp() {

		System.out.println();
		for(int i = 0; i < this.roles.size(); i++){
			System.out.println(this.roles.get(i).getName()+"   "+this.roles.get(i).getLevel()+"   "+this.roles.get(i).getCategory());
		}

	}*/


	public static void createModelConfig(String path) {
		ModelConfiguration conf = ConfigParser.getModelConfiguration(path);
		ModelConfiguration.modelConf = conf;

	}
	
	
	/**
	 * Get the level of a role
	 * @param roleName
	 * @return
	 */
	public static int getLevel(String roleName){
		int level = 0;
		int i = 0;	boolean found = false;
		while(i<modelConf.roles.size() && !found){
			if(modelConf.roles.get(i).getName().equalsIgnoreCase(roleName)){
				found = true;
				level = modelConf.roles.get(i).getLevel();
			}
			i++;
		}//while(i<this.roles.size() && !found){

		return level;
	}
	
	
	/**
	 * Get the category of a role
	 * @param roleName
	 * @return
	 */
	public static int getCategory(String roleName){
		int category = 0;
		int i = 0;	boolean found = false;
		while(i<modelConf.roles.size() && !found){
			if(modelConf.roles.get(i).getName().equalsIgnoreCase(roleName)){
				found = true;
				category = modelConf.roles.get(i).getCategory();
			}
			i++;
		}//while(i<this.roles.size() && !found){

		return category;
	}
	
	 public void addCriterion(String roleName, Criterion ctr){
		   for(int i = 0; i < this.roles.size(); i++){

			   if(this.roles.get(i).getName().equals(roleName)){

				   this.roles.get(i).addCriterion(ctr);
			   }
		   }//for(int i = 0; i < this.roles.size(); i++){
		}
	
	
	 
	 

		/**
		 * returns the threshold correspending to a giving role
		 * @param roleName
		 * @return
		 */
		


		
		public static int getNforNgram(){
			String path = "config/ngram.txt"; 
			int res = 0;
			ArrayList<String> words = TextFile.getLines(path);
			String s = words.get(0);
			res = Integer.valueOf(s);
			return res;
		}
		
		public static float getWeight(String roleName){
			float res = -1;
			
			for(int i = 0; i< ModelConfiguration.modelConf.roles.size(); i++){
				
				Role role = ModelConfiguration.modelConf.roles.get(i);
				
				for(int j = 0; j<role.getCreteria().size(); j++){
					
					if(role.getCreteria().get(j).getRoleName().equalsIgnoreCase(roleName)){
						return role.getCreteria().get(j).getWeight();
					}
					
				}
				
			}
						
			return res;
			
		}

		
		public static float getThreshold(String role){
			String path = "config/threshold.txt"; 
			float res = 0;
			ArrayList<String> words = TextFile.getLines(path);
			
			int i = 0;
			while(i< words.size()){
				String word = words.get(i);
				String aRole = MyString.readUntil(word, ' ');
				String t = MyString.readFromToEnd(word, ' ');
				float threshold = Float.valueOf(t);
				
				if(aRole.equals(role)){
					res = threshold;
					i = words.size();
				}
				i++;
				
			}
			//ArrayList<String> words = MyString.
			
			return res;
			
		}

public static void initialize(){
	ModelConfiguration.createModelConfig("config/umlcd.xmi");
}
	 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createModelConfig("config/umlcd.xmi");
		modelConf.disp();
		
		
		System.out.println(" ***************************** ");
		
		for(int i = 0; i < modelConf.getRoles().size(); i++){
			System.out.println(modelConf.getRoles().get(i).getName()+"    "+getWeight(modelConf.getRoles().get(i).getName()));
		}

		System.out.println(" ***************************** ");
		
		Role classe = getRole("ClassAttribute");
		for(int i = 0; i < classe.getSubRole().size(); i++){
			
			System.out.println(classe.getSubRole().get(i).getName()+" "+classe.getSubRole().get(i).getCategory());
			
		}

	}

}
