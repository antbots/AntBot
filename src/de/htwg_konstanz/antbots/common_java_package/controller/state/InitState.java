package de.htwg_konstanz.antbots.common_java_package.controller.state;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;

public class InitState  implements State{
	
	Ant ant;

	public InitState(Ant a) {
		this.ant = a;
	}

	@Override
	public void changeState() {
		
		if(ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)){
			ant.setState(new Attack(ant));
			AntBot.getLogger().log(ant.getState().toString());
		}
		if(AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && !ant.isDanger()){
			ant.setState(new CollectFood(ant));
			AntBot.getLogger().log(ant.getState().toString());
			
		}
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)){
			ant.setState(new Exploration(ant));
			AntBot.getLogger().log(ant.getState().toString());
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "InitState State";
	}

}
