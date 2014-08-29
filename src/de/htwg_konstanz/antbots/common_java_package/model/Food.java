package de.htwg_konstanz.antbots.common_java_package.model;

import de.htwg_konstanz.antbots.common_java_package.controller.Ant;

public class Food {

	private Tile position;
	private boolean inDemand;
	private Ant consumer;
	private boolean isAlive;
	
	public Food(Tile position) {
		this.position = position;
		this.inDemand = false;
		this.consumer = null;
		isAlive = false;
	}

	public Tile getPosition() {
		return position;
	}

	public void setPosition(Tile position) {
		this.position = position;
	}

	public boolean isInDemand() {
		return inDemand;
	}

	public void setInDemand(boolean inDemand) {
		this.inDemand = inDemand;
	}

	public Ant getConsumer() {
		return consumer;
	}

	public void setConsumer(Ant consumer) {
		this.consumer = consumer;
	}

	public void setAlive(boolean b) {
		isAlive = b;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	
	
}
