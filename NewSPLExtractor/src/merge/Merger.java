package merge;

import java.util.ArrayList;

import Edmond.jicos.WeightedMatch;
import compareAndMatch.Comparator;
import compareAndMatch.CompareAndMatchResult;
import compareAndMatch.Match;
import compareAndMatch.PairOfElement;
import model.Element;
import model.Model;
import model.ModelConfiguration;

/**
 * performs model merging
 * @author Boubakir
 *
 */
public class Merger {

	private static ArrayList<Element> unmatchedElements1 = new ArrayList<Element>() ; //unmatched elements that belong to the first model
	private static ArrayList<Element> unmatchedElements2  = new ArrayList<Element>() ; //unmatched elements that belong to the second model
	
	

		
/**
 * merging a pair of models
 * @param M1: first model
 * @param M2: second model
 * @param compareAndMatchResult: the result of comparing M1 and M2
 * @return
 */
	private static Model merge(Model M1, Model M2, CompareAndMatchResult compareAndMatchResult) {
		
		
		//Créer un nouveau modèles
		Model resultModel = new Model();	resultModel.setId(M1.getId()+"-"+M2.getId());
		
		ArrayList<Match> matches = compareAndMatchResult.getOptimalMatch();

		int layer = 1;
		while(layer <=ModelConfiguration.getModelConf().getLevelNumber()){
			
			
			
			for(int i =0; i < matches.size(); i++ ){
				Match match = matches.get(i);
				
				ArrayList<PairOfElement> pairs2 =  match.getPairs();
		
				if(match.getLayer()==layer){
					if(match.getCategory()==3){
						
mergeThirdCategory(match.getElemnts1(), match.getElemnts2(), match.getParentElement1(), match.getParentElement2(), resultModel);
//this.resultModel.addElement(match.getElemnts2(), match.getParentElement1(), match.getParentElement2());
					
					}else{
						for(int j = 0; j < pairs2.size(); j++ ){
							PairOfElement pair = pairs2.get(j);
							Element elt = new Element(pair.getFirst(), pair.getSecond()) ;
							
							
							resultModel.addElement(elt, match.getParentElement1(), match.getParentElement2());
						
							//System.out.println(elt.getId()+" ---- "+elt.getOriginal1().getId()+ "   "+elt.getOriginal2().getId());
							
						
						}//for(int j = 0; j < pairs2.size(); i++ ){
						
						
					}//if(match.getCategory()==3){
					
					
				}//if(match.getLayer()==layer){
			}//for(int i =0; i < matches.size(); i++ ){
			
			
							
			
			layer++;
		}//while(layer <=5){	
	
		
		
		layer = 1;
		while(layer <=ModelConfiguration.getModelConf().getLevelNumber()){	
		
		
		
			for(int i = 0; i < compareAndMatchResult.getUnmatched1().size(); i++){
				Element elt = compareAndMatchResult.getUnmatched1().get(i);
				if(  ModelConfiguration.getLevel(elt.getRole()) ==layer){
					resultModel.addUnmatchedElement(elt);
				}
			}//for(int i = 0; i < compareAndMatchResult.getUnmatched().size(); i++){
			

			for(int i = 0; i < compareAndMatchResult.getUnmatched2().size(); i++){
				Element elt = compareAndMatchResult.getUnmatched2().get(i);
				if(  ModelConfiguration.getLevel(elt.getRole()) ==layer){
					resultModel.addUnmatchedElement(elt);
				}
			}//for(int i = 0; i < compareAndMatchResult.getUnmatched().size(); i++){
			
			
			
			
			layer++;
		}//while(layer <=5){	
		
		
		
	return resultModel;
	}


	public static void mergeThirdCategory(ArrayList<Element> elts1, ArrayList<Element> elts2, Element parentElement1, Element parentElement2, Model resultModel){
		

		
		//ArrayList<Element> elts = new ArrayList<Element>();
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
				resultModel.addElement(elt, parentElement1, parentElement2);
			}else{
				//New 03/04/2018
				//elts.add(elt1);
				Element newElt = new Element(elt1);
				resultModel.addElement(newElt, parentElement1, parentElement2);
				Merger.unmatchedElements1.add(newElt);
				//--New 03/04/2018
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
				//New 03/04/2018
				Element newElt = new Element(elt2);
				resultModel.addElement(newElt, parentElement1, parentElement2);
				Merger.unmatchedElements2.add(newElt);
				//--New 03/04/2018
			}
		}//for(int i = 0; i < elts1.size(); i++)
		
		//this.addElement(elts, parentElement1, parentElement2);
		
		//NEW 02/04/2018
		/*for(int i = 0; i < elts.size(); i++){
			Element elt = new Element(elts.get(i));
			resultModel.addElement(elt, parentElement1, parentElement2);
		}*/
		//--NEW 02/04/2018
		
	}

	
	
