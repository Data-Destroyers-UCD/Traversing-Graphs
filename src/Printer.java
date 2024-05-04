import datastructures.Edge;
import datastructures.PositionalList;

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
}
