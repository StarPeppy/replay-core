package com.starpeppy.replaycore.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.starpeppy.replaycore.StarPep;

public class SP implements CommandExecutor {

	private StarPep plugin;

	public SP(StarPep pl) {
		plugin = pl;
	}

	static String boldblue = ChatColor.translateAlternateColorCodes('$', "$b$l");
	static String turq = ChatColor.translateAlternateColorCodes('$', "$3");
	static String fc = "$b\n• $3";
	static String f = ChatColor.translateAlternateColorCodes('$', fc);

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0) {
			String starlist = "Do /starpunish (or /sp) help (or ?) for a list of commands!";
			sender.sendMessage(boldblue + "Plugin made by Starpeppy, or BrightStarLight.\n" + turq + starlist);
			if (checkPlayer(sender) == true) {
				Player player = (Player) sender;
				playSound(player, Sound.ENTITY_LIGHTNING_THUNDER);
			}
			return true;
		}
		if (args.length == 1) {
			if (args[0].equals("help") || args[0].equals("?")) {
				if (sender.hasPermission("starpunish.help")) {

					sender.sendMessage(boldblue + "Commands include:" + f + "/sp freeze <player>" + f
							+ "/sp unfreeze <player>" + f + "/sp listfrozen" + f + "/sp freezeall (aka /sp panic)" + f
							+ "/sp disconnect <player>" + f
							+ "/sp reload (aka /sp rl)" + f
							+ "/sp dance <player>" + f
							+ "/sp undance <player>" + f
							+ "/sp listdancing"

					);
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						playSound(player, Sound.WEATHER_RAIN);
					}
					return true;
				} else {
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						playSound(player, Sound.ENTITY_BAT_DEATH);
					}
					return false;
				}
			}
			if (args[0].equals("reload") || args[0].equals("rl")) {
				if (sender.hasPermission("starpunish.reload")) {

					plugin.reloadConfig();
					sender.sendMessage(boldblue + "Config reloaded.");
					soundPlayer(sender, Sound.ENTITY_CREEPER_DEATH);
					return true;
				}
				sender.sendMessage("You don't have permission for that.");
				soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
				return false;
			}
			if (args[0].equals("listfrozen")) {
				if (sender.hasPermission("starpunish.listfrozen")) {
					List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
					boolean allfroze = allfrozen.isEmpty();
					if (allfroze) {
						sender.sendMessage(boldblue + "There are no frozen players.");
						if (checkPlayer(sender) == true) {
							Player player = (Player) sender;
							playSound(player, Sound.ENTITY_BAT_DEATH);
						}
						return false;
					}
					sender.sendMessage(boldblue + "All frozen players: ");
					for (String pn : allfrozen) {

						sender.sendMessage(turq + f + pn);
					}
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						playSound(player, Sound.BLOCK_FIRE_AMBIENT);
					}
				} else {
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						player.sendMessage(turq + "You don't have permission!");
						playSound(player, Sound.ENTITY_BAT_DEATH);
					}
				}
			}
			if (args[0].equals("listdancing")) {
				if (sender.hasPermission("starpunish.listdancing")) {
					List<String> alldancingl = plugin.getConfig().getStringList("Dancing.All");
					boolean alldancing = alldancingl.isEmpty();
					if (alldancing) {
						sender.sendMessage(boldblue + "There are no dancing players.");
						if (checkPlayer(sender) == true) {
							Player player = (Player) sender;
							playSound(player, Sound.ENTITY_BAT_DEATH);
						}
						return false;
					}
					sender.sendMessage(boldblue + "All dancing players: ");
					for (String pn : alldancingl) {

						sender.sendMessage(turq + f + pn);
					}
					soundPlayer(sender, Sound.ENTITY_GENERIC_EAT);
					return true;
				} else {
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						player.sendMessage(turq + "You don't have permission!");
						playSound(player, Sound.ENTITY_BAT_DEATH);
					}
				}
			}
			if (args[0].equals("freezeall") || args[0].equals("panic")) {
				if (sender.hasPermission("starpunish.freezeall")) {

					List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");

					sender.sendMessage(
							ChatColor.DARK_RED + "" + ChatColor.BOLD + "All online players have been frozen.");
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
						if (player != sender) {
							if (!allfrozen.contains(player.getName())) {

								allfrozen.add(player.getName());
							}
						}

					}
					plugin.getConfig().set("Frozen.All", allfrozen);
					plugin.saveConfig();
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						playSound(player, Sound.BLOCK_SNOW_BREAK);
					}
					return true;
				} else {
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						playSound(player, Sound.ENTITY_BAT_DEATH);
					}
					return false;
				}
			}

		}
		if (args.length == 2) {
			if (args[0].equals("freeze")) {
				if (sender.hasPermission("starpunish.freeze")) {

					if (Bukkit.getPlayerExact(args[1]) != null) {
						Player player = Bukkit.getServer().getPlayer(args[1]);
						if (sender == player) {
							sender.sendMessage(turq + "You can't freeze yourself. This would be very bad...");
							if (checkPlayer(sender) == true) {
								Player player2 = (Player) sender;
								playSound(player2, Sound.ENTITY_BAT_DEATH);
							}
							return false;

						}

						String UUID = player.getUniqueId().toString();
						List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
						if (allfrozen.contains(player.getName())) {
							sender.sendMessage(turq + "This player is already frozen.");
							if (checkPlayer(sender) == true) {
								Player player2 = (Player) sender;
								playSound(player2, Sound.ENTITY_BAT_DEATH);
							}
							return false;
						}
						allfrozen.add(player.getName());
						plugin.getConfig().set("Frozen.All", allfrozen);
						plugin.getConfig().set("Frozen." + UUID + ".Name", player.getName());
						plugin.getConfig().set("Frozen." + UUID + ".Frozen", "Yes");
						sender.sendMessage(boldblue + player.getName() + " was frozen! Brrrr...");
						player.sendMessage(turq + "You were frozen.");
						plugin.saveConfig();

						if (checkPlayer(sender) == true) {
							Player player2 = (Player) sender;
							playSound(player2, Sound.BLOCK_SNOW_STEP);
							return true;
						}
					} else {
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
						String UUID = player.getUniqueId().toString();
						if (sender == player) {
							sender.sendMessage(turq + "You can't freeze yourself.");
							if (checkPlayer(sender) == true) {
								Player player2 = (Player) sender;
								playSound(player2, Sound.ENTITY_BAT_DEATH);
							}
							return false;

						}
						List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
						if (allfrozen.contains(player.getName())) {
							sender.sendMessage(turq + "This player is already frozen.");
							if (checkPlayer(sender) == true) {
								Player player2 = (Player) sender;
								playSound(player2, Sound.ENTITY_BAT_DEATH);
							}
							return false;
						}
						allfrozen.add(player.getName());
						plugin.getConfig().set("Frozen.All", allfrozen);
						plugin.getConfig().set("Frozen." + UUID + ".Name", player.getName());
						plugin.getConfig().set("Frozen." + UUID + ".Frozen", "Yes");
						sender.sendMessage(boldblue + player.getName() + " was frozen! Brrrr...");
						plugin.saveConfig();
						if (checkPlayer(sender) == true) {
							Player player2 = (Player) sender;
							playSound(player2, Sound.BLOCK_SNOW_STEP);
							return true;
						}
						// Player not online
					}

				} else {
					if (checkPlayer(sender) == true) {
						Player player = (Player) sender;
						playSound(player, Sound.ENTITY_BAT_DEATH);
						return false;
					}
				}
				return false;
			}
			if (args[0].equals("dance")) {
				if (sender.hasPermission("starpunish.dance")) {
					if (Bukkit.getPlayerExact(args[1]) == null) {
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
						if (op.hasPlayedBefore() == false) {
							soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
							sender.sendMessage(turq + "That player couldn't be found.");
							return false;
						}

					}
					List<String> alldancing = plugin.getConfig().getStringList("Dancing.All");
					if (alldancing.contains(args[1])) {
						sender.sendMessage(turq + "This player is already dancing.");
						soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
						return false;
					}
					alldancing.add(args[1]);
					plugin.getConfig().set("Dancing.All", alldancing);
					plugin.saveConfig();
					sender.sendMessage(boldblue + "Player set to dancing! Bada-bing!");
					soundPlayer(sender, Sound.ENTITY_PLAYER_LEVELUP);
					return true;

				}
				sender.sendMessage(turq + "You don't have permission.");
				soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
			}

		}
		if (args[0].equals("undance")) {

			if (sender.hasPermission("starpunish.undance")) {
				if (Bukkit.getPlayerExact(args[1]) == null) {
					OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
					if (op.hasPlayedBefore() == false) {
						soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
						sender.sendMessage(turq + "That player couldn't be found.");
						return false;
					}

				}

				List<String> alldancing = plugin.getConfig().getStringList("Dancing.All");
				if (alldancing.contains(args[1])) {
					alldancing.remove(args[1]);
					plugin.getConfig().set(("Dancing.All"), alldancing);
					plugin.saveConfig();
					sender.sendMessage(boldblue + "You're boring :3... No more dancing for them!");
					soundPlayer(sender, Sound.ENTITY_GENERIC_DRINK);
					return true;
				}
				sender.sendMessage(turq + "That player isn't dancing.");
				soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
				return false;
			}
			sender.sendMessage(turq + "You don't have permission.");
			soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
			return false;
		}
		if (args[0].equals("disconnect")) {
			if (sender.hasPermission("starpunish.disconnect")) {

				if (Bukkit.getPlayerExact(args[1]) != null) {
					Player player = Bukkit.getServer().getPlayer(args[1]);
					Random rand = new Random();
					int random = rand.nextInt(40) + 1;
					if (random > 0 && random < 10) {

						player.kickPlayer(
								"Internal Exception: java.util.concurrent.ExecutionException: java.lang.IllegalArgumentException: Default value cannot be lower than minimum value!");
					}
					if (random > 10 && random < 20 || random == 10) {
						player.kickPlayer(
								"Internal exception: java.net.SocketException: Software caused connection abort: recv failed");
						// player.sendMessage("Between 10 and 19.");
					}
					if (random > 20 && random < 30 || random == 20) {
						player.kickPlayer(
								"Internal exception: java.net.SocketException: Software caused connection abort: socket write error");
						// player.sendMessage("Between 20 and 29.");
					}
					if (random > 30 && random < 40 || random == 30 || random == 40) {
						player.kickPlayer(
								"Internal Exception: io.netty.handler.codec.DecoderException: java.io.IOException: Packet 0/66 was larger than I expected, found 3 bytes extra whilst reading packet 66");
						// player.sendMessage("Between 30 and 40.");
					}

					sender.sendMessage(boldblue + "Player removed with an inconspicous message.");

				}
				if (checkPlayer(sender) == true) {
					Player player2 = (Player) sender;
					playSound(player2, Sound.BLOCK_IRON_DOOR_CLOSE);
					return true;
				}
			} else {
				if (checkPlayer(sender) == true) {
					Player player2 = (Player) sender;
					playSound(player2, Sound.ENTITY_BAT_DEATH);
					return false;
				}
			}
		}
		if (args[0].equals("unfreeze")) {
			if (sender.hasPermission("starpunish.unfreeze")) {
				if (Bukkit.getPlayerExact(args[1]) != null) {
					Player player = Bukkit.getServer().getPlayer(args[1]);
					if (sender == player) {
						sender.sendMessage(turq + "You can't unfreeze yourself.");
						if (checkPlayer(sender) == true) {
							Player player2 = (Player) sender;
							playSound(player2, Sound.ENTITY_BAT_DEATH);
						}
						return false;
					}

					List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
					if (!allfrozen.contains(player.getName())) {
						sender.sendMessage(turq + "This player is not frozen.");
						if (checkPlayer(sender) == true) {
							Player player2 = (Player) sender;
							playSound(player2, Sound.ENTITY_BAT_DEATH);
						}
						return false;
					}
					allfrozen.remove(player.getName());
					plugin.getConfig().set("Frozen.All", allfrozen);
					String UUID = player.getUniqueId().toString();
					plugin.getConfig().set("Frozen." + UUID + ".Frozen", "No");
					sender.sendMessage(boldblue + player.getName() + " was unfrozen! What a nice day...");
					player.sendMessage(turq + "You were unfrozen.");
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5, 5));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 5));
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 5));
					plugin.saveConfig();
				} else {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
					String UUID = player.getUniqueId().toString();
					if (sender == player)
						return false;
					List<String> allfrozen = plugin.getConfig().getStringList("Frozen.All");
					if (!allfrozen.contains(player.getName())) {
						sender.sendMessage(turq + "This player is not frozen.");
						if (checkPlayer(sender) == true) {
							Player player2 = (Player) sender;
							playSound(player2, Sound.ENTITY_BAT_DEATH);
						}
						return false;
					}
					allfrozen.remove(player.getName());
					plugin.getConfig().set("Frozen.All", allfrozen);
					plugin.getConfig().set("Frozen." + UUID + ".Frozen", "No");

					// Make list of peeps to read off of.
					sender.sendMessage(boldblue + player.getName() + " was unfrozen! What a nice day...");
					plugin.saveConfig();
					// Player not online
				}
				if (checkPlayer(sender) == true) {
					Player player2 = (Player) sender;
					playSound(player2, Sound.BLOCK_FURNACE_FIRE_CRACKLE);
					return true;
				}
			} else {
				if (checkPlayer(sender) == true) {
					Player player2 = (Player) sender;
					playSound(player2, Sound.ENTITY_BAT_DEATH);
				}
			}
		}

		// Next command here
		else{
		sender.sendMessage(turq + "Unknown command or usage! /sp ? for a list of commands and usage.");
		soundPlayer(sender, Sound.ENTITY_BAT_DEATH);
		return false;
		}
		return false;
	}

	public boolean checkPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		}
		return false;
	}

	public void playSound(Player player, Sound sound) {
		if (plugin.getConfig().getBoolean("Sounds") == true) {
			player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
		}

	}

	public void soundPlayer(CommandSender sender, Sound sound) {
		if (checkPlayer(sender) == true) {
			Player player2 = (Player) sender;
			playSound(player2, sound);
		}
	}
}
