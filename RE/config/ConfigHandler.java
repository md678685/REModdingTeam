package RE.config;

import java.io.File;

import RE.blocks.BlockInfo;
import RE.items.ItemInfo;

import net.minecraftforge.common.Configuration;

public class ConfigHandler {

	public static void init(File file) {
		Configuration config = new Configuration(file);

		config.load();
		// Item
		ItemInfo.LAMP_ID = config.getItem(ItemInfo.LAMP_KEY, ItemInfo.LAMP_DEFAULT).getInt() - 256;

		// Block
		BlockInfo.GENERATOR_ID = config.getBlock(BlockInfo.GENERATOR_KEY, BlockInfo.GENERATOR_DEFAULT).getInt();
		BlockInfo.SOLARPANEL_ID = config.getBlock(BlockInfo.SOLARPANEL_KEY, BlockInfo.SOLARPANEL_DEFAULT).getInt();
		BlockInfo.WIRE_ID = config.getBlock(BlockInfo.WIRE_KEY, BlockInfo.WIRE_DEFAULT).getInt();

		config.save();
	}

}
