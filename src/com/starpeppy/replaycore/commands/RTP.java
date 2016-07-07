package com.starpeppy.replaycore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;


public class RTP implements CommandExecutor {
	private StarPep plugin;
	public RTP(StarPep pl){
		plugin = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean returnFalse = false;
		
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		if(args.length != 1){
			player.sendMessage(ChatColor.DARK_AQUA + "/rtp <player>");
			return false;
		}
		Player target = null;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().toLowerCase().contains(args[0].toLowerCase())){
				returnFalse = false;
				target = p;
				break;
			}
			returnFalse = true;
		}
		if(returnFalse == true){
			player.sendMessage(ChatColor.DARK_AQUA + "Player offline!");
			return false;
		}
		if(target.getName().equals(player.getName())){
			player.sendMessage(ChatColor.DARK_AQUA + "You can't teleport to yourself.");
			return false;
		}
		if(!(player.hasPermission("replaycore.rtp"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
			return false;
		}
		
		if(!(plugin.getConfig().getBoolean("Players.TPToggles." + target.getUniqueId().toString()))){
			player.sendMessage(ChatColor.DARK_AQUA + "This player has TP's toggled off.");
			return false;
		}
		
		player.teleport(target);
		player.sendMessage(ChatColor.BLUE + "Teleported to " + ChatColor.AQUA + target.getName() + ".");
		return true;
	}
	
	
	
	
}
