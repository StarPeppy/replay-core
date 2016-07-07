package com.starpeppy.replaycore.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RTPNo implements CommandExecutor{
	boolean returnFalse = false;
private StarPep plugin;
	
	public RTPNo(StarPep pl){
		plugin = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		List<String> pendingForTargetThere = plugin.getConfig().getStringList("Players.PendingRequestsTPA." + player.getUniqueId().toString());
		List<String> pendingForTargetHere = plugin.getConfig().getStringList("Players.PendingRequestsTPAHERE." + player.getUniqueId().toString());
		if(args.length >= 2){
			player.sendMessage(ChatColor.DARK_AQUA + "/rtpno <player>");
			return false;
		}
		if(!(player.hasPermission("replaycore.rtpaccept"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
			return false;
		}
		if(args.length == 1){
			Player target = null;
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().toLowerCase().contains(args[0].toLowerCase())){
					returnFalse = false;
					target = p;
					break;
				}
				returnFalse = true;
			}
			if(returnFalse == false){
				
				if(pendingForTargetHere.contains(target.getName())){
					player.sendMessage(ChatColor.BLUE + "Teleport request cancelled.");
					if(returnFalse == false){
					target.sendMessage(ChatColor.AQUA + "Teleport request cancelled.");
					}
					pendingForTargetHere.remove(target.getName());
					plugin.getConfig().set("Players.PendingRequestsTPAHERE." + player.getUniqueId().toString(), pendingForTargetHere);
					plugin.saveConfig();
					return true;
				}
				if(pendingForTargetThere.contains(target.getName())){
					
					player.sendMessage(ChatColor.BLUE + "Teleport request cancelled.");
					if(returnFalse == false){
					target.sendMessage(ChatColor.AQUA + "Teleport request cancelled.");
					}
					pendingForTargetThere.remove(target.getName());
					plugin.getConfig().set("Players.PendingRequestsTPA." + player.getUniqueId().toString(), pendingForTargetThere);
					plugin.saveConfig();
					
					return true;
				}
				
				else{
					player.sendMessage(ChatColor.DARK_AQUA + "This player didn't request to teleport to you... I cri evry tim... " + ChatColor.LIGHT_PURPLE + "</3" );
				return false;
				}
			}
		}
		else if(args.length == 0){
			player.sendMessage(ChatColor.DARK_AQUA + "/rtpno <player>, or /tpdeny <player>");
			return false;
		}
		
		
		return true;
	}
	
	
	
	
}


