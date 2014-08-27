package de.htwg_konstanz.antbots.common_java_package.boarder;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Ilk;
import de.htwg_konstanz.antbots.common_java_package.Logger;
import de.htwg_konstanz.antbots.common_java_package.Tile;
import de.htwg_konstanz.antbots.common_java_package.helper.BreadthFirstSearch;
import de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

/**
 * 
 * @author Benjamin
 */
public class BuildBoarder {

	GameInformations gameI;
	BreadthFirstSearch bsf;
	Logger logger;
	Set<Tile> additionalTiles = new HashSet<Tile>();
	Pathfinding pathfinding;

	public BuildBoarder(GameInformations gameI) {
		this.gameI = gameI;
		this.logger = gameI.getLogger();
		bsf = new BreadthFirstSearch(gameI);
		pathfinding = new Pathfinding(gameI);
	}

	public void buildBoarder() {

		List<Set<Tile>> areas = buildAreas();
		List<Set<Tile>> boarders = new LinkedList<>();
		
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
			boarders.add(boarder);
		}

		for(Set<Tile> boarder : boarders) {
			for (Tile t : boarder) {
				OverlayDrawer.setFillColor(Color.RED);
				OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(), SubTile.TM);
			}
		}

	}

	private List<Set<Tile>> buildAreas() {

		List<Set<Tile>> areas = new LinkedList<Set<Tile>>();

		for (Ant myAnt : gameI.getMyAnts()) {
			Tile myAntPos = myAnt.getAntPosition();
			Set<Tile> tilesInRadius = gameI.getTilesInRadius(myAntPos, 9);
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
				List<Tile> path = pathfinding.aStar(enemyAntPos, myAntPos);
				int size = path.size();
				
				if(size <9) {
					size = size /2;
					Set<Tile> enemyTiles = gameI.getTilesInRadius(enemyAntPos, size);
					Set<Tile> tmp = new HashSet<>(enemyTiles);
					tmp.retainAll(visitableTiles);
					for(Tile enemyTile : tmp) {
						OverlayDrawer.setFillColor(Color.GREEN);
						OverlayDrawer.drawTileSubtile(enemyTile.getRow(), enemyTile.getCol(),
								SubTile.TL);
					}
					visitableTiles.removeAll(enemyTiles);
				}
			}
			areas.add(visitableTiles);
		}
		merge(areas);
//
//		int a = 0;
//		for (Set<Tile> area : areas) {
//			for (Tile t : area) {
//				if (a == 0) {
//					OverlayDrawer.setFillColor(Color.GREEN);
//					OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
//							SubTile.BR);
//				} else if (a == 1) {
//					OverlayDrawer.setFillColor(Color.ORANGE);
//					OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
//							SubTile.BL);
//				} else if (a == 2) {
//					OverlayDrawer.setFillColor(Color.PINK);
//					OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
//							SubTile.BM);
//				} else if (a == 3) {
//					OverlayDrawer.setFillColor(Color.RED);
//					OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
//							SubTile.TM);
//				} else if (a == 4) {
//					OverlayDrawer.setFillColor(Color.BLACK);
//					OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
//							SubTile.TR);
//				} else if (a == 5) {
//					OverlayDrawer.setFillColor(Color.GRAY);
//					OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
//							SubTile.TL);
//				}
//
//			}
//			a++;
//		}
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
