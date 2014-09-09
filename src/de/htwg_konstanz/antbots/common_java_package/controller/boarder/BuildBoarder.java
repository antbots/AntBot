package de.htwg_konstanz.antbots.common_java_package.controller.boarder;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

//	private List<Boarder> boarderToAnt;

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
		Set<Tile> enemyTilesSet = new HashSet<>();
		List<Set<Tile>> areas = new LinkedList<Set<Tile>>();

		for (Ant myAnt : AntBot.getGameI().getMyAnts()) {
			Tile myAntPos = myAnt.getAntPosition();
			Set<Tile> tilesInRadius = AntBot.getGameI().getTilesInRadius(myAntPos,	Configuration.BOARDDISTANCE);
			Set<Tile> visitableTiles = AntBot.getBsf().visitableFromSet(myAntPos,tilesInRadius);
			Set<Ant> enemyAnts = new HashSet<>();

			// ermittle gegner im Sichtradius
//			for (Ant enemy : gameI.getEnemyAnts()) {
//				if (tilesInRadius.contains(enemy.getAntPosition())) {
//					enemyAnts.add(enemy);
//				}
//			}

			// ermittle Strecke zum gegner und entferne die Tiles, die eine
			// entfernung von size von der gegnerischen Ameise hat
			// von der menge visitableTiles ab

//			for (Ant enemyAntInRange : enemyAnts) {
//				Tile enemyAntPos = enemyAntInRange.getAntPosition();
//				int size = AntBot.getGameI().getDistance(enemyAntPos, myAntPos);
//
//				if (size < 9) {
//					size = size / 2;
//					Set<Tile> enemyTiles = gameI.getTilesInRadius(enemyAntPos,
//							size);
//
//					enemyTilesSet.addAll(enemyTiles);
//
//					// for(Tile enemyTile : tmp) {
//					// OverlayDrawer.setFillColor(Color.GREEN);
//					// OverlayDrawer.drawTileSubtile(enemyTile.getRow(),
//					// enemyTile.getCol(),
//					// SubTile.TL);
//					// }
//
//				}
//			}
			areas.add(visitableTiles);
		}
		merge(areas);
//		for (Set<Tile> t : areas) {
//			t.removeAll(enemyTilesSet);
//		}

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
		
		for(Entry<Set<Tile>, Set<Ant>> test : areaToEnemyAnt.entrySet()) {
			AntBot.debug().log("Size of enemy Ants " + test.getValue().size());
		}
		
		
		

		for (Entry<Set<Tile>, Set<Ant>> entry : areaToEnemyAnt.entrySet()) {
			Set<Tile> enemyArea = new HashSet<>();
			for (Ant enemy : entry.getValue()) {
				enemyArea
						.addAll((AntBot.getGameI().getTilesInRadius(enemy
								.getAntPosition(), (int) Math.sqrt(AntBot
								.getGameI().getViewRadius2()) / 2)));
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
			for(Tile areaTile : test.getKey()) {
			
				 OverlayDrawer.setFillColor(Color.PINK);
				 OverlayDrawer.drawTileSubtile(areaTile.getRow(), areaTile.getCol(),
				 SubTile.TL);

			}
			for(Tile bTIle : test.getValue()) {
				 OverlayDrawer.setFillColor(Color.CYAN);
				 OverlayDrawer.drawTileSubtile(bTIle.getRow(), bTIle.getCol(),
				 SubTile.TL);
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

//	public void getNewBoarder() {
//		boarderToAnt = new LinkedList<>();
//		val = 1;
//
//		List<Set<Tile>> areas = new LinkedList<>();
//		for (Ant enemy : AntBot.getGameI().getEnemyAnts()) {
//			Set<Tile> area = new HashSet<>();
//			area.addAll((AntBot.getGameI().getTilesInRadius(
//					enemy.getAntPosition(),
//					(int) Math.sqrt(AntBot.getGameI().getViewRadius2()) / 2)));
//			areas.add(area);
//		}
//		merge(areas);
//
//		List<Set<Tile>> boarder = new LinkedList<>();
//		for (Set<Tile> a : areas) {
//			Set<Tile> bo = new HashSet<>();
//			for (Tile t : a) {
//				for (Tile t1 : AntBot.getGameI().getMoveAbleNeighbours(t)
//						.keySet()) {
//					if (!a.contains(t1)) {
//						bo.add(t1);
//					}
//				}
//			}
//			boarderToAnt.add(new Boarder(new HashSet<Ant>(), bo));
//		}
//
//	}
//
//	int val = 1;
//
//	public Boarder AntToBoarderScheduler(Ant a) {
//		// Boarder b = boarderToAnt.get((int)Math.random() *
//		// (boarderToAnt.size() -1));
//		Boarder boarder = null;
//		int listSize = 0;
//		for (Boarder b : boarderToAnt) {
//			int size = b.getAtns().size();
//			listSize++;
//			if (size <= Configuration.LIMITANTSTOBOARDER && size < val) {
//
//				b.getAtns().add(a);
//				boarder = b;
//				break;
//			}
//		}
//
//		if (listSize == boarderToAnt.size()) {
//			val++;
//		}
//		return boarder;
//
//	}

//	private class Boarder {
//
//		int atns;
//		Set<Tile> boarder;
//
//		public Boarder(Set<Ant> ants, Set<Tile> boarder) {
//			this.boarder = boarder;
//			this.atns = ants;
//		}
//
//		public Set<Ant> getAtns() {
//			return atns;
//		}
//
//		public Set<Tile> getBoarder() {
//			return boarder;
//		}
//
//	}

}
