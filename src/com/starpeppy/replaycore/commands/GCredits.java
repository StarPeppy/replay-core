package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;
import com.starpeppy.replaycore.utilities.Change;


public class GCredits extends Change implements CommandExecutor{

private StarPep plugin;

	public GCredits(StarPep pl) {
		plugin = pl;
	}
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return false;
		}
		
		Player player = (Player) sender;
		
		
		if(!(player.hasPermission("replaycurrency.giveselfcredits")))return false;
		if((args.length == 1)){
			Change changeg = new Change(plugin);
			int munzies = Integer.parseInt(args[0]);
			changeg.changeMoney('C', munzies, player);
			
			
			return true;
		}
		if(!(args.length == 1)){
			
			player.sendMessage("/gcredits <integer>");
		}
		
		
		
		/*int credits = plugin.getConfig().getInt("Players.Credits." + player.getUniqueId().toString());
		int args0 = Integer.parseInt(args[0]);
		int newcredits = credits + args0;
		plugin.getConfig().set("Players.Credits." + player.getUniqueId().toString(), newcredits);		
		plugin.saveConfig();*/
		return false;
	}

}

