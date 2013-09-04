package RE.tileentities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import RE.blocks.BlockInfo;
import RE.network.PacketHandler;
import RE.routing.RoutingDestination;
import RE.routing.interfaces.Destination;
import RE.routing.interfaces.Wire;
import RE.routing.util.RoutingEntry;
import RE.util.Direction;

public class TileEntityWire extends TileEntity implements Wire {

	boolean[] sides = new boolean[6];
	int[] wireConnect = { BlockInfo.WIRE_ID, BlockInfo.GENERATOR_ID, BlockInfo.SOLARPANEL_ID, BlockInfo.BATTERYCHARGER_ID };
	
	private Map<Destination, RoutingEntry> routingEntries;

	// Routing Table:
	// Source: Coords, Resistance, Sends
	// Destination: Coords, Resistance, Requests

	public TileEntityWire() {
		routingEntries = new HashMap<Destination,RoutingEntry>();
	}

	
	private boolean initDone = false;
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			if(!initDone){
				init(); initDone = true;	//Just to initialize the routingtable
			}
			
			boolean[] side = new boolean[6];

			for (int i = 0; i < side.length; i++) {
				side[i] = false;
			}

			for (int i = 0; i < wireConnect.length; i++) {
				if (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == wireConnect[i]) {
					// Bottom
					side[0] = true;
				}

				if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == wireConnect[i]) {
					// Top
					side[1] = true;
				}

				if (worldObj.getBlockId(xCoord, yCoord, zCoord - 1) == wireConnect[i]) {
					// Back
					side[2] = true;
				}

				if (worldObj.getBlockId(xCoord, yCoord, zCoord + 1) == wireConnect[i]) {
					// Front
					side[3] = true;
				}

				if (worldObj.getBlockId(xCoord - 1, yCoord, zCoord) == wireConnect[i]) {
					// Left
					side[4] = true;
				}

				if (worldObj.getBlockId(xCoord + 1, yCoord, zCoord) == wireConnect[i]) {
					// Right
					side[5] = true;
				}
			}

			if (side[0]) {
				// Bottom
				PacketHandler.sendSidePacket(true, 0, xCoord, yCoord, zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 0, xCoord, yCoord, zCoord);
			}

			if (side[1]) {
				// Top
				PacketHandler.sendSidePacket(true, 1, xCoord, yCoord, zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 1, xCoord, yCoord, zCoord);
			}

			if (side[2]) {
				// Back
				PacketHandler.sendSidePacket(true, 2, xCoord, yCoord, zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 2, xCoord, yCoord, zCoord);
			}

			if (side[3]) {
				// Front
				PacketHandler.sendSidePacket(true, 3, xCoord, yCoord, zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 3, xCoord, yCoord, zCoord);
			}

			if (side[4]) {
				// Left
				PacketHandler.sendSidePacket(true, 4, xCoord, yCoord, zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 4, xCoord, yCoord, zCoord);
			}

			if (side[5]) {
				// Right
				PacketHandler.sendSidePacket(true, 5, xCoord, yCoord, zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 5, xCoord, yCoord, zCoord);
			}
		}
	}

	public boolean[] getSides() {
		return sides;
	}

	public void setSide(boolean state, int side) {
		sides[side] = state;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
	public void updateRoutingEntry(Direction sideIncoming, RoutingEntry entry){
		updateRoutingEntry(sideIncoming, entry, true);
	}
	
	/**
	 * See {@link Wire#updateRoutingEntry(Direction, RoutingEntry)}
	 * @param sideIncoming
	 * @param entry
	 * @param propagate Whether the change should be propagated to the neighbors. 
	 */
	private void updateRoutingEntry(Direction sideIncoming, RoutingEntry entry, boolean propagate) {
		if(routingEntries.containsKey(entry.getDest()))
		{
			RoutingEntry re = routingEntries.get(entry.getDest());
			
			if(re.getTravelCost() <= entry.getTravelCost()){
				
				//No reduction in Travelcost -Larethian
				
				if(re.getRequested() != entry.getRequested()){
					//requested amount changed
					routingEntries.put(re.getDest(), new RoutingEntry(re.getDest(), re.getTravelCost(), re.getNextHop(), entry.getRequested()));
					if(propagate)propagate(sideIncoming, routingEntries.get(re.getDest()));
				}
				else{
					//do nothing, as it wouldnt change anything anyway -Larethian
				}
			}
			else
			{
				//Reduction in travelcost detected -Larethian
				routingEntries.put(entry.getDest(), new RoutingEntry(entry.getDest(), entry.getTravelCost()+this.getResistance(), sideIncoming, entry.getRequested()));
				if(propagate)propagate(sideIncoming, routingEntries.get(entry.getDest()));
			}
		}
		else
		{
			routingEntries.put(entry.getDest(), new RoutingEntry(entry.getDest(), entry.getTravelCost()+this.getResistance(), sideIncoming, entry.getRequested()));
			if(propagate)propagate(sideIncoming, routingEntries.get(entry.getDest()));
		}
	}
	
	private void propagate(Direction sideIncoming, RoutingEntry entry){
		for (Direction dir : Direction.values()) {
			if(dir != sideIncoming)//this is to prevent that two nodes bounce update-messages forth and back accidently -Larethian
			{
				TileEntity te = worldObj.getBlockTileEntity(this.xCoord+dir.xOffset, this.yCoord+dir.yOffset, this.zCoord+dir.zOffset); 
				
				if(te instanceof Wire){
					((Wire) te).updateRoutingEntry(sideIncoming.getOpposite(), entry);
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
		return 1;	//TODO Find maybe something better than simply 1 -Larethian
	}
	
	@Override
	public void init(){
		System.out.println("I'm called!");
		
		//get from every direction... -Larethian
		for(Direction dir : Direction.values()){
			TileEntity te = worldObj.getBlockTileEntity(xCoord+dir.xOffset, yCoord+dir.yOffset, zCoord+dir.zOffset);
			
			if(te instanceof Wire){
				//The routing entrys and add them to our own list -Larethian
				for (RoutingEntry re : ((Wire) te).getRoutingEntrys().values()) {
					updateRoutingEntry(dir, re, false);
				}
			}
		}
		if(routingEntries.isEmpty()) return; //early escape, preventing some calls which would consume time -Larethian
		
		//for every RoutingEntry... -Larethian
		for(RoutingEntry re : this.getRoutingEntrys().values()){
			//propagate in every direction. -Larethian
			for(Direction dir : Direction.values()){
				TileEntity te = worldObj.getBlockTileEntity(xCoord+dir.xOffset, yCoord+dir.yOffset, zCoord+dir.zOffset);
				
				if(te instanceof Wire){
					((Wire) te).updateRoutingEntry(dir, re);
				}
			}
		}
	}
}
