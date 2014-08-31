package de.htwg_konstanz.antbots.common_java_package.controller.state;

import java.util.List;
import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;

public class CollectFood implements State {

	Ant ant;
	private StateName stateName;

	public CollectFood(Ant a) {
		this.ant = a;
		stateName = StateName.CollectFood;
	}

	@Override
	public void changeState() {
		if (ant.isDanger()) {
			ant.setState(new Attack(ant));
			return;
		}
		if (AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)
				&& !ant.isDanger()) {
			return;
		}
		if (!ant.isDanger()
				&& !AntBot.getGameI().getFoodManager().getMarkedAnts()
						.containsKey(ant)
				&& AntBot.getGameI().getExplorerAnts() >= Configuration.EXPLORERANTSLIMIT) {
			ant.setState(new GoToBoarder(ant));
			return;
		}
		if (!ant.isDanger()
				&& !AntBot.getGameI().getFoodManager().getMarkedAnts()
						.containsKey(ant)
				&& AntBot.getGameI().getExplorerAnts() < Configuration.EXPLORERANTSLIMIT) {
			ant.setState(new Exploration(ant));
			return;
		}

	}

	@Override
	public void execute() {
		List<Tile> r = AntBot.getPathfinding().aStar(
				ant.getAntPosition(),
				AntBot.getGameI().getFoodManager().getMarkedAnts().get(ant)
						.getPosition());

		// da beim essen sammel nicht direkt das Tile besucht werden muss,
		// auf dem es liegt. Es reicht wenn man daneben steht.
		r.remove(r.size() - 1);
		if (r.size() > 1) {
			r.remove(0);
		}

		ant.setRoute(r);
		AntBot.getLogger().log("Route is set: " + ant.getRoute());

		OverlayDrawer.drawLine(ant.getRoute().get(0),
				ant.getRoute().get(ant.getRoute().size() - 1));
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
		if (AntBot.getGameI().getFoodManager().getMarkedAnts().containsKey(ant)) {
			AntBot.getGameI()
					.getFoodManager()
					.declineFood(
							AntBot.getGameI().getFoodManager().getMarkedAnts()
									.get(ant), ant);
		}
	}
}
