package datastructures;

import java.util.HashMap;

public class SpanningTree {
	
	public boolean Insert(String from, String to, double weight) {
		if(this.nodes.containsKey("" + from + to) || this.nodes.containsKey("" + to + from)) {
			this.nodes.replace("" + from + to,  new SpanningTreeEdge(from, to, weight));
			return false;
		}

		this.nodes.put("" + from.toString() + to.toString(), new SpanningTreeEdge(from, to, weight));
		return true;
	}
	
	public int Size() {
		return this.nodes.size();
	}
	
	public HashMap<String, SpanningTreeEdge> GetNodesMap() {
		return this.nodes;
	}
	
	public class SpanningTreeEdge {
		public String FromNode;
		public String ToNode;
		public double Weight;
		public SpanningTreeEdge(String from, String to, double weight) {
			this.FromNode = from;
			this.ToNode = to;
			this.Weight = weight;
		}
	}
	
	private HashMap<String, SpanningTreeEdge> nodes = new HashMap<>();
}
