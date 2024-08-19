import java.io.*;
import java.util.ArrayList;

public class Pro5_chenj926 {
	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	public static ArrayList<Graph> G = new ArrayList<Graph>();
	
	public static void main(String[] args) throws IOException {
		// set a loop_stop boolean to stop the loop while needed
		boolean loop_stop = false;
		
		// set tsp
		TSPSolver tsp = new TSPSolver();
		NNSolver nn = new NNSolver(G);
		NNFLSolver fl = new NNFLSolver(G);
		NISolver ni = new NISolver(G);
		tsp.setHasResults(false);
		
		// set a boolean to identify whether the graph is already existed
		boolean file_loaded = false;
		
		
		// Display the menu
		while (!loop_stop) {
			// get user choice
			String user_choice = getInput.getString("\nEnter choice: ");
		
			// user only can input q and g in the 1st time
			// use switch so that if user correctly entered each case, the program
			// will have various respond
			switch (user_choice) {
				case "L":
					// load file
					file_loaded = getInput.loadFile(G);
					// every time we have load new graph, set result to false
					// tell ppl we need to run first!!!
					if (file_loaded != false) {
						tsp.clearHasTrueRunner();;
						nn.clearHasTrueRunner();;
						fl.clearHasTrueRunner();;
						ni.clearHasTrueRunner();;
					}
					
					break;
				case "I":
					if (!getInput.load_true.contains(true)) {
						System.out.println("\nERROR: No graphs have been loaded!\n");
						break;
					}
					// display each file's info
					TSPSolver.displayGraphs(G);
					System.out.println();
					break;
				case "C":
					// if file isn't loaded
					if (!getInput.load_true.contains(true)) {
						System.out.println("\nERROR: No graphs have been loaded!\n");
						break;
					}
					// clear the ArrayList and reset the tsp
					
					G.clear(); // 
					tsp.reset();
					nn.reset();
					fl.reset();
					ni.reset();
					// set file back to unloaded
					file_loaded = false;
					getInput.load_true.clear();
					
					System.out.println("\nAll graphs cleared.\n");
					break;
				case "R":
					
					// if file isn't loaded
					if (!getInput.load_true.contains(true)) {	
						System.out.println("\nERROR: No graphs have been loaded!\n");
						break;
					}
					// update the tsp value 
					nn.keep_value(G);
					fl.keep_value(G);
					ni.keep_value(G);
					// run all the algorithm
					
					TSPSolver.runAll(G, nn, fl, ni);
					System.out.println();
					break;
				case "D":
					// if file isn't loaded or runned
					if (!getInput.load_true.contains(true) || G.isEmpty() ||
							!nn.getHasTrueRunned().contains(true) && !fl.getHasTrueRunned().contains(true) && !ni.getHasTrueRunned().contains(true)) {
						System.out.println("\nERROR: Results do not exist for all algorithms!\n");
						break;
					}
					// print the info
					// NN
					System.out.println();
					nn.printAll();
					System.out.println();
					nn.printStats();
					System.out.println();
					System.out.printf("Success rate: %.1f%%\n", nn.successRate());
					System.out.println();
					
					// FL
					System.out.println();
					fl.printAll();
					System.out.println();
					fl.printStats();
					System.out.println();
					System.out.printf("Success rate: %.1f%%\n", fl.successRate());
					System.out.println();
					
					// NI
					System.out.println();
					ni.printAll();
					System.out.println();
					ni.printStats();
					System.out.println();
					System.out.printf("Success rate: %.1f%%\n", ni.successRate());
					System.out.println();
					break;
				case "X":
					if (!getInput.load_true.contains(true) || G.isEmpty() ||
							!nn.getHasTrueRunned().contains(true) && !fl.getHasTrueRunned().contains(true) && !ni.getHasTrueRunned().contains(true)) {
						System.out.println("\nERROR: Results do not exist for all algorithms!\n");
						break;
					}
					TSPSolver.compare(nn, fl, ni);
					
					break;
				case "Q":
					// quit the program
					System.out.println("\nCiao!");
					loop_stop = true;
					break;
			}
			// stop the loop
			if (loop_stop == true) {
				break;
			}
		}
	}
	
	public static boolean createGraph(Graph G, int num, Node node) {
		// get user entered city number 
		int city_num = num;
		// after we get the number of cities, we update the node numbers and initialize the graph
		G.setN(city_num);
		G.init(city_num);
		// set a bool to be false when the user enter city and or cord already exist
		boolean city_exist = true;
		// add node to graph
		G.addNode(node);
		
		return city_exist;
	}	

	public static void addArcs(Graph G, int city_1, int city_2) {
		// link them up
		G.addArc(city_1 - 1, city_2 - 1);
		
		
	}
	
}
