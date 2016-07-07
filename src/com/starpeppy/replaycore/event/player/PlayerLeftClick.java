package com.starpeppy.replaycore.event.player;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.starpeppy.replaycore.StarPep;

public class PlayerLeftClick implements Listener{
	private StarPep plugin;

	public PlayerLeftClick(StarPep pl) {
		plugin = pl;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(!( e.getAction().equals(Action.LEFT_CLICK_BLOCK))){
			return;
		}
		ItemStack heldItem = player.getItemInHand();
		
		if (heldItem.getType().equals(Material.BLAZE_ROD)) {
			if(player.hasPermission("replaycore.select")){
				e.setCancelled(true);
				ItemMeta meta = heldItem.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('$', "$c$lReplayCORE Selection Wand"));
				meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('$', "$aSelect areas with this wand.")));
				heldItem.setItemMeta(meta);
				player.sendMessage(ChatColor.DARK_PURPLE + "First region point selected.");
				Location l = e.getClickedBlock().getLocation();
				double x = l.getBlockX();
				double y = l.getBlockY();
				double z = l.getBlockZ();
				plugin.getConfig().set("Players.LSelection." + player.getUniqueId().toString() + ".X", x);
				plugin.getConfig().set("Players.LSelection." + player.getUniqueId().toString() + ".Y", y);
				plugin.getConfig().set("Players.LSelection." + player.getUniqueId().toString() + ".Z", z);
				plugin.saveConfig();
				
			}

		}
		
	}

}
