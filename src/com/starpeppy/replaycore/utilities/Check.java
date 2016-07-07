package com.starpeppy.replaycore.utilities;

import org.bukkit.entity.Player;

import com.starpeppy.replaycore.StarPep;

public class Check {
	private StarPep plugin;

	public Check(StarPep pl) {
		plugin = pl;
	}
	// false = credits, true = debits

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

}
