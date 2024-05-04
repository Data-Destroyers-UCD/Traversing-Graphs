// Import the required packages
import java.io.BufferedReader; // Reads text from a character-input stream, buffering characters 
import java.io.BufferedWriter;
import java.io.FileReader; // Convenience class for reading character files
import java.io.FileWriter;
import java.io.IOException; // Convenience class for Input/Output exceptions 
import java.util.ArrayList; // List class in the form of arrays
import java.util.Arrays;	// Class for handling Array Like structures
import java.util.List;		// Class for creating List Structures



// Main class for getting data and executing the algorithms
public class Main {
	// Entry point for java compiler
	public static void main(String[] args) throws IOException {
		String dataPath = "data/V4E4.graph";
		char commentChar = '%';
		ArrayList<String[]> edges = new ArrayList<String[]>();
		

		// Input the data entries into the hash table
		// Read the edges file
		// Columns = [ First Name,Last Name,Email,Password ]
		try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
		    // Skip first line of headers
			String line = "";
		    
		    // var to keep track of list loading time
		    long totalLBTime = 0;
		    // var to keep track of hash map loading time
		    long totalHBTime = 0;
		    
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
	
		// Pass the file to the GraphManager class
		

		// Execution done
		System.out.println("\nExecution completed successfully!");
	}
	
	
}
