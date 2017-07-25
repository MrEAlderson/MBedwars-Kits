package de.marcely.bedwarsaddon.kits;

import org.bukkit.inventory.ItemStack;

import de.marcely.bedwars.Language;
import de.marcely.bedwars.util.Util;
import de.marcely.bedwars.config.ConfigManager;
import de.marcely.bedwars.config.ConfigManager.MultiKey.MultiKeyEntry;

public class Config {
	public static ConfigManager cm = BedwarsAddonKits.bedwarsAddon.getConfig();
	
	public static void load(){
		if(cm.isEmpty())
			regenerate();
		
		cm.load();
		
		String gui_title = cm.getConfigString("gui-title");
		
		if(gui_title != null)
			BedwarsAddonKits.kitsGUITitle = Language.stringToChatColor(gui_title);
		
		// kits
		BedwarsAddonKits.kits.clear();
		
		for(MultiKeyEntry<String, Object> e:cm.getInside(1).entrySet()){
			String[] strs = e.getKey().split("\\.");
			String value = String.valueOf(e.getValue());
			Kit kit = AUtil.getKit(strs[0]);
			
			if(kit == null){
				kit = new Kit(strs[0]);
				BedwarsAddonKits.kits.add(kit);
			}
			
			if(strs[1].equalsIgnoreCase("seticon")){
				ItemStack is = Util.getIcon(value);
				
				if(is != null)
					kit.setIcon(Util.renameItemStack(is, strs[0]));
			}else if(strs[1].equalsIgnoreCase("additem")){
				ItemStack is = AUtil.getItemStack(value);
				
				if(is != null)
					kit.addItem(is);
			}
		}
		
		cm.clear();
	}
	
	public static void regenerate(){
		cm.clear();
		
		cm.addComment("Basic configurations:");
		cm.addConfig("gui-title", Language.chatColorToString(BedwarsAddonKits.kitsGUITitle));
		
		cm.addEmptyLine();
		cm.addComment("Kits:");
		cm.addConfig("Builder.setIcon", "SANDSTONE:0");
		cm.addConfig("Builder.addItem", "SANDSTONE:0,64");
		cm.addConfig("Builder.addItem", "APPLE, 10");
		cm.addConfig("Builder.addItem", "ENDER_STONE:0,10");
		cm.addConfig("Miner.setIcon", "WOOD_PICKAXE:0");
		cm.addConfig("Miner.addItem", "WOOD_PICKAXE");
		cm.addConfig("Miner.addItem", "LADDER,5");
		cm.addConfig("EnderMan.setIcon", "ENDER_PEARL:0");
		cm.addConfig("EnderMan.addItem", "ENDER_PEARL:0,2");
		cm.addConfig("Fighter.setIcon", "STONE_SWORD");
		cm.addConfig("Fighter.addItem", "STONE_SWORD");
		cm.addConfig("Fighter.addItem", "APPLE,3");
		
		cm.save();
	}
}
