package de.marcely.bedwarsaddon.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.marcely.bedwars.api.Util;

public class AUtil {
	
	public static Kit getKit(String name){
		for(Kit kit:BedwarsAddonKits.kits){
			if(kit.getName().equalsIgnoreCase(name))
				return kit;
		}
		
		return null;
	}
	
	public static ItemStack getItemStack(String str){
		final String[] strs = str.split("\\,");
		final ItemStack is = Util.getItemItemstackByName(strs[0]);
		
		if(is != null){
			if(strs.length == 2 && Util.isInteger(strs[1]))
				is.setAmount(Integer.valueOf(strs[1]));
			
			return is;
		}else
			return null;
	}
	
	public static String toString(ItemStack is){
		return Util.itemstackToConfigName(is) + "," + is.getAmount();
	}
	
	public static String getMaterialUserFriendlyName(Material m){
		String name = "";
		
		boolean firstLetter = true;
		for(int ci=0; ci<m.name().length(); ci++){
			char c = m.name().charAt(ci);
			
			if(c != '_'){
				if(firstLetter){
					name += c;
					firstLetter = false;
				}else
					name += Character.toLowerCase(c);
			}else{
				name += ' ';
				firstLetter = true;
			}
		}
		
		return name;
	}
}
