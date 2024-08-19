import java.io.*;

public class Node {
	
	private String name ; // node name
	private double lat ; // latitude coordinate
	private double lon ; // longitude coordinate
	
	// constructors
	public Node () {
		this.lat = 0;
		this.lon = 0;
		this.name = "";
	}
	public Node(String name, double lat, double lon) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	// setters
	public void setName (String name) {
		this.name = name;
	}
	public void setLat ( double lat ) {
		// return error if user entered improper lat
		if (lat > 90 || lat < -90) {
			System.out.print("ERROR: Input must be a real number in [-90.00, 90.00]!");
			return;
		}
		this.lat = lat;
	}
	public void setLon ( double lon ) {
		// return error if user entered improper lon
		if (lat > 180 || lat < -180) {
			System.out.print("ERROR: Input must be a real number in [-180.00, 180.00]!");
			return;
		}
		this.lon = lon;
	}
	
	// getters
	public String getName () {
		return this.name;
	}
	public double getLat () {
		return this.lat;
	}
	public double getLon () {
		return this.lon;
	}

	// update the userEdit city info to node 
	public void getUserEdit(String name, double lat, double lon) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	// get user info and edit node 
	public void userEdit () throws IOException {
		// get user's updated edit info of the cities
		System.out.print("   Name: ");
		String Name = Pro5_chenj926.cin.readLine();
		this.getUserEdit(Name, getInput.getDouble("   latitude: ", -90, 90), getInput.getDouble("   longitude: ", -180, 180));
		
	}
		
	// print node info as a table row
	public void print () {
		// print the name of the node
		System.out.printf("%19s", this.name);
		
		// convert the lat and lon info into string to match the format
		String lat_string = Double.toString(this.lat);
		String lon_string = Double.toString(this.lon);
		
		// add the converted lat and lon with ( ) and control space and format
		String lat_lon_data = "(" + lat_string + "," + lon_string + ")";
		System.out.printf("%19s\n", lat_lon_data);
	}
	
	// calc distance between two nodes
	public static double distance (Node i, Node j) {
		
		// convert the degree to radians
		double i_lat_rad = Math.toRadians(i.lat);
		double j_lat_rad = Math.toRadians(j.lat);
		
		// calculations
		double del_x = Math.toRadians(Math.abs(i.lat - j.lat));
		double del_y = Math.toRadians(Math.abs(i.lon - j.lon));
		double a = Math.pow(Math.sin(del_x / 2), 2) + Math.cos(i_lat_rad) * Math.cos(j_lat_rad) * Math.pow(Math.sin(del_y / 2), 2);
		double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double c = 6371 * b;
		
		// return the final distance
		return c;
	}
}
