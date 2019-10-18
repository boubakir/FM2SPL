package fm;

import java.awt.Color;
import java.util.ArrayList;

import model.Element;
import model.Model;
import model.ModelConfiguration;
import parser.FMParser;
import util.MyColor;
import util.MyString;

public class FeatureModel {
	String id = "";
	//Feature base;
	

	private ArrayList<Feature>  mandatoryFeatures = new ArrayList<Feature>();
	private ArrayList<Feature> optionalFeatures = new ArrayList<Feature>();
	//private ArrayList<Feature> alternativeFeatures = new ArrayList<Feature>();
	
	private ArrayList<FeatureGroup> alternativeFeatureGroup = new ArrayList<FeatureGroup>();
	private ArrayList<FeatureGroup> orFeatureGroup  = new ArrayList<FeatureGroup>();
	
	//private ArrayList<Constraint> variantConstraints= new ArrayList<Constraint>();

	
	
	private ArrayList<Constraint> constraints= new ArrayList<Constraint>();


	public FeatureModel(){
	}
	public FeatureModel(String id){
		this.id = id;
	}

	public Feature getFeature(String elementID) {
		Feature feature = null;
		for(int i = 0; i < this.getAllFeatures().size(); i++ ){
			Feature feature2 = this.getAllFeatures().get(i);
			for(int j = 0; j < feature2.getElements().size(); j++ ){
				if(feature2.getElements().get(j).equals(elementID)) return feature2; 
			}
		}
		return feature;
	}
	
	
	/**
	 * return the alternative feature group that has the role role and the parent 
	 * @param feature the parent 
	 * @param role the role
	 * @return
	 */
	public FeatureGroup getAlternativeFeatureGroup(String parentFeature, String role) {

		for(int i = 0; i < this.getAlternativeFeatureGroup().size(); i++ ){
			FeatureGroup featureGroup = this.getAlternativeFeatureGroup().get(i);
			if(featureGroup.getParent().equals(parentFeature) && featureGroup.getRole().equals(role)   ){
				return featureGroup;
			}	
		}
		
		return null;
	}
	
	/**
	 * get all alternative features
	 * @return
	 */
	public ArrayList<Feature> getAlternativeFeatures(){
		ArrayList<Feature> res = new ArrayList<Feature>();
		//The first feature group contains variant fatures
		FeatureGroup featureGroup = null;
		for(int i = 1; i < this.getAlternativeFeatureGroup().size(); i++ ){
			featureGroup = this.getAlternativeFeatureGroup().get(i);
			for(int j = 0; j < featureGroup.getFeatures().size(); j++){
				res.add(featureGroup.getFeatures().get(j));
			}
			
		}
		
		return res;
		
	}
	

	
	
	
	/*
	public FeatureGroup getFeatureGroup(Feature feature) {
		for(int i = 0; i < this.getAlternativeFeatureGroup().size(); i++ ){
			FeatureGroup featureGroup = this.getAlternativeFeatureGroup().get(i);
			for(int j = 0; j < featureGroup.getFeatures().size(); j++ ){
				if(featureGroup.getFeatures().get(j)==feature) return featureGroup;
			}	
		}
		
		for(int i = 0; i < this.getOrFeatureGroup().size(); i++ ){
			FeatureGroup featureGroup = this.getOrFeatureGroup().get(i);
			for(int j = 0; j < featureGroup.getFeatures().size(); j++ ){
				if(featureGroup.getFeatures().get(j)==feature) return featureGroup;
			}	
		}
		
		return null;
	}
	
*/	

