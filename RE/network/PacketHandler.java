package RE.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import RE.ModInfo;
import RE.client.interfaces.container.ContainerGenerator;
import RE.tileentities.TileEntityGenerator;
import RE.tileentities.TileEntitySolarPanel;
import RE.tileentities.TileEntityWire;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);

		EntityPlayer entityPlayer = (EntityPlayer) player;

		byte packetId = reader.readByte();
		Container container = entityPlayer.openContainer;

		int x, y, z;
		World world;
		TileEntity te;

		switch (packetId) {
		case 0:
			double sun = reader.readDouble();
			x = reader.readInt();
			y = reader.readInt();
			z = reader.readInt();

			world = entityPlayer.worldObj;

			te = world.getBlockTileEntity(x, y, z);

			if (te != null && te instanceof TileEntitySolarPanel) {
				TileEntitySolarPanel solarPanel = (TileEntitySolarPanel) te;

				solarPanel.setSun(sun);
			}
			break;
		case 1:
			int volt = reader.readInt();
			x = reader.readInt();
			y = reader.readInt();
			z = reader.readInt();

			world = entityPlayer.worldObj;

			te = world.getBlockTileEntity(x, y, z);

			if (te != null) {
				if (te instanceof TileEntityGenerator) {
					TileEntityGenerator generator = (TileEntityGenerator) te;

					generator.setVolt(volt);
				} else if (te instanceof TileEntitySolarPanel) {
					TileEntitySolarPanel solarPanel = (TileEntitySolarPanel) te;

					solarPanel.setVolt(volt);
				}
			}
			break;
		case 2:
			boolean state = reader.readBoolean();
			int side = reader.readInt();
			x = reader.readInt();
			y = reader.readInt();
			z = reader.readInt();

			world = entityPlayer.worldObj;

			te = world.getBlockTileEntity(x, y, z);

			if (te != null) {
				if (te instanceof TileEntityWire) {
					TileEntityWire wire = (TileEntityWire) te;

					wire.setSide(state, side);
				}
			}
			break;
		case 3:
			byte buttonId = reader.readByte();

			if (container != null && container instanceof ContainerGenerator) {
				TileEntityGenerator generator = ((ContainerGenerator) container).getTileEntity();

				System.out.println("Remote:" + entityPlayer.worldObj.isRemote + ", buttonId:" + buttonId);

				// generator.fromButtonIdToSide(buttonId);
			}
			break;
		}
	}

	public static void sendSunPacket(double sun, int x, int y, int z) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 0); // id of packet
			dataStream.writeDouble(sun);
			dataStream.writeInt(x);
			dataStream.writeInt(y);
			dataStream.writeInt(z);

			PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
		} catch (IOException ex) {
			System.err.append("Failed to send button packet");
		}
	}

	public static void sendVoltPacket(int volt, int x, int y, int z) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 1); // id of packet
			dataStream.writeInt(volt);
			dataStream.writeInt(x);
			dataStream.writeInt(y);
			dataStream.writeInt(z);

			PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
		} catch (IOException ex) {
			System.err.append("Failed to send button packet");
		}
	}

	public static void sendSidePacket(boolean state, int side, int x, int y, int z) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 2); // id of packet
			dataStream.writeBoolean(state);
			dataStream.writeInt(side);
			dataStream.writeInt(x);
			dataStream.writeInt(y);
			dataStream.writeInt(z);

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
			PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
		} catch (IOException ex) {
			System.err.append("Failed to send button packet");
		}
	}

	public static void sendButtonPacket(byte id) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 3); // id of packet
			dataStream.writeByte(id);

			System.out.println("sendButtonPacket, id:" + id);

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
		} catch (IOException ex) {
			System.err.append("Failed to send button packet");
		}
	}
}
