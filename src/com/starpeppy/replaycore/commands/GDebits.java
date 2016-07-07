package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class GDebits implements CommandExecutor {
	private StarPep plugin;

	public GDebits(StarPep pl) {
		plugin = pl;
	}

	
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return false;
		}
		Player player = (Player) sender;

		if(!(player.hasPermission("replaycurrency.giveselfdebits")))return false;
		if(!(args.length == 1)){
			
			player.sendMessage("/gdebits <integer>");
			
			return false;
			}
		int debits = plugin.getConfig().getInt("Players.Debits." + player.getUniqueId().toString());
		int args0 = Integer.parseInt(args[0]);
		int newdebits = debits + args0;
		plugin.getConfig().set("Players.Debits." + player.getUniqueId().toString(), newdebits);		
		plugin.saveConfig();
		return true;
		
	}

}

