// Import the required packages
import java.io.BufferedReader; // Reads text from a character-input stream, buffering characters 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader; // Convenience class for reading character files
import java.io.FileWriter;
import java.io.IOException; // Convenience class for Input/Output exceptions 
import java.util.ArrayList; // List class in the form of arrays
import java.util.Arrays;	// Class for handling Array Like structures
import java.util.List;		// Class for creating List Structures

import datastructures.SpanningTree;



// Main class for getting data and executing the algorithms
public class Main {
	// Entry point for java compiler
	public static void main(String[] args) throws IOException {
		String[] nodeCounts = {"10", "50", "100", "500", "1000"};
		String[] edgeDensities = {"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"};
		
		long[][] primHeatmap = new long[nodeCounts.length][edgeDensities.length];
		long[][] kruskalHeatmap = new long[nodeCounts.length][edgeDensities.length];
		
		String kruskalOutputPath = "output/KruskalMST/";
		String primOutputPath = "output/PrimMST/";
		
		for(int i = 0; i < nodeCounts.length; i++) {
			for(int j = 0; j < edgeDensities.length; j++) {
				String dataPath = "data/V" + nodeCounts[i] + "ED" + edgeDensities[j] + ".graph";
				Printer.Print("\n\n======Path:\n" + dataPath);
				char commentChar = '%';
				ArrayList<String[]> edges = new ArrayList<String[]>();
				

				// Input the data entries into the hash table
				// Read the edges file
				try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
				    // Skip first line of headers
					String line = "";
				    
				    
				    
				    // Get each line in the file
				    while ((line = br.readLine()) != null) {
				    	if(line.charAt(0) == commentChar) continue;
				    	
				    	// Split line by ,
				        String[] values = line.split(" ");
			
				        edges.add(values);
				        
				        //Printer.Print(values.length);
				
				    }
							       
			    }
				
				
				
				GraphManager graphManager = new GraphManager(edges); 
				SpanningTree tree = null;
				
				// var to keep track to create MST
				long startTime = System.currentTimeMillis(); // Start the timer
	
				Printer.Print("\n\nTesting Kruskal");
				// Time kruskal and put the time in the heatmap
				tree = graphManager.TestKruskal();
				Printer.Print(tree, false);
				long opTime = System.currentTimeMillis() - startTime; // calculate the time
				kruskalHeatmap[i][j] = opTime;
		
				Main.WriteMSTToPath(tree, kruskalOutputPath, (new File(dataPath)).getName());
				
				startTime = System.currentTimeMillis(); // Start the timer
				Printer.Print("\n\nTesting Prims");
				// Time Prims and put the time in the heatmap
				tree = graphManager.TestPrims();
				Printer.Print(tree, false);
				opTime = System.currentTimeMillis() - startTime; // calculate the time
				primHeatmap[i][j] = opTime;
				
				Main.WriteMSTToPath(tree, primOutputPath, (new File(dataPath)).getName());
				
			}
		}
		
		// Print the heatmaps
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
		
		
		// Print the heatmaps
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
	
	public static void WriteMSTToPath(SpanningTree tree, String path, String inFileName) throws IOException {
		//Printer.Print(tree.toString());
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(path + "MST" + inFileName));
		outputWriter.write(tree.toString());
		
		outputWriter.flush();  
		outputWriter.close();
	}
	
	
}
