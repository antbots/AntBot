package de.htwg_konstanz.antbots.common_java_package.controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.state.InitState;
import de.htwg_konstanz.antbots.common_java_package.controller.state.State;
import de.htwg_konstanz.antbots.common_java_package.controller.state.StateName;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.common_java_package.model.settings.Missions;

public class Ant {

	private boolean danger = false;
	private Tile position;
	private Tile posBefore;
	private int weakness;
	private LinkedList<Ant> enemiesinAttackRadius;
	private Aim executedDirection;
	private List<Tile> route;
	private boolean isUsed = false;
	private State state;
	private StateName currentState;
	private int id;
	
	private Set<Ant> enemysInViewRadius = new HashSet<Ant>();


	public void doLogic(){
		state.changeState();
		state.execute();
	}
	
	public void move() {
		Tile next = route.remove(0);
		Map<Tile, Aim> neighbours = AntBot.getGameI()
				.getMoveAbleNeighbours(position);

		if (neighbours.containsKey(next)) {
			Aim aim = neighbours.get(next);
			AntBot.getGameI().issueOrder(position, aim);
			setPosition(next.getRow(), next.getCol());
		}
	}
	
	public void setState(State state1) {
		if(state != null){
			state.stateExit();
		}
        state=state1;
        state.stateEnter();
        currentState = state.getStateName();
    }
	
	public StateName getCurrentState(){
		return currentState;
	}
 
    public State getState() {
        return state;
    }

	public Ant(Tile position, int id) {
		setState(new InitState(this));
		this.position = position;
		this.id = id;;
	}

	public Tile getAntPosition() {
		return position;
	}
	
	public Tile getPosBefore() {
		return posBefore;
	}

	public void setPosition(int row, int col) {
		if(position != null) {
			posBefore = new Tile(position.getRow(), position.getCol());
		}
		
		
		position.setCol(col);
		position.setRow(row);
	}
	
	public void setPosition(Tile t) {
		if(position != null) {
			posBefore = new Tile(position.getRow(), position.getCol());
		}
		
		position.setCol(t.getCol());
		position.setRow(t.getRow());
	}

	public void setWeakness(int weakness) {
		this.weakness = weakness;
	}

	public int getWeakness() {
		return weakness;
	}

	public void setexecutedDirection(Aim executedDirection) {
		this.executedDirection = executedDirection;
	}

	public Aim getexecutedDirection() {
		return executedDirection;
	}

	public void setRoute(List<Tile> route) {
		this.route = route;
	}

	public List<Tile> getRoute() {
		return route;
	}

	@Override
	public int hashCode() {
		return position.getRow() * GameInformations.MAX_MAP_SIZE
				+ position.getCol();
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Ant) {
			Ant ant = (Ant) o;
			result = id == ant.id;

		}
		return result;
	}

	@Override
	public String toString() {
		return position.getRow() + "," + position.getCol();
	}

	public void setEnemiesinAttackRadius(LinkedList<Ant> enemies) {
		enemiesinAttackRadius = enemies;
	}

	public LinkedList<Ant> getEnemiesinAttackRadius() {
		return enemiesinAttackRadius;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean used) {
		isUsed = used;
	}

	public boolean isDanger() {
		return danger;
	}

	public void setDanger(boolean danger) {
		this.danger = danger;
	}

	public Set<Ant> getEnemysInViewRadius() {
		return enemysInViewRadius;
	}

	public void setEnemysInViewRadius(Set<Ant> enemysInRange) {
		enemysInViewRadius = enemysInRange;
	}

	public int getId() {
		return id;
	}
}

