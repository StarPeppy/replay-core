package com.starpeppy.replaycore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class RGlow implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		if(player.hasPermission("replaycore.rglow")){
			
			if(args.length == 0){
				if(player.hasPotionEffect(PotionEffectType.GLOWING) == false){
				player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60000, 3));
				player.sendMessage(ChatColor.DARK_AQUA + "You're now glowing! Run the command again to remove it. " + ChatColor.LIGHT_PURPLE + " <3");
				return true;
				}
				else if(player.hasPotionEffect(PotionEffectType.GLOWING) == true){
					player.removePotionEffect(PotionEffectType.GLOWING);
					player.sendMessage(ChatColor.DARK_AQUA + "You've stopped glowing! Run the command again to re-add it." + ChatColor.LIGHT_PURPLE + " <3");
					return true;
				}
			}
			else{
				player.sendMessage(ChatColor.DARK_AQUA + "Incorrect usage! /rglow");
				return false;
			}
			
			
		}
		else{
			player.sendMessage(ChatColor.DARK_AQUA + "No permission!");
			return false;
		}
		
		return false;
	}
	
	
	
}
