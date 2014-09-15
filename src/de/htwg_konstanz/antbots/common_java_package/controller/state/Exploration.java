package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class Exploration  implements State{
	
	private Ant ant;
	private StateName stateName;
	private Tile destination;

	public Exploration(Ant a) {
		this.ant = a;
		stateName = StateName.Exploration;
	}

	@Override
	public void changeState() {
		if(AntBot.getAttackManager().getMarkedAnts().containsKey(ant)){
			ant.setState(new Attack(ant));
			return;
		}
		if(AntBot.getEnemyHillManager().getAntsToHill().containsKey(ant)) {
			ant.setState(new AttackEnemyHill(ant));
			return;
		}
		if(GameInformations.getFoodManager().getMarkedAnts().containsKey(ant)){
			ant.setState(new CollectFood(ant));
			return;
		}
		if(!GameInformations.getFoodManager().getMarkedAnts().containsKey(ant) && ( AntBot.getGameI().getExplorerAnts() < Configuration.getExplorerAntsLimit() || BuildBoarder.getAreaAndBoarder() == null)){
			return;
		}
//		AntBot.debug().log("COLLECTFOOD FAILD");
	}

	@Override
	public void execute() {
		if(destination == null || destination.getType() != Ilk.UNKNOWN)  {
			int radius = (int) Math.sqrt(AntBot.getGameI().getViewRadius2()) + 2;

			Tile antTile = ant.getAntPosition();

			// get the tiles in viewradius+2
			Set<Tile> visibleTiles = AntBot.getGameI().getTilesInRadius(antTile, radius);


			List<Tile> route = null;
			List<Tile> targets = null;
			Tile target = null;

			// get the route of the closest of the highest exploration tiles.
			while (route == null) {
				// get the tile with die highest exploreValue
				targets = AntBot.getGameI().getUnknowTilesToExplore(visibleTiles);
				if(targets.size() != 0) {
					target = targets.get((int)Math.random() * (targets.size() - 1));
					if(AntBot.getGameI().getMoveAbleNeighbours(target).size() == 0) {
						destination = null;
						target.setType(Ilk.WATER);
						execute();
						return;
					}
				} else {
					targets = AntBot.getGameI().getValueTilesToExplore(visibleTiles);
					target = targets.iterator().next();
				}
				

				destination = target;
				// get the route to the target
				route = AntBot.getPathfinding().aStar(antTile, destination);
				
				
				// target is not rachable -> remove it form visitable
				visibleTiles.remove(destination);
			}
			
			// draw
					for (Tile rTile : route) {
						OverlayDrawer.setFillColor(Color.WHITE);
						OverlayDrawer.drawTileSubtile(rTile.getRow(), rTile.getCol(),
								SubTile.MM);
					}
					route.remove(0);
			ant.setRoute(route);
			AntBot.getLogger().log("Route is set: " + ant.getRoute());
		} else {
			List<Tile> route = null;
//			AntBot.debug().log("destiantion in Exploration " + destination.getType());
			route = AntBot.getPathfinding().aStar(ant.getAntPosition(), destination);
			if(route == null){
				if(AntBot.getGameI().getMoveAbleNeighbours(destination).size() == 0) {
					destination.setType(Ilk.WATER);
				}
				destination = null;
				execute();
				return;
			}
			route.remove(0);
			
			ant.setRoute(route);
			for (Tile rTile : route) {
				OverlayDrawer.setFillColor(Color.WHITE);
				OverlayDrawer.drawTileSubtile(rTile.getRow(), rTile.getCol(),
						SubTile.MM);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Exploration State";
	}
	
	public StateName getStateName() {
		return stateName;
	}

	@Override
	public void stateEnter() {
		AntBot.getGameI().increaseExplorerAnts();
		AntBot.getLogger().log(ant.getState().toString());
	}

	@Override
	public void stateExit() {
		AntBot.getGameI().decreaseExplorerAnts();
	}

}
