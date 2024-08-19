import java.util.ArrayList;

public class NISolver extends TSPSolver {

	
	// constructor
	public NISolver(ArrayList<Graph> G) {
		super(G);
		this.setName("NI");
	}
	
	public void initNI(ArrayList<Graph> G) {
		this.init(G);
	}
	
	@Override
	public int [] nextNode ( Graph G , ArrayList< Integer>visited ) {
		
		ArrayList<Integer> temp_ben = new ArrayList<Integer>();
		
		// first and last and any in between
		boolean add_to_last = false;
		boolean add_to_mid = false;
		boolean add_to_first = false;
		
		int[] new_node_info = new int[2];
		int next_node = 0;
		
		int node_to_find_ngh_first = visited.get(0);
		int node_to_find_ngh_last = visited.get(visited.size()-1);
		int node_to_find_ngh_mid;
		
		int next_node_first;
		int next_node_last;
		int next_node_mid = -1;
		int temp_next_mid;
		
		double first_cost = Double.MAX_VALUE;
		double last_cost = Double.MAX_VALUE;
		double mid_cost = Double.MAX_VALUE;
		double cheapest_mid = Double.MAX_VALUE;
		
		int mid_idx = -1;
		int insert_idx = 0;
		//boolean yes_insert = false;
		
		double cheapest = Double.MAX_VALUE;
		
		// if we did not get to the end-1 step
		if (visited.size() <= G.getN()-1) {
			// neartest one get chose
			
			next_node_first = this.nearestNeighbor(G, visited, node_to_find_ngh_first); 
			next_node_last = this.nearestNeighbor(G, visited, node_to_find_ngh_last); 
			
			// if no any arcs found for both last and first
			if (next_node_first == 0 && next_node_last == 0) {
				temp_ben.add(next_node_first);
				temp_ben.add(next_node_last);
			}
			// no first
			else if (next_node_first == 0) {
				temp_ben.add(next_node_first);
				last_cost = G.getCost(node_to_find_ngh_last-1, next_node_last-1);
			}
			// no last
			else if (next_node_last == 0) {
				temp_ben.add(next_node_last);
				first_cost = G.getCost(node_to_find_ngh_first-1, next_node_first-1);
			}
			else {
				first_cost = G.getCost(node_to_find_ngh_first-1, next_node_first-1);
				last_cost = G.getCost(node_to_find_ngh_last-1, next_node_last-1);
			}

			// mid arc
			for (int i = 1 ; i < visited.size()-1 ; i++) {
				// get arc x
				// since from visited, if wanna idx, need to-1
				node_to_find_ngh_mid = visited.get(i);
				
				temp_next_mid = this.nearestNeighbor(G, visited, node_to_find_ngh_mid); 
					
				// to find the cheapest mid arc
				if (temp_next_mid != 0 && temp_ben.contains(node_to_find_ngh_mid) == false) {
					mid_cost = G.getCost(node_to_find_ngh_mid-1, temp_next_mid-1);
					if (mid_cost < cheapest_mid) {
						cheapest_mid = mid_cost;
						next_node_mid = temp_next_mid; 
						mid_idx = i;
					}
				}
			}
			mid_cost = cheapest_mid;
			
				
				// last and first have no arc
				if (temp_ben.contains(next_node_last) && temp_ben.contains(next_node_first)) {
					if (next_node_mid != -1) {
						next_node = next_node_mid;
						insert_idx = mid_idx+1;
						add_to_mid = true;
					}
					
				}
				// no last
				else if (temp_ben.contains(next_node_last) && temp_ben.contains(next_node_first) == false) {
					if (first_cost <= mid_cost) {		
						next_node = next_node_first;
						insert_idx = 0;
						add_to_first = true;
					}
					else {
						if (next_node_mid != -1) {
							next_node = next_node_mid;
							insert_idx = mid_idx+1;
							add_to_mid = true;
						}
					}
				}
				// no first
				else if (temp_ben.contains(next_node_first) && temp_ben.contains(next_node_last) == false) {
					if (last_cost <= cheapest_mid) {
						next_node = next_node_last;
						insert_idx = visited.size();
						add_to_last = true;
					}
					else {
						if (next_node_mid != -1) {
							if (next_node_mid != -1) {
								next_node = next_node_mid;
								insert_idx = mid_idx+1;
								add_to_mid = true;
							}
						}
					}
				}
				// have both last first 
				else {
					if (first_cost <= mid_cost && first_cost <= last_cost) {
						if (first_cost <= cheapest) {
							cheapest = first_cost;
							next_node = next_node_first;
							insert_idx = 0;
							add_to_first = true;
						}
						
					}
					else if (last_cost < first_cost && last_cost <= mid_cost) {
						if (last_cost < cheapest) {
							cheapest = last_cost;
							next_node = next_node_last;
							insert_idx = visited.size();
							add_to_last = true;
							
						}
					}
					else {
						if (mid_cost < cheapest) {
							cheapest = mid_cost;
							next_node = next_node_mid;
							insert_idx = mid_idx+1;
							add_to_mid = true;
						}
					}
				}
				
				// if no mid, first, last exist
				if (!add_to_first && !add_to_last && !add_to_mid) {
					new_node_info[0] = -1;
					new_node_info[1] = 0;
					return new_node_info;
				}
				
				new_node_info[0] = next_node-1;
				new_node_info[1] = insert_idx;
			
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
	
	// check if node k can be inserted at position i
	public boolean canBeInserted (Graph G, ArrayList<Integer>visited, int i, int k) {
		// k and i - 1, i + 1
		boolean yes_insert = false;
		if (visited.contains(k)){
			return false;
		}
		//yes_insert = G.existsArc(i-1, k);
		yes_insert = G.existsArc(i+1, k);
		
		return yes_insert;
	}
	
}
