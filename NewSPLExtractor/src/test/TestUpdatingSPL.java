package test;

import java.util.ArrayList;

import model.Model;
import model.ModelConfiguration;
import spl.SPL;

/**
 * Calculate execution Time
 * @author Boubakir
 *
 */
public class TestUpdatingSPL {
	private static ArrayList<Model> models;
	private static long executionTimeSec;
	private static int numberOfElements1;
	private static int numberOfElements2;
	private static int numerOfClasses;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float executionTimeAverage = 0;
		float elementsNumbrAverage = 0;
		

		ModelConfiguration.initialize();
		/**
		 * Read a set of models
		 */
		models = Model.getModelsFromRep("G:/New Article/Test/executionTime/10/");
		int executionNumber = 10;
		for(int i =0; i < executionNumber ; i++){
			execute();
			System.out.println("Elements of model 1: "+TestUpdatingSPL.numberOfElements1+"  -  Elements of model 2: "+TestUpdatingSPL.numberOfElements2+" - Classes: "+TestUpdatingSPL.numerOfClasses);
			System.out.println("Execution time: "+TestUpdatingSPL.executionTimeSec +"  second");
			
			executionTimeAverage = executionTimeAverage + executionTimeSec;
			elementsNumbrAverage = elementsNumbrAverage + numberOfElements1 + numberOfElements2;
		
		}//for(int i =0; i < executionNumber ; i++){
		
		executionTimeAverage = (float) executionTimeAverage/executionNumber;
		elementsNumbrAverage = (float) elementsNumbrAverage/(executionNumber*2);
		System.out.println("");
		System.out.println("Result:");
		System.out.println("Average of Execution time: "+executionTimeAverage);
		System.out.println("Average of number of elements : "+elementsNumbrAverage);
		System.out.println("END");
	}
	
	public static void execute(){
		
		
		
		int index1  = (int )(Math.random() * models.size());
		int index2 = 0;
		boolean repeat = true;
		while(repeat){
			index2  = (int )(Math.random() * models.size());
			if(index1 != index2) repeat = false;
		}//while(repeat){
		
		Model model1 = models.get(index1);
		Model model2 = models.get(index2);
		
		TestUpdatingSPL.numberOfElements1 = model1.getElements().size();
		TestUpdatingSPL.numberOfElements2 = model2.getElements().size();
		TestUpdatingSPL.numerOfClasses = model1.getSize();
		
		
		SPL spl = new SPL();
		
		spl.addModel(model1);
		
		long startTime = System.currentTimeMillis();
		
		spl.addModel(model2);
		
		long endTime = System.currentTimeMillis();
		
		TestUpdatingSPL.executionTimeSec = (endTime-startTime)/1000;
		//long timesMin = timesSec/60;
		
		//System.out.println("Execution time: "+timesMin +"  menute");
		
		
		
		
		
		
		

	}

}
