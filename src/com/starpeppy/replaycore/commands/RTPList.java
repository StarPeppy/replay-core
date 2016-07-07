package com.starpeppy.replaycore.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RTPList implements CommandExecutor {
	
	private StarPep plugin;
	
	public RTPList(StarPep pl){
		plugin = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		if(args.length != 0){
			player.sendMessage(ChatColor.DARK_AQUA + "/rtplist");
			return false;
		}
		if(!(player.hasPermission("replaycore.tplist"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to do this.");
			return false;
		}

		List<String> pendingForTarget = plugin.getConfig().getStringList("Players.PendingRequestsTPA." + player.getUniqueId().toString());
		player.sendMessage(ChatColor.BLUE + "Accept tp requests with /rtpyes. The following players would like to TP to you: \n");
		for(String playerName : pendingForTarget){
			
				player.sendMessage(ChatColor.AQUA + playerName);
				return false;
			
		}
		List<String> pendingForTargett = plugin.getConfig().getStringList("Players.PendingRequestsTPAHERE." + player.getUniqueId().toString());
		player.sendMessage(ChatColor.BLUE + "Accept tp requests with /rtpyes.  The following players would like you to TP to them: \n");
		for(String playerName : pendingForTargett){
			
				player.sendMessage(ChatColor.AQUA + playerName);
				return false;
			
		}
		return true;
		
	}

}
