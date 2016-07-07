package com.starpeppy.replaycore.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;


public class RBuy implements CommandExecutor {
	
	private StarPep plugin;
	public RBuy(StarPep pl){
		plugin = pl;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.DARK_AQUA + "You must be a player to use the command.");
			return false;
		}
		Player player = (Player) sender;
		if(!(player.hasPermission("replaycore.rbuy"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't have permission to use this command.");
			return false;
		}
		if(!(args.length > 0)){
			player.sendMessage(ChatColor.DARK_AQUA + "/rbuy <abandon, buy, check, tp (tp and abandon have no housename)> <housename>");
			return false;
		}
		/*
		 * 4) if the argument is abandon then remove them from the list of buyers/renters if the string is not void
		 * 3) if the argument is check check the price of said house.
		 * 1) if the argument is tp tp them to their house.
		 */
		if(!(args[0].matches("abandon|buy|check|tp"))){
			player.sendMessage(ChatColor.DARK_AQUA + "You didn't enter a valid argument. /rbuy");
			return false;
		}
		List<String> allHouses = plugin.getConfig().getStringList("Buildings.AllHouse");
		
		boolean canRunAbandon = false;
		if(plugin.getConfig().getString("Players.Houses." + player.getUniqueId().toString()) == null){
			plugin.getConfig().set("Players.Houses." + player.getUniqueId().toString(), "void");
			canRunAbandon = false;
		}
		else if(plugin.getConfig().getString("Players.Houses." + player.getUniqueId().toString()).equals("void")){
			canRunAbandon = false;
		}
		else{
			canRunAbandon = true;
		}
		
		if(args[0].matches("abandon|tp") && canRunAbandon == false){
			player.sendMessage(ChatColor.DARK_AQUA + "You don't own a house!");
			return false;
		}
		if(args[0].equals("buy") && canRunAbandon == true){
			player.sendMessage(ChatColor.DARK_AQUA + "You already own " + plugin.getConfig().getString("Players.Houses." + player.getUniqueId().toString()) + "!");
			return false;
		}
		
		if(args[0].equals("tp")){
			player.sendMessage(ChatColor.BLUE + "Teleporting to your house.");
			String ownedHouse = plugin.getConfig().getString("Players.Houses." + player.getUniqueId().toString());
			double x = plugin.getConfig().getDouble("Buildings.House." + ownedHouse + ".X");
			double y = plugin.getConfig().getDouble("Buildings.House." + ownedHouse + ".Y");
			double z = plugin.getConfig().getDouble("Buildings.House." + ownedHouse + ".Z");
			Location loc = new Location(player.getWorld(), x, y, z);
			player.teleport(loc);
			return true;
		}
		if(args[0].equals("abandon")){
			player.sendMessage(ChatColor.BLUE + "House abandoned.");
			String ownedHouse = plugin.getConfig().getString("Players.Houses." + player.getUniqueId().toString());
			List<String> owners = plugin.getConfig().getStringList("Players.AllHouseOwners");
			owners.remove(player.getUniqueId().toString());
			plugin.getConfig().set("Players.AllHouseOwners", owners);
			plugin.getConfig().set("Buildings.House." + ownedHouse + ".Owned", "No");
			plugin.getConfig().set("Players.Houses." + player.getUniqueId().toString(), "void");
			plugin.saveConfig();
			return true;
		}
		if(!(args.length == 2)){
			player.sendMessage(ChatColor.DARK_AQUA + "/rbuy <abandon, buy, check, tp (tp and abandon have no housename.)> <housename>");
			return false;
		}
		boolean houseExists = false;
		for(String houseName : allHouses){
			if(houseName.equals(args[1])){
				houseExists = true;
				break;
			}
		}
		if(houseExists != true){
			player.sendMessage(ChatColor.DARK_AQUA + "This house is disabled or doesn't exist.");
			return false;
		}
		if(args[0].equals("check")){
		
			String wantedHouse = args[1];
			int priced = plugin.getConfig().getInt("Buildings.House." + wantedHouse + ".Price");
			int taxes = plugin.getConfig().getInt("Buildings.House." + wantedHouse + ".Taxes");
			String rental = "permanent house, with the price of " + priced + ", and daily taxes of " + taxes + ".";
			if(taxes == 0){
				rental = "rental, with the daily price of " + priced + ".";
			}
			
			player.sendMessage(ChatColor.DARK_AQUA + "This house's name is " + wantedHouse + ". It's a " + rental);
			
			
			
		}
		if(args[0].equals("buy")){
			
			List<String> owners = plugin.getConfig().getStringList("Players.AllHouseOwners");
			String wantedHouse = args[1];
			int priced = plugin.getConfig().getInt("Buildings.House." + wantedHouse + ".Price");
			int taxes = plugin.getConfig().getInt("Buildings.House." + wantedHouse + ".Taxes");
			int price = priced + taxes;
			if(plugin.getConfig().getString("Buildings.House." + wantedHouse + ".Enabled").equals("No")){
				player.sendMessage(ChatColor.DARK_AQUA + "This house is disabled or doesn't exist.");
				return false;
			}
			if(plugin.getConfig().getString("Buildings.House." + wantedHouse + ".Owned").equals("Yes")){
				player.sendMessage(ChatColor.DARK_AQUA + "Someone else already owns this house.");
				return false;
			}
			if(!(checkMoney('D', price, player))){
				player.sendMessage(ChatColor.DARK_AQUA + "You don't have enough money for this house.");
				return false;
			}
			
			
			
			changeMoney('D', price * -1, player);
			player.sendMessage(ChatColor.BLUE + "You bought " + wantedHouse + "!");
			owners.add(player.getUniqueId().toString());
			plugin.getConfig().set("Players.AllHouseOwners", owners);
			plugin.getConfig().set("Buildings.House." + wantedHouse + ".Owned", "Yes");
			plugin.getConfig().set("Players.Houses." + player.getUniqueId().toString(), wantedHouse);
			plugin.saveConfig();
			
			//add to owners
			
			
		}
		
		
		
		return true;
	}
	
	public boolean checkMoney(char type, int amount, Player p) {
		String puuid = p.getUniqueId().toString();

		String typeofmoney = null;
		switch (type) {

		case 'C':
			typeofmoney = ".Credits.";
			break;
		case 'D':
			typeofmoney = ".Debits.";
			break;

		}

		int compare = plugin.getConfig().getInt("Players" + typeofmoney + puuid);

		if (amount > compare) {
			return false;
		}

		return true;
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
			inaccount = plugin.getConfig().getInt("Players.Credits." + puuid);
			// Gets the amount stored in the config under the player's name.
			// Sets it to the inaccount variable.
			revised = inaccount + amount; // The new revised amount is the
			// amount in the users
			// account + the amount specified. (This works for negatives for
			// charging because 3 +-3 = 0)
			plugin.getConfig().set("Players.Credits." + puuid, revised);
			// Sets the revised variable to the config.

			break;
		case 'D':
			inaccount = plugin.getConfig().getInt("Players.Debits." + puuid);

			revised = inaccount + amount;
			plugin.getConfig().set("Players.Debits." + puuid, revised);
			break;
		}
		plugin.saveConfig();

		return;
	}
	
}
