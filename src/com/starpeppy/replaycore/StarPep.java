package com.starpeppy.replaycore;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import com.starpeppy.replaycore.commands.Credbalance;
import com.starpeppy.replaycore.commands.Debbalance;
import com.starpeppy.replaycore.commands.GCredits;
import com.starpeppy.replaycore.commands.GDebits;
import com.starpeppy.replaycore.commands.GPCredits;
import com.starpeppy.replaycore.commands.GPDebits;
import com.starpeppy.replaycore.commands.RBuilding;
import com.starpeppy.replaycore.commands.RBuy;
import com.starpeppy.replaycore.commands.RDev;
import com.starpeppy.replaycore.commands.RFly;
import com.starpeppy.replaycore.commands.RGamemode;
import com.starpeppy.replaycore.commands.RGive;
import com.starpeppy.replaycore.commands.RGlow;
import com.starpeppy.replaycore.commands.RHeal;
import com.starpeppy.replaycore.commands.RNick;
import com.starpeppy.replaycore.commands.RSudo;
import com.starpeppy.replaycore.commands.RTP;
import com.starpeppy.replaycore.commands.RTPA;
import com.starpeppy.replaycore.commands.RTPAHere;
import com.starpeppy.replaycore.commands.RTPHere;
import com.starpeppy.replaycore.commands.RTPList;
import com.starpeppy.replaycore.commands.RTPNo;
import com.starpeppy.replaycore.commands.RTPToggle;
import com.starpeppy.replaycore.commands.RTPYes;
import com.starpeppy.replaycore.commands.SP;
import com.starpeppy.replaycore.event.player.CommandSend;
import com.starpeppy.replaycore.event.player.PlayerBreakBlock;
import com.starpeppy.replaycore.event.player.PlayerChat;
import com.starpeppy.replaycore.event.player.PlayerChatMessage;
import com.starpeppy.replaycore.event.player.PlayerCommand;
import com.starpeppy.replaycore.event.player.PlayerJoin;
import com.starpeppy.replaycore.event.player.PlayerLeftClick;
import com.starpeppy.replaycore.event.player.PlayerLeftClick2;
import com.starpeppy.replaycore.event.player.PlayerMove2;
import com.starpeppy.replaycore.event.player.PlayerPlace;
import com.starpeppy.replaycore.event.player.PlayerRightClick;
import com.starpeppy.replaycore.event.player.PlayerRightClick2;
import com.starpeppy.replaycore.event.player.PlayerTab;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import net.md_5.bungee.api.ChatColor;

