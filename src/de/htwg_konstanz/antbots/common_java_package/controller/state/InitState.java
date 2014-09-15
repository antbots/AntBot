package de.htwg_konstanz.antbots.common_java_package.controller.state;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;

public class InitState  implements State{
	
	Ant ant;
	private StateName stateName;

	public InitState(Ant a) {
		this.ant = a;
		stateName = StateName.InitState;
	}

	@Override
	public void changeState() {
		
		if(AntBot.getAttackManager().getMarkedAnts().containsKey(ant)){
			ant.setState(new Attack(ant));
			return;
		}
		if(AntBot.getDefendOwnHillManager().getDefendAntsToHills().containsKey(ant) && AntBot.getGameI().getMyAnts().size() > Configuration.LIMITWHENDEFENDANTSAREORDERD) {
			ant.setState(new Defend(ant));
			return;
		}
		if(GameInformations.getFoodManager().getMarkedAnts().containsKey(ant)){
			ant.setState(new CollectFood(ant));
			return;
		}

		if( !GameInformations.getFoodManager().getMarkedAnts().containsKey(ant) && AntBot.getGameI().getExplorerAnts() >= Configuration.getExplorerAntsLimit() && BuildBoarder.marktAnts().contains(ant)){
			ant.setState(new GoToBoarder(ant));
			return;
		}
		if(!GameInformations.getFoodManager().getMarkedAnts().containsKey(ant) && ((AntBot.getGameI().getExplorerAnts() < Configuration.getExplorerAntsLimit() || !BuildBoarder.marktAnts().contains(ant)))){
			ant.setState(new Exploration(ant));
			return;
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
