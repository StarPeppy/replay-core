package com.starpeppy.replaycore.utilities;

import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class Display {
	private StarPep plugin;

	public Display(StarPep pl) {
		plugin = pl;
	}
	// false = credits, true = debits

	public int displayMoney(Player p, int amount, char type) {

		String puuid = p.getUniqueId().toString();

		String typeofmoney = null;
		switch (type) {

		case 'C':
			typeofmoney = ".Credits.";
			break;
		case 'D':
			typeofmoney = ".Debits.";
			break;
		default:
			System.out.println("INVALID MONEY EXCEPTION: USE OF CHAR INVALID.");
			break;
		}

		int inaccount = plugin.getConfig().getInt("Players" + typeofmoney + puuid);

		return inaccount;
	}

}
