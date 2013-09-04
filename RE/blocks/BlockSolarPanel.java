package RE.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import RE.RealElectricity;
import RE.tileentities.TileEntitySolarPanel;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSolarPanel extends BlockContainer {

	protected BlockSolarPanel(int id) {
		super(id, Material.rock);

		setCreativeTab(RealElectricity.creativeTab);
		setHardness(2F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName(BlockInfo.SOLARPANEL_UNLOCALIZED_NAME);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(entityPlayer, RealElectricity.instance, 1, world, x, y, z);
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	@SideOnly(Side.CLIENT)
	private Icon bottomIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {
		topIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.SOLARPANEL_TOP_ICON);
		bottomIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.WIRE_ATTACHMENT_ICON);
		sideIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.SOLARPANEL_SIDE_ICON);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		if (side == 0) { // Bottom
			return bottomIcon;
		} else if (side == 1) { // Top
			return topIcon;
		}

		return sideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySolarPanel();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntitySolarPanel) {
			IInventory inventory = (IInventory) te;

			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack itemstack = inventory.getStackInSlotOnClosing(i);

				if (itemstack != null) {
					float spawnX = x + world.rand.nextFloat();
					float spawnY = y + world.rand.nextFloat();
					float spawnZ = z + world.rand.nextFloat();

					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, itemstack);

					float mult = 0.05F;

					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
					droppedItem.motionY = (4F + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;

					world.spawnEntityInWorld(droppedItem);
				}
			}
		}

		super.breakBlock(world, x, y, z, id, meta);
	}

}
