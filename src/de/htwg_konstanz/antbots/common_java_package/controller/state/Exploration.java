package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.common_java_package.model.settings.Missions;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class Exploration  implements State{
	
	Ant ant;
	private StateName stateName;

	public Exploration(Ant a) {
		this.ant = a;
		stateName = StateName.Exploration;
	}

	@Override
	public void changeState() {
		if(ant.isDanger()){
			ant.setState(new Attack(ant));
			AntBot.getLogger().log(ant.getState().toString());
		}
		if(AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant) && !ant.isDanger()){
			ant.setState(new CollectFood(ant));
			AntBot.getLogger().log(ant.getState().toString());
		}
		if(!ant.isDanger() && !AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)){
			AntBot.getLogger().log(ant.getState().toString());
			return;
		}
	}

	@Override
	public void execute() {
		int radius = (int) Math.sqrt(AntBot.getGameI().getViewRadius2()) + 2;

		Set<Tile> isTaken = new HashSet<Tile>();

		Tile antTile = ant.getAntPosition();

		// get the tiles in viewradius+1
		Set<Tile> visibleTiles = AntBot.getGameI().getTilesInRadius(antTile, radius);

		// remove the taken food so that only one ant is going on one food
		// at the same time
		visibleTiles.remove(isTaken);

		List<Tile> route = null;
		Tile target = null;
		Set<Tile> targets = null;

		// get the route of the closest of the highest exploration tiles.
		while (route == null) {
			// get the tile with die highest exploreValue
			targets = AntBot.getGameI().getMaxVisibilityAgo(visibleTiles);

			target = targets.iterator().next();

			// get the route to the target
			route = AntBot.getPathfinding().aStar(antTile, target);

			// target is not rachable -> remove it form visitable
			visibleTiles.remove(target);
		}

		//
		isTaken.add(target);

		// draw
		for (Tile rTile : route) {
			OverlayDrawer.setFillColor(Color.WHITE);
			OverlayDrawer.drawTileSubtile(rTile.getRow(), rTile.getCol(),
					SubTile.MM);
		}

		// do step
		if (route != null && route.size() > 1) {
			for (Entry<Tile, Aim> neig : AntBot.getGameI().getMoveAbleNeighbours(
					(antTile)).entrySet()) {
				if (route.size() > 1) {
					Tile a = neig.getKey();
					if (a != null) {
						if (a.equals(route.get(1))) {

							List<Tile> order = new LinkedList<Tile>();
							order.add(new Tile(neig.getKey().getRow(), neig
									.getKey().getCol()));
							ant.setRoute(order);
							AntBot.getLogger().log("Route is set: " + ant.getRoute());
							route.remove(1);
						}
					} else {
						// keine nachbarn vorhanden
					}
				}
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

}
