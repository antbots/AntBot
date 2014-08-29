package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.model.Food;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class FoodManager {
	
	Map<Tile,Food> inDemand;
	Map<Tile,Food> onOffer;
	
	Map<Ant,Food> markedAnts;

	public FoodManager() {
		this.inDemand = new HashMap<>();
		this.onOffer = new HashMap<>();
		markedAnts = new HashMap<>();
	}
	
	public void beforeUpdate(){
		inDemand.entrySet().forEach(e -> e.getValue().setAlive(false));
		onOffer.entrySet().forEach(e -> e.getValue().setAlive(false));
	}
	
	public void update(Tile t){
		if(!onOffer.containsKey(t) && !inDemand.containsKey(t)){
			onOffer.put(t, new Food(t));
		}
		if(onOffer.containsKey(t)){
			onOffer.get(t).setAlive(true);
		}
		if(inDemand.containsKey(t)){
			inDemand.get(t).setAlive(true);
		}
		
		for(Entry<Tile,Food> e : inDemand.entrySet()){
			if(!e.getValue().isAlive()){
				for(Entry<Ant,Food> e1 : markedAnts.entrySet()){
					if(e1.getValue().getPosition().equals(e.getKey())){
						markedAnts.remove(e1);
					}
				}
				inDemand.remove(e);
				AntBot.getGameI().getMap()[e.getKey().getRow()][e.getKey().getCol()].setType(Ilk.LAND);
			}
		}
		
		for(Entry<Tile,Food> e : onOffer.entrySet()){
			if(!e.getValue().isAlive()){
				onOffer.remove(e);
				AntBot.getGameI().getMap()[e.getKey().getRow()][e.getKey().getCol()].setType(Ilk.LAND);
			}
		}

	}

	public Map<Ant, Food> getMarkedAnts() {
		return markedAnts;
	}

	public Map<Tile, Food> getInDemand() {
		return inDemand;
	}

	public Map<Tile, Food> getOnOffer() {
		return onOffer;
	}
	
	public void declineFood(Food f, Ant a) {
		f.setConsumer(null);
		inDemand.remove(f);
		onOffer.put(f.getPosition(),f);
		markedAnts.remove(a);
	}
	
	public void acceptFood(Ant a, Food f) {
		f.setConsumer(a);
		onOffer.remove(f);
		inDemand.put(f.getPosition(),f);
	}

	public void markAntsToCollectFood(){
		for (Entry<Tile,Food> foodTile : onOffer.entrySet()) {
			Set<Tile> visitableTiles = new HashSet<Tile>();
			List<Tile> tmpList = new LinkedList<>();
			tmpList.add(foodTile.getKey());

			//TODO nur explorer Ameisen
			List<Ant> nearestTarget = AntBot.getBsf().extendedBSF(tmpList,	AntBot.getGameI().getOwnNotDangeredAnts(), true, false, 0, visitableTiles);

			Ant targetAnt;
			if (nearestTarget.size() == 0) {
				continue;
			} else {
				targetAnt = nearestTarget.get(0);
			}
			
			acceptFood(targetAnt, foodTile.getValue());
			markedAnts.put(targetAnt, foodTile.getValue());
		}
	}
}
