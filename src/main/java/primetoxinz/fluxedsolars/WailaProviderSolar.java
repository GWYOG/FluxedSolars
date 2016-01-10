package primetoxinz.fluxedsolars;

import java.util.List;

import com.sun.java.accessibility.util.java.awt.ListTranslator;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import primetoxinz.fluxedsolars.block.BlockSolar;
import primetoxinz.fluxedsolars.tile.TileSolar;
@Optional.Interface(iface="mcp.mobius.waila.api.IWailaDataProvider",modid="Waila")
public class WailaProviderSolar implements IWailaDataProvider {
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		ItemStack stack = accessor.getStack();
		stack.setItemDamage(accessor.getBlockState().getValue(BlockSolar.TIER).ordinal());
		return stack;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileSolar tile = (TileSolar)accessor.getTileEntity();
		currenttip.add(tile.getEnergyStored(EnumFacing.DOWN)+"/"+tile.getMaxEnergyStored(EnumFacing.DOWN));
		currenttip.add(tile.running ? "§aOn" : "§cOff"); 
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
	
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world,
			BlockPos pos) {
		
		return tag;
	}

}

