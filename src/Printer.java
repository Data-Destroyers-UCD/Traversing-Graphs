// Import required java utilities
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
// Import the implemented data structures
import datastructures.Edge;
import datastructures.PositionalList;
import datastructures.SpanningTree;
import datastructures.SpanningTree.SpanningTreeEdge;

// Printer class for printing output to console
public class Printer {
	
	// Method to print new line
	public static void Println() {
		System.out.println();
	}
	
	// Method to print string
	public static void Print(String value) {
		System.out.print(value);
	}
	
	// Method to print integer with new line
	public static void Println(int value) {
		System.out.println(value);
	}
	
	// Method to print long 
	public static void Print(long value) {
		System.out.print(value);
	}
	
	// Method to print float 
	public static void Println(float value) {
		System.out.println(value);
	}
	
	// Method to print double
	public static void Println(double value) {
		System.out.println(value);
	}
	
	// Print String array with a new line
	public static void Println(String[] stringArray) {
		for(int i = 0; i < stringArray.length; ++i) {
			Printer.Print(" " + stringArray[i]);
		}
		Printer.Println();
	}
	
	// Method to print an int array with new line
	public static void Print(int[] intArray) {
		for(int i = 0; i < intArray.length; ++i) {
			Printer.Print(" " + intArray[i]);
		}
		Printer.Println();
	}
	
	// Method to print the edge of spanning tree as positional list with new line
	public static <T> void Println(PositionalList<Edge<T>> mst) {
		for(Edge<T> e : mst) {
			Printer.Print(" " + e.getElement());
		}
		Printer.Println();
	}
	
	// Method to print the spanning tree
	// If the printTree is set to true it will print the entire tree from node to node
	public static void Print(SpanningTree tree, boolean printTree) {
		// Get the tree nodes map
		HashMap<String, SpanningTreeEdge> nodesMap = tree.GetNodesMap();
		if(printTree)
			// Loop through each
			for (Map.Entry<String, SpanningTreeEdge> entry : nodesMap.entrySet()) {
				// Get the edge data value
				SpanningTreeEdge value = entry.getValue();	
				Printer.Print("\n"+ value.FromNode + " -> " + value.ToNode + " = " + value.Weight);
	        }
		// Print the total tree cost
		Printer.Print("\nSpanning Tree Cost: " + tree.TreeWeight());	
	}
	
	// Method to print the Hash set elements
	public static <T> void Println(HashSet<T> set) {
		for(T e : set) {
			Printer.Print(" " + e);
		}
		System.out.println();
	}

	// Method  to print the hash map elements
	public static void Print(HashMap<String, Double> wts) {
		for (Map.Entry<String, Double> entry : wts.entrySet()) {
            Printer.Print(entry.getKey() + " " + entry.getValue());
        }	
	}
}
