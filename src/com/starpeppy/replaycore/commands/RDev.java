package com.starpeppy.replaycore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RDev implements CommandExecutor {

	@SuppressWarnings("unused")
	private StarPep plugin;

	public RDev(StarPep pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;

		if (player.hasPermission("replaycore.brightstarlight")) {
			
			
			
			Location loc = player.getLocation();
			EntityType ent = EntityType.BOAT;
			Entity entity = loc.getWorld().spawnEntity(loc, ent);
			entity.setGravity(false);
			entity.setGlowing(true);
			return true;

		} else {
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
		}
		return false;
	}
	public Player getPlayerInSight(Player player) {
		   for(Entity e : player.getNearbyEntities(8, 8, 8))
		     if(e instanceof Player && player.hasLineOfSight(e))
		       return (Player) e;
		   return null;
		 }
	/*public String getCardinalDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 90) % 360;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if (0 <= rotation && rotation < 22.5) {
			return "N";
		} else if (22.5 <= rotation && rotation < 67.5) {
			return "NE";
		} else if (67.5 <= rotation && rotation < 112.5) {
			return "E";
		} else if (112.5 <= rotation && rotation < 157.5) {
			return "SE";
		} else if (157.5 <= rotation && rotation < 202.5) {
			return "S";
		} else if (202.5 <= rotation && rotation < 247.5) {
			return "SW";
		} else if (247.5 <= rotation && rotation < 292.5) {
			return "W";
		} else if (292.5 <= rotation && rotation < 337.5) {
			return "NW";
		} else if (337.5 <= rotation && rotation < 360.0) {
			return "N";
		} else {
			return null;
		}
	}*/

}
