

// Import the required libraries
import java.util.HashMap;
import java.util.HashSet;
import datastructures.*;

/**
 * A collection of graph algorithms.
 */
public class GraphAlgorithms {
  /**
   * Computes a minimum spanning tree of connected, weighted graph g using Kruskal's algorithm.
   *
   * Result is returned as a list of edges that comprise the MST (in arbitrary order).
   */
  public static <V> SpanningTree KruskalMST(Graph<V,Double> g) {
    // tree is where we will store result as it is computed
    SpanningTree tree = new SpanningTree();
    // pq entries are edges of graph, with weights as keys
    PriorityQueue<Double, Edge<Double>> pq = new HeapPriorityQueue<>();
    // union-find forest of components of the graph
    Partition<Vertex<V>> forest = new Partition<>();
    // map each vertex to the forest position
    Map<Vertex<V>,Position<Vertex<V>>> positions = new ProbeHashMap<>();

    // Loop through all vertices a create forest clusters for each vertex
    for (Vertex<V> v : g.vertices())
      positions.put(v, forest.makeCluster(v));
    // Insert all edges into the priority queue stored in order of weights
    for (Edge<Double> e : g.edges())
      pq.insert(e.getElement(), e);
    
    // Get the vertex count
    int size = g.numVertices();
    // while tree not spanning and unprocessed edges remain
    while (tree.Size() != size - 1 && !pq.isEmpty()) {
      // Get the least weighted edge entry
      Entry<Double, Edge<Double>> entry = pq.removeMin();
      // Get the edge object associated
      Edge<Double> edge = entry.getValue();
      // Get the end vertices associated with the edge
      Vertex<V>[] endpoints = g.endVertices(edge);
      // Get the forest position vertices associated with the edge
      Position<Vertex<V>> a = forest.find(positions.get(endpoints[0]));
      Position<Vertex<V>> b = forest.find(positions.get(endpoints[1]));
      // Check if the vertices are not in the same tree
      if (a != b) {
    	 // Insert the from vertex, to vertex and weight associated with the edge
        tree.Insert(endpoints[0].getElement().toString(), endpoints[1].getElement().toString(), edge.getElement());
        // Combine the two components (vertices or forests)
        forest.union(a,b);
      }
    }
    // Return the minimum spanning tree
    return tree;
  }
  
  
  
  /**
   * Computes a minimum spanning tree of connected, weighted graph g using Prim's algorithm.
   *
   * Result is returned as a list of edges that comprise the MST (in arbitrary order).
   */
  public static <V> SpanningTree PrimMST(Graph<V,Double> g) {
	  // tree is where we will store result as it is computed
	  SpanningTree tree = new SpanningTree();
	  
	  // Array for storing the parent vertex index
	  int[] pi = new int[g.numVertices()];
	  // Array for storing the current vertex index
	  int[] ind = new int[g.numVertices()];
	  // Map for associating the edge vertices and the weights
	  HashMap<String, Double> wts = new  HashMap<String, Double>();
	  // Set all the current indices to -1
	  for (int i = 0; i < ind.length; i++) {
		  ind[i] = -1;
	  }
	  // pq entries are vertices of graph, with weights as keys
	  AdaptablePriorityQueue<Double, Vertex<V>> pq = new HeapAdaptablePriorityQueue<>();
	  // Vertex map plotted against the minimal cost
	  ProbeHashMap<Vertex<V>, Double> keys = new ProbeHashMap<Vertex<V>, Double>();
	  // Set to keep track of visited vertices	  
	  HashSet<Vertex<V>> visited = new HashSet<>();
	  // tokens to map vertices with prev declared Priority Queue entries
	  Map<Vertex<V>, Entry<Double,Vertex<V>>> pqTokens =  new ProbeHashMap<>();
	  // Set all the vertices weights to infinity
	  for (Vertex<V> v : g.vertices()) {
          keys.put(v, Double.POSITIVE_INFINITY);
      }
      // Start from the first vertex
      Vertex<V> startVertex = g.vertices().iterator().next();
      // Set the weight as 0
      keys.put(startVertex, 0.0);
      
      // Insert all vertices into the priority queue
      for (Vertex<V> v : g.vertices()) {
          pqTokens.put(v, pq.insert(keys.get(v), v));
      }
	 
	 // Loop through entire PQ until it is empty
	 while(!pq.isEmpty()) {
		 // Get the least weighted vertex
		 Vertex<V> u = pq.removeMin().getValue();
		 // Remove the vertex entry associated in the token queue
         pqTokens.remove(u);
         // Add the vertex as visited
         visited.add(u);
         // Loop through all the outgoing edges of the vertex
		 for (Edge<Double> e : g.outgoingEdges(u)) {
			 // Get the opposite vertex
			 Vertex<V> v = g.opposite(u, e);
			 // If the vertex is not visited
			 if (!visited.contains(v)) {
				 // Get the weight of the edge
				 double wgt = e.getElement();
				 // If the weight of current edge is less than that of the vertex's assigned weight
				 if (wgt < keys.get(v)) {
					 // Set the vertex weight as the edge weight
	                 keys.put(v, wgt);
	                 // Replace the priority queue entry's weight for the opposite vertex
	                 pq.replaceKey(pqTokens.get(v), wgt);
	                 // Get the in vertex value
	                 int x = Integer.parseInt(u.getElement().toString());
	                 // Get the out vertex value
	                 int y = Integer.parseInt(v.getElement().toString());
	                 // Set the invertex as the parent of outvertex
	                 pi[y] = x;
	                 // Set the outvertex index in the set of indices
	                 ind[y] = y;
	                 // Check if the outvertex index value is greater than in vertex index
	                 if(x < y) {
	                	 // Store invertex + outvertex as the key and the weight as the value
	                	 wts.put(x + "" + y, wgt);
	                 } else {
	                	// Store outvertex + invertex as the key and the weight as the value
	                	 wts.put(y + "" + x, wgt);
	                 }
	             }
			 }
		 }		 
	 }

	 // Put the parent child vertex association in a Spanning Tree
	 // Skip the first index since first would be the root
	 for(int i = 1; i < ind.length; i++) { 
		 if(i < pi[i]) {
			 tree.Insert("" + i, "" + pi[i], wts.get(i + "" + pi[i]));
         } else {
        	 tree.Insert("" + pi[i], "" + i, wts.get(pi[i] + "" + i));
         }	 
	 }
	 // return the tree
	  return tree;
  }
}


