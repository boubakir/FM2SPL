package test;

import java.util.ArrayList;

import merge.Merger;
import model.Model;
import model.ModelConfiguration;

/**
 * Calculate execution Time
 * @author Boubakir
 *
 */
public class TestMerging2Models {
	private static ArrayList<Model> models;
	private static long executionTimeSec;
	private static int numberOfElements1;
	private static int numberOfElements2;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float executionTimeAverage = 0;
		float elementsNumbrAverage = 0;
		

		ModelConfiguration.initialize();
		/**
		 * Read a set of models
		 */
		String modelsPath = "G:/New Article/Test/ModelMerging/GOL/50%/1000/";
		String resultFolder = "G:/New Article/Test/ModelMerging/GOL/50%/0/";
		models = Model.getModelsFromRep(modelsPath);
		//System.out.println(models.size()+" ****");
		
		int executionNumber = 50;
		String id = "N";
		System.out.println("Please waite");
		
		for(int i =0; i < executionNumber ; i++){
			//System.out.println("Iteration "+(i+1));
			
			execute(id+(i+1), resultFolder);
			//System.out.println("Elements of model 1: "+TestMerging2Models.numberOfElements1+"  -  Elements of model 2: "+TestMerging2Models.numberOfElements2+" - Classes: "+TestMerging2Models.numerOfClasses);
			//System.out.println("Execution time: "+TestMerging2Models.executionTimeSec +"  second");
			
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
	
	public static void execute(String id, String modelsPath){
		
		
		
		int index1  = (int )(Math.random() * models.size());
		int index2 = 0;
		boolean repeat = true;
		while(repeat){
			
			index2  = (int )(Math.random() * models.size());
			//System.out.println("Repeat " + index1+"  "+index2+"  "+models.size());
			if(index1 != index2) repeat = false;
		}//while(repeat){
		
		Model model1 = models.get(index1);
		Model model2 = models.get(index2);
		
		TestMerging2Models.numberOfElements1 = model1.getElements().size();
		TestMerging2Models.numberOfElements2 = model2.getElements().size();
		model1.getSize();
		
		
		/*SPL spl = new SPL();
		
		spl.addModel(model1);*/
	
		long startTime = System.currentTimeMillis();
		
		Model merge = Merger.merge(model1, model2);

		
		long endTime = System.currentTimeMillis();
		
		
		//System.out.println(merge.getId());
		merge.setId(id);
		merge.save(modelsPath+id+".xmi");
		
		TestMerging2Models.executionTimeSec = (endTime-startTime)/1000;
		//long timesMin = timesSec/60;
		
		//System.out.println("Execution time: "+timesMin +"  menute");
		
		
		
		
		
		
		

	}

}
