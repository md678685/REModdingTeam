package RealElectricity;

import RealElectricity.lib.REference;
import RealElectricity.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

// @Mod and @NetworkMod stuff -MD678685
@Mod(modid = REference.MODID, name = REference.MODNAME, version = REference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class RE {
	
	// Why is this used? GenericMod tutorials say this is used but I can't remember what for -MD678685
	@Instance(value = REference.MODID)
	public static RE instance;
	
	// @SidedProxy -MD678685
	@SidedProxy(clientSide=REference.CLIENTPROXY, serverSide=REference.COMMONPROXY)
	public static CommonProxy proxy;
	
	// preInit, load and postInit methods -MD678685
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
