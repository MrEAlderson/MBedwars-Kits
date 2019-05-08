package de.marcely.bedwarsaddon.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.marcely.bedwars.Language;
import de.marcely.bedwars.api.Util;
import de.marcely.configmanager2.ConfigManager;
import de.marcely.configmanager2.objects.Tree;

public class Config {
	public static ConfigManager cm = BedwarsAddonKits.bedwarsAddon.getConfig();
	
	public static void load(){
		cm.load();
		
		if(cm.getRootTree().getChilds().size() == 0){
			{
				final Kit kit = new Kit("Builder");
				
				kit.setIcon(new ItemStack(Material.SANDSTONE));
				kit.addItem(new ItemStack(Material.SANDSTONE, 64));
				kit.addItem(new ItemStack(Material.APPLE, 5));
				kit.addItem(new ItemStack(Material.ENDER_STONE, 10));
				
				BedwarsAddonKits.kits.add(kit);
			}
			
			{
				final Kit kit = new Kit("Miner");
				
				kit.setIcon(new ItemStack(Material.STONE_PICKAXE));
				kit.addItem(new ItemStack(Material.STONE_PICKAXE));
				kit.addItem(new ItemStack(Material.LADDER, 10));
				kit.addItem(new ItemStack(Material.APPLE, 5));
				
				BedwarsAddonKits.kits.add(kit);
			}
			
			{
				final Kit kit = new Kit("Enderman");
				
				kit.setIcon(new ItemStack(Material.ENDER_PEARL));
				kit.addItem(new ItemStack(Material.ENDER_PEARL, 1));
				
				BedwarsAddonKits.kits.add(kit);
			}
			
			{
				final Kit kit = new Kit("Recruit");
				
				kit.setIcon(new ItemStack(Material.WOOD_SWORD));
				kit.addItem(new ItemStack(Material.WOOD_SWORD));
				kit.addItem(new ItemStack(Material.BREAD, 3));
				
				BedwarsAddonKits.kits.add(kit);
			}
			
			{
				final Kit kit = new Kit("Snowman");
				
				kit.setIcon(new ItemStack(Material.PUMPKIN));
				kit.addItem(new ItemStack(Material.SNOW_BALL, 10));
				kit.addItem(new ItemStack(Material.PUMPKIN, 1));
				kit.addItem(new ItemStack(Material.COOKIE, 4));
				
				BedwarsAddonKits.kits.add(kit);
			}
			
			{
				final Kit kit = new Kit("Farmer");
				
				kit.setIcon(new ItemStack(Material.WOOD_HOE));
				kit.addItem(new ItemStack(Material.APPLE, 4));
				kit.addItem(new ItemStack(Material.BREAD, 5));
				kit.addItem(new ItemStack(Material.RAW_FISH, 6));
				kit.addItem(new ItemStack(Material.COOKED_BEEF, 2));
				kit.addItem(new ItemStack(Material.WOOD_HOE));
				
				BedwarsAddonKits.kits.add(kit);
			}
			
			save();
			return;
		}
		
		final String version = cm.getDescription("version");
		final String gui_title = cm.getConfigString("gui-title");
		final Boolean permissions_enabled = cm.getConfigBoolean("permissions-enabled");
		final String permissions_insufficient_item_name = cm.getConfigString("permissions-insufficient-item-name");
		final String message_no_permissions = cm.getConfigString("message-no-permissions");
		final String message_set_kit = cm.getConfigString("message-set-kit");
		final String message_lore_items = cm.getConfigString("message-lore-items");
		final String message_lore_items_item = cm.getConfigString("message-lore-items-item");
		
		if(gui_title != null)
			BedwarsAddonKits.kitsGUITitle = Language.stringToChatColor(gui_title);
		
		if(permissions_enabled != null)
			BedwarsAddonKits.permissionsEnabled = permissions_enabled;
		
		if(permissions_insufficient_item_name != null)
			BedwarsAddonKits.permissionsMissingItemName = Language.stringToChatColor(permissions_insufficient_item_name);
		
		if(message_no_permissions != null)
			BedwarsAddonKits.message_noPermissions = Language.stringToChatColor(message_no_permissions);
		
		if(message_set_kit != null)
			BedwarsAddonKits.message_setKit = Language.stringToChatColor(message_set_kit);
		
		if(message_lore_items != null)
			BedwarsAddonKits.message_loreItems = Language.stringToChatColor(message_lore_items);
		
		if(message_lore_items_item != null)
			BedwarsAddonKits.message_loreItems = Language.stringToChatColor(message_lore_items_item);
		
		// kits
		BedwarsAddonKits.kits.clear();
		
		for(Tree t:cm.getRootTree().getTreeChilds()){
			Kit kit = AUtil.getKit(t.getName());
			
			if(kit == null){
				kit = new Kit(t.getName());
				BedwarsAddonKits.kits.add(kit);
			}
			
			for(de.marcely.configmanager2.objects.Config c:t.getChilds()){
				if(c.getName().equalsIgnoreCase("seticon")){
					final ItemStack is = Util.getItemItemstackByName(c.getValue());
					
					if(is != null)
						kit.setIcon(Util.renameItemstack(is, kit.getName()));
				}else if(c.getName().equalsIgnoreCase("additem")){
					final ItemStack is = AUtil.getItemStack(c.getValue());
					
					if(is != null)
						kit.addItem(is);
				}
			}
		}
		
		cm.clear();
		
		if(version == null || !version.equals(BedwarsAddonKits.plugin.getDescription().getVersion()))
			save();
	}
	
	public static void save(){
		cm.clear();
		
		cm.addDescription("version", BedwarsAddonKits.plugin.getDescription().getVersion());
		
		cm.addComment("### Basic configurations:");
		cm.addConfig("gui-title", Language.chatColorToString(BedwarsAddonKits.kitsGUITitle));
		cm.addComment("Permissions are formatted like this: ");
		cm.addComment("mbedwars.addon.kits.<kit name>");
		cm.addComment("Example: mbedwars.addon.kits.Miner");
		cm.addConfig("permissions-enabled", BedwarsAddonKits.permissionsEnabled);
		cm.addComment("The name of the item which'll be displayed if the player has insufficient permissions");
		cm.addConfig("permissions-insufficient-item-name", Language.chatColorToString(BedwarsAddonKits.permissionsMissingItemName));
		
		cm.addEmptyLine();
		cm.addEmptyLine();
		
		cm.addComment("### Messages");
		cm.addConfig("message-message-no-permissions", Language.chatColorToString(BedwarsAddonKits.message_noPermissions));
		cm.addConfig("message-set-kit", Language.chatColorToString(BedwarsAddonKits.message_setKit));
		
		cm.addEmptyLine();
		cm.addEmptyLine();
		
		cm.addComment("### Kits:");
		
		for(Kit kit:BedwarsAddonKits.kits){
			cm.addConfig(kit.getName() + ".setIcon", AUtil.toString(kit.getIcon()));
			
			for(ItemStack is:kit.getItems())
				cm.addConfig(kit.getName() + ".addItem", AUtil.toString(is));
			
			cm.addEmptyLine();
		}
		
		cm.save();
	}
}
