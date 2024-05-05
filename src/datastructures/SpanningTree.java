package datastructures;

import java.util.HashMap;
import java.util.Map;

import datastructures.SpanningTree.SpanningTreeEdge;

public class SpanningTree {
	
	public boolean Insert(String from, String to, double weight) {
		this.totalWeight += weight;
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
	
	public double TreeWeight() {
		return this.totalWeight;
	}
	
	public HashMap<String, SpanningTreeEdge> GetNodesMap() {
		return this.nodes;
	}
	
	public String toString() {
		String result = "";
		for (Map.Entry<String, SpanningTreeEdge> entry : this.nodes.entrySet()) {
			result += entry.getValue().FromNode + " " + entry.getValue().ToNode + " " + entry.getValue().Weight + "\n";
		}
		return result;
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
	private double totalWeight = 0;
}
