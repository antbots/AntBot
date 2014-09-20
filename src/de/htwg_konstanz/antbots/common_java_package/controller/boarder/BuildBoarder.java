package de.htwg_konstanz.antbots.common_java_package.controller.boarder;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

/**
 * 
 * @author Benjamin
 */
public class BuildBoarder {

	private GameInformations gameI;
	private static Map<Set<Tile>, Set<Tile>> boarders;

	public BuildBoarder(GameInformations gameI) {
		this.gameI = gameI;
	}

	public void buildBoarder() {
		boarders = new HashMap<>();
		List<Set<Tile>> areas = buildAreas();

		for (Set<Tile> area : areas) {
			Set<Tile> boarder = new HashSet<>();
			for (Tile t : area) {

				if (t.getType() != Ilk.WATER) {
					Set<Tile> neigbours = gameI.getNeighbour(t).keySet();
					boolean tr = false;
					for (Tile ne : neigbours) {
						if (ne.getType() != Ilk.WATER) {

							if (!area.contains(ne)) {
								tr = true;
								break;
							}
						}
					}
					if (tr) {
						boarder.add(t);
					}
				}
			}
			boarders.put(area, boarder);
		}

		// TODO DEBUG Ausgabe
		for (Set<Tile> boarder : boarders.values()) {
			for (Tile t : boarder) {
				OverlayDrawer.setFillColor(Color.RED);
				OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
						SubTile.TM);
			}
		}
	}

	public static Map<Set<Tile>, Set<Tile>> getAreaAndBoarder() {
		if(boarders.size() == 0) {
			return null;
		}
		return boarders;
	}

	private static List<Set<Tile>> buildAreas() {
		List<Set<Tile>> areas = new LinkedList<Set<Tile>>();

		for (Ant myAnt : AntBot.getGameI().getMyAnts()) {
			Tile myAntPos = myAnt.getAntPosition();
			Set<Tile> tilesInRadius = AntBot.getGameI().getTilesInRadius(myAntPos,	Configuration.BOARDDISTANCE);
			Set<Tile> visitableTiles = AntBot.getBsf().visitableFromSet(myAntPos,tilesInRadius);


			areas.add(visitableTiles);
		}
		merge(areas);

		return areas;

	}

	private static void merge(List<Set<Tile>> areas) {

		for (Set<Tile> area : areas) {
			for (Set<Tile> areaTwo : areas) {
				if (areaTwo != area) {
					Set<Tile> tmp = new HashSet<>(areaTwo);
					tmp.retainAll(area);

					if (tmp.size() != 0) {

						area.addAll(areaTwo);
						areas.remove(areaTwo);
						merge(areas);
						return;
					}
				}
			}
		}
	}
	static Set<Ant> ants;
	
	public static void improvedBoarder() {
		List<Set<Tile>> areas = buildAreas();
		Map<Set<Tile>, Set<Ant>> areaToEnemyAnt = new HashMap<>();
		Map<Set<Tile>, Set<Tile>> areaToBoarder = new HashMap<>();

		for (Ant enemy : AntBot.getGameI().getEnemyAnts()) {
			Tile position = enemy.getAntPosition();
			for (Set<Tile> area : areas) {
				
				if (area.contains(position)) {
					if (areaToEnemyAnt.containsKey(area)) {
						areaToEnemyAnt.get(area).add(enemy);
					} else {
						Set<Ant> e = new HashSet<>();
						e.add(enemy);
						areaToEnemyAnt.put(area, e);
					}
				}
			}
		}

		for (Entry<Set<Tile>, Set<Ant>> entry : areaToEnemyAnt.entrySet()) {
			Set<Tile> enemyArea = new HashSet<>();
			
			for (Ant enemy : entry.getValue()) {
				Tile enemyTile = enemy.getAntPosition();
				Set<Tile> tmpEnemyArea = new HashSet<>();
				tmpEnemyArea.addAll((AntBot.getGameI().getTilesInRadius(enemyTile, (int) Math.sqrt(AntBot.getGameI().getViewRadius2()) / 2)));
				enemyArea = AntBot.getBsf().visitableFromSet(enemyTile,tmpEnemyArea);
			}

			Set<Tile> bo = new HashSet<>();
			for (Tile tile : enemyArea) {

				for (Tile t : AntBot.getGameI().getMoveAbleNeighbours(tile)
						.keySet()) {
					if (!enemyArea.contains(t)) {
						bo.add(t);
					}
				}
			}
			areaToBoarder.put(entry.getKey(), bo);
		}
		for(Entry<Set<Tile>, Set<Tile>> test : areaToBoarder.entrySet()){
			for(Tile bTIle : test.getValue()) {
				 OverlayDrawer.setFillColor(Color.CYAN);
				 OverlayDrawer.drawTileSubtile(bTIle.getRow(), bTIle.getCol(),
				 SubTile.BL);
			}
		}
		boarders = areaToBoarder;
		ants =  new HashSet<>();
		for(Entry<Set<Tile>, Set<Tile>> e : boarders.entrySet()) {
			for(Ant ant : AntBot.getGameI().getMyAnts()) {
				if(e.getKey().contains(ant.getAntPosition())) {
					ants.add(ant);
				}
			}
		}
	}
	
	
	public static Set<Ant> marktAnts() {
		return ants;
	}

}
