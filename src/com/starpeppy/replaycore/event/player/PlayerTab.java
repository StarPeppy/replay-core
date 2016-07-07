package com.starpeppy.replaycore.event.player;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

public class PlayerTab implements Listener {
				
	
	
	@EventHandler
	public void onTab(TabCompleteEvent event){
		if(! (event.getSender() instanceof Player)){
			return;
		}
		//Player p = (Player) event.getSender();
		/*@SuppressWarnings("unused")
		boolean isPlayerCommand = false;
        List<String> completions = event.getCompletions();
		List<String> allPlayerNames = new ArrayList<String>();
		allPlayerNames.add("@console"); // so I don't get a null error.
        for(Player player : Bukkit.getOnlinePlayers(){
        	String playerName = player.getName();
        	allPlayerNames.add(playerName);
        }
        for(String completion : completions){
        	if(allPlayerNames.contains(completion)){
        		isPlayerCommand = true;
        	}
        }
        if(isPlayerCommand = true){
        	return;
        }*/
		boolean isPlayerCommand = false;
        List<String> completions = event.getCompletions();
		for(Player player : Bukkit.getOnlinePlayers()){
			if(completions.contains(player.getName())){
				isPlayerCommand = true;
				break;
			}
		}
		if(isPlayerCommand == true){
			return;
		}
		else if(event.getSender().isOp() == false){
        event.setCancelled(true);
        return;
        }
        else{
        	return;
        }
	}
}