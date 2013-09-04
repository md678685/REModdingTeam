package RE.routing;

import java.util.ArrayList;

public class RoutingDestination {

	// Coords
	private ArrayList<Integer> xCoord = new ArrayList<Integer>();
	private ArrayList<Integer> yCoord = new ArrayList<Integer>();
	private ArrayList<Integer> zCoord = new ArrayList<Integer>();
	// Resistance
	private ArrayList<Integer> travelCost = new ArrayList<Integer>();
	// Requests
	private ArrayList<Integer> requests = new ArrayList<Integer>();

	public RoutingDestination() {

	}

	public int getXCoord(int i) {
		return xCoord.get(i);
	}

	public int getYCoord(int i) {
		return yCoord.get(i);
	}

	public int getZCoord(int i) {
		return zCoord.get(i);
	}

	public int getTravelCost(int i) {
		return travelCost.get(i);
	}

	public int getRequests(int i) {
		return requests.get(i);
	}

	public void addDestination(int x, int y, int z, int cost, int requests) {
		xCoord.add(x);
		yCoord.add(y);
		zCoord.add(z);
		travelCost.add(cost);
		this.requests.add(requests);
	}

}
