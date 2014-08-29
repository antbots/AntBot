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
		if(AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)){
			ant.setState(new CollectFood(ant));
		}
		if(!ant.isDanger()){
			ant.setState(new Exploration(ant));
		}
		if(ant.isDanger()){
			ant.setState(new Attack(ant));
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
