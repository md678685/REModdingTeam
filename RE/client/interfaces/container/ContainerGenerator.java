package RE.client.interfaces.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import RE.client.interfaces.slot.SlotBurning;
import RE.tileentities.TileEntityGenerator;

public class ContainerGenerator extends Container {

	private TileEntityGenerator generator;

	public ContainerGenerator(InventoryPlayer invPlayer, TileEntityGenerator generator) {
		this.generator = generator;

		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
			}
		}

		addSlotToContainer(new SlotBurning(generator, 0, 80, 30));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return generator.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int i) {
		Slot slot = getSlot(i);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack = slot.getStack();
			ItemStack result = itemstack.copy();

			if (i >= 36) {
				if (!mergeItemStack(itemstack, 0, 36, false)) {
					return null;
				}
			} else {
				if (itemstack.itemID == Item.coal.itemID) {
					if (!mergeItemStack(itemstack, 36, 36 + 1, false)) {
						if (i >= 0 && i <= 8) {
							if (!mergeItemStack(itemstack, 9, 36, false)) {
								return null;
							}
						} else if (i >= 9 && i <= 36) {
							if (!mergeItemStack(itemstack, 0, 9, false)) {
								return null;
							}
						} else {
							return null;
						}
					}
				} else {
					if (i >= 0 && i <= 8) {
						if (!mergeItemStack(itemstack, 9, 36, false)) {
							return null;
						}
					} else if (i >= 9 && i <= 36) {
						if (!mergeItemStack(itemstack, 0, 9, false)) {
							return null;
						}
					} else {
						return null;
					}
				}
			}

			if (itemstack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			slot.onPickupFromSlot(entityPlayer, itemstack);

			return result;
		}

		return null;
	}

	public TileEntityGenerator getTileEntity() {
		return generator;
	}

}
