package RE.routing.util;

import RE.routing.interfaces.Destination;
import RE.util.*;

public class RoutingEntry {
	private final Destination dest;
	private final double travelCost;
	private final Direction nextHop;
	private final double requested;
	
	
	public RoutingEntry(Destination dest, double travelCost, Direction nextHop,
			double requested) {
		this.dest = dest;
		this.travelCost = travelCost;
		this.nextHop = nextHop;
		this.requested = requested;
	}


	public Destination getDest() {
		return dest;
	}


	public double getTravelCost() {
		return travelCost;
	}


	public Direction getNextHop() {
		return nextHop;
	}


	public double getRequested() {
		return requested;
	}
}
