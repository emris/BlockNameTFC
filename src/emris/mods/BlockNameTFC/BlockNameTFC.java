package emris.mods.BlockNameTFC;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid="BlockNameTFC", name="TFC Block Names", version="1.1")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class BlockNameTFC {

	@Instance("BlockNameTFC")
	public static BlockNameTFC instance;
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandBn());
	}
	
}
