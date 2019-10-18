package compareAndMatch;

import java.util.ArrayList;

import matcher.words.linguistic.LinguisticMatching;
import matcher.words.typographic.NGramDistance;
import model.Criterion;
import model.Element;
import model.Model;
import model.ModelConfiguration;


/**
 * This class allows to perform all comparison operations
 * It allows for example to compare two models, two elements, and two sets of elements, etc. 
 * @author Boubakir
 *
 */

public class Comparator {
	
	/**
	 * the first and the second model to be compared
	 */
	private Model firstModel;
	private Model secondModel;
	/**
	 * The result of compare and match 
	 */
	private CompareAndMatchResult compareAndMatchResult;
	/**
	 * the similarity degree between each two pair of elements having the same role and type.
	 */
	private SimilarityDegrees similarityDegrees;
	
	
	
	/**
	 * The constructor 
	 * @param firstModel
	 * @param secondModel
	 */
	public Comparator(Model firstModel, Model secondModel) {
		this.firstModel = firstModel;
		this.secondModel = secondModel;
		
		this.similarityDegrees
		= new SimilarityDegrees(this.firstModel, this.secondModel);
	}


	/**
	 * allows to compare the two input models by execution only one iteration
	 * @param M1: first model
	 * @param M2: second model
	 * @return
	 */
	
private CompareAndMatchResult compare(){

	this.compareAndMatchResult = new CompareAndMatchResult(this.firstModel, this.secondModel);
	
		
float SimDeg = 0;
		
ArrayList<Match> matches = new ArrayList<Match>(); 
		for(int i = 0; i < ModelConfiguration.getRole("Model").getCreteria().size(); i++){
			Criterion criterion = ModelConfiguration.getRole("Model").getCreteria().get(i);
			
			ArrayList<Element> E1 = this.firstModel.getElements(criterion.getRoleName());
			ArrayList<Element> E2 = this.secondModel.getElements(criterion.getRoleName());
			//SimDeg = SimDeg + compare(E1, E2, e1, e2, criterion.getRoleName(),  matches);
			
			
			//ArrayList<Match> matches = new ArrayList<Match>(); 
			SimDeg = SimDeg + criterion.getWeight()*compareAndMatch(E1, E2, null, null, criterion.getRoleName(), matches);
			ArrayList<Match> matches2 =  Match.confirmaMatches(matches);
			
			this.compareAndMatchResult.setOptimalMatch(matches2);
			this.compareAndMatchResult.setSimDeg(SimDeg);
			this.compareAndMatchResult.createUnmatchedElements();
			
		}		
		return this.compareAndMatchResult;
	}
	
	
	/**
	 * This function is applicable on two set of elements having the same role. 
	 * It permits to identify the matched pairs of elements and return a value between 0 and 1,
	 * which corresponds to the similarity degree between them.
	 * @param E1: first set of elements
	 * @param E2: second set of elements
	 * @param parent1: the parent element of E1
	 * @param parent2: the parent element of E2
	 * @param role: the role of E1 and E2.
	 * @param matches: the set of matches identified between the elements of E1 and those of E2
	 * @return
	 */
private float compareAndMatch(ArrayList<Element> E1, ArrayList<Element> E2, Element parent1, Element parent2, String role, ArrayList<Match> matches ){
		

		float similarityDagree = 0;

if(ModelConfiguration.getCategory(role)==1 || ModelConfiguration.getCategory(role)==3){
		
		//Create the cost matrix
		int n = E1.size(); int m = E2.size();
		int dim = n;	if(n<m) dim = m;
		float[][] matrix = new float[dim][dim];
		
	boolean isAtomic = false;
	
	//traiter le cas des ensembles vides
	if((E1.size()==0)){
		if(E2.size()==0){
			return 1; //deux ensemble vide sont considéré identique
		}else{
			if(E2.get(0).isAtomic()) isAtomic = true;
		}
	}else{//if((E1.size()==0)){
			if(E1.get(0).isAtomic()) isAtomic = true;
	}

	//if the element of E1 and E2 are atomic
	if(isAtomic){
		for(int i = 0; i< E1.size(); i++){
			Element e1 = E1.get(i);
			for(int j = 0; j< E2.size(); j++){
				Element e2 = E2.get(j);
				float s = compare2Atomic(e1, e2);

				//New
				//if(s>=Element.getThreshold(role)) matrix[i][j] = s;
				//-New
				matrix[i][j] = s;		
			}//------------for(int j = 0; j< elements2.size(); j++){
		}//----------------for(int i = 0; i< elements1.size(); i++){
	}else{//------------------------------if(E1.get(0).isAtomic()){
		//if the compared elements are compound	
		for(int i = 0; i< E1.size(); i++){
			Element e1 = E1.get(i);
			for(int j = 0; j< E2.size(); j++){	
				Element e2 = E2.get(j);
				matrix[i][j] = compare2compund(e1, e2, matches);
				
				
				//New
				//float s = compare2compund(e1, e2, matches);
				//if(s>=Element.getThreshold(role)) matrix[i][j] = s;
				//-New
			}//------------for(int j = 0; j< elements2.size(); j++){
		}//----------------for(int i = 0; i< elements1.size(); i++){					
	}//------------------------------else if(E1.get(0).isAtomic()){
				
	
	//NEW
	//manipulate values that are below the threshold
	for(int i = 0; i< E1.size(); i++){
		for(int j = 0; j< E2.size(); j++){
			if(matrix[i][j] < ModelConfiguration.getThreshold(role)){
				matrix[i][j] = 0;
			}
		}
	}
	//DEP ----------------------- orignal1
	//-NEW
	
	if(ModelConfiguration.getCategory(role)==1){
		
		
		//PRGINAL ----------------------- orignal1
		
		Match match = new Match(E1, E2, parent1, parent2, matrix);
		match.hungarianMatch();
		
		//A SUPRIMER
		//match.disp();
		//-A SUPRIMER
		
		if(match.getPairs().size()!=0) matches.add(match);
		//matches.add(match);
		
		//New 09/04/17
		//similarityDagree = match.getWeight()/Math.max(E1.size(), E2.size());
		
		similarityDagree = match.getWeight()/(E1.size()+E2.size()-match.getCard()  );
		
		//System.out.println(similarityDagree+"  "+E1.size()+"  "+E2.size()+"  "+E2.get(0).getType());
		
		//New 09/04/17

		//A SUPRIMER
		
		//System.out.println(match.getCard()+"   E1 "+E1.size()+"   E2 "+E2.size());
		//-A SUPRIMER
		
		
		match.setWeight(similarityDagree);
			
		return similarityDagree;
	
	}else{//Third category ------------------------------------------------------
		
		
		//System.out.println(" Third category ");
		Match match = new Match(E1, E2, parent1, parent2, matrix);
		match.maxMatch();
		matches.add(match);
		//A SUPP
		//match.disp3();
		//System.out.println(matches.size());
		//-A SUPP
		similarityDagree = match.getWeight();
		
		
		return similarityDagree;
		
	}
				
		 

		
//-------------------------------------------------------------------------	
}else{//if(Element.getCategory(role)==1 || Element.getCategory(role)==2){
	//The second category

	
	

//Match match = new Match(parent1, parent2);
Match match = new Match(E1, E2, parent1, parent2);

boolean isAtomic = false;
	
	if(E1.size()==E2.size()){
		//Continuer
	}else{
		return 0;
	}
	
	//traiter le cas des ensembles vides
	
	if((E1.size()==0)){
		return 1; //deux ensemble vide sont considéré identique

	}else{//if((E1.size()==0)){
			if(E1.get(0).isAtomic()) isAtomic = true;
	}

	
	
	//if the element of E1 and E2 are atomic
	if(isAtomic){
		for(int i = 0; i< E1.size(); i++){
			Element e1 = E1.get(i);
			Element e2 = E2.get(i);

			float s = compare2Atomic(e1, e2);
			
			
			
			if(s>=ModelConfiguration.getThreshold(role)){
				
				PairOfElement pair = new PairOfElement(e1, e2, s);
				match.addPairOfElement(pair);
				similarityDagree =similarityDagree + s;
			}else{
				//match = new Match(parent1, parent2);
				return 0;
			}
		}//----------------for(int i = 0; i< elements1.size(); i++){
	}else{//------------------------------if(E1.get(0).isAtomic()){
		//if the compared elements are compound	
		for(int i = 0; i< E1.size(); i++){
			Element e1 = E1.get(i);
			Element e2 = E2.get(i);
			float s = compare2compund(e1, e2, matches);
			
			if(s>=ModelConfiguration.getThreshold(role)){
				PairOfElement pair = new PairOfElement(e1, e2, s);
				match.addPairOfElement(pair);
				similarityDagree =similarityDagree + s;
			}else{
				//System.out.println(" ----------------------------------    "+role);
				//match = new Match(parent1, parent2);
				return 0;
			}
		}//----------------for(int i = 0; i< elements1.size(); i++){					
	}//------------------------------else if(E1.get(0).isAtomic()){

		matches.add(match);
		similarityDagree = similarityDagree/E1.size();
		return similarityDagree; 



}//else


	
}//End compare()
	

	
	/**
	 * Return the similarity degree between two atomic elements
	 * @param e1: first element
	 * @param e2: second element
	 * @return: similarity degree
	 */
	private float compare2Atomic(Element e1, Element e2){
		
		if(e1.getType().equals("String")){
			if(e2.getType().equals("String")){
				float res = comare2String(e1.getValue(), e2.getValue());
				this.similarityDegrees.set(e1, e2, res);
				return res;
			}else{
				return 0;
			}
			
			
		}else{
			
			
			if(e1.getType().equals("Reference")){
				if(e2.getType().equals("Reference")){
					return compare2References(e1, e2);
				}else{
					return 0;
				}
				
				
			}else{
				return 0;
			}
			
			
			
		}//if(e1.getType().equals("String")){
		
	}//end compare2Atomic(Element e1, Element e2)
	
	

