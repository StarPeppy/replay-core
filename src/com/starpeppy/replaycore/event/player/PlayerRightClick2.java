package com.starpeppy.replaycore.event.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerRightClick2 implements Listener{

	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onRightClickAction(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(!( e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
			
			return;
		}
		String nullstring = null;
		if(nullstring == null){
			return;
		}
		@SuppressWarnings("unused")
		ItemStack heldItem = player.getItemInHand();
		if (heldItem.getType().equals(Material.BLAZE_POWDER)) {
			if(player.hasPermission("replaycore.firebreath") || heldItem.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('$', "$4$lFire Powder"))){
				ItemMeta meta = heldItem.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('$', "$4$lFire Powder"));
				if(!(player.isSneaking())){
					return;
				}
				meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('$', "$aShift and hold right click on the ground.")));
				heldItem.setItemMeta(meta);

				Location targetLocation = e.getClickedBlock().getLocation();
				targetLocation.getWorld().playEffect(targetLocation, Effect.FLAME, 1);
				targetLocation.getWorld().playEffect(targetLocation, Effect.FLAME, 1);
				targetLocation.getWorld().playEffect(targetLocation, Effect.FLAME, 1);
				targetLocation.getWorld().playEffect(targetLocation, Effect.FLAME, 1);
				targetLocation.getWorld().playEffect(targetLocation, Effect.FIREWORKS_SPARK, 1);
				Player target = null;
				for(Player show : Bukkit.getOnlinePlayers()){
					if(show.equals(player)){
					} 
					else{
						double sX = show.getLocation().getX();
						double sZ = show.getLocation().getZ();
						double bX = targetLocation.getX();
						double bZ = targetLocation.getZ();
						double differenceX = sX - bX;
						double differenceZ = sZ - bZ;
						if(differenceX < 2){
							if(differenceZ < 2){
								target = show;
						}
						
						}
					}
				}
				if(target == null){
					return;
				}
				else{
					target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 40));
				}
			}

		}

	}

}
