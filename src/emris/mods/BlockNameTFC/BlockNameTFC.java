package emris.mods.BlockNameTFC;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;


@Mod(modid="BlockNameTFC", name="TFC Block Names", version="1.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class BlockNameTFC {

	@Instance("BlockNameTFC")
	public static BlockNameTFC instance;
	
	@Init
	public void load(FMLInitializationEvent event) {
		if (event.getSide() == Side.SERVER){
			// The following lines of code are needed to register a new command.
			MinecraftServer server = ModLoader.getMinecraftServerInstance();
			
			ICommandManager commandManager = server.getCommandManager();
			ServerCommandManager manager = ((ServerCommandManager) commandManager);
			
			// CommandBn can be changed into any name, as long as it is the same as the name of the class that contains the code for your command.
			manager.registerCommand(new CommandBn());
			
		}
	}
}
