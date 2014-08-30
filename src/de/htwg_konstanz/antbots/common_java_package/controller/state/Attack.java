package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.MaxN;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class Attack implements State{

	Ant ant;
	private StateName stateName;

	public Attack(Ant a) {
		this.ant = a;
		stateName = StateName.Attack;
	}
	
	@Override
	public void changeState() {
		if(ant.isDanger()){
			return;
		}
		if(AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && !ant.isDanger()){
			ant.setState(new CollectFood(ant));
			return;
		}
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && AntBot.getGameI().getExplorerAnts() >= 10){
			ant.setState(new GoToBoarder(ant));
			return;
		}
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && AntBot.getGameI().getExplorerAnts() < 10){
			ant.setState(new Exploration(ant));
			return;
		}
	}

	@Override
	public void execute() {
		//falls eine Ameise markiert wird aber in einen anderen Zustand (attack) kommt
		if(AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)){
			AntBot.getGameI().getFoodManager().declineFood(AntBot.getGameI().getFoodManager().getMarkedAnts().get(ant), ant);
		}
		List<Tile> order = new LinkedList<Tile>();
		order.add(AntBot.getAttackManager().getMarkedAnts().get(ant).getNewPosition());
		ant.setRoute(order);
	}

	@Override
	public String toString() {
		return "Attack State";
	}

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
