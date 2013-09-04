package RE.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import RE.network.PacketHandler;

public class TileEntitySolarPanel extends TileEntity implements IInventory {

	private int volt = 0;
	private int voltMax = 1000;
	private int voltPerTick = 2;
	public double sun = 0;

	public TileEntitySolarPanel() {

	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (worldObj.isDaytime()) {

				sun = 0;

				if (noBlockAbove() && !worldObj.isRaining()) {
					double time = worldObj.getWorldTime() - 24000 * (int) (worldObj.getWorldTime() / 24000);
					sun = Math.sin(((Math.PI / 2) / 6000) * time);
				}

				PacketHandler.sendSunPacket(sun, xCoord, yCoord, zCoord);
			}

			setVolt(volt + (int) (voltPerTick * sun));

			PacketHandler.sendVoltPacket(volt, xCoord, yCoord, zCoord);
		}
	}

	public double getSun() {
		return sun;
	}

	@SideOnly(Side.CLIENT)
	public void setSun(double stand) {
		sun = stand;
	}

	public boolean noBlockAbove() {
		int heightMax = worldObj.getActualHeight();

		for (int i = yCoord + 1; i < heightMax; i++) {
			if ((worldObj.getBlockId(xCoord, i, zCoord) != Block.glass.blockID) && (worldObj.getBlockId(xCoord, i, zCoord) != 0)) {
				return false;
			}
		}

		return true;
	}

	public void setVolt(int volt) {
		if (volt > voltMax) {
			this.volt = voltMax;
		} else {
			this.volt = volt;
		}
	}

	public int getVolt() {
		return volt;
	}

	public int getMaxVolt() {
		return voltMax;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		System.out.println("SAVE!");

		compound.setInteger("Volt", volt);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		volt = compound.getInteger("Volt");
		PacketHandler.sendVoltPacket(volt, xCoord, yCoord, zCoord);
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

	}

	@Override
	public String getInvName() {
		return "InventorySolarPanel";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

}
