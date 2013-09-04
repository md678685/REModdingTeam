package RE.client.interfaces.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotBurning extends Slot {

	public SlotBurning(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.itemID == Item.coal.itemID;
	}

}
