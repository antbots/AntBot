package de.htwg_konstanz.antbots.bots.lemke_bot1;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import de.htwg_konstanz.antbots.common_java_package.Aim;
import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.Bot;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Logger;
import de.htwg_konstanz.antbots.common_java_package.Tile;
import de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding;
import de.htwg_konstanz.antbots.visualizer.ExplorationVisualizer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

/**
 * 
 * @author Chrisi
 * 
 */
public class ExplorerBot1 extends Bot {

	private Logger logger = new Logger("log.txt");
	private GameInformations gameI;

	private int turn = 0;
	private Pathfinding pathfinding;

	public static void main(String[] args) throws IOException {
		new ExplorerBot1().readSystemInput();
	}

	private void init() {
		gameI = gameStateInforamtions();
		pathfinding = new Pathfinding(gameI);
	}

	@Override
	public void doTurn() {
		if (turn == 0) {
			init();
		}

		//

		// hybrid();
//		 exploration();
		// collect();

		// exploration2();
		exploration3();
		 
		 
		 ExplorationVisualizer.visualizeNewStep(gameI);
	}

	/**
	 * Send all ants to exploration.
	 */
	private void exploration() {
	
		// Für jede Ameise
		for (Ant ant : gameI.getMyAnts()) {
			Tile antsTile = ant.getAntPosition();
			exploration(antsTile);
		}
	}

	/**
	 * Send one ant to exploration.
	 */
	private void exploration(Tile antsTile) {
	
		// which tiles can be rached
		int radius = (int) Math.sqrt(gameI.getViewRadius2()) + 1;
		// Set<Tile> visitable = pathfinding.visitableInXSteps(antsTile,
		// gameI.getMap(), radius);
	
		Set<Tile> visitable = gameI.getTilesInRadius(antsTile, radius);
	
		List<Tile> route = null;
	
		// the highest destination could be behind water.
		while (route == null) {
			
			// get the tile with die highest exploreValue
			Tile target = gameI.getMaxVisibilityAgo(visitable).iterator().next();
	
			// get the route to the target
			route = pathfinding.aStar(antsTile, target);
	
			// target is not rachable -> remove it form visitable
			visitable.remove(target);
		}
	
		for (Tile ter : route) {
			OverlayDrawer.setFillColor(Color.WHITE);
			OverlayDrawer.drawTileSubtile(ter.getRow(), ter.getCol(),
					SubTile.MM);
		}
	
		//
		if (route != null && route.size() > 0) {
			List<Aim> aim = gameI.getDirections(antsTile, route.get(1));
			if (!aim.isEmpty())
				gameI.issueOrder(antsTile, aim.get(0));
		}
	}

	// check for foot and go on it, or explore
	private void exploration2() {

		int radius = (int) Math.sqrt(gameI.getViewRadius2()) + 1;
		Set<Tile> foodTiles = gameI.getFoodTiles();
		Tile target = null;
		List<Tile> route = null;
		Color color = null;

		for (Ant ant : gameI.getMyAnts()) {
			route = null;
			target = null;
			Tile antTile = ant.getAntPosition();

			// get the tiles in viewradius+1
			Set<Tile> visitableTiles = gameI.getTilesInRadius(antTile, radius);

			if (visitableTiles.contains(foodTiles)) {
				// collect the food
				color = Color.GREEN;

				Set<Tile> foodInRange = Collections.emptySet();
				foodInRange.addAll(foodTiles);
				foodInRange.retainAll(visitableTiles);

				// is the food reachable. checked by a* (also check for closest)
				// TODO check what is faster breadth-first search or a*
				target = new LinkedList<Tile>(getClosestRoute(antTile, foodInRange)).getLast();
				route = pathfinding.aStar(antTile, target);
			} else {
				// explore
				color = Color.RED;

				Set<Tile> visitleTiles = gameI
						.getTilesInRadius(antTile, radius);

				while (route == null) {
					// get the tile with die highest exploreValue
					Tile maxTile = gameI.getMaxVisibilityAgo(visitleTiles)
							.iterator().next();

					// checks TODO whyy??
					// visitable.add(antsTile);

					route = pathfinding.aStar(antTile, maxTile);
				}
			}

			// draw
			for (Tile rTile : route) {
				OverlayDrawer.setFillColor(color);
				OverlayDrawer.drawTileSubtile(rTile.getRow(), rTile.getCol(),
						SubTile.MM);
			}

			// do step
			if (route != null && route.size() > 0) {
				List<Aim> aim = gameI.getDirections(antTile, route.get(1));
				if (!aim.isEmpty())
					gameI.issueOrder(antTile, aim.get(0));
			}

		}

	}

