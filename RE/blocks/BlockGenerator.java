package RE.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import RE.RealElectricity;
import RE.tileentities.TileEntityGenerator;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGenerator extends BlockContainer {

	public BlockGenerator(int id) {
		super(id, Material.rock);

		setCreativeTab(RealElectricity.creativeTab);
		setHardness(2F); // 0.8F
		setStepSound(soundStoneFootstep);
		setUnlocalizedName(BlockInfo.GENERATOR_UNLOCALIZED_NAME);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
		int playerSideLook = MathHelper.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (playerSideLook == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (playerSideLook == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (playerSideLook == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (playerSideLook == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		System.out.println("Side: " + world.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(entityPlayer, RealElectricity.instance, 0, world, x, y, z);
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	private Icon frontOffIcon;
	@SideOnly(Side.CLIENT)
	private Icon frontOnIcon;
	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon wireSideIcon;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {
		frontOffIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.GENERATOR_FRONT_OFF_ICON);
		frontOnIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.GENERATOR_FRONT_ON_ICON);
		topIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.MACHINE_TOP_ICON);
		sideIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.MACHINE_SIDE_ICON);
		wireSideIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.WIRE_ATTACHMENT_ICON);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		if (meta == 0 || meta == 1) {
			meta = 3;
		}

		if (side == meta) {
			return frontOffIcon;
		}

		if (side == 0) { // Bottom
			return topIcon;
		} else if (side == 1) { // Top
			return topIcon;
		}

		return wireSideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityGenerator();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityGenerator) {
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
