package RE.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import RE.RealElectricity;
import RE.client.interfaces.container.ContainerGenerator;
import RE.client.interfaces.container.ContainerSolarPanel;
import RE.tileentities.TileEntityGenerator;
import RE.tileentities.TileEntitySolarPanel;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		NetworkRegistry.instance().registerGuiHandler(RealElectricity.instance, this);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);

		switch (ID) {
		case 0:
			if (te != null && te instanceof TileEntityGenerator) {
				return new ContainerGenerator(player.inventory, (TileEntityGenerator) te);
			}
			break;
		case 1:
			if (te != null && te instanceof TileEntitySolarPanel) {
				return new ContainerSolarPanel(player.inventory, (TileEntitySolarPanel) te);
			}
			break;
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);

		switch (ID) {
		case 0:
			if (te != null && te instanceof TileEntityGenerator) {
				return new GuiGenerator(player.inventory, (TileEntityGenerator) te);
			}
			break;
		case 1:

			if (te != null && te instanceof TileEntitySolarPanel) {
				return new GuiSolarPanel(player.inventory, (TileEntitySolarPanel) te);
			}
			break;
		}

		return null;
	}

}
