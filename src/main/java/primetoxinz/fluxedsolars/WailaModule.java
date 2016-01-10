package primetoxinz.fluxedsolars;

import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import primetoxinz.fluxedsolars.tile.TileSolar;

public class WailaModule {
	public void init(FMLInitializationEvent e) {
		FMLInterModComms.sendMessage("Waila", "register", getClass().getName()
                + ".callbackRegister");	
	}
	public static void callbackRegister(IWailaRegistrar registrar) {
	        registrar.registerStackProvider(new WailaProviderSolar(),
	        		TileSolar.class);
	        registrar.registerBodyProvider(new WailaProviderSolar(),
	        		TileSolar.class);
    }
}
