package compareAndMatch;

import java.util.ArrayList;

import model.Element;
import model.Model;

/**
 * allows to represent the result of comparison between two models
 *
 */
public class CompareAndMatchResult {
	private Model firstModel;
	private Model secondModel;
	private float simDeg;
	private ArrayList<Match> optimalMatch;
	
	
	private ArrayList<Element> unmatchedElements1; //unmatched elements that belong to the first model
	private ArrayList<Element> unmatchedElements2; //unmatched elements that belong to the second model
	
	

	
	
	/**
	 * return the set of unmatched elements
	 * @return
	 */
public ArrayList<Element> getUnmatched1() {
		return unmatchedElements1;
}
public ArrayList<Element> getUnmatched2() {
	return unmatchedElements2;
}



public CompareAndMatchResult(Model firstModel, Model secondModel) {
		this.firstModel = firstModel;
		this.secondModel = secondModel;
		this.optimalMatch = new ArrayList<Match>();
		this.unmatchedElements1 = new ArrayList<Element>() ;
		this.unmatchedElements2 = new ArrayList<Element>() ;
	}




//Guetter and Setter --------------------------------------------------
	

	public Model getFirstModel() {
		return firstModel;
	}


	
	

	public void setFirstModel(Model firstModel) {
		this.firstModel = firstModel;
	}




	public Model getSecondModel() {
		return secondModel;
	}




	public void setSecondModel(Model secondModel) {
		this.secondModel = secondModel;
	}



/**
 * returns the similarity degree between the two models
 * @return
 */
	public float getSimDeg() {
		return simDeg;
	}




	public void setSimDeg(float simDeg) {
		this.simDeg = simDeg;
	}



/**
 * returns the best matching
 * @return
 */
	public ArrayList<Match> getOptimalMatch() {
		return optimalMatch;
	}




	public void setOptimalMatch(ArrayList<Match> optimalMatch) {
		this.optimalMatch = optimalMatch;
	}

	
	/**
	 * allows to display the comparison result
	 */
	public void disp2(){
		System.out.println("First Model");
		this.firstModel.disp();
		System.out.println("Second Model");
		this.secondModel.disp();
		System.out.println("Similarity  :  "+this.simDeg);
		System.out.println("Match");
		for(int i = 0; i < this.optimalMatch.size(); i++){
			this.optimalMatch.get(i).disp2();
		}
	}
	/**
	 * allows to display the comparison result
	 */
	public void disp(){

		System.out.println("Similarity : "+this.simDeg);
		System.out.println("Match");
		for(int i = 0; i < this.optimalMatch.size(); i++){
			this.optimalMatch.get(i).disp2();
		}
	}
/**
 * create the set of unmatched elements
 */
	public void createUnmatchedElements(){
		
			for(int j = 0; j < this.firstModel.getElements().size() ; j++){
			Element elt = this.firstModel.getElements().get(j);
				if(elt.getCategory()==3){
				
					if(!this.optimalMatchContains(this.firstModel.getParent(elt))){
								this.unmatchedElements1.add(elt);
					}
				}else{
					if(!this.optimalMatchContains(elt)){
						this.unmatchedElements1.add(elt);
					}
				}
			}//for(int j = 0; j < this.firstModel.getElements().size() ; j++){
					
			for(int j = 0; j < this.secondModel.getElements().size() ; j++){
				Element elt = this.secondModel.getElements().get(j);
				if(elt.getCategory()==3){
					if(!this.optimalMatchContains(this.secondModel.getParent(elt))){
						this.unmatchedElements2.add(elt);
					}
				}else{
					if(!this.optimalMatchContains(elt)){
						this.unmatchedElements2.add(elt);
					}
				}
			}//for(int j = 0; j < this.firstModel.getElements().size() ; j++){
	}
	
	/*
	 * Vérifier si un élément appartient à un optimalMatch
	 * @param elt
	 * @return
	 */
	private boolean optimalMatchContains(Element elt){
		boolean res = false;
		
		int i = 0;
		while( i < this.optimalMatch.size() && !false){
			Match match = this.optimalMatch.get(i);
			if(match.contains(elt)) res = true;
			i++;
		}
			
		
		
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