public class StarPep extends JavaPlugin {
	public void onEnable() {
		registerCommands();
		registerEvents();
		registerConfig();
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {

				double sysTime = getConfig().getDouble("SystemTime");
				double newSysTime = sysTime + 1;
				getConfig().set("SystemTime", newSysTime);
				saveConfig();

				if (getConfig().getBoolean("Collidable") == false) {
					if (getConfig().getBoolean("NewPlayers") == true) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams add nocollisions");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option nocollisions collisionRule never");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join nocollisions @a");
						getConfig().set("NewPlayers", false);
						saveConfig();
					}
				}
				if(newSysTime % 86400 == 0){
					Bukkit.broadcastMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "One real life day has passed! Taxes and rent are being collected currently!");
					List<String> owners = getConfig().getStringList("Players.AllHouseOwners");
					for(String owner : owners){
						UUID pUUID = UUID.fromString(owner);
						if(Bukkit.getPlayer(pUUID) == null){
							
						}
						
						else{
							Player hOwner = Bukkit.getPlayer(pUUID);
							String wantedHouse = getConfig().getString("Players.Houses." + hOwner.getUniqueId().toString());
							int priced = getConfig().getInt("Buildings.House." + wantedHouse + ".Price");
							int taxes = getConfig().getInt("Buildings.House." + wantedHouse + ".Taxes");
							int price = 0;
							if(taxes == 0){
								price = priced + taxes;
							}
							else{
								price = taxes;
							}
							changeMoney('D', price * -1, hOwner);
							hOwner.sendMessage(ChatColor.BLUE + "Your rent or taxes for " + wantedHouse + " went through successfully!");
							saveConfig();
							
						}
					}
					
					
					
					
				}
			}
		}, 0L, 20L);
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");

		// Check if the MVdWPlaceholderAPI plugin is present
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			FBPlace();
		}
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");

	}

	public void registerCommands() {
		getCommand("gcredits").setExecutor(new GCredits(this));
		getCommand("gdebits").setExecutor(new GDebits(this));
		getCommand("gpcredits").setExecutor(new GPCredits(this));
		getCommand("gpdebits").setExecutor(new GPDebits(this));
		getCommand("credbalance").setExecutor(new Credbalance(this));
		getCommand("debbalance").setExecutor(new Debbalance(this));
		getCommand("rb").setExecutor(new RBuilding(this));
		getCommand("rh").setExecutor(new RHeal());
		getCommand("rg").setExecutor(new RGive());
		getCommand("rbuy").setExecutor(new RBuy(this));
		getCommand("rtp").setExecutor(new RTP(this));
		getCommand("rtpa").setExecutor(new RTPA(this));
		getCommand("rtpahere").setExecutor(new RTPAHere(this));
		getCommand("rtphere").setExecutor(new RTPHere(this));
		getCommand("rtpno").setExecutor(new RTPNo(this));
		getCommand("rtpyes").setExecutor(new RTPYes(this));
		getCommand("rtptoggle").setExecutor(new RTPToggle(this));
		getCommand("rsudo").setExecutor(new RSudo());
		getCommand("rtplist").setExecutor(new RTPList(this));
		getCommand("rfly").setExecutor(new RFly(this));
		getCommand("sp").setExecutor(new SP(this));
		getCommand("rglow").setExecutor(new RGlow());
		getCommand("rdev").setExecutor(new RDev(this));
		getCommand("rgamemode").setExecutor(new RGamemode());
		getCommand("rnick").setExecutor(new RNick(this));
		
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerLeftClick(this), this);
		pm.registerEvents(new PlayerRightClick(this), this);
		pm.registerEvents(new CommandSend(this), this);
		pm.registerEvents(new PlayerTab(), this);
		pm.registerEvents(new PlayerChat(this), this);
		pm.registerEvents(new PlayerBreakBlock(this), this);
		pm.registerEvents(new PlayerChatMessage(this), this);
		pm.registerEvents(new PlayerCommand(this), this);
		pm.registerEvents(new PlayerMove2(this), this);
		pm.registerEvents(new PlayerPlace(this), this);
		pm.registerEvents(new PlayerLeftClick2(), this);
		pm.registerEvents(new PlayerRightClick2(), this);
	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void FBPlace() {
		// The plugin is enabled
		PlaceholderAPI.registerPlaceholder(this, "credits", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) { // Check
																				// if
																				// the
																				// placeholder
																				// is
																				// requested
																				// while
				// the player is online.
				// boolean online = event.isOnline();
				// The player if he is online
				// NULL: If the player is not online
				Player player = event.getPlayer();
				if (player == null) {
					return "Player offline.";
				}
				// The offline player if he is not online
				// NULL: If the placeholder is requested without
				// a player (like the console) DO CHECKS YOURSELF
				OfflinePlayer offlinePlayer = event.getPlayer();
				// The placeholder that is requested to be replaced
				// (more about this in a later API example)
				// String placeholder = event.getPlaceholder();

				if (offlinePlayer == null) {
					return "Player needed!";
				}
				int credits = getConfig().getInt("Players.Credits." + player.getUniqueId().toString());
				return "" + credits;
			}

		});
		PlaceholderAPI.registerPlaceholder(this, "debits", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) { // Check
																				// if
																				// the
																				// placeholder
																				// is
																				// requested
																				// while
				// the player is online.
				// boolean online = event.isOnline();
				// The player if he is online
				// NULL: If the player is not online
				Player player = event.getPlayer();
				if (player == null) {
					return "Player offline.";
				}
				// The offline player if he is not online
				// NULL: If the placeholder is requested without
				// a player (like the console) DO CHECKS YOURSELF
				OfflinePlayer offlinePlayer = event.getPlayer();
				// The placeholder that is requested to be replaced
				// (more about this in a later API example)
				// String placeholder = event.getPlaceholder();

				if (offlinePlayer == null) {
					return "Player needed!";
				}
				int debits = getConfig().getInt("Players.Debits." + player.getUniqueId().toString());
				return "" + debits;
			}

		});
	}
	public void changeMoney(char type, int amount, Player p) { // This is the
		// syntax for
		// the command.
		// Use this when you need to change the money of a player. Include the
		// player "p" the amount being charged
		// and the character type ('C' for credits and 'D' for debits.
		String puuid = p.getUniqueId().toString(); // Gets the player uuid so it
		// can store that
		// and be uuid compatible.
		int inaccount = 0; // Initializes the inaccount variable, which will
		// hold whatever's in the account as a temporary.

		int revised = 0;
		// Revised is how much money the player has after he has been charged.

		switch (type) {
		// Looks at the char variable type that you included.
		case 'C':
			// The char variable is C. This means you chose credits.
			inaccount = getConfig().getInt("Players.Credits." + puuid);
			// Gets the amount stored in the config under the player's name.
			// Sets it to the inaccount variable.
			revised = inaccount + amount; // The new revised amount is the
			// amount in the users
			// account + the amount specified. (This works for negatives for
			// charging because 3 +-3 = 0)
			getConfig().set("Players.Credits." + puuid, revised);
			// Sets the revised variable to the config.

			break;
		case 'D':
			inaccount = getConfig().getInt("Players.Debits." + puuid);

			revised = inaccount + amount;
			getConfig().set("Players.Debits." + puuid, revised);
			break;
		}
		saveConfig();

		return;
	}
}
