package com.starpeppy.replaycore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class RHeal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			System.out.println("Only players can use this command!");
			return false;
		}
		Player tempplayer = (Player) sender;
		if (!(tempplayer.hasPermission("replaycore.rheal"))) {
			tempplayer.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to do that.");
			return false;
		} else {

			if (args.length == 0) {
				Player player = (Player) sender;
				player.setHealth(20);
				player.setFoodLevel(20);
				player.setExhaustion(-10);
				drinkMilk(player);
				player.setFireTicks(0);
				player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1F, 2F);
				player.sendMessage(ChatColor.DARK_AQUA + "You healed yourself.");

				return true;
			}
			if (args.length == 1) {

			
				Player tplayer = Bukkit.getPlayer(args[0]);
				Player player = (Player) sender;
				tplayer.setHealth(20);
				tplayer.setFoodLevel(20);
				tplayer.setExhaustion(-10);
				drinkMilk(tplayer);
				tplayer.setFireTicks(0);
				tplayer.playSound(tplayer.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1F, 2F);
				tplayer.sendMessage(ChatColor.AQUA + "You were healed by " + player.getName() + ".");
				player.sendMessage(ChatColor.DARK_AQUA + "You healed " + tplayer.getName() + ".");
				return true;
			}

		}

		return false;
	}

	public void drinkMilk(Player player) {
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.removePotionEffect(PotionEffectType.BLINDNESS);
		player.removePotionEffect(PotionEffectType.CONFUSION);
		player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
		player.removePotionEffect(PotionEffectType.GLOWING);
		player.removePotionEffect(PotionEffectType.HARM);
		player.removePotionEffect(PotionEffectType.HEAL);
		player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
		player.removePotionEffect(PotionEffectType.HUNGER);
		player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.removePotionEffect(PotionEffectType.JUMP);
		player.removePotionEffect(PotionEffectType.LEVITATION);
		player.removePotionEffect(PotionEffectType.LUCK);
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.removePotionEffect(PotionEffectType.POISON);
		player.removePotionEffect(PotionEffectType.REGENERATION);
		player.removePotionEffect(PotionEffectType.SATURATION);
		player.removePotionEffect(PotionEffectType.SLOW);
		player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.removePotionEffect(PotionEffectType.UNLUCK);
		player.removePotionEffect(PotionEffectType.WATER_BREATHING);
		player.removePotionEffect(PotionEffectType.WEAKNESS);
		player.removePotionEffect(PotionEffectType.WITHER);
	}
}
