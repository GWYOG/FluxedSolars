package primetoxinz.fluxedsolars;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import primetoxinz.fluxedsolars.tile.SolarPanelType;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		registerSolars(FluxedSolars.solar, 4);
		
		FluxedSolars.craftingitem.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	private static void registerSolars(Block block, int range) {
		Item item = Item.getItemFromBlock(block);
		for (int i = 0; i < range; i++) {
			String name = "fluxedsolars:solarpanel-" + SolarPanelType.VALUES[i].getName();
			ModelLoader.registerItemVariants(item, new ModelResourceLocation(name, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name, "inventory"));
		}
	}

	
}
