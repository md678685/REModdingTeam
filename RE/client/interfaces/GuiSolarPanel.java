package RE.client.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import RE.client.interfaces.container.ContainerSolarPanel;
import RE.tileentities.TileEntitySolarPanel;

public class GuiSolarPanel extends GuiContainer {

	TileEntitySolarPanel solarPanel;

	public GuiSolarPanel(InventoryPlayer invPlayer, TileEntitySolarPanel solarPanel) {
		super(new ContainerSolarPanel(invPlayer, solarPanel));

		this.solarPanel = solarPanel;

		xSize = 176;
		ySize = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("re", "textures/gui/solarpanel.png");

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

		fontRenderer.drawString("Solar Panel", 8, 6, 0x404040);

		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);

		int percentage = (int) (((float) solarPanel.getVolt() / (float) solarPanel.getMaxVolt()) * 16);

		drawTexturedModalRect(80, 53, xSize, 0, percentage, 4);

		percentage = (int) (solarPanel.getSun() * 16);

		drawTexturedModalRect(80, 30, xSize, 4, percentage, 16);

		if ((mouseX >= 79) && (mouseX <= (79 + 18)) && (mouseY >= 52) && (mouseY <= (52 + 6))) {
			drawCreativeTabHoveringText(solarPanel.getVolt() + "V / " + solarPanel.getMaxVolt() + "V", mouseX, mouseY);
		}

		if ((mouseX >= 79) && (mouseX <= (79 + 18)) && (mouseY >= 29) && (mouseY <= (29 + 18))) {
			drawCreativeTabHoveringText((int) (solarPanel.getSun() * 100) + "% Sun Light", mouseX, mouseY);
		}

		System.out.println("x:" + mouseX + ", y:" + mouseY);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		// PacketHandler.sendButtonPacket((byte) button.id);
	}

}
