package com.starpeppy.replaycore.event.player;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.starpeppy.replaycore.StarPep;

public class PlayerMove2 implements Listener {

	private StarPep plugin;

	public PlayerMove2(StarPep pl) {
		plugin = pl;
	}
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.isCancelled() || e.getFrom().getBlock().getLocation() == e.getFrom().getBlock().getLocation())
			return;
		Player player = (Player) e.getPlayer();
		String pn = player.getName();
		List<String> alldancing = plugin.getConfig().getStringList("Dancing.All");
			if(alldancing.contains(pn)){
				
				
				float yaw = e.getPlayer().getLocation().getYaw();
				float newyaw = yaw + Random(100, 10);
				float pitch = e.getPlayer().getLocation().getPitch();
				float newpitch = pitch + Random(180, 100);
			Location loc = new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY(),e.getPlayer().getLocation().getZ(), newyaw, newpitch);
				e.getPlayer().teleport(loc);
				
			}
		
			List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
				if (allfrozen.contains(pn)) { 
						if(plugin.getConfig().getString("Frozen.FrozenToggles.CanMove").equals("Yope"))return;
							e.getPlayer().setVelocity(new Vector().zero());
									Location loc = e.getFrom().getBlock().getLocation();
											player.teleport(loc);
													}
	}

	public int Random(int maximum, int minimum){
		Random rn = new Random();
		int range = maximum - minimum + 1;
		int randomNum =  rn.nextInt(range) + minimum;
		return randomNum;
	}
}
