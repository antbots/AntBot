/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htwg_konstanz.antbots.common_java_package.helper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import de.htwg_konstanz.antbots.common_java_package.Aim;
import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.Order;

/**
 *
 * @author Felix
 */
public class MoveCommand implements Command{
    private Map<Ant,Order> orders;
    private LinkedList<Ant> ants;
    private Aim lastExecutedDirection;


    // Hier werden die Befehle der Ameisen zugeordnet und in einer Map gespeichert.
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


    // Speichert die ausgeführte Richtung ab und versetzt die Ameise an die neue Position
    @Override
    public void execute() {
		for (Ant a : ants) {
			lastExecutedDirection = a.getexecutedDirection();
			a.setexecutedDirection(orders.get(a).getDirection());
			a.setPosition(a.getAntPosition().getRow() + orders.get(a).getDirection().getRowDelta(), a.getAntPosition().getCol() + orders.get(a).getDirection().getColDelta());
		}
    }

    // Weist der Ameise die letzte durchgeführte Richtung zu und versetzt sie wieder zurück
    @Override
    public void undo() {
			for (Ant a : ants) {
				a.setexecutedDirection(lastExecutedDirection);
				a.setPosition(orders.get(a).getPosition().getRow(), orders.get(a).getPosition().getCol());
			}
    }
}
