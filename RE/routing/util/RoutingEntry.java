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

	/**
	 * @return the dest
	 */
	public Destination getDest() {
		return this.dest;
	}

	/**
	 * @return the travelCost
	 */
	public double getTravelCost() {
		return this.travelCost;
	}

	/**
	 * @return the nextHop
	 */
	public Direction getNextHop() {
		return this.nextHop;
	}

	/**
	 * @return the requested
	 */
	public double getRequested() {
		return this.requested;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RoutingEntry [dest=" + this.dest + ", travelCost="
				+ this.travelCost + ", nextHop=" + this.nextHop
				+ ", requested=" + this.requested + "]";
	}

}
