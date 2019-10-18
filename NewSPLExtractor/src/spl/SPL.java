package spl;
import java.util.ArrayList;

import fm.Constraint;
import fm.Feature;
import fm.FeatureModel;
import merge.Merger;
import model.Element;
import model.Model;
import model.ModelConfiguration;
import parser.SPLParser;
public class SPL {

	
	private String id;
	private Model splModel;
	private FeatureModel featureModel;
	private String splPth;

	
	
	
	public String getSplPth() {
		return splPth;
	}



	public void setSplPth(String splPth) {
		this.splPth = splPth;
	}



	public String getId() {
		return id;
	}



	public void setId(String name) {
		this.id = name;
	}



	public Model getSplModel() {
		return splModel;
	}



	public void setSplModel(Model splModel) {
		this.splModel = splModel;
	}



	public FeatureModel getFeatureModel() {
		return featureModel;
	}



	public void setFeatureModel(FeatureModel featureModel) {
		this.featureModel = featureModel;
	}


	/*
	public void save(String path){
		this.splModel.save(path);
	}*/

	public SPL() {
		this.splModel = new Model() ;
		this.featureModel = new FeatureModel();

	}
	
	public SPL(String name) {
		this();
		this.id = name;
		this.splModel.setId(name);
		this.featureModel.setId(name);
		//the FM must be semlected
	}
	
	public SPL(String name, Model splModel) {
		this(name);
		this.initialize(splModel);
	}
	
	public SPL(String name, Model splModel, FeatureModel fm) {
		this(name);
		this.splModel = splModel;
		this.featureModel = fm;
	}

	private void initialize(Model model){
		this.splModel = model;
		this.featureModel.initialize(splModel);
	}

	/**
	 * Save the SPL by saving the SPL model and the FM
	 * @param splModelPth
	 * @param FMPath
	 */
	
	public void saveAs(String splPath){

		this.splPth = splPath;
		this.save();
 
	}
	
	public void save(){
		SPLParser.save(this, this.splPth);
	}
	
	public boolean isInitialized(){
		return this.featureModel.getMandatoryFeatures().size()>0;
		//SPLParser.save(this, this.splPth);
	}
	
	/**
	 * add a model to the SPL
	 * @param model
	 */
	public void addModel(Model model){
		if(this.isInitialized()){
		
			this.splModel = Merger.merge(model, this.splModel);
			this.splModel.setId(this.id);
			
			/*
			System.out.println(Merger.getUnmatchedElements1().size());
			System.out.println(Merger.getUnmatchedElements2().size());*/
			
			ArrayList<String> OnlyInLeft = new ArrayList<String>();
			ArrayList<String> OnlyInRight = new ArrayList<String>();
			for(int i =0; i< Merger.getUnmatchedElements1().size(); i++ ){
				OnlyInLeft.add(Merger.getUnmatchedElements1().get(i).getId());
			}
			for(int i =0; i< Merger.getUnmatchedElements2().size(); i++ ){
				OnlyInRight.add(Merger.getUnmatchedElements2().get(i).getId());
			}
			updateElementsID();
			
			this.featureModel.updating(OnlyInLeft, OnlyInRight, this.splModel, model);
			//model.disp();
		}else{
			this.initialize(model);
		}
	}
	
	
	
	private void updateElementsID(){
		for(int i =0; i< this.featureModel.getAllFeatures().size(); i++ ){
			Feature f = this.featureModel.getAllFeatures().get(i);
			
			for(int j =0; j < f.getElements().size(); j++ ){
				
				for(int k =0; k< this.splModel.getElements().size(); k++ ){
					Element elt = this.splModel.getElements().get(k);
					if(f.getElements().get(j).equals(elt.getOriginal1().getId()) ||
							f.getElements().get(j).equals(elt.getOriginal2().getId())
							){
				
						f.getElements().set(j, elt.getId() );
						
						
					}
					
				}//for(int k =0; k< this.splModel.getElements().size(); k++ ){
			}//	for(int j =0; j < f.getElements().size(); j++ ){
		}//for(int i =0; i< this.featureModel.getFeatures().size(); i++ ){
		
		
		
	}//	private void updateElementsID(){
	
	public void disp(){
		System.out.println(this.id);
		System.out.println("SPL model: ");
		this.splModel.disp();
		System.out.println("Feature model: ");
		this.featureModel.disp();
		
	}
	
	public String getDescription(Object obc){
		String res = "";
		
		if(obc instanceof Element){
			Element elt = (Element) obc;
			/*System.out.println(this.featureModel.getFeature(elt.getId()) );
			System.out.println(elt.getId() );*/
			res = "Element :"+System.getProperty("line.separator")+"  Feature: "+this.featureModel.getFeature(elt.getId()).getName() + elt.toStr();
		}else{
			//System.out.println(obc+"-------");
			if(obc instanceof Model){
				res = ((Model) obc).getId();
			}
		}
		
		return res;
		
	}


	/**
	 * Allows to obtain an SPL from a XMI file
	 * @param path : path of the XMI file
	 * @return return an SPL
	 */
	public static SPL getSPL(String path){	
		return SPLParser.getSPL(path);
	}
	
	public void modifyIDs(String str, int cmp){
		//int cmp = 0;
		String newId = "";
		FeatureModel fm = this.featureModel;
		Model splModel = this.splModel;
		for(int i = 0; i < splModel.getElements().size(); i++){
			Element elt = splModel.getElements().get(i);
			//String id = elt.getId();
			newId = str+(cmp++);
			
			
			ArrayList<Feature> features = fm.getAllFeatures();
			for(int j = 0; j < features.size(); j++){
				Feature f = features.get(j);
				for(int k = 0; k < f.getElements().size(); k++){
					String eltStr =  f.getElements().get(k);
					if(eltStr.equals(elt.getId())){
						//System.out.println(" ---  "+eltStr+"  -  "+elt.getId());
						f.getElements().set(k, newId);
					}
					
				}//for k
				
			}//for j
			
			String source = new String(elt.getId());
			elt.setSource(source);
			elt.setId(newId);
			
			
			
		}// for i
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ModelConfiguration.createModelConfig("config/umlcd.xmi");
		
		//SPL spl2 = SPL.getSPL("models/testAlternativeGroup/spl/popo.xmi");

		//Model m1 = Model.getModel(models/testAlternativeGroup/spl/pa.xmi")
		
		Model m1 = Model.getModel("models/runingEx/notpad1.xmi");
		Model m2 = Model.getModel("models/runingEx/notpad2.xmi");
		Model m3 = Model.getModel("models/runingEx/notpad3.xmi");
		//Model m4 = Model.getModel("models/runingEx/notpad4.xmi");
		//Model m5 = Model.getModel("models/runingEx/notpad5.xmi");
		//Model m6 = Model.getModel("models/runingEx/notpad6.xmi");
		
		/*Model m1 = Model.getModel("models/runingEx/n1.xmi");
		Model m2 = Model.getModel("models/runingEx/n2.xmi");
		Model m3 = Model.getModel("models/runingEx/n3.xmi");*/
		
		SPL spl = new SPL();
		spl.addModel(m1);
		spl.addModel(m2);
		spl.addModel(m3);
		/*spl.addModel(m4);
		spl.addModel(m5);
		spl.addModel(m6);*/
		
		Constraint.afficher(spl.getFeatureModel().getConstraints());
		
		
		System.out.println(spl.getFeatureModel().getAlternativeFeatures().size()+"  .. END");
		//spl.addModel(m4);
		//spl.disp();
		spl.modifyIDs("rrr", 1);
		spl.disp();
		
		
	

	}

}
