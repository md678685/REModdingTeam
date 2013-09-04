package RE.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import RE.RealElectricity;
import RE.tileentities.TileEntityBatteryCharger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBatteryCharger extends BlockContainer {

	protected BlockBatteryCharger(int id) {
		super(id, Material.rock);

		setCreativeTab(RealElectricity.creativeTab);
		setHardness(2F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName(BlockInfo.BATTERYCHARGER_UNLOCALIZED_NAME);
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

	@SideOnly(Side.CLIENT)
	private Icon frontOffIcon;
	@SideOnly(Side.CLIENT)
	private Icon frontOnIcon;
	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {
		frontOffIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.BATTERYCHARGER_FRONT_OFF_ICON);
		frontOnIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.BATTERYCHARGER_FRONT_ON_ICON);
		topIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.MACHINE_TOP_ICON);
		sideIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.MACHINE_SIDE_ICON);
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

		return sideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBatteryCharger();
	}

}
