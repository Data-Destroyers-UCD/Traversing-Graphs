package datastructures;

import java.util.HashMap;

public class SpanningTree {
	
	public boolean Insert(String from, String to, double weight) {
		if(this.nodes.containsKey("" + from + to)) {
			return false;
		}
		
		this.nodes.put("" + from.toString() + to.toString(), new SpanningTreeNode(from, to, weight));
		return true;
	}
	
	public int Size() {
		return this.nodes.size();
	}
	
	public HashMap<String, SpanningTreeNode> GetNodesMap() {
		return this.nodes;
	}
	
	public class SpanningTreeNode {
		public String FromNode;
		public String ToNode;
		public double Weight;
		public SpanningTreeNode(String from, String to, double weight) {
			this.FromNode = from;
			this.ToNode = to;
			this.Weight = weight;
		}
	}
	
	private HashMap<String, SpanningTreeNode> nodes = new HashMap<>();
}