	// Exploration ... and give foot a minimum exploration value
	private void exploration3() {

		int radius = (int) Math.sqrt(gameI.getViewRadius2())+2 ;
		
		// rate food higher
		for (Tile tile : gameI.getFoodTiles()) {
			setVisibilityAgoFoodValue(tile);
		}
		
		// Set<Tile> foodInRange = new HashSet<Tile>();
		Set<Tile> isTaken = new HashSet<Tile>();

		// TODO  bring the ants to an order
//		Comparator<Ant> c = new Comparator<Ant>() {
//			
//			@Override
//			public int compare(Ant that, Ant other) {
//				if(gameI.getDistance(that.getAntPosition(), other.getAntPosition()))
//					return 1;
//				else if(gameI.getDistance(that.getAntPosition() > other.getAntPosition()))
//					return -1;
//				else
//					return 0;
//			}
//		};
//		List<Ant> myants = new ArrayList<Ant>(gameI.getMyAnts());
//		
//		myants = new ArrayList<Ant>(myants).sort(c);
		
		
		for (Ant ant : gameI.getMyAnts()) {
			Tile antTile = ant.getAntPosition();

			// get the tiles in viewradius+1
			Set<Tile> visibleTiles = gameI.getTilesInRadius(antTile, radius);
			
			// remove the taken food so that only one ant is going on one food at the same time 
			visibleTiles.remove(isTaken);
			
			List<Tile> route = null;
			Tile target = null;
			Set<Tile> targets = null;
			
			// get the route of the closest of the  highest exploration tiles.
			while (route == null) {
				// get the tile with die highest exploreValue
				targets = gameI.getMaxVisibilityAgo(visibleTiles);

				// target = new LinkedList<Tile>(getClosestRoute(antTile, targets)).getLast();
				target = targets.iterator().next();
				
				// get the route to the target
				route = pathfinding.aStar(antTile, target);

				// target is not rachable -> remove it form visitable
				visibleTiles.remove(target);
			}

			// 
			isTaken.add(target);
			
			// draw
			for (Tile rTile : route) {
				OverlayDrawer.setFillColor(Color.WHITE);
				OverlayDrawer.drawTileSubtile(rTile.getRow(), rTile.getCol(),
						SubTile.MM);
			}

			// do step
			if (route != null && route.size() > 1) {
				List<Aim> aim = gameI.getDirections(antTile, route.get(1));
				if (!aim.isEmpty())
					gameI.issueOrder(antTile, aim.get(0));
			}
		}

	}
	
	/**
	 * Sets a high VisibilityAgo Value. The ants will go to that tile.
	 * 
	 * @param tile
	 * @author Chrisi
	 */
	public void setVisibilityAgoFoodValue(Tile tile) {
		gameI.setAtLastVisible(tile, gameI.getTurns() +1);
	}

	/**
	 * Returns a routelist of the closest visitable tile from targets using a star. Or null if no target is
	 * reachable.
	 * 
	 * @param source
	 * @param targets
	 * @return
	 */
	public List<Tile> getClosestRoute(Tile source, Set<Tile> targets) {
		List<Tile> path = null;
		for (Tile target : targets) {
			List<Tile> tmpPath = pathfinding.aStar(source, target);
			if(path == null || (tmpPath != null && tmpPath.size() < path.size()))
				path = tmpPath;
		}

		return new LinkedList<Tile>(path);
	}