	public ArrayList<Feature> getAllFeatures() {
		ArrayList<Feature> features = new ArrayList<Feature>();
		features.addAll(this.mandatoryFeatures);
		features.addAll(this.optionalFeatures);
		
		for(int i = 0; i < this.alternativeFeatureGroup.size(); i++){
			features.addAll(this.alternativeFeatureGroup.get(i).getFeatures());
		}
		
		for(int i = 0; i < this.orFeatureGroup.size(); i++){
			features.addAll(this.orFeatureGroup.get(i).getFeatures());
		}

		return features;
	}
	
	
	

	
	public FeatureGroup getVariants(){
		return this.alternativeFeatureGroup.get(0);
	}
	public ArrayList<FeatureGroup> getAlternativeFeatureGroup() {
		return alternativeFeatureGroup;
	}
	public ArrayList<FeatureGroup> getOrFeatureGroup() {
		return orFeatureGroup;
	}
	public ArrayList<Feature> getMandatoryFeatures() {
		return mandatoryFeatures;
	}
	public ArrayList<Feature> getOptionalFeatures() {

		return this.optionalFeatures;
	}
	
	
	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*
	public void setFeatures(ArrayList<Feature> features) {
		for(int i = 0; i < features.size(); i++){
			this.addFeature(features.get(i));
		}//for(int i = 0; i < features.size(); i++){
	}*/
	
	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}

	public void addOptionalFeature(Feature feature){
		this.optionalFeatures.add(feature);
	}
	
	public void addFeatureGroup(FeatureGroup featureGroup){
		if(featureGroup.isAlternative()){
			this.alternativeFeatureGroup.add(featureGroup);
		}else{
			if(featureGroup.isOr()) this.orFeatureGroup.add(featureGroup);
		}
	}
	
	public void addMandatoryFeature(Feature feature){
		this.mandatoryFeatures.add(feature);
	}
	
	
	public void addConstraint(Constraint constraint){
		//this.constraints.add(constraint);
		Constraint.add(constraint, this.constraints);
	}

	
	
	/**
	 * allows to display the feature model
	 */
		public void disp(){
			
			
			System.out.println("------------------------------------------- ");
			System.out.println("Feature Model ");
			System.out.println("------------------------------------------- ");
			System.out.println("The mandatory feature:");
			for(int i = 0; i < this.mandatoryFeatures.size(); i++ ){
				this.mandatoryFeatures.get(i).disp();
			}//for
			System.out.println("Optional features:");
			for(int i = 0; i < this.optionalFeatures.size(); i++ ){
				this.optionalFeatures.get(i).disp();
			}//for
			for(int i = 0; i < this.alternativeFeatureGroup.size(); i++ ){
				System.out.println("Alternative features:");
				for(int j = 0; j < alternativeFeatureGroup.get(i).getFeatures().size(); j++ ){
					alternativeFeatureGroup.get(i).getFeatures().get(j).disp();
				}//for
			}
			
			
			for(int i = 0; i < this.orFeatureGroup.size(); i++ ){
				System.out.println("Or features:");
				for(int j = 0; j < orFeatureGroup.get(i).getFeatures().size(); j++ ){
					orFeatureGroup.get(i).getFeatures().get(j).disp();
				}//for
			}
			
			
			System.out.println("Constraints:");
			for(int i = 0; i < this.constraints.size(); i++ ){
				this.constraints.get(i).disp();
			}//for
		}
	
	
	public String toStr(){
		String res = "Mandatory feature: ";
			//this.base.toStr()+System.getProperty("line.separator")+
			for(int i = 0; i < this.mandatoryFeatures.size(); i++ ){
				res = res +"      "+ this.mandatoryFeatures.get(i).toStr();
			}//for
			res = res + "Optional features: "+System.getProperty("line.separator");
			for(int i = 0; i < this.optionalFeatures.size(); i++ ){
				res = res +"      "+ this.optionalFeatures.get(i).toStr();
			}//for
			res = res +System.getProperty("line.separator"); 
			
			for(int i = 0; i < this.alternativeFeatureGroup.size(); i++ ){
				res = res+"Alternative features: "+System.getProperty("line.separator");
				for(int j = 0; j < alternativeFeatureGroup.get(i).getFeatures().size(); j++ ){
					res = res +alternativeFeatureGroup.get(i).getFeatures().get(j).toStr();
				}//for
			}
			
			
			for(int i = 0; i < this.orFeatureGroup.size(); i++ ){
				res = res+"Or features: "+System.getProperty("line.separator");
				for(int j = 0; j < orFeatureGroup.get(i).getFeatures().size(); j++ ){
					res = res +orFeatureGroup.get(i).getFeatures().get(j).toStr();
				}//for
			}
			
			
			res = res +System.getProperty("line.separator") + 
			"Constraints: "+System.getProperty("line.separator");
			for(int i = 0; i < this.constraints.size(); i++ ){
				res = res +System.getProperty("line.separator")+ this.constraints.get(i).toString();
			}//for
			return res;
		}
		
		
		
		   /**
		 * Allows to obtain a feature model from an XMI file
		 * @param path : path of the XMI file
		 * @return return a feature model
		 */
	   public static FeatureModel getFM(String path){	
			return FMParser.getFM(path);
		}
		
	   /**
			 * Allows to save a feature model in an XMI file
			 * @param path : path of the XMI file
			 * @return return a feature model
			 */
		   public void save(String path){	
			   FMParser.save(this, path);
			}
	   
	/**
	 * Initialize the FM using the element of a model
	 * @param model
	 */
	public void initialize(Model model){
		ArrayList<String> elements = new ArrayList<String>();
		for(int i = 0; i < model.getElements().size(); i++){
			elements.add(model.getElements().get(i).getId());
		}
		
		Feature base = new Feature("base", "mandatory", "root", elements);
		this.mandatoryFeatures.add(base);
		FeatureGroup variants = new FeatureGroup("variants", "alternative", "root");
		this.alternativeFeatureGroup.add(variants);
		
		//New 02/12/201/
		Feature newVariant = new Feature(model.getId()+"_Variant", "variant", this.alternativeFeatureGroup.get(0).getId());
		this.addVariant(newVariant);
		//-New 02/12/201/
	}
	
	/**
	 * add a variant to the Feature Model
	 * @param feature
	 */
	public void addVariant(Feature feature){
		this.alternativeFeatureGroup.get(0).addFeature(feature);
	}
	
	
	/**
	 * Updating constraints
	 * @param model
	 */
	public void updatingConstraints(Model model){
		
		this.constraints = new ArrayList<Constraint>();
		
		for(int i =0; i  < model.getElements().size(); i++){
			Element elt1 = model.getElements().get(i);
			
			if(elt1.getType().equalsIgnoreCase("Reference")){
				Element elt2 = model.getElement(elt1.getValue());
				Feature f1 = this.getFeature(elt1.getId());
				Feature f2 = this.getFeature(elt2.getId());
							
				if(f1.getName().equalsIgnoreCase(f2.getName()) || this.mandatoryFeatures.contains(f2)){
					
				}else{
				
					
					Constraint constraint = new Constraint();
					constraint.setLeft(f1.getName());
					constraint.setRight(f2.getName());
					constraint.setOperator("require");
					//this.constraints.add(constraint);
					addConstraint(constraint);
					
				}
				
				
			}//if(elt.getType().equalsIgnoreCase("Reference")){
			
			
			
			
			
			
		}//for(int i =0; i  < model.getElements().size(); i++){
	}
	
	
	/**
	 * updating alternative feature groups
	 * @param model
	 */
	
