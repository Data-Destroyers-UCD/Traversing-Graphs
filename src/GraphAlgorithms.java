

import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import datastructures.*;

/**
 * A collection of graph algorithms.
 */
public class GraphAlgorithms {

  /**
   * Performs depth-first search of the unknown portion of Graph g starting at Vertex u.
   *
   * @param g Graph instance
   * @param u Vertex of graph g that will be the source of the search
   * @param known is a set of previously discovered vertices
   * @param forest is a map from nonroot vertex to its discovery edge in DFS forest
   *
   * As an outcome, this method adds newly discovered vertices (including u) to the known set,
   * and adds discovery graph edges to the forest.
   */
  public static <V,E> void DFS(Graph<V,E> g, Vertex<V> u,
                    Set<Vertex<V>> known, Map<Vertex<V>,Edge<E>> forest) {
    known.add(u);                              // u has been discovered
    for (Edge<E> e : g.outgoingEdges(u)) {     // for every outgoing edge from u
      Vertex<V> v = g.opposite(u, e);
      if (!known.contains(v)) {
        forest.put(v, e);                      // e is the tree edge that discovered v
        DFS(g, v, known, forest);              // recursively explore from v
      }
    }
  }

  /**
   * Returns an ordered list of edges comprising the directed path from u to v.
   * If v is unreachable from u, or if u equals v, an empty path is returned.
   *
   * @param g Graph instance
   * @param u Vertex beginning the path
   * @param v Vertex ending the path
   * @param forest must be a map that resulting from a previous call to DFS started at u.
   */
  public static <V,E> PositionalList<Edge<E>>
  constructPath(Graph<V,E> g, Vertex<V> u, Vertex<V> v,
                Map<Vertex<V>,Edge<E>> forest) {
    PositionalList<Edge<E>> path = new LinkedPositionalList<>();
    if (forest.get(v) != null) {           // v was discovered during the search
      Vertex<V> walk = v;                  // we construct the path from back to front
      while (walk != u) {
        Edge<E> edge = forest.get(walk);
        path.addFirst(edge);               // add edge to *front* of path
        walk = g.opposite(walk, edge);     // repeat with opposite endpoint
      }
    }
    return path;
  }

  /**
   * Performs DFS for the entire graph and returns the DFS forest as a map.
   *
   * @return map such that each nonroot vertex v is mapped to its discovery edge
   * (vertices that are roots of a DFS trees in the forest are not included in the map).
   */
  public static <V,E> Map<Vertex<V>,Edge<E>> DFSComplete(Graph<V,E> g) {
    Set<Vertex<V>> known = new HashSet<>();
    Map<Vertex<V>,Edge<E>> forest = new ProbeHashMap<>();
    for (Vertex<V> u : g.vertices())
      if (!known.contains(u))
        DFS(g, u, known, forest);            // (re)start the DFS process at u
    return forest;
  }

  /**
   * Performs breadth-first search of the undiscovered portion of Graph g starting at Vertex s.
   *
   * @param g Graph instance
   * @param s Vertex of graph g that will be the source of the search
   * @param known is a set of previously discovered vertices
   * @param forest is a map from nonroot vertex to its discovery edge in DFS forest
   *
   * As an outcome, this method adds newly discovered vertices (including s) to the known set,
   * and adds discovery graph edges to the forest.
   */
  public static <V,E> void BFS(Graph<V,E> g, Vertex<V> s,
                    Set<Vertex<V>> known, Map<Vertex<V>,Edge<E>> forest) {
    PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
    known.add(s);
    level.addLast(s);                         // first level includes only s
    while (!level.isEmpty()) {
      PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
      for (Vertex<V> u : level)
        for (Edge<E> e : g.outgoingEdges(u)) {
          Vertex<V> v = g.opposite(u, e);
          if (!known.contains(v)) {
            known.add(v);
            forest.put(v, e);                 // e is the tree edge that discovered v
            nextLevel.addLast(v);             // v will be further considered in next pass
          }
        }
      level = nextLevel;                      // relabel 'next' level to become the current
    }
  }

