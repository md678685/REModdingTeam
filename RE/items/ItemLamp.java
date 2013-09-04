package RE.items;

import java.util.List;

import RE.RealElectricity;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLamp extends Item {

	public ItemLamp(int id) {
		super(id);

		setCreativeTab(RealElectricity.creativeTab);
		setMaxStackSize(16);
		setUnlocalizedName(ItemInfo.LAMP_UNLOCALIZED_NAME);
	}

	/*
	 * @Override public boolean func_111207_a(ItemStack itemStack, EntityPlayer player, EntityLivingBase target) { if (!target.worldObj.isRemote) { target.motionY = 2; itemStack.setItemDamage(itemStack.getItemDamage() + 1); }
	 * 
	 * return false; }
	 */

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("5W");
		info.add("230V");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon(ItemInfo.TEXTURE_LOCATION + ":" + ItemInfo.LAMP_ICON);
	}

}
