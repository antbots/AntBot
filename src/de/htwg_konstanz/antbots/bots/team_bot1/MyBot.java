package de.htwg_konstanz.antbots.bots.team_bot1;

import java.awt.Color;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.common_java_package.*;
import de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

/**
 * Starter bot implementation.
 */
public class MyBot extends Bot {

	GameInformations gameI = null;
	private Logger logger = new Logger("log.txt");
	private Pathfinding pathfinding = null;
	
	
	private Map<Tile, Tile> orders = new HashMap<Tile, Tile>();
	int turn = 0;

	/**
	 * Main method executed by the game engine for starting the bot.
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		new MyBot().readSystemInput();
	}

	// private boolean doMoveDirection(Tile antLoc, Aim direction) {
	// GameInformations ants = getAnts();
	// // Track all moves, prevent collisions
	// Tile newLoc = ants.getTile(antLoc, direction);
	// if (ants.getIlk(newLoc).isUnoccupied() && !orders.containsKey(newLoc)) {
	//
	// ants.issueOrder(antLoc, direction);
	// orders.put(newLoc, antLoc);
	// return true;
	// } else {
	// return false;
	// }
	// }

	/**
	 * For every ant check every direction in fixed order (N, E, S, W) and move
	 * it if the tile is passable.
	 */
	@Override
	public void doTurn() {
		if(turn == 0){
			gameI = gameStateInforamtions();
			pathfinding = new Pathfinding(gameI);
		}
		turn++;
		orders.clear();
		logger.log("start");
	
//		for (int i = 0; i < ants.getRows(); i++) {
//			for (int j = 0; j < ants.getCols(); j++) {
//				logger.log("karten infor " + ants.getMap()[i][j].getRow() + " " + ants.getMap()[i][j].getCol() + " " + ants.getMap()[i][j].getCountExplore());
////				OverlayDrawer.setTileInfo(ants.getMap()[i][j].getRow(), ants.getMap()[i][j].getCol() , "aaaaaaaaaaaaaaaaaaaa");//"" + map[i][j].getCountExplore());
//			}
//		}
		
		exploration();
//		gameI.incrementCountEcplored();
	}

	private void exploration() {

		for (int i = 0; i < gameI.getRows(); i++) {
			for (int j = 0; j < gameI.getCols(); j++) {
//				map[i][j].incCountExplore();
				
				//OverlayDrawer.setTileInfo(i, j,("" + map[i][j].getCountExplore()));
			}
		}
		for (Ant t : gameI.getMyAnts()) {
			// Tile tmp = ants.getMap()[t.getRow()][t.getCol()];
			
			Tile antsTile = t.getAntPosition(); // gameI.getTileOfMap(t.getValue());
			
			
			Set<Tile> visitable = pathfinding.visitableInXSteps(antsTile, 10);

			
//			for (Tile t1 : visitable) {
//				logger.log("vistiable " + t1.toString() + " with count " + t1.getCountExplore());
//			}
			
			
			List<Tile> tmpList = new LinkedList<Tile>();
			for(Tile tmp: visitable) {
				if(5 < gameI.getDistance(antsTile, tmp)) {
					tmpList.add(tmp);
				}
			}
			for (Tile a : tmpList) {
				OverlayDrawer.drawTile(a.getRow(), a.getCol());
			}
			
			Tile max = gameI.getMaxVisibilityAgo(tmpList).iterator().next();
			
//			Tile max = Collections.max(visitable);
//			max.setCountExplored();
			
			logger.log("");
			logger.log("ameise go to " + max.toString());
			logger.log("");
			
			Tile[] parent = new Tile[visitable.size()];
			int[] distance = new int[visitable.size()];

			if (pathfinding.searchShortestPath(antsTile, max, new LinkedList<Tile>(visitable), parent, distance)) {

				List<Tile> graph = new LinkedList<Tile>(visitable);
				LinkedList<Tile> liste = new LinkedList<Tile>();

				Tile test = max;
				liste.add(test);
				while (test != null) {
					test = parent[graph.indexOf(test)];
					
					if (test == null) {
						break;
					}
					liste.addLast(test);
					logger.log("Weg " + test.toString());
//					OverlayDrawer.drawTileSubtile(test.getRow(), test.getCol(),
//							SubTile.MM);
				}
				
				liste.removeLast();
				for(Tile ter: liste) {
					OverlayDrawer.setFillColor(Color.RED);
					OverlayDrawer.drawTileSubtile(ter.getRow(), ter.getCol(),
							SubTile.MM);
				}

				
				Tile tmp2 = liste.getLast();
				OverlayDrawer.setFillColor(Color.GREEN);
				OverlayDrawer.drawTileSubtile(tmp2.getRow(), tmp2.getCol(),	SubTile.MM);
				//tmp2.setCountExplored();
				List<Aim> aim = gameI.getDirections(antsTile, tmp2);
				gameI.issueOrder(antsTile, aim.get(0));

			}
		}
	}
}
