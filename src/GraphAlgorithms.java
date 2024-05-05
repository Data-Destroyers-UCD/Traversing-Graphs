


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

    for (Vertex<V> v : g.vertices())
      positions.put(v, forest.makeCluster(v));

    for (Edge<Double> e : g.edges())
      pq.insert(e.getElement(), e);

    int size = g.numVertices();
    // while tree not spanning and unprocessed edges remain
    while (tree.Size() != size - 1 && !pq.isEmpty()) {
      Entry<Double, Edge<Double>> entry = pq.removeMin();
      Edge<Double> edge = entry.getValue();
      Vertex<V>[] endpoints = g.endVertices(edge);
      Position<Vertex<V>> a = forest.find(positions.get(endpoints[0]));
      Position<Vertex<V>> b = forest.find(positions.get(endpoints[1]));
      if (a != b) {
        tree.Insert(endpoints[0].getElement().toString(), endpoints[1].getElement().toString(), edge.getElement());
        forest.union(a,b);
      }
    }

    return tree;
  }
  
  
  
  
  public static <V> SpanningTree PrimMST(Graph<V,Double> g) {
	  SpanningTree tree = new SpanningTree();
	  
	  int[] pi = new int[g.numVertices()];
	  int[] ind = new int[g.numVertices()];
	  
	  HashMap<String, Double> wts = new  HashMap<String, Double>();
	  
	  for (int i = 0; i < ind.length; i++) {
		  ind[i] = -1;
	}
	  
	  AdaptablePriorityQueue<Double, Vertex<V>> pq = new HeapAdaptablePriorityQueue<>();
	  
	  ProbeHashMap<Vertex<V>, Double> keys = new ProbeHashMap<Vertex<V>, Double>();
	  	  
	  HashSet<Vertex<V>> visited = new HashSet<>();
	  
	  Map<Vertex<V>, Entry<Double,Vertex<V>>> pqTokens =  new ProbeHashMap<>();

	  for (Vertex<V> v : g.vertices()) {
          keys.put(v, Double.POSITIVE_INFINITY);
      }
      // Start from the first vertex
      Vertex<V> startVertex = g.vertices().iterator().next();
      keys.put(startVertex, 0.0);
      
      // Insert all vertices into the priority queue
      for (Vertex<V> v : g.vertices()) {
          pqTokens.put(v, pq.insert(keys.get(v), v));
      }
	 
	  //Printer.Print(g.toString()); 
	  
	 while(!pq.isEmpty()) {
		 Vertex<V> u = pq.removeMin().getValue();
         pqTokens.remove(u);
         visited.add(u);
         

		 for (Edge<Double> e : g.outgoingEdges(u)) {
			 Vertex<V> v = g.opposite(u, e);
			 //Printer.Print(visited);
			 if (!visited.contains(v)) {
				 double wgt = e.getElement();
				 if (wgt < keys.get(v)) {

                     keys.put(v, wgt);
                     pq.replaceKey(pqTokens.get(v), wgt);
                     int x = Integer.parseInt(u.getElement().toString());
                     int y = Integer.parseInt(v.getElement().toString());
                     pi[y] = x;
                     ind[y] = y;
                     if(x < y) {
                    	 wts.put(x + "" + y, wgt);
                     } else {
                    	 wts.put(y + "" + x + "", wgt);
                     }
                     
                     //Printer.Print( u.getElement().toString() + " " + v.getElement().toString());
                  // Update minimum weight edge
         	         //tree.Insert(u.getElement().toString(), v.getElement().toString(), wgt);

                 }
			 }
			 
			 
		
		 }
		 
	
		 
	 }

	 for(int i = 1; i < ind.length; i++) {
		 
		 if(i < pi[i]) {
			 tree.Insert("" + i, "" + pi[i], wts.get(i + "" + pi[i]));
         } else {
        	 tree.Insert("" + pi[i], "" + i, wts.get(pi[i] + "" + i));
         }
		 
	 }

	  return tree;
  }
  
 
  

}


