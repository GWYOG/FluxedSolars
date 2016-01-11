package primetoxinz.fluxedsolars;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import primetoxinz.fluxedsolars.tile.SolarPanelType;

public class Config extends Configuration {
	public static final int[] default_energypertick= new int[]{64,512,4096,32786};
	public static final int[] default_energystorage= new int[]{64000,512000,4096000,32786000};
	public Config(File suggestedConfigurationFile) {
		super(suggestedConfigurationFile);
	}


	
	public void preInit(FMLPreInitializationEvent e) {
		this.load();
		String category = "Energy";
		String name = "";
		String comment = "";
		for(SolarPanelType type:SolarPanelType.VALUES)  {
			name = type.getName()+"_energypertick";
			comment = "Change energy production of " + type.getName() + " tier";
			type.energypertick=getInt(name, category, default_energypertick[type.ordinal()], 0, Integer.MAX_VALUE, comment);
			name = type.getName()+"_energyenergystorage";
			comment = "Change energy storage of " + type.getName() + " tier";
			type.energystorage=getInt(name, category, default_energystorage[type.ordinal()], 0, Integer.MAX_VALUE, comment);

		}
		this.save();
	}
}

