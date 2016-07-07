package com.starpeppy.replaycore.event.player;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.starpeppy.replaycore.StarPep;

public class PlayerCommand implements Listener {
	
private StarPep plugin;
public PlayerCommand(StarPep pl){
	plugin = pl;
}
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = (Player) e.getPlayer();
		String pn = player.getName();
		List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
		if (allfrozen.contains(pn)) {
			if(plugin.getConfig().getString("Frozen.FrozenToggles.CanSendCommands").equals("Yope"))return;
		player.sendMessage(ChatColor.DARK_AQUA + "You're frozen.");
		e.setCancelled(true);
	}
	
	
	
}
}