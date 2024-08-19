import java.io.*;
import java.util.ArrayList;

public class getInput {
	
	public static ArrayList<Boolean> load_true = new ArrayList<Boolean>();
	
	
	public static String getString(String prompt) throws IOException {
		// boolean variable for do while loop
		boolean loop_stop = false;
		String user_choice = "";
		
		// do while loop, keep ask for user input if they entered
		// things outside the boundry
		do {
			Display.displayMenu();
			System.out.print(prompt);
			
			// get the user input
			user_choice = Pro5_chenj926.cin.readLine();
			// convert the input into uppercase
			user_choice = user_choice.toUpperCase();
			
			// use switch so that if user correctly entered each case, the program
			// will have various respond
			switch (user_choice) {	
				case "L":
					return user_choice;
				case "I":
					return user_choice;
				case "C":
					return user_choice;
				case "R":
					return user_choice;
				case "D":
					return user_choice;
				case "X":
					return user_choice;
				case "Q":
					return user_choice;
				
				// if the user did not entered the above choices, return error msg and ask the input again
				default:
					System.out.println("\nERROR: Invalid menu choice!\n");
			}
		} while (!loop_stop);
	
		return user_choice;
	}
	
	public static boolean loadFile(ArrayList<Graph> G) throws IOException {
		// init vars for later use
		boolean file_loaded = false;
		String graph_loaded = "";
		int valid_graphs = 0;
		int total_graphs = 0;
		
		// init graph
		Graph graph = new Graph();
		
		// boolean variable for do while loop
				boolean loop_stop = false;
				
				// declare variable
				String file_name;
				BufferedReader fin = null;
				
				// do while loop
				do {
					// try catch method to avoid Exception error and handle the respond
					// of these errors
					try {
						System.out.print("\nEnter file name (0 to cancel): ");
						file_name = Pro5_chenj926.cin.readLine();
						// if user enter 0, cancel loading
						if (file_name.equals("0")) {
							System.out.println("\nFile loading process canceled.\n");
							
							file_loaded = false;
							return file_loaded;
						}
						else {
							file_loaded = true; 
							load_true.add(true);
						}
						// read line
						fin = new BufferedReader(new FileReader(file_name));	
					} catch (FileNotFoundException e) {
						System.out.println("\nERROR: File not found!\n");
						file_loaded = false;
						return file_loaded;
					}
					
				} while(loop_stop);
				
				// to contain each line's info
				String line;
				
				do {
					boolean valid_graph = true;
					// read everyline of file
					line = fin.readLine();
					// if line is not null and line is not ""
					if ((line != null) && (!line.isEmpty())) {
						// get city number section
						int city_num = Integer.parseInt(line);
						String city_info;
						String[] node_info = new String[3];
						String arc_info;
						String[] arc_list;
						
						// add node info into graph
						for (int i = 0 ; i < city_num ; i++) {
							// not get node info
							line = fin.readLine();
							
							city_info = line;
							node_info = city_info.split(",");
							
							String name = node_info[0];
							double lat = Double.parseDouble(node_info[1]);
							double lon = Double.parseDouble(node_info[2]);
							
							Node node = new Node(name, lat, lon);
							
							// check is the node is valid
							if ((lat > 90 || lat < -90) || (lon > 180 || lon < -180)) {
								valid_graph = false;
							}
							else if (graph.existsNode(node)) {
								valid_graph = false;
							}
							
							// add the node to graph
							Pro5_chenj926.createGraph(graph, city_num, node);
							file_loaded = true;
						}
						
						
						// add arcs of nodes
						for (int j = 1 ; j < city_num ; j++) {
							// not get arcs
							line = fin.readLine();
							arc_info = line;
							arc_list = arc_info.split(",");
							for (int k = 0; k < arc_list.length ; k ++) {
								if (!arc_list[k].isEmpty()) {
									int arc = Integer.parseInt(arc_list[k]);
									if (valid_graph) {
										Pro5_chenj926.addArcs(graph, j, arc);
									}
								}
							}
						}
						
						// append graph into arrayList<Graph>
						if (valid_graph) {
							G.add(graph);
							valid_graphs++;
						}
						graph = new Graph();
						total_graphs++;
					}	
					
				} while (line != null);
				// close the reader
				fin.close();
				
				graph_loaded = valid_graphs + " of " + total_graphs + " graphs loaded!";
				System.out.println("\n" + graph_loaded + "\n");
				
				/*if (G.isEmpty()) {
					file_loaded = false;
				}*/
				
				return file_loaded;
	}
	
	public static int getInteger(String prompt, int LB, int UB) throws IOException {
		// boolean variable for do while loop
		boolean loop_stop = false;
		
		// declare variable
		int user_choice = 0;
		
		// do while loop
		do {
			// if input is not num, eg !, the boolean var avoid the if statement
			// below to be execute
			boolean is_num = true;
			
			// try catch method to avoid Exception error and handle the respond
			// of these errors
			try {
				System.out.print(prompt);
				user_choice = Integer.parseInt(Pro5_chenj926.cin.readLine());
			} catch (NumberFormatException e) {
				if (UB == Integer.MAX_VALUE) {
					System.out.printf("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB);
				}
				else {
					System.out.printf("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB);
				}
				loop_stop = true;
				
				// to loop again for correct input
				is_num = false;
			}
			
			// the user_choice is num
			if (is_num == true) {
				// if input out boundary, let user input again
				if (user_choice > UB || user_choice < LB) {
					if (UB == Integer.MAX_VALUE) {
						System.out.printf("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB);
					}
					else {
						System.out.printf("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB);
					}
					loop_stop = true;
				} else {
					loop_stop = false;
				}
			}
		} while(loop_stop);
		
		return user_choice;
	}
	
	public static double getDouble(String prompt, double LB, double UB) throws IOException {
		
		// boolean variable for do while loop
		boolean loop_stop = false;
		
		// declare variable
		double user_choice = 0.0;
		
		// do while loop
		do {
			
			// if input is not num, eg !, the boolean var avoid the if statement
			// below to be execute
			boolean is_num = true;
			
			// try catch method to avoid Exception error and handle the respond
			// of these errors
			try {
				System.out.print(prompt);
				user_choice = Double.parseDouble(Pro5_chenj926.cin.readLine());
			} catch (NumberFormatException e) {
				System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
				loop_stop = true;
				
				// to loop again for correct input
				is_num = false;
			}
			
			// the user_choice is num
			if (is_num == true) {

				// if input out boundary, let user input again
				if (user_choice > UB || user_choice < LB) {
					System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
					loop_stop = true;
				} else {
					loop_stop = false;
				}
			}
		} while(loop_stop);
		
		return user_choice;
	}
}
