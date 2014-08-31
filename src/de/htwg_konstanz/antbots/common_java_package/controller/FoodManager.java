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
	
	LinkedList<Food> food;

	Map<Ant,Food> markedAnts;

	public FoodManager() {
		this.food = new LinkedList<>();
		markedAnts = new HashMap<>();
	}
	
	public void update(Tile t){
		Food newFood = new Food(t);
		if(!food.contains(newFood)){
			food.add(newFood);
			AntBot.getLogger().log("add");
		}
		if(food.contains(newFood)){
			food.get(food.indexOf(newFood)).setAlive(true);
			AntBot.getLogger().log("setAlive");
		}
	}
	
	public LinkedList<Food> getFood() {
		return food;
	}

	public Map<Ant, Food> getMarkedAnts() {
		return markedAnts;
	}
	
	public void declineFood(Food f, Ant a) {
		f.setOnOffer(true);
		markedAnts.remove(a);
	}
	
	public void acceptFood(Ant a, Food f) {
		f.setOnOffer(false);
		markedAnts.put(a, f);
	}
	
	public void removeFalseFood(){
		LinkedList<Food> toRemove = new LinkedList<Food>();
		LinkedList<Entry<Ant,Food>> toRemoveEntry = new LinkedList<Entry<Ant,Food>>();
		for(Food f : food){
			if(!f.isAlive()){
				for(Entry<Ant,Food> e : markedAnts.entrySet()){
					if(f.equals(e.getValue())){
						toRemoveEntry.add(e);
						AntBot.getLogger().log("remove entry");
					}
				}
				toRemove.add(f);
				AntBot.getGameI().getMap()[f.getPosition().getRow()][f.getPosition().getCol()].setType(Ilk.LAND);
			}
		}
		for(Food f : toRemove){
			food.remove(f);
			AntBot.getLogger().log("remove");
		}
		
		for(Entry<Ant,Food> e : toRemoveEntry){
			markedAnts.remove(e.getKey());
		}
		
		food.forEach(e -> e.setAlive(false));
	}

	public void markAntsToCollectFood(){
		removeFalseFood();
		
		LinkedList<Food> foodOnOffer= new LinkedList<>();
		for(Food f : food){
			if(f.isOnOffer()){
				foodOnOffer.add(f);
			}
		}
		
		AntBot.getLogger().log(Integer.toString(food.size()));
		AntBot.getLogger().log(Integer.toString(foodOnOffer.size()));
		
		for (Food foodTile : foodOnOffer) {
			Set<Tile> visitableTiles = new HashSet<Tile>();
			List<Tile> tmpList = new LinkedList<>();
			tmpList.add(foodTile.getPosition());

			Set<Ant> ants = new HashSet<Ant>();
			for(Ant a : AntBot.getGameI().getMyAnts()){
				if(!markedAnts.containsKey(a)){
					ants.add(a);
				}
			}
			if(ants.isEmpty()){
				continue;
			}
			List<Ant> nearestTarget = AntBot.getBsf().extendedBSF(tmpList,	ants/*AntBot.getGameI().getOwnNotDangeredAnts()*/, true, false, 0, visitableTiles);

			Ant targetAnt;
			if (nearestTarget.size() == 0) {
				continue;
			} else {
				targetAnt = nearestTarget.get(0);
			}
			
			acceptFood(targetAnt, foodTile);
			
		}
		//DEBUG
		
		for(Food f : food){
			AntBot.getLogger().log(f.toString());
		}
		for(Entry<Ant,Food> e1 : markedAnts.entrySet()){
			AntBot.getLogger().log(e1.getKey().getAntPosition() + "  " + e1.getValue().getPosition());
		}
	}
}
