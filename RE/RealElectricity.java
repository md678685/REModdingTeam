package RE;

import net.minecraft.creativetab.CreativeTabs;
import RE.blocks.Blocks;
import RE.client.interfaces.GuiHandler;
import RE.config.ConfigHandler;
import RE.items.Items;
import RE.network.PacketHandler;
import RE.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.CHANNEL }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class RealElectricity {

	// F3 + H = debug shows ids

	public static CreativeTabs creativeTab = new RealElectricityTab("RETab");

	@Instance(ModInfo.ID)
	public static RealElectricity instance;

	@SidedProxy(clientSide = "RE.proxies.ClientProxy", serverSide = "RE.proxies.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// This runs before initialization fase

		ConfigHandler.init(event.getSuggestedConfigurationFile());
		Items.init();
		Blocks.init();

		proxy.initSounds();
		proxy.initRenderers();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		// Initialization fase
		LanguageRegistry.instance().addStringLocalization("itemGroup.RETab", "en_US", "Real Electricity");

		Items.addNames();
		Items.registerRecipes();

		Blocks.addNames();
		Blocks.registerRecipes();
		Blocks.registerTileEntities();

		new GuiHandler();
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {
		// After all mods has been loaded

	}

}
