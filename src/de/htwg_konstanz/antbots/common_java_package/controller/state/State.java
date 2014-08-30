package de.htwg_konstanz.antbots.common_java_package.controller.state;

import de.htwg_konstanz.antbots.common_java_package.controller.Ant;

public interface State {
	public void execute();
	void changeState();
	StateName getStateName();
}
