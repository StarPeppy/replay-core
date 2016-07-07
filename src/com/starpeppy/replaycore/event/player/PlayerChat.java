package com.starpeppy.replaycore.event.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.starpeppy.replaycore.StarPep;
import com.starpeppy.replaycore.utilities.SystemTime;


public class PlayerChat implements Listener {
	// This is the chat listener.
	private StarPep plugin;
	public PlayerChat(StarPep pl){
		plugin = pl;
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		String newmsg = event.getMessage();
			if(event.getPlayer().hasPermission("replaycore.chatcolors")){
				newmsg = ChatColor.translateAlternateColorCodes('&', event.getMessage());
			}
			ChatColor chatColor = null;
			if(event.getPlayer().isOp()){
				chatColor = ChatColor.DARK_AQUA;
			}
			else{
				chatColor = ChatColor.BLUE;
			}
			Player player = event.getPlayer();
			String name = plugin.getConfig().getString("Players.Nicks." + player.getUniqueId().toString());
			if(name == null){
				name = player.getName();
			}
			String format = chatColor + name + ChatColor.AQUA + " » " + ChatColor.WHITE + newmsg;
				event.setFormat(format.replace('%', ' '));
				if(event.getPlayer().hasPermission("replaycore.mention")){
		if(event.getMessage().contains("@")){
			String pMsg = event.getMessage();
			for(Player p : Bukkit.getOnlinePlayers()){
				if(pMsg.contains(p.getName())){
					if(p.equals(player)){
					player.sendMessage(ChatColor.DARK_AQUA + "You can't mention yourself.");	
					}
					else{
					player.sendMessage(ChatColor.BLUE + p.getName() + " was told you said his name!");
					player.playSound(p.getLocation(), Sound.ENTITY_PLAYER_SWIM, 1, 1);
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
					p.sendMessage(ChatColor.AQUA + player.getName() + " said your name!");
					}
				}
			}
			
		}
				}
		if(event.getPlayer().isOp())return;
		SystemTime time = new SystemTime();
		double storedOldSysTime = 0;
		try{
			storedOldSysTime = plugin.getConfig().getDouble("Players.Cooldowns.MSGCooldown." + event.getPlayer().getName());
		}
		catch(NullPointerException e){
			double currentSysTime = plugin.getConfig().getDouble("SystemTime");
			plugin.getConfig().set("Players.Cooldowns.MSGCooldown." + event.getPlayer().getName(), currentSysTime);
			plugin.saveConfig();
		}
		//every time player sends command save system time and compare it to last time's
		double checkCooldown = time.calculateDifference(storedOldSysTime, plugin.getConfig().getDouble("SystemTime"));
		if(!(checkCooldown >= 2)){
			event.getPlayer().sendMessage(ChatColor.RED + "You need to wait 2 seconds between chat messages!");
			event.setCancelled(true);
			return;
		}
		
		double currentSysTime = plugin.getConfig().getDouble("SystemTime");
		plugin.getConfig().set("Players.Cooldowns.MSGCooldown." + event.getPlayer().getName(), currentSysTime);
		plugin.saveConfig();
		
		
		
	}
	
	
	
	
}
