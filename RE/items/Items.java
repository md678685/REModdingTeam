package RE.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Items {

	public static Item lamp;

	public static void init() {
		lamp = new ItemLamp(ItemInfo.LAMP_ID);
	}

	public static void addNames() {
		LanguageRegistry.addName(lamp, ItemInfo.LAMP_NAME);
	}

	public static void registerRecipes(){
		GameRegistry.addRecipe(new ItemStack(lamp),
			new Object[] {	"GGG",
							"G G",
							" W ",
							
							'G', Block.glass,
							'W', Item.redstone
		});
	}
}
