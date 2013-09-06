package RE.tileentities;

import java.util.HashMap;
import java.util.Map;

import RE.routing.interfaces.Destination;
import RE.routing.interfaces.Wire;
import RE.routing.util.RoutingEntry;
import RE.util.Direction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBatteryCharger extends TileEntity implements Destination {

	private boolean initDone = false;
	private Map<Destination, RoutingEntry> routingEntries;

	public TileEntityBatteryCharger() {
		this.routingEntries = new HashMap<Destination, RoutingEntry>();
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			if (!initDone) {

				init();
				initDone = true; // Just to initialize the routingtable
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}

	@Override
	public void updateRoutingEntry(Direction sideIncoming, RoutingEntry entry) {
		updateRoutingEntry(sideIncoming, entry, true);
	}

	/**
	 * See {@link Wire#updateRoutingEntry(Direction, RoutingEntry)}
	 * 
	 * @param sideIncoming
	 * @param entry
	 * @param propagate
	 *            Whether the change should be propagated to the neighbors.
	 */
	private void updateRoutingEntry(Direction sideIncoming, RoutingEntry entry,
			boolean propagate) {
		if (routingEntries.containsKey(entry.getDest())) {
			RoutingEntry re = routingEntries.get(entry.getDest());

			if (re.getTravelCost() <= entry.getTravelCost()) {

				// No reduction in Travelcost -Larethian

				if (re.getRequested() != entry.getRequested()) {
					// requested amount changed
					routingEntries.put(re.getDest(),
							new RoutingEntry(re.getDest(), re.getTravelCost(),
									re.getNextHop(), entry.getRequested()));
					if (propagate)
						propagate(sideIncoming,
								routingEntries.get(re.getDest()));
				} else {
					// do nothing, as it wouldnt change anything anyway
					// -Larethian
				}
			} else {
				// Reduction in travelcost detected -Larethian
				routingEntries.put(
						entry.getDest(),
						new RoutingEntry(entry.getDest(), entry.getTravelCost()
								+ this.getResistance(), sideIncoming, entry
								.getRequested()));
				if (propagate)
					propagate(sideIncoming, routingEntries.get(entry.getDest()));
			}
		} else {
			routingEntries.put(
					entry.getDest(),
					new RoutingEntry(entry.getDest(), entry.getTravelCost()
							+ this.getResistance(), sideIncoming, entry
							.getRequested()));
			if (propagate)
				propagate(sideIncoming, routingEntries.get(entry.getDest()));
		}
	}

	private void propagate(Direction sideIncoming, RoutingEntry entry) {
		for (Direction dir : Direction.values()) {
			if (dir != sideIncoming)// this is to prevent that two nodes bounce
									// update-messages forth and back accidently
									// -Larethian
			{
				TileEntity te = worldObj.getBlockTileEntity(this.xCoord
						+ dir.xOffset, this.yCoord + dir.yOffset, this.zCoord
						+ dir.zOffset);

				if (te instanceof Wire) {
					((Wire) te).updateRoutingEntry(sideIncoming.getOpposite(),
							entry);
				}
			}
		}
	}

	@Override
	public Map<Destination, RoutingEntry> getRoutingEntrys() {
		return routingEntries;
	}

	@Override
	public int getResistance() {
		return 1; // TODO Find maybe something better than simply 1 -Larethian
	}

	@Override
	public void init() {
		updateRoutingEntry(Direction.SELF, new RoutingEntry(this, 0,
				Direction.SELF, getRequested()));

		// get from every direction... -Larethian
		for (Direction dir : Direction.values()) {
			TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.xOffset,
					yCoord + dir.yOffset, zCoord + dir.zOffset);

			if (te instanceof Wire) {
				// The routing entrys and add them to our own list -Larethian
				for (RoutingEntry re : ((Wire) te).getRoutingEntrys().values()) {
					updateRoutingEntry(dir, re, false);
				}
			}
		}
		if (routingEntries.isEmpty())
			return; // early escape, preventing some calls which would consume
					// time -Larethian

		// for every RoutingEntry... -Larethian
		for (RoutingEntry re : this.getRoutingEntrys().values()) {
			// propagate in every direction. -Larethian
			for (Direction dir : Direction.values()) {
				TileEntity te = worldObj.getBlockTileEntity(xCoord
						+ dir.xOffset, yCoord + dir.yOffset, zCoord
						+ dir.zOffset);

				if (te instanceof Wire) {
					((Wire) te).updateRoutingEntry(dir, re);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see RE.routing.interfaces.Destination#getRequested()
	 */
	@Override
	public int getRequested() {
		return 20;
	}

}
