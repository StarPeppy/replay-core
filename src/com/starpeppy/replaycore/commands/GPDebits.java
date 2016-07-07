package com.starpeppy.replaycore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class GPDebits implements CommandExecutor {
	private StarPep plugin;

	public GPDebits(StarPep pl) {
		plugin = pl;
	}
	

	
	
	
	

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if(!(sender.hasPermission("replaycurrency.givedebits")))return false;
		if(!(args.length == 2)){
			
			sender.sendMessage("/gpdebits <player> <integer>");
			
			return false;
		}
		
		Player tar = sender.getServer().getPlayer(args[0]); 

		String taruuid = tar.getUniqueId().toString();		
		int debits = plugin.getConfig().getInt("Players.Debits." + taruuid);
		int args1 = Integer.parseInt(args[1]);
		int newdebits = debits + args1;
		plugin.getConfig().set("Players.Debits." + taruuid, newdebits);
		plugin.saveConfig();
		
		return true;
	}

}

