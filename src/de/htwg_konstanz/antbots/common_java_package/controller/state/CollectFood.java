package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.awt.Color;
import java.util.List;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.boarder.BuildBoarder;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

public class CollectFood implements State {

	Ant ant;
	private StateName stateName;

	public CollectFood(Ant a) {
		this.ant = a;
		stateName = StateName.CollectFood;
	}

	@Override
	public void changeState() {
//		AntBot.debug().log("Ameise " + ant + " " + GameInformations.getFoodManager().getMarkedAnts().get(ant) + " " +GameInformations.getFoodManager().getMarkedAnts().containsKey(ant));
		if (AntBot.getAttackManager().getMarkedAnts().containsKey(ant)) {
			ant.setState(new Attack(ant));
			return;
		}
		if(AntBot.getEnemyHillManager().getAntsToHill().containsKey(ant)) {
			ant.setState(new AttackEnemyHill(ant));
			return;
		}
		if (GameInformations.getFoodManager().getMarkedAnts().containsKey(ant)	&& !ant.isDanger()) {
			return;
		}
		if ( !GameInformations.getFoodManager().getMarkedAnts().containsKey(ant)
				&& AntBot.getGameI().getExplorerAnts() >= Configuration.getExplorerAntsLimit() && BuildBoarder.marktAnts().contains(ant)) {
			ant.setState(new GoToBoarder(ant));
			return;
		}
		if ( !GameInformations.getFoodManager().getMarkedAnts().containsKey(ant)	&& (( AntBot.getGameI().getExplorerAnts() < Configuration.getExplorerAntsLimit() || BuildBoarder.getAreaAndBoarder() == null) || (BuildBoarder.getAreaAndBoarder() != null && !BuildBoarder.getAreaAndBoarder().containsKey(ant))))  {
			ant.setState(new Exploration(ant));
			return;
		}
		
//		AntBot.debug().log("COLLECT FOOD FAILD");

	}

	@Override
	public void execute() {
//		AntBot.debug().log("Colleect Food ant " + ant.getAntPosition() + " " + GameInformations.getFoodManager().getMarkedAnts().get(ant));
		List<Tile> r = AntBot.getPathfinding().aStar(ant.getAntPosition(),	GameInformations.getFoodManager().getMarkedAnts().get(ant));

		
		// da beim essen sammel nicht direkt das Tile besucht werden muss,
		// auf dem es liegt. Es reicht wenn man daneben steht.
		r.remove(r.size() - 1);
		if (r.size() > 1) {
			r.remove(0);
		}
		for (Tile t : r) {
			OverlayDrawer.setFillColor(Color.GREEN);
			OverlayDrawer.drawTileSubtile(t.getRow(), t.getCol(), SubTile.TR);
		}
		ant.setRoute(r);
		AntBot.getLogger().log("Route is set: " + ant.getRoute());

	}

	@Override
	public String toString() {
		return "CollectFood State";
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
//		if (AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)) {
//			AntBot.getGameI()
//					.getFoodManager()
//					.declineFood(
//							AntBot.getGameI().getFoodManager().getMarkedAnts()
//									.get(ant), ant);
//		}
	}
}
