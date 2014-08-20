package de.htwg_konstanz.antbots.bots.collect_food_bot;

import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.common_java_package.Aim;
import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.Bot;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Logger;
import de.htwg_konstanz.antbots.common_java_package.Order;
import de.htwg_konstanz.antbots.common_java_package.Tile;
import de.htwg_konstanz.antbots.common_java_package.attack.AttackInit;
import de.htwg_konstanz.antbots.common_java_package.attack.MaxN;
import de.htwg_konstanz.antbots.common_java_package.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.helper.BreadthFirstSearch;
import de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding;
import de.htwg_konstanz.antbots.common_java_package.settings.Missions;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;
/**
 * 
 * @author Benjamin
 */
public class CollectFood extends Bot {

	GameInformations gameI;
	BreadthFirstSearch bsf;
	Logger logger = new Logger("FoodCollectAnt.txt");
	Pathfinding pathfinding;
	int turn = 0;
	BuildBoarder boarder;
	AttackInit attack;
	private MaxN gameStrategy;

	public static void main(String[] args) throws IOException {
		new CollectFood().readSystemInput();
	}

	private void init() {
		gameI = gameStateInforamtions();
		gameI.setLogger(logger);
		bsf = new BreadthFirstSearch(gameI);
		pathfinding = new Pathfinding(gameI);
		boarder = new BuildBoarder(gameI);
		attack = new AttackInit(gameI);
		gameStrategy = new MaxN();
	}

	@Override
	public void doTurn() {
		logger.log("---------------------------------------------------------------------------------------");
		if (turn == 0) {
			init();
		}
		logger.log("TURN " + turn);
		// MapPrinter mapPrinter = new MapPrinter(gameI, logger);
		// mapPrinter.printMap();

		// logger.log("Ameisen " + gameI.getMyAnts().size());
		// logger.log("BEFOREEEEEEEEEEEEEEEe");
		// for (Ant myAnt : gameI.getMyAnts()) {
		// logger.log("Ameise " + myAnt.getAntPosition());
		// }
		boarder.buildBoarder();
		
		
		initDanger();
		markOwnAntsAsDangered();
		
		
		Map<Set<Ant>, Set<Ant>> att = attack.initAttack();
		logger.log("ATACKEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE " + att.size());
		for(Entry<Set<Ant>, Set<Ant>> a : att.entrySet()) {
			logger.log("key " + a.getKey()+ " value " + a.getValue());
		}
		
		Set<Ant> ownAnts = new HashSet<Ant>();
		
		for(Set<Ant> ownAnt : att.keySet()) {
			ownAnts.addAll(ownAnt);
		}
		
		Set<Ant> enemyAnts = new HashSet<Ant>();
		for( Set<Ant> enemyAnt : att.values()){
			enemyAnts.addAll(enemyAnt);
		}
		
		logger.log("OWN ANTS:");
		for(Ant a : ownAnts) {
			logger.logWithoutLineEnding(a.toString() + " ");
		}
		logger.log("");
		logger.log("ENEMY ANTS:");
		for(Ant a : enemyAnts) {
			logger.logWithoutLineEnding(a.toString() + " ");
		}
		logger.log("");
		
		List<Set<Ant>> beteiligteAmeisen = new LinkedList<>();
		
		beteiligteAmeisen.add(ownAnts);
		beteiligteAmeisen.add(enemyAnts);
		
		LinkedList<Order> move = gameStrategy.attack(gameI, 1, MaxN.Strategy.AGGRESSIVE, beteiligteAmeisen);

		if (move != null)
			for (Order order : move) {
				gameI.issueOrder(order);
				logger.log(order.toString());
			}
		
		
		collectFood();
		exploration();
		

		// logger.log("");
		// logger.log("");
		// logger.log("AFTERRRRRRRRRRRRRRRRRRRRr");
		// for (Ant myAnt : gameI.getMyAnts()) {
		// logger.log("Ameise " + myAnt.getAntPosition());
		// }

		//
		logger.log("---------------------------------------------------------------------------------------");
		turn++;

	}
	
	/**
	 * set the dangered value of the own ants to false and
	 * set the enemy in view radius set to null
	 */
	private void initDanger() {
		for(Ant myAnt : gameI.getMyAnts()) {
			myAnt.setDanger(false);
			myAnt.setEnemysInViewRadius(null);
		}
	}

