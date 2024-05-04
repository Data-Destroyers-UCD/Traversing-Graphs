import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import datastructures.*;

public class GraphManager {
	
	
	public GraphManager(ArrayList<String[]> edges) {
		this.graph = this.graphFromEdgelist(edges, false);
		Printer.Print(GraphAlgorithms.<String>KruskalMST(this.graph)); 
		//
	}
	
	
	private Graph<String, Double> graph;
	
	/**
     * Constructs a graph from an array of array strings.
     *
     */
	private Graph<String, Double> graphFromEdgelist(ArrayList<String[]> edges, boolean directed) {
	    Graph<String, Double> g = new AdjacencyMapGraph<>(directed);
	
	    // first pass to get sorted set of vertex labels
	    TreeSet<String> labels = new TreeSet<>();
	    for (String[] edge : edges) {
	      labels.add(edge[0]);
	      labels.add(edge[1]);
	    }
	
	    // now create vertices (in alphabetical order)
	    HashMap<String, Vertex<String> > verts = new HashMap<>();
	    for (String label : labels)
	      verts.put(label, g.insertVertex(label));
	
	    // now add edges to the graph
	    for (String[] edge : edges) {
	    	Double cost = (edge.length == 2 ? 1 : Double.parseDouble(edge[2]));
	      g.insertEdge(verts.get(edge[0]), verts.get(edge[1]), cost);
	    }
	    return g;
	}
	

}
