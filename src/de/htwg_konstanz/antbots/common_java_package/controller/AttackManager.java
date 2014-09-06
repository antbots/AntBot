package de.htwg_konstanz.antbots.common_java_package.controller;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.AlphaBeta;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.MaxN;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Food;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class AttackManager {

	Map<Ant,Order> markedAnts;
	private static int groupSize = 2;
	
	
	public AttackManager() {
		
	}

	public void markAntsToAttack(){
		this.markedAnts = new HashMap<>();
		Map<Set<Ant>, Set<Ant>> att = AntBot.getAttack().initAttackGroups();
		AntBot.getLogger().log("Groups for AlphaBeta:");
		for(Entry<Set<Ant>, Set<Ant>> a : att.entrySet()) {
			
			List<Set<Ant>> beteiligteAmeisen = new LinkedList<>();
			
			beteiligteAmeisen.add(a.getKey());
			beteiligteAmeisen.add(a.getValue());
			
			AntBot.getLogger().log("key " + a.getKey()+ " value " + a.getValue());
			
			
			LinkedList<Order> move = AntBot.getGameStrategy().alphaBeta(AntBot.getGameI(), Configuration.ATTACKSEARCHDEPTH, Configuration.ATTACKSTRATEGY, beteiligteAmeisen);
			if (move != null){
				for(Ant ant : AntBot.getGameI().getMyAnts()){
					for(Order o : move){
						if(ant.getAntPosition().equals(o.getPosition())){
							markedAnts.put(ant, o);
						}
					}
				}
				
				///AntBot.getGameI().getMyAntsDangered().forEach( ant -> {Optional<Order> matchedOrder = move.stream().filter(o -> ant.getAntPosition().equals(o.getPosition())).findAny();
				//markedAnts.put(ant, matchedOrder.get());});
			}
		}
		// DEBUG
		AntBot.getLogger().log("Marked Ants:");
		for(Entry<Ant,Order> entry : markedAnts.entrySet()){
			AntBot.getLogger().log(entry.toString());
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
			
			//DEBUG
//			for(Tile t : myTiles) {
//				OverlayDrawer.setFillColor(Color.GREEN);
//				OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
//						SubTile.BR);
//			}
			
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
