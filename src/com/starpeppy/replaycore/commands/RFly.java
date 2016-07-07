package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RFly implements CommandExecutor {
	private StarPep plugin;
	public RFly(StarPep pl){
		plugin = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (!(player.hasPermission("replaycore.rfly"))) {
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to do that!");
		}
		if (args.length > 1) {
			player.sendMessage(ChatColor.DARK_AQUA + "/rfly <on/off>");
			return false;
		}
		if (args.length == 1) {
			if (args[0].equals("on")) {
				player.setAllowFlight(true);
				player.setFlying(true);
				player.sendMessage(ChatColor.BLUE + "Flight turned on!");
				plugin.getConfig().set("Players.RFlyToggles." + player.getUniqueId().toString(), true);
				plugin.saveConfig();
				return true;
			}
			if (args[0].equals("off")) {
				player.setFlying(false);
				player.setAllowFlight(false);
				player.sendMessage(ChatColor.BLUE + "Flight turned off!");
				plugin.getConfig().set("Players.RFlyToggles." + player.getUniqueId().toString(), false);
				plugin.saveConfig();
				return true;
			}
		}
		else{
 			String PD = player.getUniqueId().toString();
 			if(!(plugin.getConfig().getBoolean("Players.RFlyToggles." + PD))){
 				player.setAllowFlight(true);
				player.setFlying(true);
				player.sendMessage(ChatColor.BLUE + "Flight turned on!");
				plugin.getConfig().set("Players.RFlyToggles." + player.getUniqueId().toString(), true);
				plugin.saveConfig();
 				return true;
 			}
 			else{
 				player.setFlying(false);
				player.setAllowFlight(false);
				player.sendMessage(ChatColor.BLUE + "Flight turned off!");
				plugin.getConfig().set("Players.RFlyToggles." + player.getUniqueId().toString(), false);
				plugin.saveConfig();
 				return true;
 			}
	}
		return false;
	}
}
