package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class EnemyHillManager {

	Map<Ant, Tile> antToHill;

	public void antsToEnemyHill() {
		antToHill = new HashMap<>();

		for (Tile hill : AntBot.getGameI().getEnemyHills()) {
			Set<Ant> allMyAnts = new HashSet<>();
			allMyAnts.addAll(AntBot.getGameI().getMyAnts());

			List<Tile> tmp = new LinkedList<>();
			tmp.add(hill);

			Set<Ant> myAnts = AntBot.getBsf().extendedBSF(tmp, allMyAnts, false, true, 30, null);

			if (myAnts.size() > Configuration.ANTSINGROUPTOENEMYHILL) {
				int i = 0;
				for (Ant ant : myAnts) {
					if (i <= Configuration.ANTSINGROUPTOENEMYHILL) {
						antToHill.put(ant, hill);
						i++;
					}
				}
			}
		}
		for(Entry<Ant, Tile> e : antToHill.entrySet()) {
			AntBot.getGameI().getLogger().log("Ameise " + e.getKey() + " geht zu Hill " + e.getValue());
		}
		
		AntBot.getGameI().getLogger().log("Attack Enemy Hill");
	}

	public Map<Ant, Tile> getAntsToHill() {
		return antToHill;
	}
}
