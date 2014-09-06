package de.htwg_konstanz.antbots.common_java_package.controller.attack;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;


/**
 * 
 * @author Felix
 */
public class MoveCommand implements Command {
	private HashMap<Ant, Order> orders;
	private int numOfMyDeadAnts;
	private int numOfEnemyDeadAnts;
	private LinkedList<Ant> myDeadAnts;
	private LinkedList<Ant> enemyDeadAnts;
	private LinkedList<Ant> ants;
	private LinkedList<Ant> enemyAnts;
	private AlphaBeta ab;
	private boolean min = false;
	private int directionPoints = 0;

	// Hier werden die Befehle der Ameisen zugeordnet und in einer Map
	// gespeichert.
	public MoveCommand(LinkedList<Order> order, LinkedList<Ant> ants, LinkedList<Ant> enemyAnts, AlphaBeta ab, boolean min) {
		this.min = min;
		this.ants = ants;
		this.enemyAnts = enemyAnts;
		this.orders = new HashMap<Ant, Order>();
		this.myDeadAnts = new LinkedList<Ant>();
		this.enemyDeadAnts = new LinkedList<Ant>();
        for (Order o : order) {
			for (Ant a : ants) {
				if(a.getAntPosition().getCol() == o.getPosition().getCol() && a.getAntPosition().getRow() == o.getPosition().getRow()){
					orders.put(a,o);
				}
			}
		}
        this.ab = ab;
	}

	// Speichert die ausgeführte Richtung ab und versetzt die Ameise an die neue
	// Position
	@Override
	public void execute() {
		ants.forEach(a -> {
			a.setPosition(orders.get(a).getNewPosition());
			a.setexecutedDirection(orders.get(a).getDirection());});
		if(min){
			directionPoints = directionPoints(1);
			ab.setDirectionPoint(directionPoints);
			
			for (Ant ant : ants) {
				enemies(ant, enemyAnts);
			}
			for (Ant ant : enemyAnts) {
				enemies(ant,ants);
			}
			
			numOfEnemyDeadAnts=calculateDeadAnts(enemyAnts, enemyDeadAnts);
			numOfMyDeadAnts=calculateDeadAnts(ants, myDeadAnts);
			
			ab.setEnemyDeadAnts(numOfMyDeadAnts);		// enemyDeadAnts
			ab.setMyDeadAnts(numOfEnemyDeadAnts);	// myDeadAnts
		}
	}

	// Weist der Ameise die letzte durchgeführte Richtung zu und versetzt sie
	// wieder zurück
	@Override
	public void undo() {
		if(min){
			ab.setDirectionPoint(-directionPoints);
			ab.setEnemyDeadAnts(-numOfMyDeadAnts);		// enemyDeadAnts
			ab.setMyDeadAnts(-numOfEnemyDeadAnts);	// myDeadAnts
			
			for(Ant a : enemyDeadAnts){
				enemyAnts.add(a);
			}
			
			for(Ant a : myDeadAnts){
				ants.add(a);
			}
		}
		ants.forEach(a -> {a.setPosition(orders.get(a).getPosition());});
	}
	
	private int calculateDeadAnts(LinkedList<Ant> myAntsToGo, LinkedList<Ant> deadAnts) {
		LinkedList<Ant> antsToRemove = new LinkedList<>();
		int numOfDeadAnts = 0;
		for (Ant ant : myAntsToGo){
			LinkedList<Ant> enemiesInAttackRadius = ant.getEnemiesinAttackRadius();
			for (Ant enemy : enemiesInAttackRadius) {
				if(ant.getWeakness() >= enemy.getWeakness()){
					deadAnts.add(ant);
					numOfDeadAnts++;
					antsToRemove.add(ant);
					break;
				}
			}
		}
		for(Ant a: antsToRemove){
			myAntsToGo.remove(a);
		}
		AntBot.getLogger().log(Integer.toString(numOfDeadAnts));
		return numOfDeadAnts;
	}
	
	private void enemies(Ant ant, LinkedList<Ant> enemyAnts){
		int weakness = 0;
		LinkedList<Ant> enemiesInAttackRadius = new LinkedList<Ant>();
		Set<Tile> attackRadiusTiles= ab.getBoard().getTilesInAttackRadius(ant.getAntPosition(), (int)Math.sqrt(ab.getBoard().getAttackRadius2()));
		for (Tile tile : attackRadiusTiles) {
			for (Ant enemy : enemyAnts) {
				if(tile.getRow() == enemy.getAntPosition().getRow() && tile.getCol() == enemy.getAntPosition().getCol()){
					weakness++;
					enemiesInAttackRadius.add(enemy);
				}
			}
			
		}
		ant.setWeakness(weakness);
		ant.setEnemiesinAttackRadius(enemiesInAttackRadius);
	}
	
	private int directionPoints(int increase){
		int points = 0;
		for (Ant ant : enemyAnts) {
			if(!ants.isEmpty()){
				for (Aim aim : ab.getBoard().getDirections(ant.getPosBefore(), ants.get((int)((Math.random()) * ants.size()-1 + 0)).getAntPosition())) {
					if(ant.getexecutedDirection() == aim){
						AntBot.getLogger().log("increase");
						points = points + increase;
						break;
					}
				}
			}else{
				for (Aim aim : ab.getBoard().getDirections(ant.getPosBefore(), ants.get((int)((Math.random()) * ants.size()-1 + 0)).getAntPosition())) {
					if(ant.getexecutedDirection() == aim){
						AntBot.getLogger().log("increase");
						points = points + increase;
						break;
					}
				}
			}
		}
		return points;
	}
}
