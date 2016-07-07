package com.starpeppy.replaycore.event.player;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerLeftClick2 implements Listener{
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent e) {
		String nullstring = null;
		if(nullstring == null){
			return;
		}
		@SuppressWarnings("unused")
		Player player = e.getPlayer();
		if(!( e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_AIR))){
			return;
		}
		ItemStack heldItem = player.getItemInHand();
	if(heldItem.getType().equals(Material.BEETROOT)){
		if(!(player.hasPermission("replaycore.boom") || heldItem.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('$', "$5$lMagical Beet")))){
			return;
		}
		Player target = null;
		for(Entity ent : player.getNearbyEntities(4, 4, 4)){
		if(ent instanceof Player){
			target = (Player) ent;
		}
		}
		if(target == null){
			player.sendMessage(ChatColor.DARK_AQUA + "No target in sight.");
			return;
		}
		ItemMeta meta = heldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('$', "$5$lMagical Beet"));
		meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('$', "$aLeft click a block or the air near a player while jumping.")));
		heldItem.setItemMeta(meta);
		Location targetLocation = target.getEyeLocation();
		targetLocation.getWorld().playEffect(targetLocation, Effect.ENDER_SIGNAL, 1);
		targetLocation.getWorld().playEffect(targetLocation, Effect.END_GATEWAY_SPAWN, 1);
		targetLocation.getWorld().playEffect(targetLocation, Effect.DRAGON_BREATH, 1);
		
		target.setVelocity(player.getVelocity().multiply(5.0F));
	}
}
}