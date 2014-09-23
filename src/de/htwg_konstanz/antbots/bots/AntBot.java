package de.htwg_konstanz.antbots.bots;


import java.awt.Color;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import jdk.internal.org.objectweb.asm.commons.GeneratorAdapter;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.AttackManager;
import de.htwg_konstanz.antbots.common_java_package.controller.Bot;
import de.htwg_konstanz.antbots.common_java_package.controller.DefendOwnHillManager;
import de.htwg_konstanz.antbots.common_java_package.controller.EnemyHillManager;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.Logger;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.AlphaBeta;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.AttackInit;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.MaxN;
import de.htwg_konstanz.antbots.common_java_package.controller.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.BreadthFirstSearch;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.CardPrinter;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Pathfinding;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Food;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

/**
 * 
 * @author Benjamin
 */
public class AntBot extends Bot {

	private static GameInformations gameI;
	private static BreadthFirstSearch bsf;
	private static Logger logger = new Logger("AntBot.txt");
	private static Pathfinding pathfinding;
	private static int turn = 0;
	private static BuildBoarder boarder;
	private static AttackInit attack;
	private static AlphaBeta gameStrategy;
	private static AttackManager attackManager;
	private static EnemyHillManager enemyHillManager;
	private static LinkedList<Order> antsOrders;
	private static boolean moveError = false;
	private static Logger debug = new Logger("Debug.txt");
	private static long startTurnTime;
	

	public static void main(String[] args) throws IOException {
		new AntBot().readSystemInput();
	}

	private void init() {
		gameI = gameStateInforamtions();
		GameInformations.setLogger(logger);
		bsf = new BreadthFirstSearch(gameI);
		pathfinding = new Pathfinding(gameI);
		boarder = new BuildBoarder(gameI);
		attack = new AttackInit(gameI);
		gameStrategy = new AlphaBeta();
		attackManager = new AttackManager();
		enemyHillManager = new EnemyHillManager();

	}

	@Override
	public void doTurn() {
		debug.log("--------------------------------------");
		startTurnTime = System.currentTimeMillis();
		if (turn == 0) {
			init();
			
		}
		debug.log("TURN " + turn);
		CardPrinter.PrintCard(turn);
		DefendOwnHillManager.initDefendTiles();
		
		antsOrders = new LinkedList<Order>();

		long timeBefore = System.currentTimeMillis();
		BuildBoarder.improvedBoarder();
		Long timeAfter = System.currentTimeMillis();
		
		//debug.log("Time Boarder " + ((timeAfter - timeBefore)));
		
		//debug().log("After boarder");
		
		enemyHillManager.antsToEnemyHill();
		//debug().log("After antsToEnemyHill");
		
		DefendOwnHillManager.defendAntsToDefendTile();
		
		attackManager.markOwnAntsAsDangered();
		attackManager.markAntsToAttack();
		//debug().log("After attackManager");
		
		GameInformations.getFoodManager().markAntsToCollectFood();
		//debug().log("After getFoodManager");
		
		gameI.getMyAnts().forEach(a -> {
			a.doLogic();
			a.move();
		});
		
		AntBot.setMoveError(true);
		while(moveError){
			resolveMoveError();
			if((System.nanoTime()- AntBot.getStartTime())/1000000 > 10000){
				moveError = false;
			}
		}
		sendMovesToSimulation();
		
		turn++;
		GameInformations.getFoodManager().initFood();
		debug.log("--------------------------------------");
	}
	
	

	public static AttackManager getAttackManager() {
		return attackManager;
	}

	public static GameInformations getGameI() {
		return gameI;
	}

	public static BreadthFirstSearch getBsf() {
		return bsf;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static Pathfinding getPathfinding() {
		return pathfinding;
	}

	public static int getTurn() {
		return turn;
	}

	public static BuildBoarder getBoarder() {
		return boarder;
	}

	public static AttackInit getAttack() {
		return attack;
	}

	public static AlphaBeta getGameStrategy() {
		return gameStrategy;
	}
	
	public static EnemyHillManager getEnemyHillManager() {
		return enemyHillManager;
	}
		
	public static LinkedList<Order> getAntsOrders(){
		return antsOrders;
	}
	
	public static Logger debug() {
		return debug;
	}

	public static void resolveMoveError(){
		boolean skip = false;
		LinkedList<Order> errorMoves = new LinkedList<>();
		for(Order o1 : AntBot.getAntsOrders()){
			for(Order o2 : AntBot.getAntsOrders()){
				if((o1.getNewPosition().equals(o2.getNewPosition()) && !o1.equals(o2))){
					skip = true;
					errorMoves.add(o1);

				}
			}
		}
		if(skip) {
			for(Order error : errorMoves){
				AntBot.getAntsOrders().remove(error);
				
				LinkedList<Aim> aim = new LinkedList<>();
				aim.add(Aim.EAST);
				aim.add(Aim.NORTH);
				aim.add(Aim.SOUTH);
				aim.add(Aim.WEST);
				//aim.add(Aim.DONTMOVE);
				Collections.shuffle(aim);
				
				List<Aim> toRemove = new LinkedList<>();
				for(Aim a: aim){
					Order o1 = new Order(error.getPosition(),a);
					for(Order o2 : AntBot.getAntsOrders()){
						if(o1.getNewPosition().equals(o2.getNewPosition()) || o1.getNewPosition().getType() == Ilk.WATER){
							toRemove.add(a);
							break;
						}
					}
				}
				for(Aim a : toRemove) {
					aim.remove(a);
				}
				
				Order newOrder = null;
				// ist null wenn es überhaupt keinen anderen Weg gibt. Bei vielen Ameisen auf dem Haufen mögliche
				// endloss schleife
				if(aim.size() == 0){
					newOrder = new Order(error.getPosition(), Aim.DONTMOVE);
				}else{
					newOrder = new Order(error.getPosition(), aim.get((int)(Math.random() * aim.size()-1)));
				}

				newOrder.setAnt(error.getAnt());
				AntBot.getAntsOrders().add(newOrder);

			}
			AntBot.setMoveError(true);
		}else{
			AntBot.setMoveError(false);
		}
	}

	public static void setMoveError(boolean b) {
		moveError = b;
	}
	
	private static void sendMovesToSimulation(){
		for(Order o : getAntsOrders()){
			AntBot.getGameI().issueOrder(o.getPosition(), o.getDirection());
			o.getAnt().setPosition(o.getNewPosition().getRow(), o.getNewPosition().getCol());
		}
	}

	public static long getStartTime() {
		return startTurnTime;
	}
	
}
