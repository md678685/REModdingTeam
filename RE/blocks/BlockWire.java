package RE.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import RE.RealElectricity;
import RE.tileentities.TileEntityWire;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWire extends BlockContainer {

	public BlockWire(int id) {
		super(id, Material.cloth);

		setCreativeTab(RealElectricity.creativeTab);
		setHardness(2F);
		setStepSound(soundClothFootstep);
		setUnlocalizedName(BlockInfo.WIRE_UNLOCALIZED_NAME);
	}

	@SideOnly(Side.CLIENT)
	private Icon sideIcon;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {
		sideIcon = register.registerIcon(BlockInfo.TEXTURE_LOCATION + ":" + BlockInfo.WIRE_SIDE_ICON);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		return sideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWire();
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.collisionRayTrace(world, x, y, z, start, end);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0F, 0.35F, 0.35F, 1F, 0.65F, 0.65F);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return BlockInfo.WIRE_RENDER_ID;
	}

}
