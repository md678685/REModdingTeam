package RE.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import RE.tileentities.TileEntityWire;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderWire implements ISimpleBlockRenderingHandler {

	private int id;

	public RenderWire() {
		id = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);

		GL11.glPushMatrix();
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		Tessellator tessellator = Tessellator.instance;

		// Bottom
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, metadata));
		tessellator.draw();

		// Top
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(1, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(2, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0, 0, 0, block.getIcon(3, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(4, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0, 0, 0, block.getIcon(5, metadata));
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator.instance.setColorOpaque_F(1F, 1F, 1F);

		TileEntity te = world.getBlockTileEntity(x, y, z);

		float startX = 0.35F;
		float endX = 0.65F;

		float startY = 0.35F;
		float endY = 0.65F;

		float startZ = 0.35F;
		float endZ = 0.65F;

		if (te != null) {
			boolean[] sides = ((TileEntityWire) te).getSides();

			System.out.println("sides: " + sides[0]);

			if (sides[0]) { // Bottom
				startY = 0F;
			}

			if (sides[1]) { // Top
				endY = 1F;
			}

			if (sides[2]) { // Back
				startZ = 0F;
			}

			if (sides[3]) { // Front
				endZ = 1F;
			}

			if (sides[4]) { // Left
				startX = 0F;
			}

			if (sides[5]) { // Right
				endX = 1F;
			}
		}

		// Center
		block.setBlockBounds(0.35F, 0.35F, 0.35F, 0.65F, 0.65F, 0.65F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		// Bottom
		block.setBlockBounds(0.35F, startY, 0.35F, 0.65F, 0.35F, 0.65F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		// Top
		block.setBlockBounds(0.35F, 0.65F, 0.35F, 0.65F, endY, 0.65F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		// Front
		block.setBlockBounds(0.35F, 0.35F, startZ, 0.65F, 0.65F, 0.35F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		// Back
		block.setBlockBounds(0.35F, 0.35F, 0.65F, 0.65F, 0.65F, endZ);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		// Left
		block.setBlockBounds(startX, 0.35F, 0.35F, 0.35F, 0.65F, 0.65F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		// Right
		block.setBlockBounds(0.65F, 0.35F, 0.35F, endX, 0.65F, 0.65F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}

}
