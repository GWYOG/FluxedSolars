package primetoxinz.fluxedsolars.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import primetoxinz.fluxedsolars.block.BlockSolar;
import primetoxinz.fluxedsolars.tile.SolarPanelType;

public class ItemBlockSolar extends ItemBlock {
	public ItemBlockSolar(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabAllSearch);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + ((BlockSolar)block).getStateFromMeta(stack.getItemDamage()).getValue(BlockSolar.TIER).getName();
	}
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		int meta = stack.getItemDamage();
		String info = "§a"+SolarPanelType.VALUES[meta].energyoutput + " RF / t";
		
		tooltip.add(info);
		if(SolarPanelType.VALUES[meta]==SolarPanelType.BLAZE)
			tooltip.add("Also works in the Nether");
		else if(SolarPanelType.VALUES[meta]==SolarPanelType.ENDER)
			tooltip.add("Works everywhere but the Nether");
	}
	@Override
	public int getMetadata(int damage) {
		return damage;
	}

}
