package com.starpeppy.replaycore.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RTPYes implements CommandExecutor{
	boolean returnFalse = false;
private StarPep plugin;
	
	public RTPYes(StarPep pl){
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
			player.sendMessage(ChatColor.DARK_AQUA + "/rtpaccept <player> or /rtpaccept (if you only have one pending)");
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
			if(returnFalse == true){
				player.sendMessage(ChatColor.DARK_AQUA + "This player is offline. Use /rtpno to deny the request.");
				return false;
			}
			if(returnFalse == false){
				
				if(pendingForTargetHere.contains(target.getName())){
					player.sendMessage(ChatColor.BLUE + "Teleporting to " + ChatColor.AQUA + target.getName() + ".");
					target.sendMessage(ChatColor.BLUE + player.getName() + ChatColor.AQUA + " is teleporting to you.");
					player.teleport(target);
					pendingForTargetHere.remove(target.getName());
					plugin.getConfig().set("Players.PendingRequestsTPAHERE." + player.getUniqueId().toString(), pendingForTargetHere);
					plugin.saveConfig();
					return true;
				}
				if(pendingForTargetThere.contains(target.getName())){
					
					target.sendMessage(ChatColor.AQUA + "Teleporting to " + ChatColor.BLUE + player.getName() + ".");
					player.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.AQUA + " is teleporting to you.");
					target.teleport(player);
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
			boolean hereCommand = false;
			int i = 0;
			String onePlayer = null;
			for(String playerName : pendingForTargetThere){
				
				onePlayer = playerName;
				i++;
			}
			for(String playerName : pendingForTargetHere){
				
				hereCommand = true;
				onePlayer = playerName;
				i++;
			}
			if(i == 1){
				if(hereCommand){
					Player target = Bukkit.getPlayer(onePlayer);
					player.sendMessage(ChatColor.BLUE + "Teleporting to " + ChatColor.AQUA + target.getName() + ".");
					target.sendMessage(ChatColor.BLUE + player.getName() + ChatColor.AQUA + " is teleporting to you.");
					player.teleport(target);
					pendingForTargetHere.remove(target.getName());
					plugin.getConfig().set("Players.PendingRequestsTPAHERE." + player.getUniqueId().toString(), pendingForTargetHere);
					plugin.saveConfig();
					return true;
				}
				else if(!(hereCommand)){
					Player target = Bukkit.getPlayer(onePlayer);
					target.sendMessage(ChatColor.AQUA + "Teleporting to " + ChatColor.BLUE + player.getName() + ".");
					player.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.AQUA + " is teleporting to you.");
					target.teleport(player);
					pendingForTargetThere.remove(target.getName());
					plugin.getConfig().set("Players.PendingRequestsTPA." + player.getUniqueId().toString(), pendingForTargetThere);
					plugin.saveConfig();
					
					return true;
				}
				
			}
			else if(i > 1){
				player.sendMessage(ChatColor.DARK_AQUA + "More than one player has requested to teleport to you! See a list with /rtplist.");
				return false;
			}
			else{
				player.sendMessage(ChatColor.DARK_AQUA + "No one has requested to teleport to you... so sad...");
				return false;
			}
			
			
			
			
		}
		
		
		return true;
	}
	
	
	
	
}


