import java.util.ArrayList;

public class NNSolver extends TSPSolver {
	
	// constructor
	public NNSolver(ArrayList<Graph> G) {
		super(G);
		this.setName("NN");
		this.setHasResults(false);
	}
	
	public void initNN(ArrayList<Graph> G) {
		this.init(G);
	}
	
	@Override
	public int [] nextNode ( Graph G , ArrayList<Integer>visited ) {
		int node_to_find_ngh;
		int[] new_node_info = new int[2];
		
		node_to_find_ngh = visited.get(visited.size()-1); // size=1 - arc 1
		
		int next_node;
		// link n arcs
		if (visited.size() <= G.getN()-1) {
			
			next_node = this.nearestNeighbor(G, visited, node_to_find_ngh); 
		
			new_node_info[0] = next_node-1;
			new_node_info[1] = visited.size();
		}
		// link the last arc back to 1
		else {
			if (G.existsArc(node_to_find_ngh-1, 0)) {
				next_node = 1;
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
