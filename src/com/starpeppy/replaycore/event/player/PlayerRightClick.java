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

public class PlayerRightClick implements Listener{
	private StarPep plugin;

	public PlayerRightClick(StarPep pl) {
		plugin = pl;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClickAction(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(! e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			
			return;
		}
		ItemStack heldItem = player.getItemInHand();
		if (heldItem.getType().equals(Material.BLAZE_ROD)) {
			if(player.hasPermission("replaycore.select")){
				ItemMeta meta = heldItem.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('$', "$c$lReplayCORE Selection Wand"));
				meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('$', "$aSelect areas with this wand.")));
				heldItem.setItemMeta(meta);
				player.sendMessage(ChatColor.DARK_PURPLE + "Second region point selected.");
				Location l = e.getClickedBlock().getLocation();
				double x = l.getBlockX();
				double y = l.getBlockY();
				double z = l.getBlockZ();
				
				plugin.getConfig().set("Players.RSelection." + player.getUniqueId().toString() + ".X", x);
				plugin.getConfig().set("Players.RSelection." + player.getUniqueId().toString() + ".Y", y);
				plugin.getConfig().set("Players.RSelection." + player.getUniqueId().toString() + ".Z", z);
				plugin.saveConfig();
				
			}

		}

	}

}
