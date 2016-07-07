package com.starpeppy.replaycore.utilities;

import org.bukkit.entity.Player;

public class Change {
	private com.starpeppy.replaycore.StarPep plugin;

	public Change(com.starpeppy.replaycore.StarPep pl) {
		plugin = pl;
	}

	// false = credits, true = debits
	public Change() {

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