/*	
	public void updatingAlternativeFeatures2(Model model){
		
		for(int i = 0; i < model.getElements().size(); i++){
			Element element = model.getElements().get(i);
			ArrayList<Role> subRoles = ModelConfiguration.getRole(element.getRole()).getSubRole();
			

			for(int j = 0;  j< subRoles.size(); j++ ){
				Role subRole = subRoles.get(j); 
				ArrayList<Element> subElements = element.getSubElements(subRole, 3);
				
				if(subElements.size()>1){
					FeatureGroup featureGroup = getFeatureGroup(subElements);
					//System.out.println(featureGroup);
					
					if(featureGroup==null){
						featureGroup = new FeatureGroup();
						featureGroup.setParent(this.getFeature(element.getId()).getName());
						featureGroup.setType("alternative");
						featureGroup.setId("alt"+this.getAlternativeFeatureGroup().size());
						this.alternativeFeatureGroup.add(featureGroup);
					}else{
						if(featureGroup.getParent().equalsIgnoreCase(this.getFeature(element.getId()).getName())){
						}else{
							featureGroup.setParent(this.getFeature(element.getId()).getName());
						}
						
					}//if(featureGroup==null){
					
					addToFeatureGroup(subElements, featureGroup);
					
	
					
				}//if(subElements.size()>1){
			}//for(int j = 0;  j< subRoles.size(); j++ ){
		}//for(int i = 0; i < model.getElements().size(); i++){
		
	}
	
*/	
	
	
/*	
	
public void updatingAlternativeFeatures(Model splModel){
	//Feature newModelOptionalFeature = null;
	//Feature newVariant = null;
		
		for(int i = 0; i < this.optionalFeatures.size(); i++){
			Feature f = this.optionalFeatures.get(i);
			for(int j = 0; j < f.getElements().size(); j++){
				Element e = splModel.getElement(f.getElements().get(j));
				if(e.isAtomic()){
					ArrayList<Feature> features = new ArrayList<Feature>(); 
					features.addAll(this.optionalFeatures);
					features.addAll(this.getAlternativeFeatures());
					
					int k = 0;	boolean found = false; 
					while(k < features.size()  &&  !found){
						Feature f1 = features.get(k);
						int m = 0;
						while( m < f1.getElements().size() && !found){
							Element e1 = splModel.getElement(f1.getElements().get(m));
							//System.out.println(e+"  .. "+e1+ " . "+f1.getElements().get(m));
							if(areAttributesForTheSameRole(e, e1, splModel) && e!=e1 ){
								found = true;
							}
							
							m++;
						}//while( m < f1.getElements().size() && !found){
					
						k++;
					}//while(k < features.size()  &  !found){
					
					//System.out.println(" =="+this.alternativeFeatureGroup.size()+"  AAAAAAAAAA     i="+i+"  j="+j+"   "+this.optionalFeatures.size());
					
					if(found){		
							
						Feature f_parent = this.getFeature(splModel.getParent(e).getId());			
						FeatureGroup fg = getAlternativeFeatureGroup(f_parent.getName(), e.getRole());

						if(fg==null){
									
									//create a new feature group
									
fg = new FeatureGroup(splModel.getParent(e).getId()+"_"+e.getRole(), "alternative", f_parent.getName(), e.getRole());
this.alternativeFeatureGroup.add(fg);									

									
								}//if(fg==null){
								
								//fg.disp();
								ArrayList<String> elts = new ArrayList<String>();
								elts.add(e.getId());
						Feature f2 = new Feature("alt"+(this.getAlternativeFeatures().size()+1), 
										"alternative", fg.getId(), elts);
										//"alternative", f_parent.getName(), elts);
						
						
								f.removeElement(e.getId());
					
						fg.addFeature(f2);
						
						Constraint constraint = null;
						for(k = 0; k < this.getVariants().getFeatures().size(); k++){
							
							Feature variant = this.getVariants().getFeatures().get(k);
							Constraint.afficher(this.constraints);
							System.out.println(f.getName()+"..");
							if(Constraint.contains(this.constraints, variant.getName(), f.getName(), "implies")    ){
								System.out.println(" ------->>>");
								constraint = new Constraint(variant.getName(), f2.getName());
								addConstraint(constraint);
							}
						}//for(k = 0; k < this.getVariants().getFeatures().size(); k++){
						
						
						
						
					}//if(found){
							
				}//if(e.isAtomic()){
			}//for(int j = 0; j < f.getElements().size(); j++){
		}//for(int i = 0; i < this.optionalFeatures.size(); i++){
		
		
		//updating the name of alternative feature group names
		for(int i = 1; i < this.alternativeFeatureGroup.size(); i++){
			FeatureGroup fg = this.alternativeFeatureGroup.get(i);
			fg.setId(id);
			
			String parentName = "";
			if(fg.getFeatures().size()!=0){
				Feature f = fg.getFeatures().get(0);
				if(f.getElements().size()!=0){
					Element e = splModel.getElement(f.getElements().get(0));
					parentName = splModel.getParent(e).getId();
				}
			}
			fg.setId(parentName+"_"+fg.getRole());
			
			for(int j = 0; j < fg.getFeatures().size(); j++){
				fg.getFeatures().get(j).setParent(fg.getId());
			}
			
		}
		//---updating the name of alternative feature group names
		
	}
	
*/
	
	
public void updatingAlternativeFeatures(Model splModel, Feature f_n, Feature v_n, 
		ArrayList<String>	OnlyInRight, Model newModel  ){
	

	//New 08/01/2019
	for(int i = 0; i<this.getAlternativeFeatures().size(); i++){  
		Feature f = this.getAlternativeFeatures().get(i);
		ArrayList<String> elts = f.getElements();
		ArrayList<String> elts1 = MyString.intersection(OnlyInRight, elts);
		for(int ii = 0; ii < OnlyInRight.size(); ii++ ){
			System.out.print(OnlyInRight.get(ii)+"   ");
		}
		if(elts1.size()==0 ){
			Constraint constraint = new Constraint(v_n.getName(), f.getName());
			addConstraint(constraint);
		}else{	
		}

	
	}//for(int i = 0; i<this.getAllFeatures().size(); i++){ 
	//New 08/01/2019
	if(f_n!=null) {
			for(int j = 0; j < f_n.getElements().size(); j++){
				Element e_n = splModel.getElement(f_n.getElements().get(j));
				//if(e.isAtomic()){
					ArrayList<Feature> features = new ArrayList<Feature>(); 
					features.addAll(this.optionalFeatures);
					features.addAll(this.getAlternativeFeatures());
					Feature f = null;	Element e = null;
					int k = 0;	int found = -1; //found=0, f1 is in optionalFeatures, found=1, f1 is in getAlternativeFeatures,  
					while(k < features.size()  &&  found==-1){
						f = features.get(k);
						int m = 0;
						while( m < f.getElements().size() && found==-1){
							e = splModel.getElement(f.getElements().get(m));
							
							if(areAttributesForTheSameRole(e_n, e, splModel) &&
									f_n!=f){
								if(k<this.optionalFeatures.size()){
									found = 0;
								}else{
									found = 1;
								}
								
							}//(areAttributesForTheSameRole(e_n, e1, splModel) && newModelOptionalFeature!=f1 ){
							
							m++;
						}//while( m < f1.getElements().size() && found==-1){
					
						k++;
					}//while(k < features.size()  &  found==-1){
					
					//System.out.println(" =="+this.alternativeFeatureGroup.size()+"  AAAAAAAAAA     i="+i+"  j="+j+"   "+this.optionalFeatures.size());
					
					if(found==-1){
					}else{
						FeatureGroup fg = null;
						Feature f_parent = this.getFeature(splModel.getParent(e_n).getId());
						if(found==0){//found=0, f1 is in optionalFeatures
										
							//FeatureGroup fg = getAlternativeFeatureGroup(f_parent.getName(), e.getRole());
							fg = new FeatureGroup(splModel.getParent(e_n).getId()+"_"+e_n.getRole(), "alternative", f_parent.getName(), e_n.getRole());
							this.alternativeFeatureGroup.add(fg);
							
							ArrayList<String> elts = new ArrayList<String>();
							elts.add(e.getId());
					        Feature f1 = new Feature("alt"+(this.getAlternativeFeatures().size()+1), 
									"alternative", fg.getId(), elts);
									//"alternative", f_parent.getName(), elts);					
							f.removeElement(e.getId());
							fg.addFeature(f1);

							

							Constraint constraint = null;
							for(k = 0; k < this.getVariants().getFeatures().size(); k++){
								
								Feature variant = this.getVariants().getFeatures().get(k);
								//Constraint.afficher(this.constraints);
								//System.out.println(f.getName()+"..");
								if(Constraint.contains(this.constraints, variant.getName(), f.getName(), "implies")    ){
									//System.out.println(" ------->>>");
									constraint = new Constraint(variant.getName(), f1.getName());
									addConstraint(constraint);
								}
							}//for(k = 0; k < this.getVariants().getFeatures().size(); k++){
							
							
						}else{//f1 is in getAlternativeFeatures
							fg = getAlternativeFeatureGroup(f_parent.getName(), e_n.getRole());
						}//if(found==0){
							
						ArrayList<String> elts = new ArrayList<String>();
						elts.add(e_n.getId());
				        Feature f_n1 = new Feature("alt"+(this.getAlternativeFeatures().size()+1), 
								"alternative", fg.getId(), elts);
									
						f_n.removeElement(e_n.getId());
						fg.addFeature(f_n1);
						Constraint constraint = new Constraint(v_n.getName(), f_n1.getName());
						addConstraint(constraint);


						
					}//if(found==-1){
							
				//}//if(e.isAtomic()){
			}//for(int j = 0; j < f.getElements().size(); j++){
		//}//for(int i = 0; i < this.optionalFeatures.size(); i++){
	}//	if(f_n!=null) {
		
		
		//updating the alternative feature group names
		for(int i = 1; i < this.alternativeFeatureGroup.size(); i++){
			FeatureGroup fg = this.alternativeFeatureGroup.get(i);
			fg.setId(id);
			
			String parentName = "";
			if(fg.getFeatures().size()!=0){
				Feature f = fg.getFeatures().get(0);
				if(f.getElements().size()!=0){
					Element e = splModel.getElement(f.getElements().get(0));
					parentName = splModel.getParent(e).getId();
				}
			}
			fg.setId(parentName+"_"+fg.getRole());
			
			for(int j = 0; j < fg.getFeatures().size(); j++){
				fg.getFeatures().get(j).setParent(fg.getId());
			}
			
		}
		//---updating the name of alternative feature group names
		
	}
	

	
	
