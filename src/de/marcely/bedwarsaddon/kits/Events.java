package de.marcely.bedwarsaddon.kits;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.marcely.bedwars.api.ArenaStatus;
import de.marcely.bedwars.api.event.ArenaStatusUpdateEvent;
import de.marcely.bedwars.api.event.PlayerJoinArenaEvent;
import de.marcely.bedwars.api.event.PlayerQuitArenaEvent;

public class Events implements Listener {
	
	public static Random rand = new Random();
	
	@EventHandler
	public void onPlayerJoinArenaEvent(PlayerJoinArenaEvent event){
		// give the player who is joining a random kit
		if(BedwarsAddonKits.kits.size() >= 1)
			BedwarsAddonKits.selectedKits.put(event.getPlayer(), BedwarsAddonKits.kits.get(rand.nextInt(BedwarsAddonKits.kits.size())));
	}
	
	@EventHandler
	public void onPlayerQuitArenaEvent(PlayerQuitArenaEvent event){
		// remove the selected kit of the player who is leaving
		BedwarsAddonKits.selectedKits.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onArenaStatusUpdateEvent(final ArenaStatusUpdateEvent event){
		// give every player the items of their kits if the arena is starting
		if(BedwarsAddonKits.kits.size() >= 1 && event.getStatusBefore() == ArenaStatus.Lobby && event.getStatus() == ArenaStatus.Running){
			// wait a secound before giving him his items because if we would give them him now, they would disappear
			new BukkitRunnable(){
				@Override
				public void run(){
					for(Player player:event.getArena().getPlayers()){
						for(ItemStack is:BedwarsAddonKits.selectedKits.get(player).getItems())
							player.getInventory().addItem(is);
					}
				}
			}.runTaskLater(BedwarsAddonKits.plugin, 20);
		}
	}
}
