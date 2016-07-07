package com.starpeppy.replaycore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RGive implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return false;
		}
		Player player = (Player) sender;
		if (!(player.hasPermission("replaycore.rgive")))
			return false;
		if (args.length == 0) {
			player.sendMessage(ChatColor.DARK_AQUA
					+ "/rgive [item] (case doesn't matter. with underscores, though; and it has to be a valid item.) <player> or <stacksize>");
			return false;
		}
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Recieve your items");
		ItemStack itemtogive = new ItemStack(Material.AIR);
		try {
			itemtogive = new ItemStack(Material.valueOf(args[0].toUpperCase()));
		} catch (IllegalArgumentException e) {
			player.sendMessage(ChatColor.DARK_AQUA + "This item does not exist! (Remember underscores!)");
			return false;
		} catch (CommandException e) {
			player.sendMessage(ChatColor.DARK_AQUA + "This item does not exist! (Remember underscores!)");
			return false;
		}
		if (args.length == 1) {
			inv.setItem(4, itemtogive);
		}
		if (args.length == 2) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().equals(args[1])) {
					inv.setItem(4, itemtogive);
					p.openInventory(inv);
					return true;
				}
			}

			if (args[1].contains("1") || args[1].contains("2") || args[1].contains("3") || args[1].contains("4")
					|| args[1].contains("5") || args[1].contains("6") || args[1].contains("7") || args[1].contains("8")
					|| args[1].contains("9") || args[1].contains("0")) {
				int stacksize = Integer.parseInt(args[1]);
				itemtogive.setAmount(stacksize);
				inv.setItem(4, itemtogive);
			}
		}
		player.openInventory(inv);
		return true;

	}

}
