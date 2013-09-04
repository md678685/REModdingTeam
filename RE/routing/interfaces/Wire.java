package RE.routing.interfaces;

import java.util.List;

import RE.routing.util.RoutingEntry;

public interface Wire {
	/**
	 * Gives entry to be (possibly) entered into the internal Routing Table. It does not have to be entered, depending on the algorithm used.
	 * @param entry
	 */
	public void update(RoutingEntry entry);
	/**
	 * Returns the internal routing list. May never return null, but instead an empty List if there is no Entry
	 * @return
	 */
	public List<RoutingEntry> getRoutingEntrys();
	
	/**
	 * Returns the resistance of the wire.
	 * @return
	 */
	public double getResistance();
}
