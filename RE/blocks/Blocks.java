package RE.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import RE.tileentities.TileEntityGenerator;
import RE.tileentities.TileEntitySolarPanel;
import RE.tileentities.TileEntityWire;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Blocks {

	public static Block generator;
	public static Block solarPanel;
	public static Block wire;

	public static void init() {
		generator = new BlockGenerator(BlockInfo.GENERATOR_ID);
		solarPanel = new BlockSolarPanel(BlockInfo.SOLARPANEL_ID);
		wire = new BlockWire(BlockInfo.WIRE_ID);

		GameRegistry.registerBlock(generator, BlockInfo.GENERATOR_KEY);
		GameRegistry.registerBlock(solarPanel, BlockInfo.SOLARPANEL_KEY);
		GameRegistry.registerBlock(wire, BlockInfo.WIRE_KEY);
	}

	public static void addNames() {
		LanguageRegistry.addName(generator, BlockInfo.GENERATOR_NAME);
		LanguageRegistry.addName(solarPanel, BlockInfo.SOLARPANEL_NAME);
		LanguageRegistry.addName(wire, BlockInfo.WIRE_NAME);
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityGenerator.class, BlockInfo.GENERATOR_TE_KEY);
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, BlockInfo.SOLARPANEL_TE_KEY);
		GameRegistry.registerTileEntity(TileEntityWire.class, BlockInfo.WIRE_TE_KEY);
	}

	public static void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(generator), new Object[] { "QQQ", "QFQ", "QQQ",

		'Q', Block.blockNetherQuartz, 'F', Block.furnaceIdle });

		GameRegistry.addRecipe(new ItemStack(solarPanel), new Object[] { "QDQ", "Q Q", "QQQ",

		'Q', Block.blockNetherQuartz, 'D', Block.daylightSensor });
	}

}
