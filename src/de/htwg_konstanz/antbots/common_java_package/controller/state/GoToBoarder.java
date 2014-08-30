package de.htwg_konstanz.antbots.common_java_package.controller.state;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;

public class GoToBoarder implements State{

	private StateName stateName;
	
	Ant ant;
	
	public GoToBoarder(Ant a) {
		this.stateName = StateName.GoToBoarder;
		ant = a;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
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
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && AntBot.getGameI().getExplorerAnts() >= 10){
			return;
		}
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && AntBot.getGameI().getExplorerAnts() < 10){
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
