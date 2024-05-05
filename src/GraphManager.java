// Import the required libraries
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import datastructures.*;

// Class for managing graphs
public class GraphManager {
	
	/**
     * Constructs a graph manager for an array of array strings.
     * 
     */
	public GraphManager(ArrayList<String[]> edges) {
		// Create a graph
		this.graph = this.graphFromEdgelist(edges, false);
	}
	
	/**
     * Method for applying Kruskal MST on current graph
     */
	public SpanningTree TestKruskal() {
		// Return the spanning tree result
		return GraphAlgorithms.<String>KruskalMST(this.graph);
	}
	
	/**
     * Method for applying Prim MST on current graph
     * 
     */
	public SpanningTree TestPrims() {
		// Return the spanning tree result
		return GraphAlgorithms.<String>PrimMST(this.graph);
	}
	
	// Graph variable as a form on adjancency map
	private Graph<String, Double> graph;
	
	/**
     * Constructs a graph from an array of array strings.
     *
     */
	private Graph<String, Double> graphFromEdgelist(ArrayList<String[]> edges, boolean directed) {
	    // Initialise the graph variable
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
	    // return the graph
	    return g;
	}
}
