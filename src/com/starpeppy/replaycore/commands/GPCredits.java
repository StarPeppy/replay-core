package com.starpeppy.replaycore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class GPCredits implements CommandExecutor {
	private StarPep plugin;

	public GPCredits(StarPep pl) {
		plugin = pl;
	}

	
	
	
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
	
		if(!(sender.hasPermission("replaycurrency.givecredits")))return false;
		if(!(args.length == 2)){
			
			sender.sendMessage("/gpcredits <player> <integer>");
			
			return false;
		}
		Player tar = sender.getServer().getPlayer(args[0]); 

		String taruuid = tar.getUniqueId().toString();		
		int credits = plugin.getConfig().getInt("Players.Credits." + taruuid);
		int args1 = Integer.parseInt(args[1]);
		int newcredits = credits + args1;
		plugin.getConfig().set("Players.Credits." + taruuid, newcredits);
		plugin.saveConfig();
		
		
		return true;
	}

}

