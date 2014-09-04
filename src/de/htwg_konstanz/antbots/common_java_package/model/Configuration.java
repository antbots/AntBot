package de.htwg_konstanz.antbots.common_java_package.model;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.AlphaBeta;

public class Configuration {

	// Zustand
	public static final int EXPLORERANTSLIMIT = 5;
	public static final int DANGERRADIUS = (int)Math.sqrt(AntBot.getGameI().getAttackRadius2()) +2;
	
	// Angriffsalgorithmus
	public static final AlphaBeta.Strategy ATTACKSTRATEGY = AlphaBeta.Strategy.PASSIVE;
	public static final int ATTACKSEARCHDEPTH = 3;
	public static final int GROUPSIZE = 3;
	
	//angriff auf gegnerischen Ameisenhügel
	public static final int ANTSINGROUPTOENEMYHILL = 3;
	
	//Verteidigung eigener Ameisenhügel
	public static final int DEFENDANTS = 3;
	
	
	
}
