package RE.routing;

import java.util.ArrayList;

public class RoutingSource {

	// Coords
	private ArrayList<Integer> xCoord = new ArrayList<Integer>();
	private ArrayList<Integer> yCoord = new ArrayList<Integer>();
	private ArrayList<Integer> zCoord = new ArrayList<Integer>();
	// Resistance
	private ArrayList<Integer> travelCost = new ArrayList<Integer>();
	// Sends
	private ArrayList<Integer> sends = new ArrayList<Integer>();

	public RoutingSource() {

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

	public int getSends(int i) {
		return sends.get(i);
	}

	public void addSource(int x, int y, int z, int cost, int sends) {
		xCoord.add(x);
		yCoord.add(y);
		zCoord.add(z);
		travelCost.add(cost);
		this.sends.add(sends);
	}

}
