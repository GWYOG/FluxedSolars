package primetoxinz.fluxedsolars.tile;

import net.minecraft.util.IStringSerializable;

public enum SolarPanelType implements IStringSerializable {
	REDSTONE(0,"redstone", 64),
	GOLD(1,"gold",512),
	BLAZE(2,"blaze",4096),
	ENDER(3,"ender",32768);
	
	SolarPanelType(int id,String name) {
		this.name = name;
		this.id = id;
	}
	SolarPanelType(int id,String name, int energyoutput) {
		this(id, name);
		this.energyoutput = energyoutput;
	}
	SolarPanelType(int id,int energyinput,String name) {
		this(id, name);
		this.energyinput = energyinput;
	}
	private final String name;
	private final int id;
	public int energyoutput;
	public int energyinput;
	
	@Override	
	public String getName() {
		return name;
	}

	public static SolarPanelType[] VALUES = new SolarPanelType[]{REDSTONE,GOLD,BLAZE,ENDER};
}
