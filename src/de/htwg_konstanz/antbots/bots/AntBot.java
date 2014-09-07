package de.htwg_konstanz.antbots.bots;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

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
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Pathfinding;
import de.htwg_konstanz.antbots.common_java_package.model.Food;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

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
	private static DefendOwnHillManager defendOwnHillManager;
	private static LinkedList<Order> antsOrders;
	private static LinkedList<Tile> invalidPositions;
	private static Logger debug = new Logger("Debug.txt");
	

	public static void main(String[] args) throws IOException {
		new AntBot().readSystemInput();
	}

	private void init() {
		gameI = gameStateInforamtions();
		gameI.setLogger(logger);
		bsf = new BreadthFirstSearch(gameI);
		pathfinding = new Pathfinding(gameI);
		boarder = new BuildBoarder(gameI);
		attack = new AttackInit(gameI);
		gameStrategy = new AlphaBeta();
		attackManager = new AttackManager();
		enemyHillManager = new EnemyHillManager();
		defendOwnHillManager = new DefendOwnHillManager();
	}

	@Override
	public void doTurn() {
		debug.log("---------------------------------------------------------------------------------------");
		if (turn == 0) {
			init();
		}
		
		debug.log("TURN " + turn);
		antsOrders = new LinkedList<Order>();
		invalidPositions = new LinkedList<>();
		
		logger.log("TURN " + turn);
		boarder.buildBoarder();
		
		enemyHillManager.antsToEnemyHill();
		defendOwnHillManager.defendAntsToOwnHill();
		
		attackManager.markOwnAntsAsDangered();
		attackManager.markAntsToAttack();
		
		GameInformations.getFoodManager().markAntsToCollectFood();
		
		gameI.getMyAnts().forEach(a -> {
			logger.log("Process Ant: " + a.getAntPosition());
			a.doLogic();
			a.move();
		});
		gameI.getMyAnts().forEach(b -> { debug.log("Position " + b.getAntPosition() + " Zustand " + b.getCurrentState() + " Route " + b.getRoute() );});
		
		debug.log("---------------------------------------------------------------------------------------");
		turn++;
		GameInformations.getFoodManager().initFood();
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
	
	public static DefendOwnHillManager getDefendOwnHillManager(){
		return defendOwnHillManager;
	}
	
	public static LinkedList<Order> getAntsOrders(){
		return antsOrders;
	}

	public static LinkedList<Tile> getInvalidPositions(){
		return invalidPositions;
	}
	
	public static Logger debug() {
		return debug;
	}
	
}
