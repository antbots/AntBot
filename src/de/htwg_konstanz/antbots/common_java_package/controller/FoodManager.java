package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Food;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;


public class FoodManager {

	LinkedList<Tile> food;

	Map<Ant, Tile> markedAnts;
	List<AntFood> antfood;


	public FoodManager() {
		food = new LinkedList<>();
		markedAnts = new HashMap<>();
	}

	/**
	 * must be call at the beginning of each turn
	 */
	public void initFood(){
		food = new LinkedList<>();
	}
	
	public void update(Tile t) {
		food.add(t);
	}
	
	public void markAntsToCollectFood() {
		antfood = new LinkedList<>();
		markedAnts = new HashMap<>();

		Set<Ant> ants = new HashSet<Ant>();
		for (Ant a : AntBot.getGameI().getMyAnts()) {
			if (!getMarkedAnts().containsKey(a)) {
				ants.add(a);
			}
		}
		if (ants.isEmpty()) {
			return;
		}

		for (Ant a : ants) {
			LinkedList<Tile> foodTilesInViewRadius = new LinkedList<Tile>();
			for (Tile food : food) {
				AntBot.getGameI().getTilesInRadius(	a.getAntPosition(),	(int) Math.sqrt(AntBot.getGameI().getViewRadius2())).stream().filter(t -> t.equals(food)).forEach(foodTilesInViewRadius::add);		
			}

			if (foodTilesInViewRadius.isEmpty()) {
				continue;
			}


			for (Tile t : foodTilesInViewRadius) {
				List<Tile> tmp = AntBot.getPathfinding().aStar(a.getAntPosition(), t);
				if(tmp == null) {
					AntBot.debug().log("Ameise " + a.getAntPosition() + " food Tile " + t);
				}
				int distance = tmp.size();
				if(distance <= Configuration.COLLECTFOODRADIUS) {
					antfood.add(new AntFood(a, t, distance));
				}
			}
		}
		
		antfood.sort(new AntFoodComperator());
		
		List<Tile> tmpFood = new LinkedList<>();
		
		for(AntFood a : antfood) {
//			AntBot.debug().log(a.toString());
			Ant ant = a.getA();
			Tile foodT = a.getFood();
			if(!markedAnts.containsKey(ant) && !tmpFood.contains(foodT)) {
				markedAnts.put(ant, foodT);
				tmpFood.add(foodT);
			}
		}
//		AntBot.debug().log("--------------------------------------");
	}
	
	class AntFood {
		
		private Ant a;
		private Tile food;
		private int distance;

		public AntFood(Ant a, Tile food, int distance) {
			this.a = a;
			this.food = food;
			this.distance = distance;
		}
				
		public int getDistance(){
			return distance;
		}

		public Tile getFood() {
			return food;
		}

		public Ant getA() {
			return a;
		}
		
		@Override
		public String toString() {
			return "[Ameise : " + a.getAntPosition() + " collect Food " + food + " with distance " + distance;
		}
	}
	
	class AntFoodComperator implements Comparator<AntFood> {

		@Override
		public int compare(AntFood o1, AntFood o2) {
			return o1.distance-o2.getDistance();
		}
		
	}
	
	public Map<Ant, Tile> getMarkedAnts() {
		return markedAnts;
	}
}
