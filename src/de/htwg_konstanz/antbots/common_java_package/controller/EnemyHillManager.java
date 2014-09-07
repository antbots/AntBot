package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.HashMap;
import java.util.HashSet;
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
			Set<Tile> allMyAnts = new HashSet<>();
			Map<Tile, Ant> tileToAnt = new HashMap<>();
			
			for(Ant a : AntBot.getGameI().getMyAnts()) {
				Tile t = a.getAntPosition();
				allMyAnts.add(t);
				tileToAnt.put(t, a);
			}

			Set<Tile> myAnts = AntBot.getBsf().extendedBSF(hill, allMyAnts, false, true, 30, null);

			if (myAnts.size() > Configuration.ANTSINGROUPTOENEMYHILL) {
				int i = 0;
				for (Tile t : myAnts) {	
					Ant ant = tileToAnt.get(t);
					if (i <= Configuration.ANTSINGROUPTOENEMYHILL) {
						antToHill.put(ant, hill);
						i++;
					}
				}
			}
		}
		for(Entry<Ant, Tile> e : antToHill.entrySet()) {
			AntBot.getLogger().log("Ameise " + e.getKey() + " geht zu Hill " + e.getValue());
		}
		
		AntBot.getLogger().log("Attack Enemy Hill");
	}

	public Map<Ant, Tile> getAntsToHill() {
		return antToHill;
	}
}
