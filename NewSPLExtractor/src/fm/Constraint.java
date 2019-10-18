package fm;

import java.util.ArrayList;

import util.MyString;

public class Constraint {
	private String left;
	private String right;
	private String operator; //implies 
	
	public Constraint(String letft, String right, String operator){
		this.left = letft;
		this.right = right;
		this.operator = operator;
	}
	
/*
	if(this.operator.contains("not")){
		return "\u00AC" + operator;
	}else{
		return operator;
	}*/
	public Constraint(String letft, String right){
		this(letft, right,  "implies");
	}
	
	
	public Constraint(){
	}
	
	public String toString(){
		return this.left + " " + this.operator + " " + this.right;
	}
	
	public String getDescription(){
		String res = this.toString() + System.getProperty("line.separator")+
				"If the feature "+this.left+" is selected, the feature "+this.right+" must be selected";
		
		return res;
	}
	

	
	
	
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String letft) {
		this.left = letft;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public boolean isIdentic(Constraint constr){
		if(this.left.equals(constr.getLeft())
				&& this.right.equals(constr.getRight())
				&& this.operator.equals(constr.getOperator())   ){
			return true;
		}else return false;
	}
	
	/**
	 * check whether a liste of constraints contains a constraint
	 * @param liste
	 * @param constraint
	 * @return
	 */
	/*
	public static boolean contains(ArrayList<Constraint> liste, Constraint constraint){
		boolean res = false;	int i = 0;
		while(i< liste.size() && !res){
			
			if(liste.get(i).isIdentic(constraint)){
				res = true;
			}else{
				i++;
			}
		}
		
		return res;
	}
*/	
	public static boolean contains(ArrayList<Constraint> liste, String left, String right, String operator){
		boolean res = false;	int i = 0;
		while(i< liste.size() && !res){
			
			if(liste.get(i).getLeft().equals(left) && 
					//liste.get(i).getRight().equals(right) && 
					liste.get(i).getOperator().equals(operator)  ){
				ArrayList<String> words = MyString.getWords(liste.get(i).getRight(), ' ');
				int j = 0;
				while(j< words.size() && !res){
					if(words.get(j).equals(right)) 	res = true;
					j++;
				}
			}
			i++;
		}
		
		return res;
	}
	

	/**
	 * allows to display the feature
	 */
	public void disp(){
		System.out.println("     "+this.toString());	
	}
	
	public static ArrayList<Constraint> group(ArrayList<Constraint> liste){
		ArrayList<Constraint> res = new ArrayList<Constraint>();
		
		for(int i = 0; i < liste.size(); i++){
			for(int j = 0; j < liste.size(); j++){
				if(i!=j){
					Constraint c1 = liste.get(i);
					Constraint c2 = liste.get(j);
					if(c1.getLeft().equals(c2.getLeft()) && !c1.getLeft().equals("") && c1.getOperator().equals(c2.getOperator()) ){						
						//c1.setRight(c1.getRight()+"\u21D2"+c2.getRight());
						c1.setRight(c1.getRight()+" AND "+c2.getRight());
						c2.setLeft("");
					}
				}//if(i!=j){
				
				
				
			}//for(int j = 0; j < liste.size(); j++){
		}//for(int i = 0; i < liste.size(); i++){
		
		for(int i = 0; i < liste.size(); i++){
			if(!liste.get(i).getLeft().equals("")){
				res.add(liste.get(i)); 
			}
		}
		//liste = res;
		//Constraint.afficher(res);
		return res;
	}
	
	
	
	public static ArrayList<Constraint> removeFromRgight(String var, ArrayList<Constraint> liste){
		
		ArrayList<Constraint> res = new ArrayList<Constraint>(); 
		ArrayList<String> words = null;
	
		for(int i = 0; i < liste.size(); i++){
			words = MyString.getWords(liste.get(i).getRight(), ' ');
			String newRight = "";
			for(int j = 0; j < words.size(); j++){
				if(words.get(j).contains(var)){
					words.set(j, "");
				}
			}
			for(int j = 0; j < words.size(); j++){
				String word  = words.get(j);
				if(!word.equals("AND") && !word.equals("") ){
					if(newRight.equals("")){
						newRight = word;
					}else{
						newRight = newRight+" AND "+ word;
					}
				}
			}
		
			if(newRight.equals("")){
				System.out.println("A"+newRight+"B");
			}else{
				liste.get(i).setRight(newRight);
				res.add(liste.get(i));
			}
			
		}
		return res;
		
	}
	
	public static void add(Constraint c, ArrayList<Constraint> liste){
		Constraint c1 = null;
		int i = 0; boolean found = false;
		while(i < liste.size()  && !found){
			c1 = liste.get(i);
			if(c1.getLeft().equals(c.getLeft()) && c1.getOperator().equals(c.getOperator()) ){		
				c1.setRight(c1.getRight()+" AND "+c.getRight());
				found = true;
			}
			i++;
		}
		if(!found){
			liste.add(c);
		}
	}
			
	public static void afficher(ArrayList<Constraint> liste) {
		System.out.println(" *********  ");
		for(int i = 0; i < liste.size(); i++){
			System.out.println(liste.get(i).toString());
			
		}
	}
	public static void main(String[] args) {
		
		ArrayList<Constraint> liste = new ArrayList<Constraint>();
		Constraint c = new Constraint("A", "1");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("B", "3");
		//liste.add(c)
		add(c, liste);
		c = new Constraint("B", "2");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("A", "3");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("C", "3");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("C", "\u00AC(3)");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("C", "4");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("C", "5");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("C", "3");
		//liste.add(c);
		add(c, liste);
		c = new Constraint("D", "3");
		//liste.add(c);
		add(c, liste);
		
		
		
		//liste = Constraint.group(liste);
		Constraint.afficher(liste);
		liste = Constraint.removeFromRgight("3", liste);
		Constraint.afficher(liste);
		
		
		
		
		
		
	}
	

}

