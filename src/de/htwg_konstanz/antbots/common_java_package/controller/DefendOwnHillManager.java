package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class DefendOwnHillManager {

	Map<Ant, Tile> defendAnts;
	Map<Ant, List<Tile>> antTotilesAroundHill;
	
	public void defendAntsToOwnHill() {
		defendAnts = new HashMap<>();
		antTotilesAroundHill = new HashMap<>();
		for (Tile hill : AntBot.getGameI().getMyHills()) {

			Set<Ant> allMyAnts = new HashSet<>();
			allMyAnts.addAll(AntBot.getGameI().getMyAnts());

			List<Tile> ownHill = new LinkedList<>();
			ownHill.add(hill);

			Set<Tile> tilesAroundHill = new HashSet<>();
			
			Set<Ant> myAnts = AntBot.getBsf().extendedBSF(ownHill, allMyAnts, false, true, 8, tilesAroundHill);

			int i = 0;
			for (Ant ant : myAnts) {
				if (i <= Configuration.DEFENDANTS) {
					defendAnts.put(ant, hill);
					List<Tile> tmp = new LinkedList<>();
					tmp.addAll(tilesAroundHill);
					antTotilesAroundHill.put(ant, tmp);
					i++;
				}
			}

		}
		for (Entry<Ant, Tile> e : defendAnts.entrySet()) {
			AntBot.getGameI().getLogger().log("Ameise " + e.getKey() + " verteidigt "	+ e.getValue());
		}

		AntBot.getGameI().getLogger().log("defend hill");
	}

	public Map<Ant, Tile> getDefendAntsOfOwnHills() {
		return defendAnts;
	}
	
	public Map<Ant,List<Tile>> getTilesAroundHill() {		
		return antTotilesAroundHill;
	}
}
