package com.starpeppy.replaycore.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class RBuilding implements CommandExecutor {
	public StarPep plugin;

	public RBuilding(StarPep pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return false;
		}
		Player player = (Player) sender;

		if (player.hasPermission("replaycore.rbuilding")) {
			if (args.length == 0) {
				player.sendMessage(ChatColor.DARK_AQUA
						+ "Use a blaze rod to select an area, then use /rb <create, list, show, remove (list doesn't need args. show needs a type. remove needs type and name. create needs everything.)> (c, d, l, s, r) <Type> (e.g. House) <Name> (e.g. H1 or whatever you want to name it.)  <Price> (Price to be payed during recurring cycles or one time payed price.) <true/false> (Whether it should be recurring. Once a day.) <bills/taxes per day> (The bills and taxes payed per day. If it's recurring put 0, since it's included in the rent.)");
				return false;
			}
			if (args.length == 3) {
				if (args[0].equals("remove") || args[0].equals("r")) {
					String type = args[1];
					String name = args[2];
					plugin.getConfig().set("Buildings." + type + "." + name + ".Enabled", false);
					List<String> allHouses = plugin.getConfig().getStringList("Buildings.All" + type);
					allHouses.remove(name);
					plugin.getConfig().set("Buildings.All" + type, allHouses);
					plugin.saveConfig();
					player.sendMessage(ChatColor.AQUA + "Building has been permanently disabled.");
					return true;
				}
			}
			if (args.length == 2) {
				if (args[0].equals("show") || args[0].equals("s")) {
					List<String> allBuildings = plugin.getConfig().getStringList("Buildings.All" + args[1]);
					player.sendMessage("The current " + args[1] + "s are: ");
					for (String buildingName : allBuildings) {
						player.sendMessage(buildingName);
						double x = plugin.getConfig().getInt("Buildings." + args[1] + "." + buildingName + ".X");
						double y = plugin.getConfig().getInt("Buildings." + args[1] + "." + buildingName + ".Y");
						double z = plugin.getConfig().getInt("Buildings." + args[1] + "." + buildingName + ".Z");
						player.sendMessage("At coordinates: " + x + ", " + y + ", and " + z + ".");
					}
					return true;
				}
			} else if (args.length == 1) {
				if (args[0].equals("list") || args[0].equals("l")) {
					player.sendMessage(ChatColor.AQUA + "The current existing building(s) (is/are): House");
				}
			} else if (args.length != 6) {
				player.sendMessage(ChatColor.DARK_AQUA
						+ "Use a blaze rod to select an area, then use /rb <create, list, show, remove (list doesn't need args. show needs a type. remove needs type and name. create needs everything.)> (c, d, l, s, r) <Type> (e.g. House) <Name> (e.g. H1 or whatever you want to name it.)  <Price> (Price to be payed during recurring cycles or one time payed price.) <true/false> (Whether it should be recurring. Once a day.) <bills/taxes per day> (The bills and taxes payed per day. If it's recurring put 0, since it's included in the rent.)");
				return false;
			} else if (args.length == 6) {
				String bool = null;
				boolean booli = false;
				if (args[4].equals("true")) {
					bool = "on";
					booli = true;
				} else {
					bool = "off";
					booli = false;
				}

				player.sendMessage("A(n) " + args[1] + " was created, with the name of " + args[2]
						+ ", with recurring rental cycles " + bool + ", with a price/rent of " + args[3]
						+ " debits, and with a daily tax/bill rate of " + args[5] + ".");
				String type = args[1];
				String name = args[2];
				boolean cycles = booli;
				double rent = Double.parseDouble(args[3]);
				double tax = Double.parseDouble(args[5]);
				if (!(type.equals("House"))/* || type.equals("SomethingElse") */) {
					player.sendMessage("You have to specify a type that exists! Do /rb l to see the current types!");
				}
				plugin.getConfig().set("Buildings." + type + "." + name + ".Owned", false);
				plugin.getConfig().set("Buildings." + type + "." + name + ".Price", rent);
				plugin.getConfig().set("Buildings." + type + "." + name + ".Recurring", cycles);
				plugin.getConfig().set("Buildings." + type + "." + name + ".Taxes", tax);
				plugin.getConfig().set("Buildings." + type + "." + name + ".Enabled", true);
				plugin.getConfig().set("Buildings." + type + "." + name + ".X", player.getLocation().getX());
				plugin.getConfig().set("Buildings." + type + "." + name + ".Y", player.getLocation().getY());
				plugin.getConfig().set("Buildings." + type + "." + name + ".Z", player.getLocation().getZ());
				//R
				double rDX = plugin.getConfig().getDouble("Players.RSelection." + player.getUniqueId().toString() + ".X");
				double rDY = plugin.getConfig().getDouble("Players.RSelection." + player.getUniqueId().toString() + ".Y");
				double rDZ  = plugin.getConfig().getDouble("Players.RSelection." + player.getUniqueId().toString() + ".Z");
				plugin.getConfig().set("Buildings." + type + "." + name + ".RXCorner", rDX);
				plugin.getConfig().set("Buildings." + type + "." + name + ".RYCorner", rDY);
				plugin.getConfig().set("Buildings." + type + "." + name + ".RZCorner", rDZ);
				//L
				double lDX = plugin.getConfig().getDouble("Players.LSelection." + player.getUniqueId().toString() + ".X");
				double lDY = plugin.getConfig().getDouble("Players.LSelection." + player.getUniqueId().toString() + ".Y");
				double lDZ  = plugin.getConfig().getDouble("Players.LSelection." + player.getUniqueId().toString() + ".Z");
				plugin.getConfig().set("Buildings." + type + "." + name + ".LXCorner", lDX);
				plugin.getConfig().set("Buildings." + type + "." + name + ".LYCorner", lDY);
				plugin.getConfig().set("Buildings." + type + "." + name + ".LZCorner", lDZ);
				List<String> allHouses = plugin.getConfig().getStringList("Buildings.AllHouse");
				allHouses.add(name);
				plugin.getConfig().set("Buildings.AllHouse", allHouses);
				plugin.saveConfig(); // That wasn't there. I'm stupid lol...
				return true;
			}
		}

		return false;
	}

}