  /**
   * Performs BFS for the entire graph and returns the BFS forest as a map.
   *
   * @return map such that each nonroot vertex v is mapped to its discovery edge
   * (vertices that are roots of a BFS trees in the forest are not included in the map).
   */
  public static <V,E> Map<Vertex<V>,Edge<E>> BFSComplete(Graph<V,E> g) {
    Map<Vertex<V>,Edge<E>> forest = new ProbeHashMap<>();
    Set<Vertex<V>> known = new HashSet<>();
    for (Vertex<V> u : g.vertices())
      if (!known.contains(u))
        BFS(g, u, known, forest);
    return forest;
  }

  /**
   * Converts graph g into its transitive closure.
   * This uses the Floyd-Warshall algorithm.
   */
  @SuppressWarnings({"unchecked"})
  public static <V,E> void transitiveClosure(Graph<V,E> g) {
    for (Vertex<V> k : g.vertices())
      for (Vertex<V> i : g.vertices())
        // verify that edge (i,k) exists in the partial closure
        if (i != k && g.getEdge(i,k) != null)
          for (Vertex<V> j : g.vertices())
            // verify that edge (k,j) exists in the partial closure
            if (i != j && j != k && g.getEdge(k,j) != null)
              // if (i,j) not yet included, add it to the closure
              if (g.getEdge(i,j) == null)
                g.insertEdge(i, j, null);
  }


  /**
   * Returns a list of verticies of directed acyclic graph g in topological order.
   * If graph g has a cycle, the result will be incomplete.
   */
  public static <V,E> PositionalList<Vertex<V>> topologicalSort(Graph<V,E> g) {
    // list of vertices placed in topological order
    PositionalList<Vertex<V>> topo = new LinkedPositionalList<>();
    // container of vertices that have no remaining constraints
    Stack<Vertex<V>> ready = new LinkedStack<>();
    // map keeping track of remaining in-degree for each vertex
    Map<Vertex<V>, Integer> inCount = new ProbeHashMap<>();
    for (Vertex<V> u : g.vertices()) {
      inCount.put(u, g.inDegree(u));           // initialize with actual in-degree
      if (inCount.get(u) == 0)                 // if u has no incoming edges,
        ready.push(u);                         // it is free of constraints
    }
    while (!ready.isEmpty()) {
      Vertex<V> u = ready.pop();
      topo.addLast(u);
      for (Edge<E> e : g.outgoingEdges(u)) {   // consider all outgoing neighbors of u
        Vertex<V> v = g.opposite(u, e);
        inCount.put(v, inCount.get(v) - 1);    // v has one less constraint without u
        if (inCount.get(v) == 0)
          ready.push(v);
      }
    }
    return topo;
  }

