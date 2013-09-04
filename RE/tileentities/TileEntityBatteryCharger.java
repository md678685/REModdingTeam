package RE.tileentities;

import java.util.Map;

import RE.routing.interfaces.Destination;
import RE.routing.util.RoutingEntry;
import RE.util.Direction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBatteryCharger extends TileEntity implements Destination{

	@Override
	public void updateEntity() {

	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}

	@Override
	public void updateRoutingEntry(Direction sideIncoming, RoutingEntry entry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Destination, RoutingEntry> getRoutingEntrys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
