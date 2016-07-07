package com.starpeppy.replaycore.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RTPAHere implements CommandExecutor{
	private StarPep plugin;
	public RTPAHere(StarPep pl){
		plugin = pl;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean returnFalse = false;
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		if(args.length != 1){
			player.sendMessage(ChatColor.DARK_AQUA + "/rtpahere <player>, or /tpahere <player>");
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
		if(target.getName().equals(player.getName())){
			player.sendMessage(ChatColor.DARK_AQUA + "You can't teleport to yourself.");
			return false;
		}
		if(!(player.hasPermission("replaycore.rtpahere"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
			return false;
		}
		
		if(!(plugin.getConfig().getBoolean("Players.TPToggles." + target.getUniqueId().toString()))){
			player.sendMessage(ChatColor.DARK_AQUA + "This player has TP's toggled off.");
			return false;
		}
		
		player.sendMessage(ChatColor.BLUE + "Teleport request sent to " + ChatColor.AQUA + target.getName() + ".");
		target.sendMessage(ChatColor.AQUA + "Teleport request recieved from " + ChatColor.BLUE + player.getName() + ChatColor.AQUA + " to tp to them.");
		target.sendMessage(ChatColor.DARK_AQUA + "Use /rtpyes || /tpaccept  to accept, or /rtpno || /tpdeny to deny.");
		target.sendMessage(ChatColor.DARK_AQUA + "Use /rtplist to see a list of pending requests.");
		List<String> pendingForTarget = plugin.getConfig().getStringList("Players.PendingRequestsTPAHERE." + target.getUniqueId().toString());
		List<String> pendingForTargetThere = plugin.getConfig().getStringList("Players.PendingRequestsTPA." + target.getUniqueId().toString());
		if(pendingForTargetThere.contains(player.getName())){
			pendingForTargetThere.remove(player.getName());
			plugin.getConfig().set("Players.PendingRequestsTPA." + target.getUniqueId().toString(), pendingForTarget);
		}
		if(pendingForTarget.contains(player.getName())){
			
			return false;
		}
		pendingForTarget.add(player.getName());
		plugin.getConfig().set("Players.PendingRequestsTPAHERE." + target.getUniqueId().toString(), pendingForTarget);
		plugin.saveConfig();
		return true;
	}
	
	
	
}

