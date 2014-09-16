package de.htwg_konstanz.antbots.common_java_package.controller;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.state.State;
import de.htwg_konstanz.antbots.common_java_package.controller.state.StateName;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class DefendOwnHillManager {

	private static Map<Tile, List<Tile>> defendTilesArroundHill = new HashMap<>();

	private static Map<Ant, Tile> defendAnts;

	public static void initDefendTiles() {
			
		for (Tile hill : AntBot.getGameI().getMyHills()) {

			int col = hill.getCol();
			int row = hill.getRow();
			List<Tile> defendTiles = new LinkedList<>();
			
			Tile defendTile = AntBot.getGameI().getTileOfMap(new Tile(row - 1, col + 1));
			if (defendTile.getType() != Ilk.WATER && defendTile.getType() != Ilk.MY_ANT) {
				defendTiles.add(defendTile);
			}
			defendTile = AntBot.getGameI().getTileOfMap(new Tile(row - 1, col - 1));
			if (defendTile.getType() != Ilk.WATER && defendTile.getType() != Ilk.MY_ANT) {
				defendTiles.add(defendTile);
			}
			defendTile = AntBot.getGameI().getTileOfMap(new Tile(row + 1, col - 1));
			if (defendTile.getType() != Ilk.WATER && defendTile.getType() != Ilk.MY_ANT) {
				defendTiles.add(defendTile);
			}
			defendTile = AntBot.getGameI().getTileOfMap(new Tile(row + 1, col + 1));
			if (defendTile.getType() != Ilk.WATER && defendTile.getType() != Ilk.MY_ANT) {
				defendTiles.add(defendTile);
			}
			defendTilesArroundHill.put(hill, defendTiles);
		}
	}

	static List<Ant> markedAnts;
	
	public static void defendAntsToDefendTile() {
		
		for (Entry<Tile, List<Tile>> rTile : defendTilesArroundHill.entrySet()) {
			for(Tile t : rTile.getValue()) {
				OverlayDrawer.setFillColor(Color.BLACK);
				OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
						SubTile.TL);
			}
			
		}
		
		defendAnts = new HashMap<Ant, Tile>();
		markedAnts = new LinkedList<>();
		Set<Tile> allMyAnts = new HashSet<>();
		Map<Tile, Ant> tileToAnt = new HashMap<>();
		
		for (Ant a : AntBot.getGameI().getMyAnts()) {
			Tile t = a.getAntPosition();
			allMyAnts.add(t);
			tileToAnt.put(t, a);
		}

		for (Entry<Tile, List<Tile>> entry : defendTilesArroundHill.entrySet()) {
			List<Tile> tiles = entry.getValue();
			Set<Tile> myAnts = AntBot.getBsf().extendedBSF(entry.getKey(), allMyAnts, false, true, 4, null);
			for(Tile tile : myAnts) {
				Ant ant = tileToAnt.get(tile);
				if (ant.getCurrentState() == StateName.Defend) {
					markedAnts.add(ant);
					break;
				}
			}
			for (Tile t : tiles) {
				for(Tile tile : myAnts) {
					Ant ant = tileToAnt.get(tile);
					if(!markedAnts.contains(ant)) {
						defendAnts.put(ant, t);
						markedAnts.add(ant);
						break;
					}
				}
			}
		}

	}

	public static List<Ant> getMarkedAnts() {
		return markedAnts;
	}


	public static Map<Ant, Tile> getDefendAntsToHills() {
		return defendAnts;
	}
}
