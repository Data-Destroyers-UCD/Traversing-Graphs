package datastructures;

import java.util.HashMap;

public class SpanningTree {
	
	public boolean Insert(int from, int to, double weight) {
		if(this.nodes.containsKey("" + from + to)) {
			return false;
		}
		
		this.nodes.put("" + from + to, new SpanningTreeNode(from, to, weight));
		return true;
	}
	
	public HashMap<String, SpanningTreeNode> GetNodesMap() {
		return this.nodes;
	}
	
	public class SpanningTreeNode {
		public int FromNode;
		public int ToNode;
		public double Weight;
		public SpanningTreeNode(int from, int to, double weight) {
			this.FromNode = from;
			this.ToNode = to;
			this.Weight = weight;
		}
	}
	
	private HashMap<String, SpanningTreeNode> nodes = new HashMap<>();
}
