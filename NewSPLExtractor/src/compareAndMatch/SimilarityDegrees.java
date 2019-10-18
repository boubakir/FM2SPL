package compareAndMatch;

import java.util.ArrayList;

import model.Element;
import model.Model;
import model.ModelConfiguration;

/**
 * This class allows to manage the similarity degrees of
 * ReferncdEleemnts 
 * @author Boubakir
 *
 */
public class SimilarityDegrees {
	private ArrayList<PairOfElement> similarities = new ArrayList<PairOfElement>();
	private float maxDiff; //le max de des differences des valeurs entre l'itération courante et l'iteration precedente
	 

	
	public void setMaxDiff(float maxDiff) {
		this.maxDiff = maxDiff;
	}
	public float getMaxDiff() {
		return maxDiff;
	}



	public SimilarityDegrees(Model m1, Model m2) {
		
		this.maxDiff = 0;
		
		ArrayList<Element> elts1 = m1.getElements();
		ArrayList<Element> elts2 = m2.getElements();
		
		//System.out.println(elts1.size()+"   *** "+elts2.size());		
		float simDeg = 0;
		for(int i = 0; i < elts1.size(); i++ ){
			for(int j = 0; j < elts2.size(); j++ ){
				if( elts1.get(i).getType().equals(elts2.get(j).getType())
						&&  elts1.get(i).getRole().equals(elts2.get(j).getRole())
						){
					PairOfElement pair = new PairOfElement(elts1.get(i), elts2.get(j), simDeg);
				    similarities.add(pair);
				}
				
				
				
			}//for(int j = 0; j < refElts2.size(); j++ ){
		}//for(int i = 0; i < refElts1.size(); i++ ){
	}

	
	
	/*
	
	public SimilarityDegreeOfReferencedElements(Model m1, Model m2) {
		
		ArrayList<Element> refElts1 = m1.getRferencedElements();
		ArrayList<Element> refElts2 = m2.getRferencedElements();
		
		System.out.println(refElts1.size()+"   *** "+refElts2.size());		
	
		for(int i = 0; i < refElts1.size(); i++ ){
			for(int j = 0; j < refElts2.size(); j++ ){
				float simDeg = 0;
				PairOfElement pair = new PairOfElement(refElts1.get(i), refElts2.get(j), simDeg);
				similarities.add(pair);
				
				
			}//for(int j = 0; j < refElts2.size(); j++ ){
		}//for(int i = 0; i < refElts1.size(); i++ ){
	}
*/
	public void disp(){
		
		for(int i = 0; i < this.similarities.size(); i++ ){
			this.similarities.get(i).disp();
		}
		System.out.println("Max of diff :"+this.maxDiff);
		
	}


	public float get(Element e1 , Element e2){
		float res = -1;
		for(int i = 0; i < this.similarities.size(); i++ ){
			
			PairOfElement pair = this.similarities.get(i);
			if(e1.getId().equals(pair.getFirst().getId()) && e2.getId().equals(pair.getSecond().getId()) 
				|| e1.getId().equals(pair.getSecond().getId()) && e2.getId().equals(pair.getFirst().getId())	){
				return pair.getSimDeg();
			}
		}
		
		return res;
		
	}
	
	
	public void set(Element e1 , Element e2, float simDeg){

		for(int i = 0; i < this.similarities.size(); i++ ){
			PairOfElement pair = this.similarities.get(i);
			if(e1.getId().equals(pair.getFirst().getId()) && e2.getId().equals(pair.getSecond().getId()) 
				|| e1.getId().equals(pair.getSecond().getId()) && e2.getId().equals(pair.getFirst().getId())	){
				
				float simDegActual = pair.getSimDeg();
				if(simDeg-simDegActual > this.maxDiff){
					this.maxDiff = simDeg-simDegActual; 
					//System.out.println(simDeg+"  HHHHHHH  "+simDegActual);
				}
				
				pair.setSimDeg(simDeg);
			}
		}

		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ModelConfiguration.createModelConfig("config/umlcd.xmi");
		Model m1 = Model.getModel("models/ref/M1.xmi");
		Model m2 = Model.getModel("models/ref/M2.xmi");
		
		
		SimilarityDegrees similarityDegreeOfReferencedElements
		= new SimilarityDegrees(m1, m2);
		similarityDegreeOfReferencedElements.disp();
		
		Element e1 = m1.getElement("C2");
		Element e2 = m2.getElement("C02");
		
		similarityDegreeOfReferencedElements.set(e1, e2, 0.35f);
		
		System.out.println(similarityDegreeOfReferencedElements.get(e1, e2));
		
		
	}

}
