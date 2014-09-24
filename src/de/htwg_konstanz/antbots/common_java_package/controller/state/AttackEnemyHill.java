package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class AttackEnemyHill implements State{

	Ant ant;
	private StateName stateName;
	private Tile destination;
	private boolean goToEnemyHill = false;
	
	public AttackEnemyHill(Ant ant) {
		this.ant = ant;
		stateName = StateName.AttackEnemyHill;
	}
	
	
	@Override
	public void execute() {
		if(destination != null && ant.getAntPosition().equals(destination)){
			goToEnemyHill = false;
			
		}
		if(!goToEnemyHill) {
			
			
			Map<Ant, Tile> antToHill = AntBot.getEnemyHillManager().getAntsToHill();
			
			if(antToHill.containsKey(ant)) {
				List<Tile> route = AntBot.getPathfinding().aStar(ant.getAntPosition(), antToHill.get(ant));
				//remove because position 0 is the ant position
				route.remove(0);
				AntBot.debug().log("Route " + route.size());
				ant.setRoute(route);
				destination = route.get(route.size() - 1);
			}
		
			goToEnemyHill = true;
		} else {
			//damit der weg jedes mal neu berechnet wird um zu verhindern, dass die Route über unentdecktes Land geht(könnte nämlich Wasser sein)
			List<Tile> route = AntBot.getPathfinding().aStar(ant.getAntPosition(), destination);
			//remove because position 0 is the ant position
			route.remove(0);
			ant.setRoute(route);
		}
		
		for (Tile rTile : ant.getRoute()) {
			OverlayDrawer.setFillColor(Color.CYAN);
			OverlayDrawer.drawTileSubtile(rTile.getRow(), rTile.getCol(),
					SubTile.TL);
		}
	}

	@Override
	public void changeState() {
		if(AntBot.getEnemyHillManager().getAntsToHill().containsKey(ant)) {
			return;
		}
		if (GameInformations.getFoodManager().getMarkedAnts().containsKey(ant)) {

			ant.setState(new CollectFood(ant));
			return;
		}
		if (!GameInformations.getFoodManager().getMarkedAnts()
						.containsKey(ant)
				&& AntBot.getGameI().getExplorerAnts() >= Configuration.getExplorerAntsLimit() && BuildBoarder.marktAnts().contains(ant)) {
			ant.setState(new GoToBoarder(ant));
			return;
		}
		if (!GameInformations.getFoodManager().getMarkedAnts()
						.containsKey(ant)
				&& AntBot.getGameI().getExplorerAnts() < Configuration.getExplorerAntsLimit()) {
			ant.setState(new Exploration(ant));
			return;
		}
	}

	@Override
	public void stateEnter() {
		
	}

	@Override
	public void stateExit() {
		
	}

	@Override
	public StateName getStateName() {
		return stateName;
	}

}
