package de.marcely.bedwarsaddon.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.marcely.bedwars.Language;
import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.BedwarsAddon;
import de.marcely.bedwars.api.BedwarsAddon.BedwarsAddonCommand;
import de.marcely.bedwars.api.CustomLobbyItem;
import de.marcely.bedwars.api.Util;
import de.marcely.bedwars.api.VersionAPI;
import de.marcely.bedwars.api.gui.GUI;
import de.marcely.bedwars.api.gui.GUIItem;

public class BedwarsAddonKits extends JavaPlugin {
	
	public static Plugin plugin;
	public static BedwarsAddon bedwarsAddon;
	
	public static List<Kit> kits = new ArrayList<Kit>();
	public static String kitsGUITitle = ChatColor.GOLD + "Kits";
	public static CustomLobbyItem lobbyItem = null;
	
	public static HashMap<Player, Kit> selectedKits = new HashMap<Player, Kit>();
	
	public void onEnable(){
		plugin = this;
		bedwarsAddon = new BedwarsAddon(this);
		
		// register events
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		
		// load config
		load();
		
		// add commands
		bedwarsAddon.registerCommand(new BedwarsAddonCommand("reload"){
			@Override
			public void onWrite(CommandSender sender, String[] args, String fullUsage){
				final long startTime = System.currentTimeMillis();
				sender.sendMessage(Language.Configurations_Reload_Start.getMessage());
				load();
				sender.sendMessage(Language.Configurations_Reload_End.getMessage().replace("{time}", "" + ((System.currentTimeMillis() - startTime) / 1000D)));
			}
		});
	}
	
	public void load(){
		if(lobbyItem != null) BedwarsAPI.unregisterLobbyItem(lobbyItem);
		
		Config.load();
		
		// set lores of every kit
		for(Kit kit:kits){
			List<String> lore = new ArrayList<String>();
			lore.add("" + ChatColor.GRAY + ChatColor.UNDERLINE + "Items:");
			for(ItemStack is:kit.getItems())
				lore.add(ChatColor.DARK_PURPLE + " " + AUtil.getMaterialUserFriendlyName(is.getType()) + ChatColor.LIGHT_PURPLE + " " + is.getAmount());
			
			ItemStack is = kit.getIcon();
			ItemMeta im = is.getItemMeta();
			im.setLore(lore);
			is.setItemMeta(im);
			kit.setIcon(VersionAPI.removeAttributes(is)); // also remove meta attributes
		}
		
		// add lobby item
		BedwarsAPI.registerLobbyItem(new CustomLobbyItem("kits"){
			@Override
			public void onUse(Player player){
				GUI gui = new GUI(kitsGUITitle, 0);
				for(final Kit kit:kits){
					ItemStack is = Util.renameItemstack(kit.getIcon().clone(), ChatColor.WHITE + kit.getName());
					
					if(selectedKits.containsKey(player) && selectedKits.get(player).equals(kit))
						is = VersionAPI.addGlow(is);
					
					gui.addItem(new GUIItem(is){
						@Override
						public void onClick(Player whoClicked, boolean leftClick, boolean shiftClick){
							selectedKits.put(whoClicked, kit);
							
							whoClicked.sendMessage(ChatColor.GREEN + "You changed your Kit to " + ChatColor.DARK_GREEN + kit.getName());
							whoClicked.closeInventory();
						}
					});
				}
				
				gui.open(player);
			}
		});
	}
}
