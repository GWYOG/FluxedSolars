package primetoxinz.fluxedsolars.tile;

import net.minecraft.util.IStringSerializable;

public enum SolarPanelType implements IStringSerializable {
	REDSTONE(0,"redstone", 64,64000),
	GOLD(1,"gold",512,512000),
	BLAZE(2,"blaze",4096,4096000),
	ENDER(3,"ender",32768, 32768000);

	SolarPanelType(int id,String name, int energypertick, int energystorage) {
		this.name = name;
		this.id = id;
		this.energypertick = energypertick;
		this.energystorage = energystorage;
	}
	private final String name;
	private final int id;
	public int energypertick;
	public int energystorage;
	
	@Override	
	public String getName() {
		return name;
	}

	public static SolarPanelType[] VALUES = new SolarPanelType[]{REDSTONE,GOLD,BLAZE,ENDER};
}
