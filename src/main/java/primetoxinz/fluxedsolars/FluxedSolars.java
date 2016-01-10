package primetoxinz.fluxedsolars;

import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import primetoxinz.fluxedsolars.block.BlockSolar;
import primetoxinz.fluxedsolars.item.ItemBlockSolar;
import primetoxinz.fluxedsolars.item.ItemCrafting;
import primetoxinz.fluxedsolars.tile.TileSolar;

@Mod(modid = FluxedSolars.MOD_ID, name = FluxedSolars.MOD_ID, version = "$GRADLEVERSION", dependencies = FluxedSolars.DEP, acceptedMinecraftVersions="[1.8.8,1.8.9]")
public class FluxedSolars {

	public static final String MOD_ID = "fluxedsolars";
	public static final String DEP = "required-after:Forge;";
	@Mod.Instance(MOD_ID)
	public static FluxedSolars instance;

	@SidedProxy(serverSide = "primetoxinz.fluxedsolars.CommonProxy", clientSide = "primetoxinz.fluxedsolars.ClientProxy", modId = MOD_ID)
	public static CommonProxy proxy;

	public static Config config;

	public static Block solar;
	public static ItemCrafting craftingitem;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		config = new Config(e.getSuggestedConfigurationFile());
		config.preInit(e);
		solar = new BlockSolar("solarpanel", ItemBlockSolar.class, TileSolar.class);
		craftingitem = new ItemCrafting("craftingitem","solarcollector");
		proxy.preInit(e);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		addRecipes();
		if(Loader.isModLoaded("Waila")) {
			info("Initializing Waila Compat");
			new WailaModule().init(e);
		}
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	public static void addRecipes() {
		ItemStack solarpanel = new ItemStack(solar, 1, 0);
		ItemStack solarcollector = new ItemStack(craftingitem, 4, 0);
		GameRegistry.addRecipe(new ShapedOreRecipe(solarcollector, "GGG", "LLL", "IRI", 'G',
				new ItemStack(Blocks.glass_pane), 'L', "gemLapis", 'I', "ingotIron", 'R', "dustRedstone"));

		GameRegistry.addRecipe(new ShapedOreRecipe(solarpanel, "CCC", "IRI", "IRI", 'C', solarcollector, 'I',
				"ingotIron", 'R', "dustRedstone"));
		solarpanel.setItemDamage(1);
		GameRegistry.addRecipe(new ShapedOreRecipe(solarpanel, "CCC", "gRg", "gRg", 'C', solarcollector, 'R',
				"dustRedstone", 'g', "blockGold"));
		solarpanel.setItemDamage(2);
		GameRegistry.addRecipe(new ShapedOreRecipe(solarpanel, "CCC", "bRb", "gRg", 'C', solarcollector, 'R',
				"dustRedstone", 'b', new ItemStack(Items.blaze_rod), 'g', "blockGold"));
		solarpanel.setItemDamage(3);
		GameRegistry.addRecipe(new ShapedOreRecipe(solarpanel, "CCC", "pRp", "ERE", 'C', solarcollector, 'R',
				"dustRedstone", 'p', new ItemStack(Items.ender_pearl), 'E', new ItemStack(Blocks.end_stone)));

	}
	public static void info(String info) {
		FMLLog.info("["+MOD_ID+"]:"+info);
	}
}
