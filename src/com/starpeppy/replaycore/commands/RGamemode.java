package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class RGamemode implements CommandExecutor{

@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player player = (Player) sender;
		if(!(player.hasPermission("replaycore.rgamemode"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have access to this command.");
			return false;
		}
		if(!(args.length == 0 || args.length == 1)){
			player.sendMessage(ChatColor.DARK_AQUA + "/rgamemode <gamemode> or /gmc etc.");
			return false;
		}
		if(args.length == 0){
				if(!(label.matches("gm|gamemode|rgm"))){
			
			if(label.matches("gmc|creative")){
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage(ChatColor.BLUE + "Gamemode set to creative.");
			return true;
			}
			if(label.matches("gms|survival")){
				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage(ChatColor.BLUE + "Gamemode set to survival.");
			return true;
			}
			if(label.matches("gmsp|spectator")){
				player.setGameMode(GameMode.SPECTATOR);
				player.sendMessage(ChatColor.BLUE + "Gamemode set to spectator.");
			return true;
			}
			if(label.matches("gma|adventure")){
				player.setGameMode(GameMode.ADVENTURE);
				player.sendMessage(ChatColor.BLUE + "Gamemode set to adventure.");
			return true;
			}
			
				}
				else{
					player.sendMessage(ChatColor.DARK_AQUA + "/" + label + " <gamemode>");
					return false;
			}
		}
		else{
		if(args[0].matches("c|creative|1|")){
			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage(ChatColor.BLUE + "Gamemode set to creative.");
			return true;
		}
		if(args[0].matches("s|survival|0|")){
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage(ChatColor.BLUE + "Gamemode set to survival.");
			return true;
		}
		if(args[0].matches("a|adventure|2|")){
			player.setGameMode(GameMode.ADVENTURE);
			player.sendMessage(ChatColor.BLUE + "Gamemode set to adventure.");
			return true;
		}
		if(args[0].matches("sp|spectator|3|")){
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage(ChatColor.BLUE + "Gamemode set to spectator.");
			return true;
		}
		}
		return true;
	}	
	
}
