package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RTPToggle implements CommandExecutor {

	
	private StarPep plugin;
	public RTPToggle(StarPep pl){
		plugin = pl;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		if(args.length > 1){
			player.sendMessage(ChatColor.DARK_AQUA + "/rtptoggle <on/off>");
			return false;
		}
 		if(!(player.hasPermission("replaycore.rtptoggle"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
			return false;
		}
 		if(args.length == 1){
		if(args[0].equals("on")){
			player.sendMessage(ChatColor.BLUE + "TP's toggled on.");
			plugin.getConfig().set("Players.TPToggles." + player.getUniqueId().toString(), true);
			plugin.saveConfig();
			return true;
		}
		if(args[0].equals("off")){
			player.sendMessage(ChatColor.BLUE + "TP's toggled off.");
			plugin.getConfig().set("Players.TPToggles." + player.getUniqueId().toString(), false);
			plugin.saveConfig();
			return true;
		}
 		}
 		else{
 			String PD = player.getUniqueId().toString();
 			if(!(plugin.getConfig().getBoolean("Players.TPToggles." + PD))){
 				player.sendMessage(ChatColor.BLUE + "TP's toggled on.");
 				plugin.getConfig().set("Players.TPToggles." + player.getUniqueId().toString(), true);
 				plugin.saveConfig();
 				return true;
 			}
 			else{
 				player.sendMessage(ChatColor.BLUE + "TP's toggled off.");
 				plugin.getConfig().set("Players.TPToggles." + player.getUniqueId().toString(), false);
 				plugin.saveConfig();
 				return true;
 			}
 			
 			
 			
 			
 			
 		}
		
		return false;
	}

}
