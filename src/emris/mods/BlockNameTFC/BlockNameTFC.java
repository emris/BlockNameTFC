/*
 *  Copyright (C) 2013  emris
 *  https://github.com/emris/BlockNameTFC
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package emris.mods.BlockNameTFC;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid="BlockNameTFC", name="TFC Block Names", version="1.2.b76", dependencies = "required-after:TerraFirmaCraft")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class BlockNameTFC {

	@Instance("BlockNameTFC")
	public static BlockNameTFC instance;
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandBn());
	}
	
}
