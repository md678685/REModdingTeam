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
		this.routingEntries = new HashMap<Destination,RoutingEntry>();
	}

	
	private boolean initDone = false;
	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			if(!this.initDone){
				init(); this.initDone = true;	//Just to initialize the routingtable
			}
			
			boolean[] side = new boolean[6];

			for (int i = 0; i < side.length; i++) {
				side[i] = false;
			}

			for (int i = 0; i < this.wireConnect.length; i++) {
				if (this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord) == this.wireConnect[i]) {
					// Bottom
					side[0] = true;
				}

				if (this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) == this.wireConnect[i]) {
					// Top
					side[1] = true;
				}

				if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) == this.wireConnect[i]) {
					// Back
					side[2] = true;
				}

				if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) == this.wireConnect[i]) {
					// Front
					side[3] = true;
				}

				if (this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) == this.wireConnect[i]) {
					// Left
					side[4] = true;
				}

				if (this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) == this.wireConnect[i]) {
					// Right
					side[5] = true;
				}
			}

			if (side[0]) {
				// Bottom
				PacketHandler.sendSidePacket(true, 0, this.xCoord, this.yCoord, this.zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 0, this.xCoord, this.yCoord, this.zCoord);
			}

			if (side[1]) {
				// Top
				PacketHandler.sendSidePacket(true, 1, this.xCoord, this.yCoord, this.zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 1, this.xCoord, this.yCoord, this.zCoord);
			}

			if (side[2]) {
				// Back
				PacketHandler.sendSidePacket(true, 2, this.xCoord, this.yCoord, this.zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 2, this.xCoord, this.yCoord, this.zCoord);
			}

			if (side[3]) {
				// Front
				PacketHandler.sendSidePacket(true, 3, this.xCoord, this.yCoord, this.zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 3, this.xCoord, this.yCoord, this.zCoord);
			}

			if (side[4]) {
				// Left
				PacketHandler.sendSidePacket(true, 4, this.xCoord, this.yCoord, this.zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 4, this.xCoord, this.yCoord, this.zCoord);
			}

			if (side[5]) {
				// Right
				PacketHandler.sendSidePacket(true, 5, this.xCoord, this.yCoord, this.zCoord);
			} else {
				PacketHandler.sendSidePacket(false, 5, this.xCoord, this.yCoord, this.zCoord);
			}
		}
	}

	public boolean[] getSides() {
		return this.sides;
	}

	public void setSide(boolean state, int side) {
		this.sides[side] = state;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
		if(this.routingEntries.containsKey(entry.getDest()))
		{
			RoutingEntry re = this.routingEntries.get(entry.getDest());
			
			if(re.getTravelCost() <= entry.getTravelCost()){
				
				//No reduction in Travelcost -Larethian
				
				if(re.getRequested() != entry.getRequested()){
					//requested amount changed
					this.routingEntries.put(re.getDest(), new RoutingEntry(re.getDest(), re.getTravelCost(), re.getNextHop(), entry.getRequested()));
					if(propagate)propagate(sideIncoming, this.routingEntries.get(re.getDest()));
				}
				else{
					//do nothing, as it wouldnt change anything anyway -Larethian
				}
			}
			else
			{
				//Reduction in travelcost detected -Larethian
				this.routingEntries.put(entry.getDest(), new RoutingEntry(entry.getDest(), entry.getTravelCost()+this.getResistance(), sideIncoming, entry.getRequested()));
				if(propagate)propagate(sideIncoming, this.routingEntries.get(entry.getDest()));
			}
		}
		else
		{
			this.routingEntries.put(entry.getDest(), new RoutingEntry(entry.getDest(), entry.getTravelCost()+this.getResistance(), sideIncoming, entry.getRequested()));
			if(propagate)propagate(sideIncoming, this.routingEntries.get(entry.getDest()));
		}
	}
	
	private void propagate(Direction sideIncoming, RoutingEntry entry){
		for (Direction dir : Direction.values()) {
			if(dir != sideIncoming)//this is to prevent that two nodes bounce update-messages forth and back accidently -Larethian
			{
				TileEntity te = this.worldObj.getBlockTileEntity(this.xCoord+dir.xOffset, this.yCoord+dir.yOffset, this.zCoord+dir.zOffset); 
				
				if(te instanceof Wire){
					((Wire) te).updateRoutingEntry(sideIncoming.getOpposite(), entry);
				}
			}
		}
	}

	@Override
	public Map<Destination, RoutingEntry> getRoutingEntrys() {
		return this.routingEntries;
	}

	@Override
	public int getResistance() {
		return 1;	//TODO Find maybe something better than simply 1 -Larethian
	}
	
	@Override
	public void init(){
		//get from every direction... -Larethian
		for(Direction dir : Direction.values()){
			TileEntity te = this.worldObj.getBlockTileEntity(this.xCoord+dir.xOffset, this.yCoord+dir.yOffset, this.zCoord+dir.zOffset);
			
			if(te instanceof Wire){
				//The routing entrys and add them to our own list -Larethian
				for (RoutingEntry re : ((Wire) te).getRoutingEntrys().values()) {
					updateRoutingEntry(dir, re, false);
				}
			}
		}
		if(this.routingEntries.isEmpty()) return; //early escape, preventing some calls which would consume time -Larethian
		
		//for every RoutingEntry... -Larethian
		for(RoutingEntry re : this.getRoutingEntrys().values()){
			//propagate in every direction. -Larethian
			for(Direction dir : Direction.values()){
				TileEntity te = this.worldObj.getBlockTileEntity(this.xCoord+dir.xOffset, this.yCoord+dir.yOffset, this.zCoord+dir.zOffset);
				
				if(te instanceof Wire){
					((Wire) te).updateRoutingEntry(dir, re);
				}
			}
		}
		
		for (RoutingEntry re : routingEntries.values()) {
			System.out.println(re);
		}
		System.out.println();
	}
}
