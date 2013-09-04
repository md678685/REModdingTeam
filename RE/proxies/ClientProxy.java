package RE.proxies;

import RE.blocks.BlockInfo;
import RE.client.renderer.RenderWire;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initSounds() {
		// Init all the sounds
		
	}

	@Override
	public void initRenderers() {
		// Init the rendering stuff
		RenderWire wireRender = new RenderWire();
		BlockInfo.WIRE_RENDER_ID = wireRender.getRenderId();
		RenderingRegistry.registerBlockHandler(wireRender);
	}

}
