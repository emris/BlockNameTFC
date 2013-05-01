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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityCrop;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityIngotPile;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityToolRack;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;


public class CommandBn extends CommandBase {

	@Override
	public String getCommandName()	{
		return "bn";
	}

	@Override
	public String getCommandUsage(ICommandSender s)	{
		return "/" + getCommandName() + " -> Returns the Block Name you are looking at.";
	}

	public List getCommandAliases()	{
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {
		if (sender instanceof EntityPlayerMP) {
			EntityPlayerMP curPlayer = (EntityPlayerMP) sender;
			MovingObjectPosition mop = PlayerUtils.getTargetBlock(curPlayer);
			
			if (mop != null) {
				if (mop.typeOfHit == EnumMovingObjectType.TILE) {
					World world = curPlayer.worldObj;
					int bID = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
					if (bID > 0) {
						int bMeta = world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
						StringTranslate st = curPlayer.getTranslator();
						String strBN = "";

						// for debugging
//						sender.sendChatToPlayer("[" + bID + ":" + bMeta + "] " + Block.blocksList[bID].getBlockName());
						
						strBN = getTFCBlockName(bID, bMeta, world, mop.blockX, mop.blockY, mop.blockZ, st);
						if (!strBN.equalsIgnoreCase("")) {
							sender.sendChatToPlayer(ChatFormatCodes.GREEN.toString() + strBN);
						}
					}
				}
			}
		} else {
			sender.sendChatToPlayer("Not usable by console!");
		}
	}

	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true; //This way all players can use this command.
	}
	