  /**
   * Computes shortest-path distances from src vertex to all reachable vertices of g.
   *
   * This implementation uses Dijkstra's algorithm.
   *
   * The edge's element is assumed to be its integral weight.
   */
  public static <V> Map<Vertex<V>, Double>
  shortestPathLengths(Graph<V,Double> g, Vertex<V> src) {
    // d.get(v) is upper bound on distance from src to v
    Map<Vertex<V>, Double> d = new ProbeHashMap<>();
    // map reachable v to its d value
    Map<Vertex<V>, Double> cloud = new ProbeHashMap<>();
    // pq will have vertices as elements, with d.get(v) as key
    AdaptablePriorityQueue<Double, Vertex<V>> pq;
    pq = new HeapAdaptablePriorityQueue<>();
    // maps from vertex to its pq locator
    Map<Vertex<V>, Entry<Double,Vertex<V>>> pqTokens;
    pqTokens = new ProbeHashMap<>();

    // for each vertex v of the graph, add an entry to the priority queue, with
    // the source having distance 0 and all others having infinite distance
    for (Vertex<V> v : g.vertices()) {
      if (v == src)
        d.put(v,0.0);
      else
        d.put(v, Double.MAX_VALUE);
      pqTokens.put(v, pq.insert(d.get(v), v));       // save entry for future updates
    }
    // now begin adding reachable vertices to the cloud
    while (!pq.isEmpty()) {
      Entry<Double, Vertex<V>> entry = pq.removeMin();
      double key = entry.getKey();
      Vertex<V> u = entry.getValue();
      cloud.put(u, key);                             // this is actual distance to u
      pqTokens.remove(u);                            // u is no longer in pq
      for (Edge<Double> e : g.outgoingEdges(u)) {
        Vertex<V> v = g.opposite(u,e);
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

  /**
   * Reconstructs a shortest-path tree rooted at vertex s, given distance map d.
   * The tree is represented as a map from each reachable vertex v (other than s)
   * to the edge e = (u,v) that is used to reach v from its parent u in the tree.
   */
  public static <V> Map<Vertex<V>,Edge<Double>>
  spTree(Graph<V,Double> g, Vertex<V> s, Map<Vertex<V>,Double> d) {
    Map<Vertex<V>, Edge<Double>> tree = new ProbeHashMap<>();
    for (Vertex<V> v : d.keySet())
      if (v != s)
        for (Edge<Double> e : g.incomingEdges(v)) {   // consider INCOMING edges
          Vertex<V> u = g.opposite(v, e);
          double wgt = e.getElement();
          if (d.get(v) == d.get(u) + wgt)
            tree.put(v, e);                            // edge is is used to reach v
        }
    return tree;
  }

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
  
  public static <V> SpanningTree BoruvkaMST(Graph<V,Double> g) {
	  SpanningTree tree = new SpanningTree();
	  // T <- V {just the vertices of G}
	  Graph<String, Double> t = new AdjacencyMapGraph<String, Double>(false);
	  for (Vertex<V> v : g.vertices()) {
		  t.insertVertex(v.getElement().toString());  
      }	
	  
	  while(t.numEdges() < g.numVertices() - 1) {
		  
	  }
	 
	  
	  
	  return tree;
  }
  
  
  public static <V> SpanningTree PrimMST(Graph<V,Double> g) {
	  SpanningTree tree = new SpanningTree();
	  
	  AdaptablePriorityQueue<Double, Vertex<V>> pq = new HeapAdaptablePriorityQueue<>();
	  
	  ProbeHashMap<Vertex<V>, Double> keys = new ProbeHashMap<Vertex<V>, Double>();
	  	  
	  ProbeHashMap<Vertex<V>, Double> presence = new ProbeHashMap<Vertex<V>, Double>();
	  
	  Map<Vertex<V>, Entry<Double,Vertex<V>>> pqTokens =  new ProbeHashMap<>();

	  for (Vertex<V> v : g.vertices()) {
		  keys.put(v, Double.MAX_VALUE);

		  pqTokens.put(v, pq.insert(keys.get(v), v)); 
      }	
	  
	  keys.put(g.vertices().iterator().next(), 0.0);
	  
	  
	 while(!pq.isEmpty()) {
		 Vertex<V> v = pq.removeMin().getValue();
		 pqTokens.remove(v); 
		 double min = Double.MAX_VALUE;
		 Vertex<V> niceVertex = null;
		 for (Edge<Double> e : g.outgoingEdges(v)) {
			 Vertex<V> w = g.opposite(v, e);
			 if(presence.get(w) == null) {
				 double wgt = e.getElement();
		          if (keys.get(v) + wgt < keys.get(w)) {              // better path to v?
		        	keys.put(w, keys.get(v) + wgt);                   // update the distance
		            pq.replaceKey(pqTokens.get(w), keys.get(v));   // update the pq entry
		            
		          }
			 }
			 
		 }
		
		 if(niceVertex != null)
			 tree.Insert(v.getElement().toString(), niceVertex.getElement().toString(), min);

		 
	 }
	  
	 
	  
	  return tree;
  }
  

}


