package de.htwg_konstanz.antbots.common_java_package.attack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import de.htwg_konstanz.antbots.common_java_package.Aim;
import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Order;

/**
 * 
 * @author Felix
 */
public class MoveCommand implements Command {
	private LinkedList<Order> order;
	private LinkedList<Ant> ants;
	private Aim lastExecutedDirection;

	// Hier werden die Befehle der Ameisen zugeordnet und in einer Map
	// gespeichert.
	public MoveCommand(LinkedList<Order> order, LinkedList<Ant> ants) {
		this.ants = ants;
		this.order = order;
	}

	// Speichert die ausgeführte Richtung ab und versetzt die Ameise an die neue
	// Position
	@Override
	public void execute() {
		ants.forEach( a -> {Optional<Order> matchedOrder = order.stream().filter(o -> a.getAntPosition().equals(o.getPosition())).findAny();
			lastExecutedDirection = a.getexecutedDirection();
			a.setexecutedDirection(matchedOrder.get().getDirection());
			a.setPosition(matchedOrder.get().getNewPosition());
		});
	}

	// Weist der Ameise die letzte durchgeführte Richtung zu und versetzt sie
	// wieder zurück
	@Override
	public void undo() {
		ants.forEach(a -> {a.setexecutedDirection(lastExecutedDirection);
				a.setPosition(a.getPosBefore().getRow(), a.getPosBefore().getCol());});
	}
}
