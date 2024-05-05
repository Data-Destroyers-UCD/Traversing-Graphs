// Import the required packages
import java.io.BufferedReader; // Reads text from a character-input stream, buffering characters 
import java.io.BufferedWriter; // Writes text to a character-output stream, buffering characters 
import java.io.File; // Class for handling files
import java.io.FileReader; // Convenience class for reading character files
import java.io.FileWriter; // Class for wrting files
import java.io.IOException; // Convenience class for Input/Output exceptions 
import java.util.ArrayList; // List class in the form of arrays

// Import Spanning Tree for writing to file
import datastructures.SpanningTree;



// Main class for getting data and executing the algorithms
public class Main {
	// Entry point for java compiler
	public static void main(String[] args) throws IOException {
		//Num of Nodes to be used
		String[] nodeCounts = {"10", "50", "100", "500", "1000"};
		// Edge densities to be used
		String[] edgeDensities = {"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"};
		// 2D matrix for storing prim's analysis
		long[][] primHeatmap = new long[nodeCounts.length][edgeDensities.length];
		// 2D matrix for storing kruskal's analysis
		long[][] kruskalHeatmap = new long[nodeCounts.length][edgeDensities.length];
		// Output location for storing Kruskal MST
		String kruskalOutputPath = "output/KruskalMST/";
		// Output location for storing Prim MST
		String primOutputPath = "output/PrimMST/";
		
		// Loop through all the node counts
		for(int i = 0; i < nodeCounts.length; i++) {
			// Loop through all the edge densities
			for(int j = 0; j < edgeDensities.length; j++) {
				// Output file name
				String dataPath = "data/V" + nodeCounts[i] + "ED" + edgeDensities[j] + ".graph";
				
				Printer.Print("\n\n======Path:\n" + dataPath);
				
				// Character used for commenting in the graph files
				char commentChar = '%';
				// Array for storing all the edges of the graph
				ArrayList<String[]> edges = new ArrayList<String[]>();
				

				// Read the edges file
				try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
				    // File line store var
					String line = "";
				    
				    // Get each line in the file
				    while ((line = br.readLine()) != null) {
				    	// if the line starts with comment char skip
				    	if(line.charAt(0) == commentChar) continue;
				    	
				    	// Split line by white space
				        String[] values = line.split(" ");
				        
				        // Append the edge info ["FromNode", "ToNode", "Weight"]
				        edges.add(values);				
				    }		       
			    }
				
				
				// Create graph manager instance for current set of edges
				GraphManager graphManager = new GraphManager(edges); 
				// Initialise the spanning tree to null
				SpanningTree tree = null;
				
				// var to keep track of time to create MST
				long startTime = System.currentTimeMillis(); // Start the timer
				Printer.Print("\n\nTesting Kruskal");
				// Time kruskal and put the time in the heatmap
				tree = graphManager.TestKruskal();
				// Print Tree cost
				Printer.Print(tree, false);
				long opTime = System.currentTimeMillis() - startTime; // calculate the time
				// Add the time to the matrix
				kruskalHeatmap[i][j] = opTime;
				// Write the Kruskal MST to output folder
				Main.WriteMSTToPath(tree, kruskalOutputPath, (new File(dataPath)).getName());
				
				// var to keep track of time to create MST
				startTime = System.currentTimeMillis(); // Start the timer
				Printer.Print("\n\nTesting Prims");
				// Time Prims and put the time in the heatmap
				tree = graphManager.TestPrims();
				// Print Tree cost
				Printer.Print(tree, false);
				opTime = System.currentTimeMillis() - startTime; // calculate the time
				// Add the time to the matrix
				primHeatmap[i][j] = opTime;
				// Write the Prim MST to output folder
				Main.WriteMSTToPath(tree, primOutputPath, (new File(dataPath)).getName());
			}
		}
		
		// Print the Kruskal heatmap matrix
		Printer.Print("\n---------------------\nKruskal Heatmap\n");
		Printer.Print("\nNodeCount vs Edge Densities\n");
		for(int j = 0; j < edgeDensities.length; j++) {
			Printer.Print("\t" + edgeDensities[j]);
		}	
		Printer.Print("\n");
		for(int i = 0; i < nodeCounts.length; i++) {
			Printer.Print(nodeCounts[i]);
			for(int j = 0; j < edgeDensities.length; j++) {
				
				Printer.Print("\t" + kruskalHeatmap[i][j]);
				
			}
			Printer.Print("\n");
		}
		
		
		// Print the Prim heatmap matrix
		Printer.Print("\n---------------------\nPrim Heatmap\n");
		Printer.Print("\nNodeCount vs Edge Densities\n");
		for(int j = 0; j < edgeDensities.length; j++) {
			Printer.Print("\t" + edgeDensities[j]);
		}
		Printer.Print("\n");
		for(int i = 0; i < nodeCounts.length; i++) {
			Printer.Print(nodeCounts[i]);
			for(int j = 0; j < edgeDensities.length; j++) {
				
				Printer.Print("\t" + primHeatmap[i][j]);
				
			}
			Printer.Print("\n");
		}
		
		// Execution done
		Printer.Print("\nExecution completed successfully!");
	}
	
	// Function to write the spanning tree to a file
	public static void WriteMSTToPath(SpanningTree tree, String path, String inFileName) throws IOException {
		// Initialise the output writer with the file path
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(path + "MST" + inFileName));
		// Write the string format for tree
		outputWriter.write(tree.toString());
		// Flush the ouput writer
		outputWriter.flush();  
		// Close the output writer
		outputWriter.close();
	}
	
	
}
