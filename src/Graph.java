import java.io.*;
import java.util.ArrayList;


public class Graph {
	
	private int n ; // number of nodes
	private int m ; // number of arcs
	private ArrayList <Node> node ; // ArrayList *or* array of nodes
	private boolean [][] A ; // adjacency matrix
	private double [][] C ; // cost matrix
	
	// constructors
	// constructor 1
	public Graph () {
		this.n = 0;
		this.m = 0;
		this.node = new ArrayList <Node>();
		this.A = new boolean[0][0];
		this.C = new double[0][0];
	}
	
	// constructor 2
	public Graph(int n) {
		this.n = n;
		this.m = 0;
		
		// allocate n*n memory space for A and C
		this.A = new boolean [n][n]; 
		this.C = new double [n][n];
		
		this.node = new ArrayList <Node>();
	}
	
	// setters
	public void setN(int n) {
		this.n = n;
	}
	public void setM (int m) {
		this.m = m;
	}
	public void setArc (int i, int j, boolean b) {
		this.A[i][j] = b;
		this.A[j][i] = b;
	}
	public void setCost (int i, int j, double c) {
		this.C[i][j] = c;
		this.C[j][i] = c;
	}
	
	// getters
	public int getN () {
		return this.n;
	}
	public int getM () {
		return this.m;
	}
	public boolean getArc (int i, int j) {
		return this.A[i][j];
	}
	public double getCost (int i, int j) {
		return this.C[i][j];
	}
	public Node getNode (int i) {
		return this.node.get(i);
	}
	
	// initialize values and arrays
	public void init (int n) {
		this.A = new boolean [n][n]; 
		this.C = new double [n][n];
	}
	
	// reset the graph
	public void reset () {
		this.node.clear();
		this.n = 0;
		this.m = 0;
		this.A = new boolean [0][0];
		this.C = new double [0][0];
	}
	
	// check if arc exists
	public boolean existsArc (int i ,int j) {
		// since A is square matrix we just need to find the largest between i and j
		// to compare with a length
		int largest = Math.max(i, j);
		
		// if i or j is > A; or  A is null, A[i][j] definitely not exist
		if (this.A == null || largest > this.A.length) {
			return false;
		}
		// else if A i j exist, 
		else {
			if (this.A[i][j] == true) {
				return true;
			}
		}
		return false;
	}
	
	// check if node exists
	public boolean existsNode (Node t) {
		// whether the node is already exist 
		boolean node_exist = false;
		
		// check if user enter duplicate name
		for (int i = 0 ; i < node.size() ; i++) {
			// if node is empty, definitely not exist
			if (this.node.isEmpty()) {
				node_exist = false;
				break;
			}
			
			// if new enter name is duplicated 
			// or both lat and lon are duplicated
			else if (getNode(i).getName().equals(t.getName())
					|| getNode(i).getLat() == t.getLat() && getNode(i).getLon() == t.getLon()) {
				//System.out.println("ERROR: City name and/or coordinates already exist!");
				node_exist = true;
				break;
			}
		}
		
		return node_exist;
	}
	
	// add an arc , return T/F success
	public boolean addArc (int i, int j) {
		if (existsArc(i, j) == false) {
			// set arc, cost, and number of arcs
			setArc(i, j, true);
			setM(this.m + 1);
			double cost = Node.distance(this.node.get(i), this.node.get(j));
			setCost(i, j, cost);
			
			return true;
		}
		return false;
	}
	
	// change the A and C info while user changed city info
	public void changeCost_with_change_city(int i, int j) {
		double cost = Node.distance(this.node.get(i), this.node.get(j));
		setCost(i, j, cost);
	}
	
	// remove an arc
	// Since the matrix is symmetric, we only need to check half of it (diagonally)
	public void removeArc (int k) { 				
		// get the length of A
		int len = A.length;
		// set a counter to count whether we reach kth arc
		int count = 0;
		for (int i = 0 ; i < len ; i++) {
			for (int j = i + 1 ; j < len ; j++) {
				// if the node is true, increment counter, until it reach kth arc
				if (A[i][j] == true) {
					count++;
				}
				// if counter is the kth arc, we remove it	
				if (count == k) {
					// set arc, cost, and number of arcs
					setArc(i, j, false);
					setM(this.m - 1);
					setCost(i, j, 0);
					break;
				}
			}
			// after we remove the arc we no longer need to loop any more
			if (count == k) {
				break;
			}
		}
	}
	
	// add a node
	public boolean addNode (Node t) { 
		// the node is already exist, do not add it
		if (existsNode(t)) {
			return false;
		}
		else {
			this.node.add(t);
			return true;
		}
	}
	
	// print all graph info
	public void print () {
		System.out.printf("\nNumber of nodes: %d\n", this.n);
		System.out.printf("Number of arcs: %d\n", this.m);
		printNodes();
		System.out.println();
		printArcs();
		
		// temp加的
		System.out.println();
	}
	
	// print node list
	public void printNodes () {						
		System.out.println("\nNODE LIST");
		System.out.println("No.               Name        Coordinates");
		System.out.println("-----------------------------------------");
		for (int i = 0 ; i < this.node.size() ; i++) {
			System.out.printf("%3d", i+1);
			node.get(i).print();
		}
	}
	
	// print arc list
	public void printArcs () { 		
		// count arc_numbers
		int nth_arc = 1;
		System.out.println("ARC LIST");
		System.out.println("No.    Cities       Distance");
		System.out.println("----------------------------");
		
		for (int i = 0 ; i < this.node.size() ; i++) {
			for (int j = i+1 ; j < this.node.size() ; j++) {
				// if arc i j exist, print it 
				if (existsArc(i, j)) {
					System.out.printf("%3d", nth_arc);
					// convert to string for spacing
					String arc_connect = Integer.toString(i+1) + "-" + Integer.toString(j+1);
					System.out.printf("%10s", arc_connect);
					System.out.printf("%15.2f\n", Node.distance(node.get(i), node.get(j)));
					nth_arc++;
				}
			}
		}
	}
	
	// check feasibility of path P
	public boolean checkPath (int[] P) { 			
		boolean path_valid = true;
		
		for (int i = 0 ; i < P.length - 1; i++) {
			// Since A is symmetric, we only need to check for e.g.
			// A[1][2], but no need for vice versa
			path_valid = A[P[i]-1][P[i+1]-1];
			if (path_valid == false) {
				break;
			}
		}
		return path_valid;
	}
	
	// calculate cost of path P
	public double pathCost (int[] P) { 			
		double cost = 0.0;
		double temp_cost = 0.0;
		
		for (int i = 0 ; i < P.length - 1; i++) {
			// accumulate temp_cost into cost
			temp_cost = C[P[i]-1][P[i+1]-1];
			cost += temp_cost;
		}
		
		return cost;
	}
	
	
	public void userEidtNode(int city_to_edit) throws IOException {
		//int city_to_edit = getInput.getInteger("\nEnter city to edit (0 to quit): ", 0, this.n);
		if (city_to_edit == 0) { 
			return;
		}
		else {
			node.get(city_to_edit - 1).userEdit();;
			// print extra line for format
			System.out.println();
		}
	}
}