private boolean areAttributesForTheSameRole(Element e1, Element e2, Model model){
	boolean res = false;
	
	if(e1.isAtomic() && e2.isAtomic() && 
			e1.getRole().equalsIgnoreCase(e2.getRole()) 
			&& model.getParent(e1)==model.getParent(e2)  ){
			res = true;
			
		
	}
	
	
	return res;
	
}
	
	/**
	 * Add a set of elements to a feature group
	 * @param elements
	 * @param featureGroup
	 */
	/**
	 * returns the feature group of a set of elements
	 * @param elements
	 * @return
	 */
	/*
	private FeatureGroup getFeatureGroup(ArrayList<Element> elements){
		FeatureGroup featureGroup = null;
		int i = 0; 		boolean found = false;
		
		while(i < elements.size() && !found){
			
			featureGroup = this.getFeatureGroup( this.getFeature(elements.get(i).getId()) );
			if(featureGroup == null){
				i++;
			}else{
				found = true;
			}
		}
		
		
		
		return featureGroup;
	}//	public static FeatureGroup getFeatureGroup(ArrayList<Element> elements){
*/	

	/**
	 * allows to updating the feature model
	 * @param OnlyInLeft: the elements that belong only to the new model
	 * @param OnlyInRight: the elements that belong only to the SPL model
	 * @param model: the new model added to the SPL
	 */
	public void updating(ArrayList<String>	OnlyInLeft, ArrayList<String>	OnlyInRight, Model splModel, Model newModel){
		
		
		
		this.updatingOptionalFeature(OnlyInLeft, OnlyInRight, splModel, newModel);
		//this.updatingAlternativeFeatures(splModel);
		
		this.removeEmptyFeature();
		
		
		//this.updatingConstraints(newModel);
		
		//System.out.println("  ------  "+this.constraints.size());
		//System.out.println("  --**--  "+this.constraints.size());
	}
	
	
	
	private void removeEmptyFeature(){
		ArrayList<Feature> features = new ArrayList<Feature>();
		for(int i= 0; i < this.optionalFeatures.size(); i++){
			if(this.optionalFeatures.get(i).isEmpty()){
				this.constraints = 
						Constraint.removeFromRgight(optionalFeatures.get(i).getName(), this.constraints);
			}else{
				features.add(this.optionalFeatures.get(i));
			}
		}
		this.optionalFeatures = features;
	}
	
	/**
	 * Allows to updating the set of optional feature
	 * @param OnlyInLeft: the elements that belong only to the new model
	 * @param OnlyInRight: the elements that belong only to the SPL model
	 * @param model: the new model added to the SPL
	 */
	private void updatingOptionalFeature(ArrayList<String>	OnlyInLeft, ArrayList<String>	OnlyInRight, Model splModel, Model newModel){
//STEP 1 ---------------------------------------------------------------------------------------------
		//Feature optionalFeature = null;		
		//New 02/12/201/
		Feature newModelOptionalFeature = null;
		Feature newVariant = null;
		Constraint constraint = null;
		newVariant = new Feature(newModel.getId()+"_Variant", "variant", this.alternativeFeatureGroup.get(0).getId());
		this.addVariant(newVariant);
		//-New 02/12/201/
		
		if(OnlyInLeft.size()==0){
		}else{
			newModelOptionalFeature= new Feature("f"+(this.optionalFeatures.size()+1) , "optional", this.getBase().getName(), OnlyInLeft);
			this.optionalFeatures.add(newModelOptionalFeature);
			
			//New 02/12/201/
			for(int i = 0; i < this.getVariants().getFeatures().size();i++){
				Feature variant = this.getVariants().getFeatures().get(i);
				if(variant==newVariant){
				}else{
					constraint = new Constraint(variant.getName(), "\u00AC"+newModelOptionalFeature.getName());
					//addConstraint(constraint);
				}//if(variant==newVariant){
				
			}//for(int i = 0; i <this.getVariants().getFeatures().size();i++){	
			//-New 02/12/201/
			constraint = new Constraint(newVariant.getName(), newModelOptionalFeature.getName());
			//this.constraints.add(constraint);
			addConstraint(constraint);
			
			//System.out.println("  ***  "+this.constraints.size());
			//System.out.println(" ... "+constraint.toString());
			
		}//if(OnlyInLeft.size()==0){

//STEP 2 ---------------------------------------------------------------------------------------------
		
		Feature optionalFeature = null;
		
		ArrayList<String> elts = MyString.intersection(OnlyInRight, this.getBase().getElements());
		if(elts.size()==0){
		}else{
			
			this.getBase().setElements(MyString.minus(this.getBase().getElements(), elts));
			optionalFeature= new Feature("f"+(this.optionalFeatures.size()+1) , "optional", this.getBase().getName(), elts);
			this.optionalFeatures.add(optionalFeature);
			
			//OnlyInRight = MyString.minus(OnlyInRight, elts);
			
			//New 03/12/2018
			
			for(int i = 0; i < this.getVariants().getFeatures().size();i++){
				Feature variant = this.getVariants().getFeatures().get(i);
				if(variant==newVariant){
				}else{
					constraint = new Constraint(variant.getName(), optionalFeature.getName());
					//this.constraints.add(constraint);
					addConstraint(constraint);
				}//if(variant==newVariant){
			}//for(int i = 0; i <this.getVariants().getFeatures().size();i++){
			
			constraint = new Constraint(newVariant.getName(), "\u00AC" + optionalFeature.getName());
			//this.constraints.add(constraint);
			//addConstraint(constraint);
			//-New 03/12/2018
			
			//New 03/12/2018
			//update parent feature of feature groups
			
			
			
			for(int i = 1; i < this.getAlternativeFeatureGroup().size(); i++){
				FeatureGroup fg = this.getAlternativeFeatureGroup().get(i);
				
				if(fg.getParent().equals(this.getBase().getName())){
					
					String elt = fg.getFeatures().get(0).getElements().get(0); 
					String parentElt = splModel.getParent(splModel.getElement(elt)).getId();
					
					if(optionalFeature.contains(parentElt)){
						//System.out.println("%%%%%%%%%%%%% + "+parentElt);
						fg.setParent(optionalFeature.getName());
					}
				}
			}//for(int i = 1; i < this.getAlternativeFeatureGroup().size(); i++){
			
			//-New 03/12/2018
		}//if(elts.size()==0){
		
//STEP 3 ---------------------------------------------------------------------------------------------

		
		int i = 0;
		while( i<this.optionalFeatures.size()){  //New 03/12/02018
			Feature f = this.optionalFeatures.get(i);
			
			
			
			if(f==newModelOptionalFeature){
			}else{
				elts = f.getElements();
				ArrayList<String> elts1 = MyString.intersection(OnlyInRight, elts);
				if(elts1.size()==0 ){
					//SUP
					//New 03/12/02018
					constraint = new Constraint(newVariant.getName(), f.getName());
					//this.constraints.add(constraint);
					addConstraint(constraint);
					//-New 03/12/02018
				}else{
					if( MyString.contains(OnlyInRight, elts) ){
						//New 03/12/02018
						//OnlyInRight = MyString.minus(OnlyInRight, elts); 
						constraint = new Constraint(newVariant.getName(), "\u00AC"+f.getName());
						//addConstraint(constraint);
						//-New 03/12/02018
					}else{
						optionalFeature= new Feature("f"+(this.optionalFeatures.size()+1) , "optional", this.getBase().getName(), elts1);
						this.optionalFeatures.add(optionalFeature);
						f.setElements(MyString.minus(elts, elts1));
						
						//OnlyInRight = MyString.minus(OnlyInRight, elts1);

						//New 09-01-2019
						//OnlyInRight = elts; 
						//--------New 09-01-2019
						
						for(int j = 0; j < this.getVariants().getFeatures().size(); j++){
							Feature variant = this.getVariants().getFeatures().get(j);
							if(variant==newVariant){
							}else{ 
								if(Constraint.contains(this.constraints, variant.getName(), f.getName(), "implies")    ){
									constraint = new Constraint(variant.getName(), optionalFeature.getName());
									//this.constraints.add(constraint);
									addConstraint(constraint);
								}
							}//if(variant==newVariant){
						}//for(int j = 0; j < this.getVariants().getFeatures().size(); j++){
							
						
							constraint = new Constraint(newVariant.getName(), "\u00AC"+optionalFeature.getName());
							//this.constraints.add(constraint);
							//addConstraint(constraint);
							constraint = new Constraint(newVariant.getName(), f.getName());
							//this.constraints.add(constraint);
							addConstraint(constraint);
						
						//-New 03/12/02018
						
							//New 03/12/2018
							//update parent feature of feature groups
							
							
							
							for(int j = 1; j < this.getAlternativeFeatureGroup().size(); j++){
								FeatureGroup fg = this.getAlternativeFeatureGroup().get(j);
								
								if(fg.getParent().equals(f.getName())){
									
									String elt = fg.getFeatures().get(0).getElements().get(0); 
									String parentElt = splModel.getParent(splModel.getElement(elt)).getId();
									
									if(optionalFeature.contains(parentElt)){
									
										fg.setParent(optionalFeature.getName());
									}
								}
							}//for(int i = 1; i < this.getAlternativeFeatureGroup().size(); i++){
							
							//-New 03/12/2018
						
					}//if( MyString.contains(OnlyInRight, f.getElements()) ){
				}//if( elts1.size()==0 ){
			
			}//if(f==newModelOptionalFeature){ New 03/12/02018
			i++;
		}//while(){
		
	
		
		this.updatingAlternativeFeatures(splModel, newModelOptionalFeature, newVariant, OnlyInRight, newModel);
	}
		   
	public Feature getBase(){
		if(this.mandatoryFeatures.size()>0){
			return this.mandatoryFeatures.get(0);
		}else{
			return null;
		}
	}
	
	public String toString(){
		return this.getId();
	}
	
	/*public void remove(Feature feature){
	
		this.getAllFeatures().remove(feature);
		System.out.println(feature.getName());
	}*/
	
	public void remove(Feature feature){
		this.mandatoryFeatures.remove(feature);
		this.optionalFeatures.remove(feature);
		
		for(int i = 0; i < this.alternativeFeatureGroup.size(); i++){
			this.alternativeFeatureGroup.get(i).getFeatures().remove(feature);
		}

		for(int i = 0; i < this.orFeatureGroup.size(); i++){
			this.orFeatureGroup.get(i).getFeatures().remove(feature);
		}
	}
	
	
	public String getDescription(){
		String res = "A feature model "+System.getProperty("line.separator")+
				"Id : "+this.id;
		return res;
		
	}
	
	/**
	 * 
	 * @param feature
	 * @return
	 */
	public Color getColor(Feature feature){
		int num = this.getAllFeatures().indexOf(feature);
		return MyColor.getColor(num);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
		test2();
		
	}
	
	public static void test2() {
		
		ModelConfiguration.initialize();
		}

		
	public static void test1() {
		FeatureModel fm = FeatureModel.getFM("models/simple/FM3.xmi");
		//fm.disp();
		System.out.println("******************   "+fm.getId());
		fm.save("models/simple/FM2.xmi" );
		System.out.println(fm.toStr());
		System.out.println("--------------------------------------");
		for(int i = 0; i < fm.getAllFeatures().size(); i++){
			//Feature f  = fm.getAllFeatures().get(i);
			//System.out.println(f.getName()+"  "+fm.getParentIndex(f));
		}

	}

}
