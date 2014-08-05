package de.htwg_konstanz.antbots.common_java_package;

import java.util.LinkedList;
import java.util.List;

import de.htwg_konstanz.antbots.common_java_package.settings.Missions;

public class Ant {

	boolean danger = false;
	private Tile position;
	private Tile posBefore;
	private int weakness;
	private LinkedList<Ant> enemiesinAttackRadius;
	private Aim executedDirection;
	private Missions mission = Missions.NON;
	private List<Tile> routeForMission;
	private boolean isUsed = false;

	public Ant(Tile position) {
		this.position = position;
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

	public void setMission(Missions mission) {
		this.mission = mission;
	}

	public Missions getMission() {
		return mission;
	}

	public void setRoute(List<Tile> route) {
		this.routeForMission = route;
	}

	public List<Tile> getRoute() {
		return routeForMission;
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
			result = position.equals(ant.getAntPosition());

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
}