/**
 * Compare two models
 * @param M1: the first model
 * @param M2: the second model
 * @param nbrIteration: number of iteration to perform when comparing a pair of models 
 * @param precision: precision sufficient to stop the iteration when comparing a pair of models   
 * @return
 */
	public static Model merge(Model M1, Model M2, int nbrIteration, float precision) {
		unmatchedElements1 = new ArrayList<Element>() ; //unmatched elements that belong to the first model
		unmatchedElements2  = new ArrayList<Element>();

		CompareAndMatchResult compareAndMatchResult = Comparator.compare(M1, M2, nbrIteration, precision);
		Model result = Merger.merge(M1, M2, compareAndMatchResult);
		unmatchedElements1.addAll(compareAndMatchResult.getUnmatched1());
		unmatchedElements2.addAll(compareAndMatchResult.getUnmatched2());
		
	
		
		manipulateRefereceElements(result);
		
		
		
		return result;
			
		}
	
	
	public static Model merge(Model M1, Model M2) {
		return merge(M1, M2, 1, 0.5f);
	}
	
	
	private static void manipulateRefereceElements(Model model) {
		
	
		
		ArrayList<Element> elementsToDelete= new ArrayList<Element>();
		
		for(int i = 0; i < model.getElements().size(); i++){
			Element elt = model.getElements().get(i);
			for(int j = 0; j < model.getElements().size(); j++){
				Element ReferencedElt =  model.getElements().get(j); 
				if(elt.getType().equalsIgnoreCase("Reference")){
					if( elt.getValue().equalsIgnoreCase(ReferencedElt.getOriginal1().getId()) || elt.getValue().equalsIgnoreCase(ReferencedElt.getOriginal2().getId()) 	){
						elt.setValue(ReferencedElt.getId());
					}//if( elt.getValue().equalsIgnoreCase(ReferencedElt.getOriginal1().getId()) || elt.getValue().equalsIgnoreCase(ReferencedElt.getOriginal2().getId()) 	){
				}	
			}//for(int j = 0; j < model.getElements().size(); j++){
		}//for(int i = 0; i < model.getElements().size(); i++){
		
		
		
		
		for(int i = 0; i < model.getElements().size(); i++){
			Element elt1 = model.getElements().get(i);
			for(int j = 0; j < model.getElements().size(); j++){
				
				Element elt2 =  model.getElements().get(j);
				if(i!=j){
					if(elt1.getType().equalsIgnoreCase("Reference") && elt2.getType().equalsIgnoreCase("Reference")){
						if(elt1.getRole().equalsIgnoreCase(elt2.getRole()) 
								&& elt1.getValue().equalsIgnoreCase(elt2.getValue()) 
								&& model.getParent(elt1)==model.getParent(elt2)){
	
							unmatchedElements1.remove(elt1);
							unmatchedElements2.remove(elt2);
							
							unmatchedElements2.remove(elt1);
							unmatchedElements2.remove(elt2);
							elementsToDelete.add(elt2);
				
							Element mergeElement = new Element(elt1, elt2);
					
							model.getElements().set(i, mergeElement);
				
							model.getParent(elt1).addSubElement(mergeElement);
							model.getParent(elt1).getSubElements().remove(elt1);
							elt2.setValue("");
						}
					}//if(elt1.getType().equalsIgnoreCase("Reference") && elt2.getType().equalsIgnoreCase("Reference")){
				}	
			}//for(int j = 0; j < model.getElements().size(); j++){
		}//for(int i = 0; i < model.getElements().size(); i++){
		
			for(int i = 0; i <elementsToDelete.size(); i++){
			model.getElements().remove(elementsToDelete.get(i));
			Element elt = elementsToDelete.get(i);
			Element eltP = model.getParent(elementsToDelete.get(i));
			eltP.getSubElements().remove(elt);
		}//for(int i = 0; i <elementsToDelete.size(); i++){
			

		
	}//	public static void manipulateRefereceElements(Model model) {
	
	
	/**
	 * performs an iteration of 3M algorithm
	 * @param models: a set of models
	 * @param nbrIteration: number of iteration to perform when comparing a pair of models 
	 * @param precision: precision sufficient to stop the iteration when comparing a pair of models   
	 * @return
	 */
	public static ArrayList<Model> iterate(ArrayList<Model> models, int nbrIteration, float precision){
		ArrayList<Model> models2 = new ArrayList<Model>();
		
		int[][] weightMatrix = new int[models.size()][models.size()];
		CompareAndMatchResult[][] comparetMatrix = new CompareAndMatchResult[models.size()][models.size()];
	
		//Comparer chaque paire de modèles
		for(int i = 0; i < models.size(); i++){
			//System.out.println("*************");
			for(int j = 0; j < models.size(); j++){
			
				if(i==j){
					weightMatrix[i][j] = 0;
				}else{
CompareAndMatchResult cAResult = Comparator.compare(models.get(i), models.get(j), nbrIteration, precision);
							
					
					float x = cAResult.getSimDeg()*10000;
					
					weightMatrix[i][j] = (int)x;
					comparetMatrix[i][j] = cAResult;
					
				}//if(i==j){
				
				
				
			}//for(int j = 0; j < models.size(); j++){
		}//for(int i = 0; i < models.size(); i++){
		
		
		//WeightedMatch.afficher(weightMatrix, "       ");
		
		//System.out.println(" Les pairs de modèles a merger ");
		
		ArrayList<int[]> result = WeightedMatch.getResult(weightMatrix);
		for ( int i = 0; i < result.size(); i++ ){
       	// System.out.println(result.get(i)[0]+"   "+result.get(i)[1]);
        
     	//System.out.println("Merger les modèles suivants :");
       	 if(result.get(i)[1]==-1){
       		 
       		Model m1 = models.get(result.get(i)[0]);
       		models2.add(m1);
       		 
       	 }else{
       		 
       		Model m1 = models.get(result.get(i)[0]);
       		Model m2 = models.get(result.get(i)[1]);
       		//System.out.println(m1.getId()+"   "+m2.getId()); 
       		
       		CompareAndMatchResult cAResult = comparetMatrix[result.get(i)[0]][result.get(i)[1]];
       				
       		//CompareAndMatchResult compareAndMatchResult = Compare.CompareAndMatch(m1, m2);
       		
    		
    		Model model = Merger.merge(m1, m2, cAResult);
    		manipulateRefereceElements(model);
    		models2.add(model);
    		
       	 }//else
       	}//for ( int i = 0; i < result.size(); i++ ){
		
		return models2; 
	}


	
	

	
	/**
	 * performs the merging of a set of models, this methods implements 3M algorithm
	 * @param models: the set of models to merge
	 * @param nbrIteration: number of iteration to perform when comparing a pair of models 
	 * @param precision: precision sufficient to stop the iteration when comparing a pair of models   
	 * @return
	 */
	public static Model mmm(ArrayList<Model> models, int nbrIteration, float precision){
		

		while(models.size()>1){
			models = Merger.iterate(models, nbrIteration, precision);	
		}
		
		return models.get(0);
	}
	
	public static Model mmm(ArrayList<Model> models){
		return mmm(models, 1, 0.5f);
	}

	/**
	 * Merge a set of model 
	 * @param models
	 * @return
	 */
	public static Model simpleMerge(ArrayList<Model> models){
		Model res = models.get(0);
		
		int i = 1;

		while(i < models.size()){
			res = Merger.merge(res, models.get(i));
			i++;
		}
		
		return res;
	}
	
	
