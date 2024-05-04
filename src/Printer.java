import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import datastructures.Edge;

import datastructures.PositionalList;

import datastructures.SpanningTree;
import datastructures.SpanningTree.SpanningTreeEdge;

public class Printer {
	public static void Print(String[] stringArray) {
		for(int i = 0; i < stringArray.length; ++i) {
			System.out.print(" " + stringArray[i]);
		}
		System.out.println();
	}
	
	public static void Print(int[] intArray) {
		for(int i = 0; i < intArray.length; ++i) {
			System.out.print(" " + intArray[i]);
		}
		System.out.println();
	}
	
	public static void Print(String value) {
		System.out.println(value);
	}
	
	public static void Print(int value) {
		System.out.println(value);
	}
	
	public static void Print(float value) {
		System.out.println(value);
	}
	
	public static void Print(double value) {
		System.out.println(value);
	}

	public static <T> void Print(PositionalList<Edge<T>> mst) {
		for(Edge<T> e : mst) {
			System.out.print(" " + e.getElement());
		}
		System.out.println();
		
	}
	
	public static void Print(SpanningTree tree) {
		HashMap<String, SpanningTreeEdge> nodesMap = tree.GetNodesMap();
		double cost = 0.0;
		for (Map.Entry<String, SpanningTreeEdge> entry : nodesMap.entrySet()) {
            //String key = entry.getKey();
			SpanningTreeEdge value = entry.getValue();
			cost += value.Weight;
            System.out.println(value.FromNode + " -> " + value.ToNode + " = " + value.Weight);
        }
        System.out.println("Spanning Tree Cost: " + cost);

		
	}
	
	
	public static <T> void Print(HashSet<T> set) {
		for(T e : set) {
			System.out.print(" " + e);
		}
		System.out.println();
	}

	public static void Print(HashMap<String, Double> wts) {
		for (java.util.Map.Entry<String, Double> entry : wts.entrySet()) {

		
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
		
	}
	
	
}
