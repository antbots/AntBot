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
			Set<Tile> allEnemyAnts = new HashSet<>();
			Map<Tile, Ant> tileToAnt = new HashMap<>();
			
			for(Ant a : AntBot.getGameI().getMyAnts()) {
				Tile t = a.getAntPosition();
				allMyAnts.add(t);
				tileToAnt.put(t, a);
			}
			for(Ant a : AntBot.getGameI().getEnemyAnts()) {
				allEnemyAnts.add(a.getAntPosition());
			}

			Set<Tile> myAnts = AntBot.getBsf().extendedBSF(hill, allMyAnts, false, true, Configuration.RADIUSTOENEMYHILL, null);
			Set<Tile> enemyAnt = AntBot.getBsf().extendedBSF(hill, allEnemyAnts, true, true, Configuration.RADIUSTOENEMYHILL, null);

			for(Tile myAnt :  myAnts){
				if(enemyAnt.size() == 0 || (AntBot.getGameI().getDistance(myAnt, hill) < AntBot.getGameI().getDistance((Tile)enemyAnt.toArray()[0], hill))){
					antToHill.put(tileToAnt.get(myAnt), hill);
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
