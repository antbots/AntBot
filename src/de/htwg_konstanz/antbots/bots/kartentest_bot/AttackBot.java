package de.htwg_konstanz.antbots.bots.kartentest_bot;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.Bot;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Order;
import de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding;
import de.htwg_konstanz.antbots.common_java_package.helper.GameStrategy;
import de.htwg_konstanz.antbots.common_java_package.helper.Statistic;
import de.htwg_konstanz.antbots.common_java_package.helper.Statistic.Measure;

public class AttackBot extends Bot {

	private GameInformations gameI;

	private int turn = 0;
	private Pathfinding pathfinding;
	private GameStrategy gameStrategy;
	private Statistic statistics;
	Measure alphaBeta;

	public static void main(String[] args) throws IOException {
		new AttackBot().readSystemInput();
	}

	private void init() {
		gameI = gameStateInforamtions();
		gameStrategy = new GameStrategy();
		statistics = new Statistic(gameI);
		alphaBeta = statistics.createMeasure("AlphaBeta");
	}

	@Override
	public void doTurn() {
		if (turn == 0) {
			init();
		}
		
		if(gameI.getMyAnts().size() != 0){
			alphaBeta.startSample();
			attack();
			alphaBeta.sample();
		}
		
		statistics.trackStatistics();
		
		if(turn == 5){
			statistics.saveMeasures();
		}
		turn++;
	}

	private void attack() {
		// nur Ameisen übergeben die an Kampf beteiligt sein sollen
		LinkedList<List<Ant>> beteiligteAmeisen = new LinkedList<>();
		beteiligteAmeisen.addLast(gameI.getMyAnts());
		beteiligteAmeisen.addAll(gameI.getEnemyAntsAsList());
		LinkedList<Order> move = gameStrategy.attack(gameI,1, GameStrategy.Strategy.AGGRESSIVE, beteiligteAmeisen);
		 if (move != null)
			 for (Order order : move) {
				 gameI.issueOrder(order);
			}
		    
	}
}