	/**
	 * Compare two reference elements
	 * @param e1
	 * @param e2
	 * @return
	 */
	private float compare2References(Element e1, Element e2){
		Element referencedElt1 = this.firstModel.getElement(e1.getValue());
		Element referencedElt2 = this.secondModel.getElement(e2.getValue());
		
		float res = this.similarityDegrees.get(referencedElt1, referencedElt2);
				
		//System.out.println(" compare2References : "+referencedElt1.getId()+" "+referencedElt2.getId()+":  "+ res);
		
		return res;
		
	}
	
	
	
	/**
	 * Calculate the similarity degree between two compound elements
	 * @param e1: first element
	 * @param e2: second element
	 * @return: similarity degree
	 */
	private float compare2compund(Element e1, Element e2, ArrayList<Match> matches ){
		
		float result = 0;
		
		for(int i = 0; i < ModelConfiguration.getRole(e1.getRole()).getCreteria().size(); i++){
			Criterion criterion = ModelConfiguration.getRole(e1.getRole()).getCreteria().get(i);
			ArrayList<Element> E1 = e1.getSubElements(criterion.getRoleName());
			ArrayList<Element> E2 = e2.getSubElements(criterion.getRoleName());
			result = result + criterion.getWeight()*compareAndMatch(E1, E2, e1, e2, criterion.getRoleName(),  matches);
			
			//if(e1.getId().contains("c17") || e2.getId().contains("c17")) System.out.println(result+"  "+e1.getId()+"  "+e2.getId()+" .....>>>>> "+compareAndMatch(E1, E2, e1, e2, criterion.getRoleName(),  matches)+" ..  "+ criterion.getWeight());
			
			//System.out.println("..  "+e1.getId()+"  "+e2.getId());
		}
		
//System.out.println(result+" >>>>>>>>>>> "+e1.getId()+"  "+e2.getId());

		this.similarityDegrees.set(e1, e2, result);
	return result;
}//
	

	
	
	
	/**
	 * Compare tow String values using typographic and linguistic matching
	 * @param: first string
	 * @param: second string
	 * @return: similarity degree
	 */
	private float comare2String(String s1, String s2){
		float x1 = typographicMatching(s1, s2, ModelConfiguration.getNforNgram());
//NEW 01-01-2019
		float x2 = linguisticMatching(s1, s2);
		//float x2 = 0;
//-NEW 01-01-2019
		float res = Math.max(x1, x2);
		return res;
		
	}
	
