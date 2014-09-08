package de.htwg_konstanz.antbots.common_java_package.controller.boarder;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.BreadthFirstSearch;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Pathfinding;
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
	private BreadthFirstSearch bsf;
	private Pathfinding pathfinding;
	private static Map<Set<Tile>, Set<Tile>> boarders; 

	public BuildBoarder(GameInformations gameI) {
		this.gameI = gameI;
		bsf = new BreadthFirstSearch(gameI);
		pathfinding = new Pathfinding(gameI);
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

		//TODO DEBUG Ausgabe
		for(Set<Tile> boarder : boarders.values()) {
			for (Tile t : boarder) {
				OverlayDrawer.setFillColor(Color.RED);
				OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(), SubTile.TM);
			}
		}
	}
	
	public static Map<Set<Tile>, Set<Tile>> getAreaAndBoarder() {
		return boarders;
	}

	private List<Set<Tile>> buildAreas() {
		Set<Tile> enemyTilesSet = new HashSet<>();
		List<Set<Tile>> areas = new LinkedList<Set<Tile>>();

		for (Ant myAnt : gameI.getMyAnts()) {
			Tile myAntPos = myAnt.getAntPosition();
			Set<Tile> tilesInRadius = gameI.getTilesInRadius(myAntPos, Configuration.BOARDDISTANCE);
			Set<Tile> visitableTiles = bsf.visitableFromSet(myAntPos, tilesInRadius);
			Set<Ant> enemyAnts = new HashSet<>();
			
			//ermittle gegner im Sichtradius
			for(Ant enemy: gameI.getEnemyAnts()) {
				if(tilesInRadius.contains(enemy.getAntPosition())) {
					enemyAnts.add(enemy);
				}
			}
			
			//ermittle Strecke zum gegner und entferne die Tiles, die eine entfernung von size von der gegnerischen Ameise hat
			//von der menge visitableTiles ab
			
			for(Ant enemyAntInRange : enemyAnts ) {
				Tile enemyAntPos = enemyAntInRange.getAntPosition();
				int size= AntBot.getGameI().getDistance(enemyAntPos, myAntPos);
				
				
				if(size <9) {
					size = size /2;
					Set<Tile> enemyTiles = gameI.getTilesInRadius(enemyAntPos, size);
					
					enemyTilesSet.addAll(enemyTiles);
	
//					for(Tile enemyTile : tmp) {
//						OverlayDrawer.setFillColor(Color.GREEN);
//						OverlayDrawer.drawTileSubtile(enemyTile.getRow(), enemyTile.getCol(),
//								SubTile.TL);
//					}

				}
			}
			areas.add(visitableTiles);
		}
		merge(areas);
		for(Set<Tile> t : areas) {
			t.removeAll(enemyTilesSet);
		}
		
		return areas;

	}

	private void merge(List<Set<Tile>> areas) {

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

}
