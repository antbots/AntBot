package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.bots.AntBot;

import de.htwg_konstanz.antbots.common_java_package.controller.state.StateName;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;


public class DefendOwnHillManager {

	Map<Ant, Tile> defendAnts;
	Map<Ant, List<Tile>> antTotilesAroundHill;
	
	public void defendAntsToOwnHill() {
		defendAnts = new HashMap<>();
		antTotilesAroundHill = new HashMap<>();
		for (Tile hill : AntBot.getGameI().getMyHills()) {

			Set<Tile> allMyAnts = new HashSet<>();
			Map<Tile, Ant> tileToAnt = new HashMap<>();
			
			for(Ant a : AntBot.getGameI().getMyAnts()) {
				Tile t = a.getAntPosition();
				allMyAnts.add(t);
				tileToAnt.put(t, a);
			}


			Set<Tile> tilesAroundHill = new HashSet<>();
			Set<Tile> myAnts = AntBot.getBsf().extendedBSF(hill, allMyAnts, false, true, 8, tilesAroundHill);
			
			int i = 0;
			
			//TODO bug behoben aber schlechte lösung
			for(Tile a : myAnts) {
				Ant ant = tileToAnt.get(a);
				if(ant.getCurrentState() == StateName.Defend) {
					defendAnts.put(ant, hill);
					List<Tile> tmp = new LinkedList<>();
					tilesAroundHill.remove(ant.getAntPosition());
					tmp.addAll(tilesAroundHill);
					antTotilesAroundHill.put(ant, tmp);
					i++;
				}
			}
			
			for(Tile a : myAnts) {
				Ant ant = tileToAnt.get(a);
				if (i < Configuration.DEFENDANTS) {
					defendAnts.put(ant, hill);
					List<Tile> tmp = new LinkedList<>();
					tilesAroundHill.remove(ant.getAntPosition());
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

	public Map<Ant, Tile> getDefendAntsToHills() {
		return defendAnts;
	}
	
	public Map<Ant,List<Tile>> getTilesAroundHill() {		
		return antTotilesAroundHill;
	}
}