	/**
	 * performs the typographic matching
	 * @param s1: first element
	 * @param s2: second element
	 * @param N: The number of their identical substrings of length N.
	 * (3 is usually used)
	 * @return: typographic similarity
	 */
	private float typographicMatching(String s1, String s2, int N){
		
		NGramDistance ng = new NGramDistance(N);
		return ng.getDistance(s1.toLowerCase(), s2.toLowerCase()); 
	}
	
	/**
	 * performs the linguistic matching
	 * @param s1: first element
	 * @param s2: second element
	 * @return: linguistic similarity
	 */
	private float linguisticMatching(String s1, String s2){
		double distance = LinguisticMatching.compute(s1.toLowerCase(), s2.toLowerCase());
		
		return (float) distance;
	}
	

	
	
	
	
	/**
	 * Compare two models by performing many iteration
	 * @param m1: the first model
	 * @param m2: the second model
	 * @param nbrIteration: number of iteration to perform when comparing a pair of models 
	 * @param precision: precision sufficient to stop the iteration when comparing a pair of models   
	 * @return
	 */
	public static CompareAndMatchResult compare(Model m1, Model m2, int nbrIteration, float precision){
		Comparator cmp = new Comparator(m1, m2);
		CompareAndMatchResult compareAndMatchResult = null;
		

		
		for(int i = 0; i < nbrIteration; i++){
			
			cmp.similarityDegrees.setMaxDiff(0);
			compareAndMatchResult = cmp.compare();
			//System.out.println("Iteration: "+(i+1));
			//System.out.println("MaxDiff: "+cmp.similarityDegrees.getMaxDiff());
			//cmp.similarityDegrees.disp();
			if(cmp.similarityDegrees.getMaxDiff()<=precision) i = nbrIteration;
		}
		
		
		//compareAndMatchResult.disp();
		
		return compareAndMatchResult;
		
	}

	
		
/**
 * Test the algorithm
 * @param args
 */
	public static void main(String[] args) {
		test() ;
	}

	
	public static void test() {
		ModelConfiguration.createModelConfig("config/umlcd.xmi");
		Model m1 = Model.getModel("models/ref/M1.xmi");
		Model m2 = Model.getModel("models/ref/M2.xmi");

		
		CompareAndMatchResult compareAndMatchResult = 
				compare(m1, m2, 3, 0.5f);
		
		System.out.println("RESULT ----------------");
		compareAndMatchResult.disp();
		

		
	}
	
	
	

}
