package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;


public class RCoreReload implements CommandExecutor {

	private StarPep plugin;

	public RCoreReload(StarPep pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "ReplayCORE config reloaded.");
			plugin.reloadConfig();

			return true;
		}
		Player player = (Player) sender;

		if (player.hasPermission("replaycore.reload")) {
			player.sendMessage(ChatColor.BLUE + "ReplayCORE config reloaded.");
			plugin.reloadConfig();
			return true;

		} else {
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.ocmm gg");
		}
		return false;
	}

}
