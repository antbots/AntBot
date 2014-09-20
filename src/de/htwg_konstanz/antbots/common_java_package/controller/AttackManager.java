package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class AttackManager {

	Map<Ant,Order> markedAnts;
	
	public AttackManager() {
		
	}

	public void markAntsToAttack(){
		this.markedAnts = new HashMap<>();
		Map<Set<Ant>, Set<Ant>> att = AntBot.getAttack().initAttackGroups();
		for(Entry<Set<Ant>, Set<Ant>> a : att.entrySet()) {
			
			List<Set<Ant>> beteiligteAmeisen = new LinkedList<>();
			
			beteiligteAmeisen.add(a.getKey());
			beteiligteAmeisen.add(a.getValue());
			
			if((AntBot.getStartTime() - System.nanoTime())/1000000 > 7000){
				for(Ant ant : a.getKey()){
					markedAnts.put(ant, new Order(ant.getAntPosition(), Aim.DONTMOVE));
				}
			}else{
				LinkedList<Order> move = AntBot.getGameStrategy().alphaBeta(AntBot.getGameI(), Configuration.ATTACKSEARCHDEPTH, Configuration.ATTACKSTRATEGY, beteiligteAmeisen);
				if (move != null){
					for(Ant ant : a.getKey()){
						for(Order o : move){
							if(ant.getAntPosition().equals(o.getPosition())){
								markedAnts.put(ant, o);
							}
						}
					}
				}
			}
		}
	}
	
	public Map<Ant, Order> getMarkedAnts() {
		return markedAnts;
	}

	/**
	 * set the dangered value of the own ants to false and
	 * set the enemy in view radius set to null
	 */
	private static void initDanger() {
		for(Ant myAnt : AntBot.getGameI().getMyAnts()) {
			myAnt.setDanger(false);
			myAnt.setEnemysInViewRadius(null);
		}
	}

	/**
	 * mark the own ants as dangerd if their are enemy ants in the view radius
	 * if their are enemy ants in the view radius the method save this enemy ants in
	 * a set and 
	 */
	public void markOwnAntsAsDangered() {
		initDanger();
		Set<Ant> myAnts = new HashSet<Ant>();

		for (Ant myAnt : AntBot.getGameI().getMyAnts()) {
			Set<Ant> enemyAnts = new HashSet<Ant>();
			
			Tile myAntTile = myAnt.getAntPosition();
			Set<Tile> myTiles = AntBot.getGameI().getTilesInRadius(myAntTile,Configuration.DANGERRADIUS);
			
			for (Ant enemyAnt : AntBot.getGameI().getEnemyAnts()) {
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
		AntBot.getGameI().setMyAntDangered(myAnts);

	}
}
