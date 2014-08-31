package de.htwg_konstanz.antbots.common_java_package.controller;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.state.State;
import de.htwg_konstanz.antbots.common_java_package.controller.state.StateName;
import de.htwg_konstanz.antbots.common_java_package.model.Food;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;


public class FoodManager {
	
	LinkedList<Food> food;

	Map<Ant,Food> markedAnts;

	public FoodManager() {
		this.food = new LinkedList<>();
		
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
	
	/*public void declineFood(Food f, Ant a) {
		f.setOnOffer(true);
		markedAnts.remove(a);
	}
	
	public void acceptFood(Ant a, Food f) {
		f.setOnOffer(false);
		markedAnts.put(a, f);
	}*/
	
	public void removeFalseFood(){
		LinkedList<Food> toRemove = new LinkedList<Food>();
		//LinkedList<Entry<Ant,Food>> toRemoveEntry = new LinkedList<Entry<Ant,Food>>();
		for(Food f : food){
			if(!f.isAlive()){
				/*for(Entry<Ant,Food> e : markedAnts.entrySet()){
					if(f.equals(e.getValue())){
						toRemoveEntry.add(e);
						AntBot.getLogger().log("remove entry");
					}
				}*/
				toRemove.add(f);
				AntBot.getGameI().getMap()[f.getPosition().getRow()][f.getPosition().getCol()].setType(Ilk.LAND);
			}
		}
		for(Food f : toRemove){
			food.remove(f);
			AntBot.getLogger().log("remove");
		}
		
		/*for(Entry<Ant,Food> e : toRemoveEntry){
			markedAnts.remove(e.getKey());
		}*/
		
		food.forEach(e -> e.setAlive(false));
	}

	public void markAntsToCollectFood(){
		markedAnts = new HashMap<>();
		removeFalseFood();
		
		for (Food foodTile : food) {
			Set<Tile> visitableTiles = new HashSet<Tile>();
			List<Tile> tmpList = new LinkedList<>();
			tmpList.add(foodTile.getPosition());

			List<Ant> nearestAnt = AntBot.getBsf().extendedBSF(foodTile.getPosition(),AntBot.getGameI().getOwnNotDangeredAnts(),true, false,0,null);
			AntBot.getLogger().log("sizeeee " + nearestAnt.size());
			if (nearestAnt == null || nearestAnt.size() == 0) {
				
				continue;
			}
			
			Ant ant = nearestAnt.get(0);
				

			List<Tile> r =  AntBot.getPathfinding().aStar(ant.getAntPosition(),foodTile.getPosition());

			// da beim essen sammel nicht direkt das Tile besucht werden muss,
			// auf dem es liegt. Es reicht wenn man daneben steht.
			r.remove(r.size() - 1);
			if (r.size() > 1) {
				r.remove(0);
			}

			if(ant.getRoute() == null || ant.getRoute().size() == 0) {
				markedAnts.put(ant, foodTile);
				ant.setRoute(r);

			} else if ( ant.getRoute().size() > r.size() && ant.getCurrentState() == StateName.CollectFood){
				markedAnts.put(ant, foodTile);
				ant.setRoute(r);
			}
			
			// TODO DEBUG
			for (Tile t : ant.getRoute()) {
				OverlayDrawer.setFillColor(Color.GREEN);
				OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(),
						SubTile.TR);

			}
			

		}
		
		/*LinkedList<Food> foodOnOffer= new LinkedList<>();
		for(Food f : food){
			if(f.isOnOffer()){
				foodOnOffer.add(f);
			}
		}
		
		AntBot.getLogger().log(Integer.toString(food.size()));
		AntBot.getLogger().log(Integer.toString(foodOnOffer.size()));
		Set<Ant> ants = new HashSet<Ant>();
		for(Ant a : AntBot.getGameI().getMyAnts()){
			if(!markedAnts.containsKey(a)){
				ants.add(a);
			}
		}
		
		for (Food foodTile : foodOnOffer) {
			Set<Tile> visitableTiles = new HashSet<Tile>();
			Set<Tile> tmpList = new HashSet<>();
			tmpList.add(foodTile.getPosition());

			
			if(ants.isEmpty()){
				continue;
			}
			List<Ant> nearestTarget = AntBot.getBsf().extendedBSF(ants, tmpList/*AntBot.getGameI().getOwnNotDangeredAnts());

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
		}*/
	}
}
