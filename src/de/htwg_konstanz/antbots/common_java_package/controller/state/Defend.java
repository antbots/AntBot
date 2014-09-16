package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.DefendOwnHillManager;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class Defend implements State {

	private Ant ant;
	private StateName stateName;
	Tile destination;
	boolean defendOwnHill = false;

	public Defend(Ant a) {
		this.ant = a;
		stateName = StateName.Defend;
	}

	@Override
	public void execute() {
		if (!defendOwnHill) {
			Tile a = DefendOwnHillManager.getDefendAntsToHills().get(ant);
			List<Tile> route = AntBot.getPathfinding().aStar(ant.getAntPosition(),	a);
			AntBot.debug().log("AMiese " + ant.getAntPosition() + " " + route + "                " + a);
			destination = route.get(route.size() - 1);
			// remove because position 0 is the ant position
			route.remove(0);
			ant.setRoute(route);
			defendOwnHill = true;
		} else if (destination != ant.getAntPosition()) {

//			// damit der weg jedes mal neu berechnet wird um zu verhindern, dass
//			// die Route über unentdecktes Land geht(könnte nämlich Wasser sein)
//			List<Tile> route = AntBot.getPathfinding().aStar(ant.getAntPosition(), destination);
//			// remove because position 0 is the ant position
//			List<Tile> route = ant.getRoute();
//			ant.setRoute(route);
		}
		AntBot.debug().log("Amiese " + ant.getAntPosition() + " destination " + destination);
	}

	@Override
	public void changeState() {

		if (DefendOwnHillManager.getMarkedAnts().contains(ant)	&& AntBot.getGameI().getMyAnts().size() > Configuration.LIMITWHENDEFENDANTSAREORDERD) {
			return;
		}
		// AntBot.debug().log("DEFEND FAILD");
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
		return stateName;
	}

}
