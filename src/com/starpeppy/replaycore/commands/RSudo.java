package com.starpeppy.replaycore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RSudo implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean returnFalse = false;
			CommandSender player = sender;
		if(args.length != 7){
			player.sendMessage(ChatColor.DARK_AQUA + "/rsudo <player> <5 arguments. Put 0 to represent nothing. eg /kill BrightStarLight 0 0 0> <on/off> (if the player will run this as op)");
			return false;
		}

		Player target = null;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().toLowerCase().contains(args[0].toLowerCase())){
				returnFalse = false;
				target = p;
				break;
			}
			returnFalse = true;
		}
		if(returnFalse == true){
			player.sendMessage(ChatColor.DARK_AQUA + "Player offline!");
			return false;
		}
		if(player.isOp() == false){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
			return false;
		}
		if(!(player.hasPermission("replaycore.sudo"))){
			
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
			return false;
		}
		
		String fullchatmsg = args[1] +  " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5];
		String modchatmsg = fullchatmsg.replace('0', ' ');
		boolean isTargetOp = target.isOp();
		if(args[6].equals("off")){
			target.chat(modchatmsg);
		}
		else if(args[6].equals("on")){
		if(!(isTargetOp)){
		target.setOp(true);
		target.chat(modchatmsg);
		target.setOp(false);
		}
		else{
		target.chat(modchatmsg);
		}
		}
		player.sendMessage(ChatColor.BLUE + "Target sudo'd.");
		
		
		return false;
	}
}