	private static String getTFCBlockName(int ID, int meta, World world, int x, int y, int z, StringTranslate st) {
		String bName = "";
		String[] stoneNames = {"Granite", "Diorite", "Gabbro", 
				"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", "Chalk",
				"Rhyolite", "Basalt", "Andesite", "Dacite", 
				"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
		String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine","Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
		String[] FruitTreeNames = {"Red Apple","Banana", "Orange", "Green Apple","Lemon","Olive","Cherry","Peach","Plum","Cacao"};
		String[] AnvilNames = {"Stone", "Copper", "Bronze", "Wrought Iron", "Steel", "Black Steel", "Red Steel", "Blue Steel", "Bismuth Bronze", "Black Bronze", "RoseGold"};
		String[] dyeColorNames = {"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Silver", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"};
		String[] cropNames = {"Wheat", "", "Maize", "", "Tomato", "Barley", "", "Rye", "", "Oat", "", "Rice", "", "Potato", "", "Onion", "Cabbage", "Garlic", "Carrot", "Yellow Bell Pepper", "Red Bell Pepper", "Soybean", "Greenbean", "Squash"};
		String[] flora = {"Golden Rod", "Cat Tails"};
		String[] ingots = {"Bismuth Ingot Pile","Bismuth Bronze Ingot Pile","Black Bronze Ingot Pile",
							"Black Steel Ingot Pile","Blue Steel Ingot Pile","Brass Ingot Pile","Bronze Ingot Pile",
							"Copper Ingot Pile","Gold Ingot Pile","Wrought Iron Ingot Pile","Lead Ingot Pile",
							"Nickel Ingot Pile","Pig Iron Ingot Pile","Platinum Ingot Pile","Red Steel Ingot Pile",
							"Rose Gold Ingot Pile","Silver Ingot Pile","Steel Ingot Pile","Sterling Silver Ingot Pile",
							"Tin Ingot Pile","Zinc Ingot Pile"};
		// Igneous Intrusive Stone
		if (ID == TFCBlocks.StoneIgIn.blockID){ bName = stoneNames[meta] + " Raw";}
		else if (ID == TFCBlocks.StoneIgInCobble.blockID){ bName = stoneNames[meta] + " Cobble";}
		else if (ID == TFCBlocks.StoneIgInSmooth.blockID){ bName = "Smooth " + stoneNames[meta];}
		else if (ID == TFCBlocks.StoneIgInBrick.blockID){ bName = stoneNames[meta] + " Brick";}
		// Wall
		else if (ID == TFCBlocks.WallRawIgIn.blockID){ bName = stoneNames[meta] + " Wall";}
		else if (ID == TFCBlocks.WallCobbleIgIn.blockID){ bName = stoneNames[meta] + " Cobble Wall";}
		else if (ID == TFCBlocks.WallSmoothIgIn.blockID){ bName = "Smooth " + stoneNames[meta] + " Wall";}
		else if (ID == TFCBlocks.WallBrickIgIn.blockID){ bName = stoneNames[meta] + " Brick Wall";}
		// Sedimentary Stone
		else if (ID == TFCBlocks.StoneSed.blockID){ bName = stoneNames[meta + 3] + " Raw";}
		else if (ID == TFCBlocks.StoneSedCobble.blockID){ bName = stoneNames[meta + 3] + " Cobble";}
		else if (ID == TFCBlocks.StoneSedSmooth.blockID){ bName = "Smooth " + stoneNames[meta + 3];}
		else if (ID == TFCBlocks.StoneSedBrick.blockID){ bName = stoneNames[meta + 3] + " Brick";}
		// Wall
		else if (ID == TFCBlocks.WallRawSed.blockID){ bName = stoneNames[meta + 3] + " Wall";}
		else if (ID == TFCBlocks.WallCobbleSed.blockID){ bName = stoneNames[meta + 3] + " Cobble Wall";}
		else if (ID == TFCBlocks.WallSmoothSed.blockID){ bName = "Smooth " + stoneNames[meta + 3] + " Wall";}
		else if (ID == TFCBlocks.WallBrickSed.blockID){ bName = stoneNames[meta + 3] + " Brick Wall";}
		// Igneous Extrusive Stone
		else if (ID == TFCBlocks.StoneIgEx.blockID){ bName = stoneNames[meta + 13] + " Raw";}
		else if (ID == TFCBlocks.StoneIgExCobble.blockID){ bName = stoneNames[meta + 13] + " Cobble";}
		else if (ID == TFCBlocks.StoneIgExSmooth.blockID){ bName = "Smooth " + stoneNames[meta + 13];}
		else if (ID == TFCBlocks.StoneIgExBrick.blockID){ bName = stoneNames[meta + 13] + " Brick";}
		// Wall
		else if (ID == TFCBlocks.WallRawIgEx.blockID){ bName = stoneNames[meta + 13] + " Wall";}
		else if (ID == TFCBlocks.WallCobbleIgEx.blockID){ bName = stoneNames[meta + 13] + " Cobble Wall";}
		else if (ID == TFCBlocks.WallSmoothIgEx.blockID){ bName = "Smooth " + stoneNames[meta + 13] + " Wall";}
		else if (ID == TFCBlocks.WallBrickIgEx.blockID){ bName = stoneNames[meta + 13] + " Brick Wall";}
		// Metamorphic Stone
		else if (ID == TFCBlocks.StoneMM.blockID){ bName = stoneNames[meta + 17] + " Raw";}
		else if (ID == TFCBlocks.StoneMMCobble.blockID){ bName = stoneNames[meta + 17] + " Cobble";}
		else if (ID == TFCBlocks.StoneMMSmooth.blockID){ bName = "Smooth " + stoneNames[meta + 17];}
		else if (ID == TFCBlocks.StoneMMBrick.blockID){ bName = stoneNames[meta + 17] + " Brick";}
		// Wall
		else if (ID == TFCBlocks.WallRawMM.blockID){ bName = stoneNames[meta + 17] + " Wall";}
		else if (ID == TFCBlocks.WallCobbleMM.blockID){ bName = stoneNames[meta + 17] + " Cobble Wall";}
		else if (ID == TFCBlocks.WallSmoothMM.blockID){ bName = "Smooth " + stoneNames[meta + 17] + " Wall";}
		else if (ID == TFCBlocks.WallBrickMM.blockID){ bName = stoneNames[meta + 17] + " Brick Wall";}
		// LooseRock
		else if (ID == TFCBlocks.LooseRock.blockID){
			DataLayer rockLayer = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(x, z, 0);
			bName = stoneNames[TFC_Core.getItemMetaFromStone(rockLayer.data1, rockLayer.data2)] + " Rock"; }
		
		// Fruit Trees
		else if (ID == TFCBlocks.fruitTreeWood.blockID) { bName = FruitTreeNames[meta] + " Tree"; }
		else if (ID == TFCBlocks.fruitTreeLeaves.blockID) { bName = FruitTreeNames[meta] + " Leaves"; }
		else if (ID == TFCBlocks.fruitTreeLeaves2.blockID) { bName = FruitTreeNames[meta + 8] + " Leaves"; }
		
		// Wood
		else if (ID == TFCBlocks.Wood.blockID) { bName = WoodNames[meta] + " Tree"; }
		else if (ID == TFCBlocks.WoodVert.blockID) { bName = WoodNames[meta] + " Log"; }
		else if (ID == TFCBlocks.WoodHoriz.blockID) { bName = WoodNames[(meta & 7)] + " Log"; }
		else if (ID == TFCBlocks.WoodHoriz2.blockID) { bName = WoodNames[(meta & 7) + 8] + " Log"; }
		else if (ID == TFCBlocks.Leaves.blockID) { bName = WoodNames[meta] + " Leaves"; }
		else if (ID == TFCBlocks.Sapling.blockID) { bName = WoodNames[meta] + " Sapling"; }
		else if (ID == Block.blocksList[5].blockID) { bName = WoodNames[meta] + " Planks"; }
		else if (ID == TFCBlocks.WoodSupportV.blockID || ID == TFCBlocks.WoodSupportH.blockID) { bName = WoodNames[meta] + " Support Beam"; }
		else if (ID == TFCBlocks.ToolRack.blockID) {
			TileEntityToolRack tr = (TileEntityToolRack)world.getBlockTileEntity(x, y, z);
			bName = WoodNames[tr.woodType] + " Tool Rack"; }
		else if (ID == TFCBlocks.WoodConstruct.blockID) {
//			TileEntityWoodConstruct te = (TileEntityWoodConstruct)world.getBlockTileEntity(x, y, z);
//			BitSet d = te.data;
//			d.get(?);
			bName = "Wood Construct"; } // ToDo ////////////////////////////////////
		
		// Stairs & Slabs
		else if (ID == TFCBlocks.stoneStairs.blockID || ID == TFCBlocks.stoneSlabs.blockID || ID == TFCBlocks.Detailed.blockID) {
			int sMeta;
			int sType;
			if (ID == TFCBlocks.Detailed.blockID) {
				TileEntityDetailed te = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
				sMeta = te.MetaID;
				sType = te.TypeID;
			} else {
				TileEntityPartial te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
				sMeta = te.MetaID;
				sType = te.TypeID;
			}

			String bType = "";
			if (ID == TFCBlocks.stoneStairs.blockID) { bType = "Stairs"; }
			else if (ID == TFCBlocks.stoneSlabs.blockID) { bType = "Slab"; }
			else if (ID == TFCBlocks.Detailed.blockID) { bType = "Detailed"; }
			
			switch(sType) {
			// Igneous Intrusive Stone
			case 209:
				bName = "Raw " + stoneNames[sMeta] + " " + bType; break;
			case 186:
				bName = stoneNames[sMeta] + " Brick " + bType; break;
			case 182:
				bName = "Smooth " + stoneNames[sMeta] + " " + bType; break;
			case 198:
				bName = stoneNames[sMeta] + " Cobble " + bType; break;
			// Sedimentary Stone
			case 210:
				bName = "Raw " + stoneNames[sMeta + 3] + " " + bType; break;
			case 187:
				bName = stoneNames[sMeta + 3] + " Brick " + bType; break;
			case 183:
				bName = "Smooth " + stoneNames[sMeta + 3] + " " + bType; break;
			case 199:
				bName = stoneNames[sMeta + 3] + " Cobble " + bType; break;
			// Igneous Extrusive Stone
			case 211:
				bName = "Raw " + stoneNames[sMeta + 13] + " " + bType; break;
			case 188:
				bName = stoneNames[sMeta + 13] + " Brick " + bType; break;
			case 184:
				bName = "Smooth " + stoneNames[sMeta + 13] + " " + bType; break;
			case 200:
				bName = stoneNames[sMeta + 13] + " Cobble " + bType; break;
			// Metamorphic Stone
			case 212:
				bName = "Raw " + stoneNames[sMeta + 17] + " " + bType; break;
			case 189:
				bName = stoneNames[sMeta + 17] + " Brick " + bType; break;
			case 185:
				bName = "Smooth " + stoneNames[sMeta + 17] + " " + bType; break;
			case 201:
				bName = stoneNames[sMeta + 17] + " Cobble " + bType; break;
			// Wood
			case 5:
				bName = WoodNames[sMeta] + " Wood " + bType; break;
			default:
				bName = bType;
			}
		}

		// Wood Doors
		else if (ID == TFCBlocks.DoorOak.blockID) {	bName = "Oak Door"; }
		else if (ID == TFCBlocks.DoorAspen.blockID) { bName = "Aspen Door"; }
		else if (ID == TFCBlocks.DoorChestnut.blockID) { bName = "Chestnut Door"; }
		else if (ID == TFCBlocks.DoorDouglasFir.blockID) { bName = "Douglas Fir Door"; }
		else if (ID == TFCBlocks.DoorHickory.blockID) { bName = "Hickory Door"; }
		else if (ID == TFCBlocks.DoorMaple.blockID) {	bName = "Maple Door"; }
		else if (ID == TFCBlocks.DoorAsh.blockID) { bName = "Ash Door"; }
		else if (ID == TFCBlocks.DoorPine.blockID) { bName = "Pine Door"; }
		else if (ID == TFCBlocks.DoorSequoia.blockID) { bName = "Sequoia Door"; }
		else if (ID == TFCBlocks.DoorSpruce.blockID) { bName = "Spruce Door"; }
		else if (ID == TFCBlocks.DoorSycamore.blockID) { bName = "Sycamore Door"; }
		else if (ID == TFCBlocks.DoorWhiteCedar.blockID) { bName = "WhiteCedar Door"; }
		else if (ID == TFCBlocks.DoorWhiteElm.blockID) { bName = "WhiteElm Door"; }
		else if (ID == TFCBlocks.DoorWillow.blockID) { bName = "Willow Door"; }
		else if (ID == TFCBlocks.DoorKapok.blockID) {	bName = "Kapok Door"; }

		// Log Pile
		else if (ID == TFCBlocks.LogPile.blockID) {	bName = "Log Pile"; }

		// Farm Land
		else if (ID == TFCBlocks.tilledSoil.blockID || ID == TFCBlocks.tilledSoil2.blockID) { bName = "Farmland"; }

		// Peat
		else if (ID == TFCBlocks.Peat.blockID || ID == TFCBlocks.PeatGrass.blockID) {
			if (ID == TFCBlocks.Peat.blockID) {bName = "Peat"; } else { bName = "Peat Grass"; }	}

		// Grass & Dry Grass
		else if (ID == TFCBlocks.Grass.blockID || ID == TFCBlocks.Grass2.blockID) { bName = "Grass"; }
		else if (ID == TFCBlocks.DryGrass.blockID || ID == TFCBlocks.DryGrass2.blockID) { bName = "Dry Grass"; }

		// Clay
		else if (ID == TFCBlocks.Clay.blockID || ID == TFCBlocks.Clay2.blockID) { bName = "Clay"; }
		else if (ID == TFCBlocks.ClayGrass.blockID || ID == TFCBlocks.ClayGrass2.blockID) { bName = "Clay Grass"; }

		// Dirt
		else if (ID == TFCBlocks.Dirt.blockID || ID == TFCBlocks.Dirt2.blockID) { bName = "Dirt"; }
		
		// Wool
		else if (ID == Block.cloth.blockID) { bName = dyeColorNames[meta] + " Wool"; } 
		
		// Bellows
		else if (ID == TFCBlocks.Bellows.blockID) { bName = "Bellows"; }
		
		// Firepit
		else if (ID == TFCBlocks.Firepit.blockID) { bName = "Firepit"; }
		
		// Forge
		else if (ID == TFCBlocks.Forge.blockID) { bName = "Forge"; }
		
		// Scribing Table
		else if (ID == TFCBlocks.Scribe.blockID) { bName = "Scribing Table"; }

		// Metallurgy Table
		else if (ID == TFCBlocks.MetalTable.blockID) { bName = "Metallurgy Table"; }

		// Bloomery
		else if (ID == TFCBlocks.Bloomery.blockID) { bName = "Bloomery"; }

		// Sluice
		else if (ID == TFCBlocks.Sluice.blockID) { bName = "Sluice"; }
		
		// Charcoal
		else if (ID == TFCBlocks.Charcoal.blockID) { bName = "Charcoal"; }
		
		// SpawnMeter
		else if (ID == TFCBlocks.SpawnMeter.blockID) { bName = "SpawnMeter"; }
		
		// FoodPrep
		else if (ID == TFCBlocks.FoodPrep.blockID) { bName = "Food Preparation Table"; }
		
		// Quern
		else if (ID == TFCBlocks.Quern.blockID) { bName = "Quern Base"; }
		
		// Nestbox
		else if (ID == TFCBlocks.Nestbox.blockID) { bName = "Nestbox"; }
		
		// SuperDetailed ???
		else if (ID == TFCBlocks.SuperDetailed.blockID) { bName = "Super Detailed Block"; }
		
		// Crops
		else if (ID == Block.blocksList[59].blockID) {
			TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(x, y, z);
//			CropIndex i = CropManager.getInstance().getCropFromId(te.cropId);
			bName = cropNames[te.cropId];
//			if (te.growth >= i.numGrowthStages) {
//				bName = bName + " [Fully Grown]";
//			} else {
//				bName = bName + " [Still Growing]";
//			}
		}
		
		// Anvil
		else if (ID == TFCBlocks.Anvil.blockID) { bName = AnvilNames[(meta & 7)] + " Anvil"; }
		else if (ID == TFCBlocks.Anvil2.blockID) { bName = AnvilNames[(meta & 7) + 8] + " Anvil"; }
		
		// Crucible
		else if (ID == TFCBlocks.Crucible.blockID) { bName = "Crucible"; }
		
		// IngotPile
		else if (ID == TFCBlocks.IngotPile.blockID) {
			TileEntityIngotPile te = (TileEntityIngotPile) world.getBlockTileEntity(x, y, z);
			bName = ingots[te.type]; }
		
		// Barrel
		else if (ID == TFCBlocks.Barrel.blockID) {
			TileEntityBarrel te = (TileEntityBarrel) world.getBlockTileEntity(x, y, z);
			bName = WoodNames[te.blockMetadata]; }
		
		// Flora
		else if (ID == TFCBlocks.Flora.blockID) {
			bName = flora[meta]; }
		
		else { bName = st.getInstance().translateNamedKey(Block.blocksList[ID].getUnlocalizedName()); }

		return bName;
	}
}