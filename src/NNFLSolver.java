import java.util.ArrayList;

public class NNFLSolver extends TSPSolver {
	
	// constructor
	public NNFLSolver(ArrayList<Graph> G) {
		super(G);
		this.setName("FL");
		this.setHasResults(false);
	}
	
	public void initFL(ArrayList<Graph> G) {
		this.init(G);
	}
	
	@Override
	public int [] nextNode (Graph G, ArrayList<Integer>visited) {
		
		// first and last
		boolean add_to_last;
		
		int[] new_node_info = new int[2];
		int next_node;
		
		int node_to_find_ngh_first = visited.get(0);
		int node_to_find_ngh_last = visited.get(visited.size()-1);
		int next_node_first;
		int next_node_last;
		
		// if we did not get to the end-1 step
		if (visited.size() <= G.getN()-1) {
			// neartest one get chose
			
			next_node_first = this.nearestNeighbor(G, visited, node_to_find_ngh_first); 
			next_node_last = this.nearestNeighbor(G, visited, node_to_find_ngh_last); 
			 
			// if no any arcs found for both last and first
			if (next_node_first == 0 && next_node_last == 0) {
				new_node_info[0] = -1;
				new_node_info[1] = 0;
				return new_node_info;
			}
			// if no arc for first
			else if (next_node_first == 0) {
				next_node = next_node_last;
				new_node_info[0] = next_node-1;
				new_node_info[1] = visited.size();
				return new_node_info;
			}
			// if no arc for last
			else if (next_node_last == 0) {
				next_node = next_node_first;
				new_node_info[0] = next_node-1;
				new_node_info[1] = 0;
				return new_node_info;
			}
			// init distance for both 1st and last
			double first_cost = G.getCost(node_to_find_ngh_first-1, next_node_first-1);
			double last_cost = G.getCost(node_to_find_ngh_last-1, next_node_last-1);
			
			// last dis is cheapest
			if (last_cost <= first_cost) {
				next_node = next_node_last;
				add_to_last = true;
			}
			// first is cheapest
			else {
				next_node = next_node_first;
				add_to_last = false;
			}
			
			// update return arc
			new_node_info[0] = next_node-1;
			
			// if we insert to last
			if (add_to_last == true) {
				new_node_info[1] = visited.size();
			}
			// if we insert it to 1st
			else {
				new_node_info[1] = 0;
			}
		}
		// if we are at the place to link back to beginning arc
		else {
			if (G.existsArc(node_to_find_ngh_last-1, node_to_find_ngh_first-1)) {
				next_node = node_to_find_ngh_first;
			}
			else {
				next_node = 0;
			}
			
			new_node_info[0] = next_node-1;
			new_node_info[1] = visited.size();
		}
		
		return new_node_info;
	}
}
