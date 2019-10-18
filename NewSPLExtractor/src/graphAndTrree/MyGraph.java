package graphAndTrree;



import java.awt.Color;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import fm.FeatureModel;
import model.Element;
import model.Model;




public class MyGraph  {
	private Graph graph;
	
	public MyGraph(String name, Model model, FeatureModel attachedFM) {
		
	graph = new SingleGraph(name);
	
	//graph.addAttribute("ui.stylesheet", "node {shape: box;fill-color: blue, green, red;text-mode:normal;text-background-mode: plain; fill-mode: dyn-plain;}");
	graph.addAttribute("ui.stylesheet", "node {fill-mode: dyn-plain;}");
	
	graph.addNode("model");
	Node root = graph.getNode("model");
	root.setAttribute("ui.style", "shape:triangle;");
	root.addAttribute("ui.style", "fill-color: blue;");
	root.addAttribute("ui.style", "size: 20;");
	root.setAttribute("role", "model");
	
	//root.addAttribute("ui.style", "fill-color: green;");
	//root.addAttribute("ui.color", Color.green);
	
	for(int i = 0; i < model.getElements().size(); i++){
	
				
		Element elt = model.getElements().get(i);
		
		Node node = graph.getNode(elt.getId());
		
		
		
		
	if(node==null){
		graph.addNode(elt.getId());
		node = graph.getNode(elt.getId());
		node.setAttribute("type", elt.getType());
		node.setAttribute("role", elt.getRole());
		node.setAttribute("volue", elt.getValue());
		node.setAttribute("order", elt.getOrder());
		 
		//node.addAttribute("ui.style", "fill-color: blue;");
		//node.addAttribute("ui.color", Color.blue);

		if(attachedFM==null){
			
		}else{
			
			node.addAttribute("ui.color", Color.blue);
			Color color = attachedFM.getColor(attachedFM.getFeature(elt.getId()));
					node.addAttribute("ui.color", color);
			/*System.out.println(elt.getId()+" ...................... ");
			System.out.println(attachedFM.getFeature(elt.getId())+" ////// ");*/
		}
		
		
		
		
		
		//System.out.println("1111");
		
		graph.addEdge(root.getId()+"-"+elt.getId(), root.getId(), elt.getId());
		Edge edge = graph.getEdge(root.getId()+"-"+elt.getId());

		edge.addAttribute("ui.style", "fill-color: green;");

		
	}else{
	}
	
	
	for(int j = 0; j < elt.getSubElements().size(); j++){
		Element subElt = elt.getSubElements().get(j);
		node = graph.getNode(subElt.getId());
		if(node==null){
			graph.addNode(subElt.getId());
			node = graph.getNode(subElt.getId());
			node.setAttribute("type", subElt.getType());
			node.setAttribute("role", subElt.getRole());
			node.setAttribute("volue", subElt.getValue());
			node.setAttribute("order", subElt.getOrder());
			if(attachedFM==null){
				
			}else{
				
				node.addAttribute("ui.color", Color.blue);
				Color color = attachedFM.getColor(attachedFM.getFeature(subElt.getId()));
						node.addAttribute("ui.color", color);
				/*System.out.println(elt.getId()+" ...................... ");
				System.out.println(attachedFM.getFeature(elt.getId())+" ////// ");*/
			}
			
		}else{
		}
		graph.addEdge(elt.getId()+"-"+subElt.getId(), elt.getId(), subElt.getId());
		Edge edge = graph.getEdge(elt.getId()+"-"+subElt.getId());
		
		//if(elt.getLevel()==1) edge.addAttribute("ui.style", "fill-color: bla;");
		if(elt.getLevel()==2) edge.addAttribute("ui.style", "fill-color: blue;");
		if(elt.getLevel()==3) edge.addAttribute("ui.style", "fill-color: red;");
		if(elt.getLevel()==4) edge.addAttribute("ui.style", "fill-color: yellow;");
	
		//String str = subElt.getId();
		//graph.addEdge(str, elt.getId(), subElt.getId());
		
	}//for(int j = 0; j < elt.getSubElements().size(); j++){
}//for(int i = 0; i < model.getElements().size(); i++){

		
		
		  for (Node node : graph) {
		        node.addAttribute("ui.label", node.getId());
		    }
		  
		  /**
		   * Create edges between elements of Reference type and referenced elements.
		   */
		  for (Node node : graph) {
			  //System.out.println(node.getId()+" ----------->>");
			  Element elt1 = model.getElement(node.getId());
			  if(elt1!=null){
				  if(elt1.getType().equalsIgnoreCase("Reference")){
					Element elt2 = model.getElement(elt1.getValue());
					graph.addEdge(elt1.getId()+"-"+elt2.getId(), elt1.getId(), elt2.getId(), true);
					Edge edge = graph.getEdge(elt1.getId()+"-"+elt2.getId());
					edge.setAttribute("ui.style", "stroke-mode: dashes;");
					edge.setAttribute("ui.label", "ref");
					edge.addAttribute("ui.style", "fill-color: pink;");
				  }//
		  		}
		        node.addAttribute("ui.label", node.getId());
		    }


	}



	public Graph getGraph() {
		return graph;
	}

	
 
}