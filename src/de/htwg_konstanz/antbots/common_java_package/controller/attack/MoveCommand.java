package de.htwg_konstanz.antbots.common_java_package.controller.attack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Order;

/**
 * 
 * @author Felix
 */
public class MoveCommand implements Command {
	private HashMap<Ant, Order> orders;
	private LinkedList<Ant> ants;

	// Hier werden die Befehle der Ameisen zugeordnet und in einer Map
	// gespeichert.
	public MoveCommand(LinkedList<Order> order, LinkedList<Ant> ants) {
		this.ants = ants;
		this.orders = new HashMap<Ant, Order>();
        for (Order o : order) {
			for (Ant a : ants) {
				if(a.getAntPosition().getCol() == o.getPosition().getCol() && a.getAntPosition().getRow() == o.getPosition().getRow()){
					orders.put(a,o);
				}
			}
		}
	}

	// Speichert die ausgeführte Richtung ab und versetzt die Ameise an die neue
	// Position
	@Override
	public void execute() {
		ants.forEach(a -> {
			a.setPosition(orders.get(a).getNewPosition());
			a.setexecutedDirection(orders.get(a).getDirection());});
	}

	// Weist der Ameise die letzte durchgeführte Richtung zu und versetzt sie
	// wieder zurück
	@Override
	public void undo() {
		ants.forEach(a -> {a.setPosition(orders.get(a).getPosition());});
	}
}