public static ArrayList<Element> getUnmatchedElements1() {
		return unmatchedElements1;
	}



	public static ArrayList<Element> getUnmatchedElements2() {
		return unmatchedElements2;
	}



public static void main(String[] args) {
	
	ModelConfiguration.createModelConfig("config/umlcd.xmi");
	
	ArrayList<Model> models = Model.getModelsFromRep("models/simple");
	//Model M1 = models.get(0);		Model M2 = models.get(1);	
	
	/*Model M3 = models.get(2);
	Model M4 = models.get(3);
	
	
	Model M12 = Merger.merge(M1, M2);
	Model M123 = Merger.merge(M12, M3);
	Model M1234 = Merger.merge(M123, M4);*/
	
	//Model M13 = Merger.merge(M1, M3);
	//M1234.disp();
	Model M = Merger.mmm(models, 5, 0.125f);
	M.disp();
	M.save("models/simple/res.xmi");
	
	/*
	CompareAndMatchResult s = Compare.CompareAndMatch(M1, M2);
	s.disp();*/

	/*
	System.out.println(Merger.getUnmatchedElements1().size());
	System.out.println(Merger.getUnmatchedElements2().size());*/
	/*
	for(int i = 0; i < m.getElements().size(); i++){
		Element e = m.getElements().get(i);
		System.out.println(e.getId()+"    "+e.getOriginal1().getId()+"    "+e.getOriginal2().getId());
	}*/
	
	
	System.out.println("END");
		
	}
	
	
}
