package de.htwg_konstanz.antbots.common_java_package.controller.state;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;

public class Defend implements State{

	private Ant ant;
	private StateName stateName; 
	
	public Defend(Ant a) {
		this.ant = a;
		stateName = StateName.CollectFood;
	}
	
	@Override
	public void execute() {
		
	}

	@Override
	public void changeState() {
		if (AntBot.getAttackManager().getMarkedAnts().containsKey(ant)) {
			ant.setState(new Attack(ant));
			return;
		}
	}

	@Override
	public void stateEnter() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stateExit() {
		// TODO Auto-generated method stub
	}

	@Override
	public StateName getStateName() {
		// TODO Auto-generated method stub
		return null;
	}

}
