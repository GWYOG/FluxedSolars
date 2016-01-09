package primetoxinz.fluxedsolars;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config extends Configuration {
	public Config(File suggestedConfigurationFile) {
		super(suggestedConfigurationFile);
	}



	public void preInit(FMLPreInitializationEvent e) {
		this.load();
		String category = "";
		String name = "";
		String comment = "";
		this.save();
	}
}
