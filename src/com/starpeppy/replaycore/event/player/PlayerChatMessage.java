package com.starpeppy.replaycore.event.player;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.starpeppy.replaycore.StarPep;


public class PlayerChatMessage implements Listener {
	private StarPep plugin;

	public PlayerChatMessage(StarPep pl) {
		plugin = pl;
	}
	@EventHandler	
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		String pn = player.getName();
		List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
		if (allfrozen.contains(pn)) {
			if(plugin.getConfig().getString("Frozen.FrozenToggles.CanTalk").equals("Yope"))return;
		event.setCancelled(true);
		player.sendMessage(ChatColor.DARK_AQUA + "You're frozen.");
	}
	}
	
}
	

