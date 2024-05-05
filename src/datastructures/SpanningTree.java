package datastructures;

// Import the required libraries
import java.util.HashMap;
import java.util.Map;

/*
 * A class that stores the tree data
 * */
public class SpanningTree {
	 
	/**
	   * Inserts an entry in the edges hash table
	   * @param from  first endpoint vertex name of the edge
	   * @param to  other endpoint vertex name of the edge
	   * @return   boolean value stating if the edges already contain the required from to vertices
	   */
	public boolean Insert(String from, String to, double weight) {
		// Increase the weight count
		this.totalWeight += weight;
		// Check if the from node and to node exists in the table
		if(this.edges.containsKey("" + from + to) || this.edges.containsKey("" + to + from)) {
			// replace the edge data for the key
			this.edges.replace("" + from + to,  new SpanningTreeEdge(from, to, weight));
			return false;
		}
		// from and to nodes dont exist, create a new entry in the table
		this.edges.put("" + from.toString() + to.toString(), new SpanningTreeEdge(from, to, weight));
		return true;
	}
	
	/**
	 * Function for checking the number of edges
	 * @return the count of edges
	 */
	public int Size() {
		return this.edges.size();
	}
	
	/**
	 * Function to get the total weight of the tree
	 * @return the total weight of the tree
	 */
	public double TreeWeight() {
		return this.totalWeight;
	}
	
	/**
	 * Function to get edges hash map
	 * @return return the edges hash map
	 */
	public HashMap<String, SpanningTreeEdge> GetNodesMap() {
		return this.edges;
	}
	
	/**
	 * Function to get the spanning tree data as a string
	 * @return return the space separated edge data
	 */
	public String toString() {
		String result = "";
		for (Map.Entry<String, SpanningTreeEdge> entry : this.edges.entrySet()) {
			result += entry.getValue().FromNode + " " + entry.getValue().ToNode + " " + entry.getValue().Weight + "\n";
		}
		return result;
	}
	
	/**
	 * Class for storing the edge data
	 */
	public class SpanningTreeEdge {
		// from node associated
		public String FromNode;
		// to node associated
		public String ToNode;
		// Weight of the edge
		public double Weight;
		// Constructer for initialising the edge
		public SpanningTreeEdge(String from, String to, double weight) {
			this.FromNode = from;
			this.ToNode = to;
			this.Weight = weight;
		}
	}
	
	// Hash map to store all edge entries of the spanning tree
	private HashMap<String, SpanningTreeEdge> edges = new HashMap<>();
	// weight counter to store the total weights
	private double totalWeight = 0;
}
