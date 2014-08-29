package de.htwg_konstanz.antbots.bots.kartentest_bot;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.Bot;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.Logger;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.MaxN;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Pathfinding;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Statistic;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Statistic.Measure;
import de.htwg_konstanz.antbots.common_java_package.model.Order;

public class AttackBot extends Bot {

	private GameInformations gameI;

	private int turn = 0;
	private Logger logger = new Logger("AttackBot.txt");
	private Pathfinding pathfinding;
	private MaxN gameStrategy;
	private Statistic statistics;
	Measure alphaBeta;

	public static void main(String[] args) throws IOException {
		new AttackBot().readSystemInput();
	}

	private void init() {
		gameI = gameStateInforamtions();
		gameI.setLogger(logger);
		gameStrategy = new MaxN();
		statistics = new Statistic(gameI);
		alphaBeta = statistics.createMeasure("AlphaBeta");
	}

	@Override
	public void doTurn() {
		if (turn == 0) {
			init();
		}
		logger.log("_------------------------------------ ");

		if (gameI.getMyAnts().size() != 0) {
			alphaBeta.startSample();
			attack();
			alphaBeta.sample();
		}

		statistics.trackStatistics();

		if (turn == 5) {
			statistics.saveMeasures();
		}
		turn++;
	}

	private void attack() {
		// nur Ameisen übergeben die an Kampf beteiligt sein sollen

		logger.log("----------------------" + gameI.getMyAnts() + "--------------------");
		
		LinkedList<Ant> eigene = new LinkedList<>();
		LinkedList<Ant> gegner = new LinkedList<>();
		for (Ant ant : gameI.getMyAnts()) {
			eigene.add(ant);
		}
		for (Ant ant : gameI.getEnemyAnts()) {
			gegner.add(ant);
		}

		while (!eigene.isEmpty()) {
			LinkedList<Set<Ant>> beteiligteAmeisen = new LinkedList<>();
			long start = System.nanoTime();
			Set<Ant> tmp1 = new HashSet<>();
			for (int i = 0; i < 3; i++) {
				if (eigene.size() > 0) {
					tmp1.add(eigene.get(0));
					eigene.remove(0);
				}
			}
			beteiligteAmeisen.addFirst(tmp1);
			Set<Ant> tmp = new HashSet<>();
			for (int i = 0; i < 3; i++) {
				if (gegner.size() > 0) {
					tmp.add(gegner.get(0));
					gegner.remove(0);
				}
			}
			if (!(gegner.size() > 0)) {
				tmp.add((Ant) gameI.getEnemyAnts().toArray()[0]);
			}

			beteiligteAmeisen.addLast(tmp);
			//logger.log(beteiligteAmeisen.toString());
			LinkedList<Order> move = gameStrategy.attack(gameI, 1, MaxN.Strategy.AGGRESSIVE, beteiligteAmeisen);
			logger.log(Long.toString((System.nanoTime() - start) / 1000000));
			if (move != null)
				for (Order order : move) {
					gameI.issueOrder(order);
					logger.log(order.toString());
				}
		}
		// beteiligteAmeisen.addLast(gameI.getMyAnts());
		// beteiligteAmeisen.addAll(gameI.getEnemyAntsAsList());

	}
}