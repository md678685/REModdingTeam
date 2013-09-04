package RE.client.interfaces;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import RE.client.interfaces.container.ContainerGenerator;
import RE.tileentities.TileEntityGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGenerator extends GuiContainer {

	TileEntityGenerator generator;

	public GuiGenerator(InventoryPlayer invPlayer, TileEntityGenerator generator) {
		super(new ContainerGenerator(invPlayer, generator));

		this.generator = generator;

		xSize = 176;
		ySize = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("re", "textures/gui/generator.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		
		int mouseX = x - guiLeft;
		int mouseY = y - guiTop;
		
		fontRenderer.drawString("Generator", 8, 6, 0x404040);

		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);

		int percentage = (int) (((float) generator.getVolt() / (float) generator.getMaxVolt()) * 16);

		drawTexturedModalRect(80, 53, xSize, 0, percentage, 4);

		if((mouseX >= 79) && (mouseX <= (79 + 18)) && (mouseY >= 52) && (mouseY <= (52 + 6))){
			drawCreativeTabHoveringText(generator.getVolt() + "V / " + generator.getMaxVolt() + "V", mouseX, mouseY);
		}
		
		System.out.println("x:" + mouseX + ", y:" + mouseY);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();

		buttonList.add(new GuiButton(0, guiLeft + 100 + 30 * 0, guiTop + 14 + 25 * 0, 25, 20, "Up"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		// PacketHandler.sendButtonPacket((byte) button.id);
	}

}
