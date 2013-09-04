package RE.tileentities;

import java.util.ArrayList;

import RE.network.PacketHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityGenerator extends TileEntity implements IInventory {

	private ItemStack[] items;
	private int volt = 50;
	private int voltMax = 100;

	public TileEntityGenerator() {
		items = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		
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

		// Save Items In Inventory
		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack itemstack = getStackInSlot(i);

			if (itemstack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				itemstack.writeToNBT(item);
				items.appendTag(item);
			}
		}

		compound.setTag("Items", items);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		// Load Items For Inventory
		NBTTagList items = compound.getTagList("Items");

		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
			int slot = item.getByte("Slot");

			if (slot >= 0 && slot <= getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int count) {

		ItemStack itemstack = getStackInSlot(i);

		if (itemstack != null) {
			if (itemstack.stackSize <= count) {
				setInventorySlotContents(i, null);
			} else {
				itemstack = itemstack.splitStack(count);
				onInventoryChanged();
			}
		}

		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack itemstack = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		items[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}

		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "InventoryGenerator";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
		return itemstack.itemID == Item.coal.itemID;
	}
}
