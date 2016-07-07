package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class Credbalance implements CommandExecutor {
	private StarPep plugin;

	public Credbalance(StarPep pl) {
		plugin = pl;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(!(sender instanceof Player))return false;
		

		Player player = (Player) sender;
		if(player.hasPermission("replaycurrency.credbalance")){
		int credits = plugin.getConfig().getInt("Players.Credits." + player.getUniqueId().toString());
		player.sendMessage(ChatColor.translateAlternateColorCodes('$', "$3$lYou have " + credits + " credits!"));
		}
		return true;
	}

}
