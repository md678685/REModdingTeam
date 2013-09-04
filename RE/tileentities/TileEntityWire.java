package RE.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import RE.blocks.BlockInfo;
import RE.network.PacketHandler;
import RE.routing.RoutingDestination;

public class TileEntityWire extends TileEntity {

	boolean[] sides = new boolean[6];
	int[] wireConnect = { BlockInfo.WIRE_ID, BlockInfo.GENERATOR_ID, BlockInfo.SOLARPANEL_ID };
	RoutingDestination routing = new RoutingDestination();

	// Routing Table:
	// Source: Coords, Resistance, Sends
	// Destination: Coords, Resistance, Requests

	public TileEntityWire() {

	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

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

}
