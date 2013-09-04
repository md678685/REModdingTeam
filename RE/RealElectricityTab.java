package RE;

import net.minecraft.creativetab.CreativeTabs;
import RE.blocks.Blocks;

public class RealElectricityTab extends CreativeTabs {

	public RealElectricityTab(String label) {
		super(label);
	}

	// Blocks
	@Override
	public int getTabIconItemIndex() {
		return Blocks.solarPanel.blockID;
	}

	// Items
	/*
	 * public ItemStack getIconItemStack() { return new ItemStack(Item.eyeOfEnder); }
	 */

}
