package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.controller.helper.Pathfinding;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class GoToBoarder implements State{

	private StateName stateName;
	
	Ant ant;
	
	public GoToBoarder(Ant a) {
		this.stateName = StateName.GoToBoarder;
		ant = a;
	}

	@Override
	public void execute() {
		for(Entry<Set<Tile>, Set<Tile>> e : BuildBoarder.getAreaAndBoarder().entrySet()) {
			if(e.getKey().contains(ant.getAntPosition())) {
				Tile target = (Tile) e.getValue().toArray()[(int) (Math.random() * e.getValue().size()) ];
				List<Tile> route = AntBot.getPathfinding().aStar(ant.getAntPosition(), target);
				ant.setRoute(route);
			}
		}
	}

	@Override
	public void changeState() {
		if(ant.isDanger()){
			ant.setState(new Attack(ant));
			return;
		}
		if(AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && !ant.isDanger()){
			ant.setState(new CollectFood(ant));
			return;
		}
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && AntBot.getGameI().getExplorerAnts() >= Configuration.EXPLORERANTSLIMIT){
			return;
		}
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && AntBot.getGameI().getExplorerAnts() < Configuration.EXPLORERANTSLIMIT){
			ant.setState(new Exploration(ant));
			return;
		}
	}

	@Override
	public StateName getStateName() {
		return stateName;
	}
	
	@Override
	public void stateEnter() {
		AntBot.getLogger().log(ant.getState().toString());
	}

	@Override
	public void stateExit() {
		
	}
}