	/**
	 * Every foot is a destiny of the next ant. THe other ants do exploration.
	 */
	private void hybrid() {
		int radius = (int) Math.sqrt(gameI.getViewRadius2());

		Set<Tile> antsGoingForFoot = new HashSet<Tile>();

		Set<Tile> foodTiles = gameI.getFoodTiles();
		List<Ant> myAnts = gameI.getMyAnts();

		// next ant to foot
		for (Tile foot : foodTiles) {
			// get the next ant
			int distance = Integer.MAX_VALUE;
			List<Tile> theNextPath = null;
			for (Ant ant : myAnts) {

				Tile antPosition = ant.getAntPosition();
				// ignore ants with footjob
				if (antsGoingForFoot.contains(ant))
					continue;

				// visitables
				Set<Tile> visitable = gameI.getTilesInRadius(foot, radius);

				// ant is too far away
				if (!visitable.contains(ant))
					continue;

				// djkstra
				// List<Tile> trypath =
				// pathfinding.searchShortestPath(antPosition, foot, visitable);
				List<Tile> trypath = pathfinding.aStar(antPosition, foot);
				// find nearest
				if (trypath != null && trypath.size() < distance) {
					distance = trypath.size();
					theNextPath = trypath;
				}

			}

			if (theNextPath != null && theNextPath.size() >= 1) {
				// get the foot!!!
				Tile ant = theNextPath.get(0);
				antsGoingForFoot.add(ant);

				// draw arror
				OverlayDrawer.drawArrow(ant,
						theNextPath.get(theNextPath.size() - 1));

				List<Aim> aim = gameI.getDirections(ant, theNextPath.get(1));
				if (!aim.isEmpty())
					gameI.issueOrder(ant, aim.get(0));
			}
		}

		// other ants go explore
		for (Ant ant : myAnts) {
			Tile antPosition = ant.getAntPosition();
			if (!antsGoingForFoot.contains(antPosition)) {
				exploration(antPosition);
			}
		}

	}

	/**
	 * the ant runs to their next food
	 */
	private void collect() {
		if (turn == 0) {
			gameI = gameStateInforamtions();
			pathfinding = new Pathfinding(gameI);
		}
		turn++;

		for (Ant ant : gameI.getMyAnts()) {

			Tile antsTile = ant.getAntPosition();

			// which tiles can be rached
			int maxSteps = (int) Math.sqrt(gameI.getViewRadius2());
			// Set<Tile> visitable = pathfinding.visitableInXSteps(antsTile,
			// gameI.getMap(), maxSteps);
			//
			// for (Tile a : visitable) {
			// OverlayDrawer.drawTileSubtile(a.getRow(), a.getCol(),
			// OverlayDrawer.SubTile.BM);
			// }

			// get the food tiles
			// Set<Tile> visitableFood = new HashSet<Tile>();
			// for (Tile tile : visitable) {
			// if (gameI.getIlk(tile) == Ilk.FOOD)
			// visitableFood.add(tile);
			// }

			// find the next food
			List<Tile> nextFoodRoute = null;
			for (Tile food : gameI.getFoodTiles()) {
				List<Tile> tmp = pathfinding.aStar(antsTile, food);
				// List<Tile> tmp = pathfinding.searchShortestPath(antsTile,
				// food,
				// visitable);

				if (nextFoodRoute == null
						|| (tmp.size() <= maxSteps && tmp.size() > 1 && nextFoodRoute
								.size() > tmp.size())) {
					nextFoodRoute = tmp;
				}
			}

			if (nextFoodRoute != null && !nextFoodRoute.isEmpty()) {
				// get food!
				for (Tile a : nextFoodRoute) {
					OverlayDrawer.setFillColor(Color.YELLOW);
					OverlayDrawer.drawTileSubtile(a.getRow(), a.getCol(),
							SubTile.MM);
				}

				if (nextFoodRoute != null && nextFoodRoute.size() > 1) {
					List<Aim> aim = gameI.getDirections(antsTile,
							nextFoodRoute.get(1));
					if (!aim.isEmpty()) {
						gameI.issueOrder(antsTile, aim.get(0));
					}
				}

			} else {
				// explore
				exploration(antsTile);
			}
		}
	}

}
