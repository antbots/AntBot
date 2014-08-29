package de.htwg_konstanz.antbots.bots;

import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.AttackManager;
import de.htwg_konstanz.antbots.common_java_package.controller.Bot;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.Logger;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.AttackInit;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.MaxN;
import de.htwg_konstanz.antbots.common_java_package.controller.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.BreadthFirstSearch;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Pathfinding;
import de.htwg_konstanz.antbots.common_java_package.controller.state.InitState;
import de.htwg_konstanz.antbots.common_java_package.controller.state.State;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.common_java_package.model.settings.Missions;
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
	private static MaxN gameStrategy;
	private static AttackManager attackManager;
	

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
		gameStrategy = new MaxN();
		attackManager = new AttackManager();
		
	}

	@Override
	public void doTurn() {
		logger.log("---------------------------------------------------------------------------------------");
		if (turn == 0) {
			init();
		}
		logger.log("TURN " + turn);
		boarder.buildBoarder();
		
		attackManager.markOwnAntsAsDangered();
		attackManager.markAntsToAttack();
		gameI.getFoodManager().markAntsToCollectFood();
		
		gameI.getMyAnts().forEach(a -> {
			a.doLogic();
			a.move();
		});

		logger.log("---------------------------------------------------------------------------------------");
		turn++;

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

	public static MaxN getGameStrategy() {
		return gameStrategy;
	}

	
}
