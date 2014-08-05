package de.htwg_konstanz.antbots.bots.kartentest_bot;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.sun.istack.internal.logging.Logger;

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
	de.htwg_konstanz.antbots.common_java_package.Logger log = new de.htwg_konstanz.antbots.common_java_package.Logger("bla.txt");

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
		
		LinkedList<Ant> eigene = new LinkedList<>();
		LinkedList<Ant> gegner = new LinkedList<>();
		for (Ant ant : gameI.getMyAnts()) {
			eigene.add(ant);
		}
		for (Ant ant : gameI.getEnemyAnts()) {
			gegner.add(ant);
		}
		
		
		
		while(!eigene.isEmpty()){
			LinkedList<Set<Ant>> beteiligteAmeisen = new LinkedList<>();
			long start = System.nanoTime();
			Set<Ant> tmp1 = new HashSet<>();
			for (int i = 0; i <3; i++) {
				if(eigene.size() > 0){
					tmp1.add(eigene.get(0));
					eigene.remove(0);
				}
			}
			beteiligteAmeisen.addFirst(tmp1);
			Set<Ant> tmp = new HashSet<>();
			for (int i = 0; i <3; i++) {
				if(gegner.size() > 0){
					tmp.add(gegner.get(0));
					gegner.remove(0);
				}
			}
			if(!(gegner.size() > 0)){
				tmp.add((Ant)gameI.getEnemyAnts().toArray()[0]);
			}
			
			beteiligteAmeisen.addLast(tmp);
			
			
			
			LinkedList<Order> move = gameStrategy.attack(gameI,1, GameStrategy.Strategy.AGGRESSIVE, beteiligteAmeisen);
			log.log(Long.toString((System.nanoTime() - start) / 1000000));
			 if (move != null)
				 for (Order order : move) {
					 gameI.issueOrder(order);
					 log.log(order.toString());
				}
		}
		//beteiligteAmeisen.addLast(gameI.getMyAnts());
		//beteiligteAmeisen.addAll(gameI.getEnemyAntsAsList());
		
		    
	}
}