package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;


public class RNick implements CommandExecutor {
	private StarPep plugin;
	public RNick(StarPep pl) {
		plugin = pl;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		if(!(player.hasPermission("replaycore.nick"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to nick yourself!");
			return false;
		}
		if(!(args.length == 1)){
			player.sendMessage(ChatColor.DARK_AQUA + "/nick <name>");
			return false;
		}
		String nick = args[0];
		
		String unstripped = ChatColor.translateAlternateColorCodes('&', nick);
		String stripped = ChatColor.stripColor(unstripped);
		if(stripped.equals("")){
			player.sendMessage(ChatColor.DARK_AQUA + "Your nick doesn't contain any characters!");
			return false;
		}
		if(!(player.hasPermission("replaycore.nick.color"))){
		player.sendMessage(ChatColor.BLUE + "Your nick has been set to " + nick + ", " + player.getName() + ".");
		return true;
		}
		if(player.hasPermission("replaycore.nick.color") && (!(player.hasPermission("replaycore.nick.format")))){
			if(nick.contains("&l") || nick.contains("&n") || nick.contains("&o") || nick.contains("&k") || nick.contains("&m") || nick.contains("&r")){
				player.sendMessage(ChatColor.DARK_AQUA + "You're not allowed to use formatting codes.");
				return false;
			}
			String nickc = ChatColor.translateAlternateColorCodes('&', nick);
			
			player.sendMessage(ChatColor.BLUE + "Your nick has been set to " + nickc + ChatColor.BLUE +  ", " + player.getName() + ".");
			nick = nickc;
		}
		if(player.hasPermission("replaycore.nick.color") && player.hasPermission("replaycore.nick.format")){
			String nickf = ChatColor.translateAlternateColorCodes('&', nick);
			player.sendMessage(ChatColor.BLUE + "Your nick has been set to " + nickf + ChatColor.BLUE + ", " + player.getName() + ".");
			nick = nickf;
		}
		
		plugin.getConfig().set("Players.Nicks." + player.getUniqueId().toString(), nick);
		plugin.saveConfig();
		
		return true;
	}

}
