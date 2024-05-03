import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import datastructures.*;

public class GraphManager {
	
	
	public GraphManager(ArrayList<String[]> edges) {
		this.graph = this.graphFromEdgelist(edges, false);
		for(Vertex<String> v : this.graph.vertices()){
			this.shortestPathLengths(v);
		}
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
	

	  /**
	   * Computes shortest-path distances from src vertex to all reachable vertices of g.
	   *
	   * This implementation uses Dijkstra's algorithm.
	   *
	   * The edge's element is assumed to be its integral weight.
	   */
	  private Map<Vertex<String>, Double>
	  shortestPathLengths(Vertex<String> src) {
	    // d.get(v) is upper bound on distance from src to v
	    Map<Vertex<String>, Double> d = new ProbeHashMap<>();
	    // map reachable v to its d value
	    Map<Vertex<String>, Double> cloud = new ProbeHashMap<>();
	    // pq will have vertices as elements, with d.get(v) as key
	    AdaptablePriorityQueue<Double, Vertex<String>> pq;
	    pq = new HeapAdaptablePriorityQueue<>();
	    // maps from vertex to its pq locator
	    Map<Vertex<String>, Entry<Double,Vertex<String>>> pqTokens;
	    pqTokens = new ProbeHashMap<>();

	    // for each vertex v of the graph, add an entry to the priority queue, with
	    // the source having distance 0 and all others having infinite distance
	    for (Vertex<String> v : this.graph.vertices()) {
	      if (v == src)
	        d.put(v,0.0);
	      else
	        d.put(v, Double.MAX_VALUE);
	      pqTokens.put(v, pq.insert(d.get(v), v));       // save entry for future updates
	    }
	    
	    // now begin adding reachable vertices to the cloud
	    while (!pq.isEmpty()) {
	      Entry<Double, Vertex<String>> entry = pq.removeMin();
	      double key = entry.getKey();
	      Vertex<String> u = entry.getValue();
	      cloud.put(u, key);                             // this is actual distance to u
	      pqTokens.remove(u);                            // u is no longer in pq
	      for (Edge<Double> e : this.graph.outgoingEdges(u)) {
	        Vertex<String> v = this.graph.opposite(u,e);
	        if (cloud.get(v) == null) {
	          // perform relaxation step on edge (u,v)
	          double wgt = e.getElement();
	          if (d.get(u) + wgt < d.get(v)) {              // better path to v?
	            d.put(v, d.get(u) + wgt);                   // update the distance
	            pq.replaceKey(pqTokens.get(v), d.get(v));   // update the pq entry
	          }
	        }
	      }
	    }
	    return cloud;         // this only includes reachable vertices
	  }
}
