package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class Defend implements State{

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
		if(destination != null && ant.getAntPosition().equals(destination)){
			defendOwnHill = false;
			
		}
		if(!defendOwnHill) {
			
			
			Map<Ant, Tile> antToHill = AntBot.getDefendOwnHillManager().getDefendAntsOfOwnHills();
			Map<Ant, List<Tile>> antTileToTilesArroundHill = AntBot.getDefendOwnHillManager().getTilesAroundHill();
			
			if(antToHill.containsKey(ant)) {
				List<Tile> tilesArroundHill = antTileToTilesArroundHill.get(ant);
				
				Tile tileToGo = tilesArroundHill.get((int) (Math.random() * tilesArroundHill.size()));
				
				List<Tile> route = AntBot.getPathfinding().aStar(ant.getAntPosition(), tileToGo);
				route.remove(0);
				ant.setRoute(route);
				destination = route.get(route.size() - 1);
			}
		
			defendOwnHill = true;
		} else {
			//damit der weg jedes mal neu berechnet wird um zu verhindern, dass die Route über unentdecktes Land geht(könnte nämlich Wasser sein)
			List<Tile> route = AntBot.getPathfinding().aStar(ant.getAntPosition(), destination);
			route.remove(0);
			ant.setRoute(route);
		}
		
		for (Tile rTile : ant.getRoute()) {
			OverlayDrawer.setFillColor(Color.GRAY);
			OverlayDrawer.drawTileSubtile(rTile.getRow(), rTile.getCol(),
					SubTile.BM);
		}
	}

	@Override
	public void changeState() {
		if (AntBot.getAttackManager().getMarkedAnts().containsKey(ant)) {
			ant.setState(new Attack(ant));
			return;
		}
		if(AntBot.getDefendOwnHillManager().getDefendAntsOfOwnHills().containsKey(ant) && AntBot.getGameI().getMyAnts().size() > Configuration.LIMITWHENDEFENDANTSAREORDERD) {
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
