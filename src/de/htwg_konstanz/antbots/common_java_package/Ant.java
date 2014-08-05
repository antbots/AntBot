package de.htwg_konstanz.antbots.common_java_package;


import java.util.LinkedList;


public class Ant {

	boolean danger = false;
	Tile position;
	Order order = null;
	private int weakness;
	private LinkedList<Ant> enemiesinAttackRadius;
	private Aim executedDirection;
	
	public Ant(Tile position) {
		this.position = position;
	}
	
	public Tile getAntPosition() {
		return position;
	}
	
	public void setPosition(int row, int column){
		position = new Tile(row,column);
	}
	
	public void setWeakness(int weakness){
		this.weakness = weakness;
	}
	
	public int getWeakness(){
		return weakness;
	}
	
	public void setexecutedDirection(Aim executedDirection){
		this.executedDirection = executedDirection;
	}
	
	public Aim getexecutedDirection(){
		return executedDirection;
	}
	
	@Override
    public String toString() {
        return position.getRow() + "," + position.getCol();
    }
	
	public void setEnemiesinAttackRadius(LinkedList<Ant> enemies){
		enemiesinAttackRadius = enemies;
	}
	public LinkedList<Ant> getEnemiesinAttackRadius(){
		return enemiesinAttackRadius;
	}
}
