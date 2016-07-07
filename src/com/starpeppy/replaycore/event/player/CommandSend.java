package com.starpeppy.replaycore.event.player;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.starpeppy.replaycore.StarPep;
import com.starpeppy.replaycore.utilities.SystemTime;


public class CommandSend extends SystemTime implements Listener{

	private StarPep plugin;
	public CommandSend(StarPep pl){
		plugin = pl;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		if(event.getPlayer().isOp())return;
		SystemTime time = new SystemTime();
		double storedOldSysTime = 0;
		try{
			storedOldSysTime = plugin.getConfig().getDouble("Players.Cooldowns.CMDCooldown." + event.getPlayer().getName());
		}
		catch(NullPointerException e){
			double currentSysTime = plugin.getConfig().getDouble("SystemTime");
			plugin.getConfig().set("Players.Cooldowns.CMDCooldown." + event.getPlayer().getName(), currentSysTime);
			plugin.saveConfig();
		}
		//everytime player sends command save system time and compare it to last time's
		double checkCooldown = time.calculateDifference(storedOldSysTime, plugin.getConfig().getDouble("SystemTime"));
		if(!(checkCooldown >= 2)){
			event.getPlayer().sendMessage(ChatColor.RED + "You need to wait 2 seconds between commands!");
			event.setCancelled(true);
			return;
		}
		
		double currentSysTime = plugin.getConfig().getDouble("SystemTime");
		plugin.getConfig().set("Players.Cooldowns.CMDCooldown." + event.getPlayer().getName(), currentSysTime);
		plugin.saveConfig();
		
		// I don't know why that logic hurt so bad yesterday...
		
		// That's so if they type for example /bukkit:help it won't work
		// 
		if(event.getMessage().contains(":")){
			event.getPlayer().sendMessage("Unknown command. Type \"/help\" for help.");
			event.setCancelled(true);	
			return;
		}
		String msg = event.getMessage().replace(" ", "");
		String msg2 = msg.replace("/", "").toLowerCase();
		if(msg2.equals("plugins") || msg2.equals("pl")){
			event.getPlayer().sendMessage("Plugins (1): " + ChatColor.GREEN + "ReplayCORE");
			event.setCancelled(true);
			return;
		}
		List<String> allCmds = plugin.getConfig().getStringList("AllDisabledCommands");
		String msg3 = event.getMessage().replace(" ", "").toLowerCase();
		if(allCmds.contains(msg3)){
			if(plugin.getConfig().getBoolean("PermNodesWork") == true){
				String msg4 = msg3.replace("/", "");
				String permission = plugin.getConfig().getString("AllDisabledCommandsPerms." + msg4);
				if(event.getPlayer().hasPermission(permission))return;
		
			event.getPlayer().sendMessage(plugin.getConfig().getString("DisabledCommandMessage"));
			event.setCancelled(true);
		}
			else{
				event.getPlayer().sendMessage(plugin.getConfig().getString("DisabledCommandMessage"));
				event.setCancelled(true);
			}
		}
		
		
	}
	
}
