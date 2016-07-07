package com.starpeppy.replaycore.event.player;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.starpeppy.replaycore.StarPep;

public class PlayerJoin implements Listener {
	private StarPep plugin;
	public PlayerJoin(StarPep pl){
		plugin = pl;
	}
	@EventHandler	
	public void onPlayerJoin(PlayerLoginEvent event){
		Player player = event.getPlayer();
	if(player.hasPermission("replaycore.spawnincreative")){
		player.setGameMode(GameMode.CREATIVE);
	}
	List<String> allPlayers = plugin.getConfig().getStringList("AllPlayers");
	if(!allPlayers.contains(player.getName())){
		allPlayers.add(player.getName());
		plugin.getConfig().set("AllPlayers", allPlayers);
		plugin.getConfig().set("NewPlayers", true);
		plugin.saveConfig();
	}
		
		String PD = player.getUniqueId().toString();
		if(!(plugin.getConfig().get("Players.TPToggles." + PD) == null)){
			
		}
		else{
			plugin.getConfig().addDefault("Players.TPToggles." + PD, true);
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
		}

if(!(plugin.getConfig().get("Players.Credits." + PD) == null)){
			
			
		}
		else{
	plugin.getConfig().addDefault("Players.Credits." + PD, 0);
	plugin.getConfig().options().copyDefaults(true);
	plugin.saveConfig();
		}
		if(!(plugin.getConfig().get("Players.Debits." + PD) == null)){
			
			
		}
		
		else{
	plugin.getConfig().addDefault("Players.Debits." + PD, 0);
	plugin.getConfig().options().copyDefaults(true);
	plugin.saveConfig();
		}
	
	}
	
	
}
