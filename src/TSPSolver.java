import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TSPSolver {
	private ArrayList <int [] > solnPath ; // ArrayList *or* array of solution paths
	private double [] solnCost ; // ArrayList *or* array of solution costs
	private double [] compTime ; // ArrayList *or* array of computation times
	private boolean [] solnFound ; // ArrayList *or* array of T/F solns found
	private boolean resultsExist ; // whether or not results exist
	
	// new var for Pro5
	private String name; // name of this solver
	
	private long elapsedTime;
	private ArrayList<String> route;
	private ArrayList<Boolean> has_true_runned;
	
	// constructors
	public TSPSolver () { 
		this.solnPath = new ArrayList<int[]>();
		this.solnCost = new double[0];
		this.compTime = new double[0];
		this.solnFound = new boolean[0];
		this.resultsExist = false;
		this.elapsedTime = 0;
		this.route = new ArrayList<String>();
		this.name = "n/a";
		this.has_true_runned  = new ArrayList<Boolean>();
		
	}
	
	public TSPSolver ( ArrayList < Graph > G ) { 
		this.init(G);
	}

	// getters
	public int [] getSolnPath ( int i ) {
		return this.solnPath.get(i);
	}
	public double getSolnCost ( int i ) {
		return this.solnCost[i];
	}
	public double getCompTime ( int i ) {
		return this.compTime[i];
	}
	public boolean getSolnFound ( int i ) {
		return this.solnFound[i];
	}
	public boolean hasResults () {
		return this.resultsExist;
	}
	// getter for my self created variables
	public long getElapsed () {
		return this.elapsedTime;
	}
	public String getRoute (int i) {
		return this.route.get(i);
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<Boolean> getHasTrueRunned () {
		return this.has_true_runned;
	}

	// setters
	public void setSolnPath ( int i , int [] solnPath ) {
		this.solnPath.add(i, solnPath); 
	}
	public void setSolnCost ( int i , double solnCost ) {
		this.solnCost[i] = solnCost;
	}
	public void setCompTime ( int i , double compTime ) {
		this.compTime[i] = compTime;
	}
	public void setSolnFound ( int i , boolean solnFound ) {
		this.solnFound[i] = solnFound;
	}
	public void setHasResults ( boolean b ) {
		this.resultsExist = b;
	}
	// setter for my self created variables
	public void setElapsed (long start, long end) {
		this.elapsedTime = end - start;
	}
	public void setRoute (int i, String route) {
		this.route.add(i, route);
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setHasTrueRunned (boolean b) {
		this.has_true_runned.add(b);
	}
	
	public void clearHasTrueRunner( ) {
		this.has_true_runned.clear();
	}

	// initialize variables and arrays
	public void init ( ArrayList < Graph > G ) {
		this.solnPath = new ArrayList<int[]>(G.size());
		this.solnFound = new boolean[G.size()];
		this.solnCost = new double[G.size()];
		this.compTime  = new double[G.size()];
		this.resultsExist = true;
		
		
		// initialize for my self created variables
		this.elapsedTime = 0;
		this.route = new ArrayList<String>(G.size());
		this.has_true_runned  = new ArrayList<Boolean>();
		
	}
	
	// this function is for update the old tsp value into new tsp
	// when user loaded file again and runned them
	// this avoids old tsp value being lost
	public void keep_value(ArrayList < Graph > G) {
		// previous tsp variables size
		int previous_size = this.solnPath.size();
		
		// set temp variable to hold old tsp values
		ArrayList<int[]> temp_solnPath = new ArrayList<int[]>(previous_size);
		double[] temp_solnCost = new double[previous_size];
		double[] temp_compTime = new double[previous_size];
		boolean[] temp_solnFound = new boolean[previous_size];
		boolean temp_resultsExist = false;
		long temp_elapsedTime = 0;
		ArrayList<String> temp_route = new ArrayList<String>(previous_size);
		//ArrayList<Boolean> temp_has_true_runned = new ArrayList<Boolean>();
		
		
		// let temp variables to hold the old tsp values
		temp_solnPath.addAll(this.solnPath);
		temp_solnCost = this.solnCost;
		temp_compTime = this.compTime;
		temp_solnFound = this.solnFound;
		temp_elapsedTime = this.elapsedTime;
		temp_route.addAll(this.route);
		//temp_has_true_runned.addAll(has_true_runned);
		
		
		// init tsp 
		this.init(G);
		
		// transfer the old tsp value into new tsp
		this.solnPath.addAll(temp_solnPath);
		this.solnFound = Arrays.copyOf(temp_solnFound, Math.max(this.solnFound.length, temp_solnFound.length));
		this.solnCost = Arrays.copyOf(temp_solnCost, Math.max(this.solnCost.length, temp_solnCost.length));
		this.compTime  = Arrays.copyOf(temp_compTime, Math.max(this.compTime.length, temp_compTime.length));		
		this.resultsExist = temp_resultsExist;
		this.elapsedTime = temp_elapsedTime;
		this.route.addAll(temp_route);
		//this.has_true_runned = temp_has_true_runned;
		
	}
	
	// reset variables and arrays
	public void reset () {
		this.solnPath = new ArrayList<int[]>();
		this.solnCost = new double[0];
		this.compTime = new double[0];
		this.solnFound = new boolean[0];
		this.resultsExist = true;
		this.elapsedTime = 0;
		this.route = new ArrayList<String>();
		this.has_true_runned.clear();
	
	}
	
	public static void runAll(ArrayList<Graph> G, NNSolver NN, NNFLSolver FL, NISolver NI) {
		boolean suppressOutput = true;
		
		
		// NN
		NN.initNN(G);
		for (int i = 0 ; i < G.size(); i++) {
			NN.run(G, i, suppressOutput);
			if (NN.hasResults() == false) {
				System.out.printf("\nERROR: NN did not find a TSP route for Graph %d!", i+1);
			}
		}
		System.out.println("\nNearest neighbor algorithm done.");
		
		// FL
		FL.initFL(G);
		for (int i = 0 ; i < G.size(); i++) {
			FL.run(G, i, suppressOutput);
			if (FL.hasResults() == false) {
				System.out.printf("\nERROR: NN-FL did not find a TSP route for Graph %d!", i+1);
			}
		}
		System.out.println("\nNearest neighbor first-last algorithm done.");
		
		// NI
		NI.initNI(G);
		for (int i = 0 ; i < G.size(); i++) {
			NI.run(G, i, suppressOutput);
			if (NI.hasResults() == false) {
				System.out.printf("\nERROR: NI did not find a TSP route for Graph %d!", i+1);
			}
		}
		System.out.println("\nNode insertion algorithm done.");
	}


	// run nearest neighbor on Graph i
	public void run ( ArrayList < Graph > G , int i , boolean suppressOutput) {
		// create an arrayList visited to store all node visited
		ArrayList<Integer> visited = new ArrayList<Integer>();
		visited.add(1);
		int[] next_node = new int[2];
		
		// allocate space for path
		int[] path = new int[G.get(i).getN()+1];
				
		// string to hold the path
		String visited_path = "";
		this.resultsExist = true; 
				
		// init vars for later use
		double cost = 0.0;
		
		
		
		long start = System.currentTimeMillis ();
		// a for loop iterating every arc to find the path connect together
		for (int j = 0 ; j < G.get(i).getN(); j++) {
			// get the nearest arc of the previous arc
			// if no path, [1] return -1
			next_node = this.nextNode(G.get(i), visited); // next node [0]  = nextnode, [1] its insertion position
			
			// if there is no connected arc with the previous arc, we stop run
			if (next_node[0] == -1) {
				// set the result to false
				
				this.setHasResults(false);
				break;
			}
			
			// add the node into visited
			visited.add(next_node[1], next_node[0]+1);

		}
		if (next_node[0] != -1) { 
			for (int k = 0 ; k < path.length ; k++) {
					path[k] = visited.get(k);
					
					if (k != path.length-1) {
						visited_path += visited.get(k);
						visited_path += "-";
						
					}
					if (k <= path.length-2) {
						cost += G.get(i).getCost(visited.get(k)-1, visited.get(k+1)-1);
					}
					else {
						visited_path += visited.get(k);
					}
			}
		}
	
		// end the time the set elapsed time
		long end = System.currentTimeMillis();
		setElapsed(start, end);
		
		// is did not find a path
		if (this.hasResults() == false) {
			// set 0 in arrary[i]
			this.setSolnFound(i, false);
			this.setSolnCost(i, 0);
			this.setCompTime(i, 0);
			
			int[] temp = {0};
			this.setSolnPath(i, temp);
			this.setRoute(i, "");
			this.setHasResults(false);
			//this.has_true_runned.add(false);
			this.setHasTrueRunned(false);
			suppressOutput = false;
			
		}
		// if found a path
		else {
			// update the vars
			this.setSolnFound(i, true);
			this.setSolnCost(i, cost);
			this.setCompTime(i, (double)this.elapsedTime);			
			this.setSolnPath(i, path);
			this.setRoute(i, visited_path);
			this.setHasResults(true);
			this.has_true_runned.add(true);
			this.setHasTrueRunned(false);
			suppressOutput = true;
			
		}
		
	}
	
	// find next node ( return 2D array with [0]= next node , [1]= insertion position )
	public int [] nextNode ( Graph G , ArrayList < Integer > visited ) {
		
		//for (int i = 0 ; i < G.getN() ; i++) {
			// 1st start with 1
		int node_to_find_ngh;
		int[] new_node_info = new int[2];
		
		node_to_find_ngh = visited.get(visited.size()-1); // size=1 - arc 1
		
		int next_node;
		next_node = this.nearestNeighbor(G, visited, node_to_find_ngh); 
		
		new_node_info[0] = next_node-1;
		new_node_info[1] = visited.size();
		return new_node_info;
		//}
	}
	
	// find node k’s nearest unvisited neighbor
	public int nearestNeighbor ( Graph G , ArrayList < Integer > visited , int k ) {
		int current_arc = k;
		int nearest = 0;
		double shortest = Double.MAX_VALUE;

		// if it is NI_nearest 
		if (this.getName() == "NI" && visited.indexOf(k) != 0 && visited.indexOf(k) != visited.size()-1) {
			for (int i = 0 ; i < G.getN(); i++) {	
				double temp_cost = G.getCost(current_arc-1, i); 
				// pre condition to check validity, if no valid do not check this arc
				if (shortest > temp_cost && current_arc-1 != i && !visited.contains(i+1) 
						&& G.getArc(current_arc-1, i) != false 
						&& G.getArc(i, visited.get(visited.indexOf(k)+1)-1 )) {
							shortest = temp_cost;
							nearest = i+1;
				}
			}
		}
		else {
			for (int i = 0 ; i < G.getN(); i++) {	
				double temp_cost = G.getCost(current_arc-1, i); 
				// pre condition to check validity, if no valid do not check this arc
				if (shortest > temp_cost && current_arc-1 != i && !visited.contains(i+1) 
						&& G.getArc(current_arc-1, i) != false) {
							shortest = temp_cost;
							nearest = i+1;
				}
			}
		}
		return nearest;
	}
	
	
	// print results for a single graph
	public void printSingleResult ( int i , boolean rowOnly ) {
		System.out.printf("%3d", i+1);	
		if (rowOnly == true) {
			System.out.printf("%17.2f", this.solnCost[i]);
			System.out.printf("%19.3f", this.compTime[i]);
			System.out.printf("%3s%s", "", this.route.get(i));
		}
		else {
			System.out.printf("%17s%19s%4s", "-", "-", "-");
		}
	}
	
	// print results for all graphs
	public void printAll () {
		boolean rowOnly = true;
		//print def header for diff sub class
		if (this.getName() == "NN") {
			System.out.println("Detailed results for nearest neighbor:");
		}
		else if (this.getName() == "FL") {
			System.out.println("Detailed results for nearest neighbor first-last:");
		}
		else if (this.getName() == "NI") {
			System.out.println("Detailed results for node insertion:");
		}
		else {
			System.out.println("Detailed results:");
		}

		System.out.println("-----------------------------------------------");
		System.out.println("No.        Cost (km)     Comp time (ms)   Route   ");
		System.out.println("-----------------------------------------------");
		for (int i = 0 ; i < Pro5_chenj926.G.size(); i++) {
			if (this.solnFound[i] == false) {
				rowOnly = false;
			}
			else {
				rowOnly = true;
			}
			printSingleResult(i, rowOnly);
			System.out.println();
		}
		
	}
	
	// calculate average cost of successful routes
	
	public double avgCost () {
		double cost = 0;
		double avg_cost;
		int count = 0;
		
		for (int i = 0 ; i < this.solnFound.length; i++) {
			if (this.solnFound[i] != false) {
				cost += this.solnCost[i];
				//time += this.compTime[i];
				count++;
			}		
		}
		// cal avg time and cost
		avg_cost = cost / count;
		return avg_cost;
	}
	
	// calculate average comp time of successful routes
	public double avgTime () {
		double time = 0;
		double avg_time = 0;
		int count = 0;
		
		for (int i = 0 ; i < this.solnFound.length; i++) {
			if (this.solnFound[i] != false) {
				//cost += this.solnCost[i];
				time += this.compTime[i];
				count++;
			}		
		}
		
		avg_time = time / count;
		return avg_time;
	}
	
	// print statistics
	public void printStats () {
		// avg cost and time vars
		double avg_cost = 0;
		double avg_time = 0;
		//double cost = 0;
		//double time = 0;
		// count var
		double count = 0;
		// min and max vars
		double min = Double.MAX_VALUE;
		double min_time = Double.MAX_VALUE;;
		double max = 0;
		double max_time = 0;
		// std dev vars
		double sigma_x = 0;
		double sigma_time = 0;
		double std_dev = 0;
		double std_dev_time = 0;
		
		if (this.getName() == "NN") {
			System.out.println("Statistical summary for nearest neighbor:");
		}
		else if (this.getName() == "FL") {
			System.out.println("Statistical summary for nearest neighbor first-last:");
		}
		else if (this.getName() == "NI") {
			System.out.println("Statistical summary for node insertion:");
		}
		else {
			System.out.println("Statistical summary for node insertion:");
		}
		
		System.out.println("---------------------------------------");
		System.out.println("           Cost (km)     Comp time (ms)");
		System.out.println("---------------------------------------");
		
		// cal avg time and cost
		avg_cost = this.avgCost();
		//this.average_cost = avg_cost;
		avg_time = this.avgTime();
		//this.average_time = avg_time;
		//print avg
		System.out.printf("Average%13.2f%19.3f", avg_cost, avg_time);
		System.out.println();
		
		// reset count
		count = 0;
		for (int i = 0 ; i < this.solnFound.length; i++) {
			if (this.solnFound[i] != false) {
				sigma_x += Math.pow((this.solnCost[i] - avg_cost), 2);
				sigma_time = Math.pow((this.compTime[i] - avg_time), 2);
				count++;
				// update min, max cost and time
				if (min > this.solnCost[i]) {
					min = this.solnCost[i];
				}
				if (min_time > this.compTime[i]) {
					min_time = this.compTime[i];
				}
				if (max < this.solnCost[i]) {
					max = this.solnCost[i];
				}
				if (max_time < this.compTime[i]) {
					max_time = this.compTime[i];
				}
			}		
		}
		// cal std dev
		std_dev = Math.sqrt(sigma_x / (count - 1));
		std_dev_time = Math.sqrt(sigma_time / (count - 1));
		// is std dev is 0
		if (std_dev == 0) {
			System.out.printf("St Dev%14s%19s", "NaN", "NaN");
			System.out.println();
			}
		else {
			System.out.printf("St Dev%14.2f%19.3f", std_dev, std_dev_time);
			System.out.println();
		}
		// print min and max
		System.out.printf("Min%17.2f%19.3f", min, min_time);
		System.out.println();
		System.out.printf("Max%17.2f%19.3f", max, max_time);
		System.out.println();
	}
	

	// calculate success rate
	public double successRate () {
		double success_rate = 0.0;
		double success = 0.0;
		
		for (int i = 0 ; i < this.solnFound.length; i++) {
			if (this.solnFound[i] == true) {
				success++;
			}
		}
		// cal success rate
		success_rate = (success / (double)this.solnFound.length) * 100;
		//success_rate = success_rate;
		
		return success_rate;
	}
	
	// display graphs
	public static void displayGraphs(ArrayList<Graph> G) throws IOException {
		int user_choice;
		
		do {
			System.out.println("\nGRAPH SUMMARY");
			System.out.println("No.    # nodes    # arcs");
			System.out.println("------------------------");
			for (int i = 0 ; i < G.size(); i++) {
				System.out.printf("%3d", i+1);
				System.out.printf("%11d", G.get(i).getN());
				System.out.printf("%10d", G.get(i).getM());
				System.out.println();
			}
			System.out.println();
			// get user choice
			user_choice = getInput.getInteger("Enter graph to see details (0 to quit): ", 0, G.size());
			// 0 to quit
			if (user_choice != 0) {
				G.get(user_choice-1).print();
			}
			else {
				return;
			}
		} while(user_choice != 0);
	}
	
	public static void compare(NNSolver NN, NNFLSolver FL, NISolver NI) {
		String cost_winner;
		String time_winner;
		String sucs_winner;
		String winner = "NN";

		System.out.println("\n------------------------------------------------------------");
		System.out.println("           Cost (km)     Comp time (ms)     Success rate (%)");
		System.out.println("------------------------------------------------------------");
		System.out.printf("%2s%18.2f%19.3f%21.1f\n", "NN", NN.avgCost(), NN.avgTime(), NN.successRate());
		System.out.printf("%2s%15.2f%19.3f%21.1f\n", "NN-FL", FL.avgCost(), FL.avgTime(), FL.successRate());
		System.out.printf("%2s%18.2f%19.3f%21.1f\n", "NI", NI.avgCost(), NI.avgTime(), NI.successRate());
		System.out.println("------------------------------------------------------------");
		if (NN.avgCost() <= FL.avgCost() && NN.avgCost() <= NI.avgCost()) {
			cost_winner = "NN";
		}
		else if (FL.avgCost() < NN.avgCost() && FL.avgCost() <= NI.avgCost()) {
			cost_winner = "NN-FL";
		}
		else {
			cost_winner = "NI";
		}

		if (NN.avgTime() <= FL.avgTime() && NN.avgTime() <= NI.avgTime()) {
			time_winner = "NN";
		}
		else if (FL.avgTime() < NN.avgTime() && FL.avgTime() <= NI.avgTime()) {
			time_winner = "NN-FL";
		}
		else {
			time_winner = "NI";
		}
		
		if (NN.successRate() >= FL.successRate() && NN.successRate() >= NI.successRate()) {
			sucs_winner = "NN";
		}
		else if (FL.successRate() > NN.successRate() && FL.successRate() >= NI.successRate()) {
			sucs_winner = "NN-FL";
		}
		else {
			sucs_winner = "NI";
		}
		
		System.out.printf("Winner%14s%19s%21s\n", cost_winner, time_winner, sucs_winner);
		System.out.println("------------------------------------------------------------");
		
		if (cost_winner.equals("NN") && time_winner.equals("NN") && sucs_winner.equals("NN")) {
			winner = "NN";
		}
		else if (cost_winner.equals("NN-FL") && time_winner.equals("NN-FL") && sucs_winner.equals("NN-FL")) {
			winner = "NN-FL";
		}
		else if (cost_winner.equals("NI") && time_winner.equals("NI") && sucs_winner.equals("NI")) {
			winner = "NI";
		}
		else {
			winner = "Unclear";
		}
		
		System.out.printf("Overall winner: %s\n\n", winner);
	}
	
	
	
}