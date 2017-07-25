package de.marcely.bedwarsaddon.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Kit {
	
	private String name;
	private ItemStack icon = new ItemStack(Material.SULPHUR);
	private List<ItemStack> is = new ArrayList<ItemStack>();
	
	public Kit(String name){
		this.name = name;
	}
	
	public void setIcon(ItemStack is){ this.icon = is; }
	public void addItem(ItemStack is){ this.is.add(is); }
	
	public String getName(){ return this.name; }
	public ItemStack getIcon(){ return this.icon; }
	public List<ItemStack> getItems(){ return this.is; }
	
}