	/**
	 * mark the own ants as dangerd if their are enemy ants in the view radius
	 * if their are enemy ants in the view radius the method save this enemy ants in
	 * a set and 
	 */
	private void markOwnAntsAsDangered() {
		Set<Ant> myAnts = new HashSet<Ant>();

		for (Ant myAnt : gameI.getMyAnts()) {
			Set<Ant> enemyAnts = new HashSet<Ant>();
			
			Tile myAntTile = myAnt.getAntPosition();
			Set<Tile> myTiles = gameI.getTilesInRadius(myAntTile,(int)Math.sqrt(gameI.getViewRadius2()));
			
			//DEBUG
			for(Tile t : myTiles) {
				OverlayDrawer.setFillColor(Color.GREEN);
				OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
						SubTile.BR);
			}
			
			for (Ant enemyAnt : gameI.getEnemyAnts()) {
				Tile enemyAntTile = enemyAnt.getAntPosition();
				if (myTiles.contains(enemyAntTile)) {
					myAnt.setDanger(true);
					enemyAnts.add(enemyAnt);
				}
			}
			if(myAnt.isDanger()) {
				myAnt.setEnemysInViewRadius(enemyAnts);
				myAnts.add(myAnt);
			}

		}
		gameI.setMyAntDangered(myAnts);

	}
	
	

	private void exploration() {

		int radius = (int) Math.sqrt(gameI.getViewRadius2()) + 2;

		Set<Tile> isTaken = new HashSet<Tile>();

		for (Ant ant : gameI.getOwnNotDangeredAnts()) {
			if (!(ant.getMission() == Missions.NON)) {
				continue;
			}
			Tile antTile = ant.getAntPosition();

			// get the tiles in viewradius+1
			Set<Tile> visibleTiles = gameI.getTilesInRadius(antTile, radius);

			// remove the taken food so that only one ant is going on one food
			// at the same time
			visibleTiles.remove(isTaken);

			List<Tile> route = null;
			Tile target = null;
			Set<Tile> targets = null;

			// get the route of the closest of the highest exploration tiles.
			while (route == null) {
				// get the tile with die highest exploreValue
				targets = gameI.getMaxVisibilityAgo(visibleTiles);

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
				for (Entry<Tile, Aim> neig : gameI.getMoveAbleNeighbours(
						(antTile)).entrySet()) {
					if (route.size() > 1) {
						Tile a = neig.getKey();
						if (a != null) {
							if (a.equals(route.get(1))) {

								gameI.issueOrder(antTile, neig.getValue());
								ant.setPosition(neig.getKey().getRow(), neig
										.getKey().getCol());
								route.remove(1);
							}
						} else {
							// keine nachbarn vorhanden
						}
					}
				}
			}
		}
	}

	private void collectFood() {

		for (Tile foodTile : gameI.getFoodTiles()) {
			Set<Tile> visitableTiles = new HashSet<Tile>();
			List<Tile> tmpList = new LinkedList<>();
			tmpList.add(foodTile);

			List<Ant> nearestTarget = bsf.extendedBSF(tmpList,	gameI.getOwnNotDangeredAnts(), true, false, 0, visitableTiles);

			Ant targetAnt;
			if (nearestTarget.size() == 0) {
				continue;
			} else {
				targetAnt = nearestTarget.get(0);
			}

			List<Tile> r = pathfinding.aStar(targetAnt.getAntPosition(),
					foodTile);

			// da beim essen sammel nicht direkt das Tile besucht werden muss,
			// auf dem es liegt. Es reicht wenn man daneben steht.
			r.remove(r.size() - 1);
			if (r.size() > 1) {
				r.remove(0);
			}

			if ((targetAnt.getMission() == Missions.NON)) {

				targetAnt.setMission(Missions.COLLECT_FOOD);

				targetAnt.setRoute(r);

			} else if (targetAnt.getRoute().size() > r.size()) {

				targetAnt.setRoute(r);

			}

		}

		for (Ant ant : gameI.getMyAnts()) {

			if (ant.getRoute() == null || ant.getRoute().size() == 0
					|| ant.getMission() == Missions.NON) {
				ant.setMission(Missions.NON);
				ant.setRoute(null);
				continue;
			}

			OverlayDrawer.drawLine(ant.getRoute().get(0),
					ant.getRoute().get(ant.getRoute().size() - 1));

			Tile next = ant.getRoute().remove(0);

			executeMove(ant, next);

		}
	}

	private void executeMove(Ant ownAnt, Tile nextTile) {
		Tile actuallPosition = ownAnt.getAntPosition();
		Map<Tile, Aim> neighbours = gameI
				.getMoveAbleNeighbours(actuallPosition);

		if (neighbours.containsKey(nextTile)) {
			Aim aim = neighbours.get(nextTile);
			gameI.issueOrder(actuallPosition, aim);
			ownAnt.setPosition(nextTile.getRow(), nextTile.getCol());

		} else {
			ownAnt.setMission(Missions.NON);
			ownAnt.setRoute(null);
		}
	}
}
