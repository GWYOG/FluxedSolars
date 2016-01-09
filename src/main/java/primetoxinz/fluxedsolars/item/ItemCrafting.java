package primetoxinz.fluxedsolars.item;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrafting extends Item {
	protected String name;
	protected String[] subnames;
	public ItemCrafting(String name,String... subnames) {
		this.name = name;
		this.subnames = subnames;
		GameRegistry.registerItem(this, name);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName(name);
		setHasSubtypes(true);
	}
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for(int i = 0; i< subnames.length;i++) {
			subItems.add(new ItemStack(this,1,i));
		}
	}
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName()+"-"+subnames[stack.getItemDamage()];
	}
	@SideOnly(Side.CLIENT)
	public void preInit() {
		for(int i = 0; i< subnames.length;i++) {
			String name = "fluxedsolars:"+this.name+"-"+subnames[i];
			System.out.println("registering model for " + name);
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(name, "inventory"));
		}
	}
	
		
}
