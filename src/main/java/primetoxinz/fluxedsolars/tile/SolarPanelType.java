package primetoxinz.fluxedsolars.tile;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.config.Configuration;
import primetoxinz.fluxedsolars.Config;
import primetoxinz.fluxedsolars.FluxedSolars;

public enum SolarPanelType implements IStringSerializable {
	REDSTONE(0,"redstone"),
	GOLD(1,"gold"),
	BLAZE(2,"blaze"),
	ENDER(3,"ender");
	
	SolarPanelType(int id,String name) {
		this.name = name;
		this.id = id;
	}
	private final String name;
	private final int id;
	public int energypertick;
	public int energystorage;
	public static Config config = FluxedSolars.config;

	@Override	
	public String getName() {
		return name;
	}

	public static SolarPanelType[] VALUES = new SolarPanelType[]{REDSTONE,GOLD,BLAZE,ENDER};
}
