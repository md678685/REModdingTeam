package RE.routing.interfaces;

import java.util.List;
import java.util.Map;

import RE.routing.util.RoutingEntry;
import RE.util.Direction;

public interface Wire {
	/**
	 * Gives entry to be (possibly) entered into the internal Routing Table. It does not have to be entered, depending on the algorithm used.
	 * @param sideIncoming The side from which the Update is incoming. This might be the new Routingdirection
	 * @param entry
	 */
	public void updateRoutingEntry(Direction sideIncoming,RoutingEntry entry);
	/**
	 * Returns the internal routing list. May never return null, but instead an empty List if there is no Entry
	 * @return
	 */
	public Map<Destination, RoutingEntry> getRoutingEntrys();
	
	/**
	 * Returns the resistance of the wire.
	 * @return
	 */
	public int getResistance();
	
	public void init();
}
