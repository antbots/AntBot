package de.htwg_konstanz.antbots.common_java_package.model;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.attack.AlphaBeta;

public class Configuration {



	public static final int DANGERRADIUS = (int)Math.sqrt(AntBot.getGameI().getAttackRadius2()) + 3 ;
	
	// Angriffsalgorithmus
	public static final AlphaBeta.Strategy ATTACKSTRATEGY = AlphaBeta.Strategy.NEUTRAL;
	public static final int ATTACKSEARCHDEPTH = 2; // muss durch 2 teilbar sein
	public static final int GROUPSIZE = 3;
	
	//angriff auf gegnerischen Ameisenhügel
	public static final int ANTSINGROUPTOENEMYHILL = 3;
	public static final int RADIUSTOENEMYHILL = 15;
	
	//Verteidigung eigener Ameisenhügel
	public static final int DEFENDANTS = 3;
	public static final int LIMITWHENDEFENDANTSAREORDERD = 10;
	
	//Boardergroesse
	public static final int BOARDDISTANCE = 15;
	
	//CollectFoodRadius
	public static final int COLLECTFOODRADIUS = 15;
	
	
	
	public static int getExplorerAntsLimit(){
		int size = AntBot.getGameI().getMyAnts().size();

		return (int) (size * 0.25)+10;
	}
	
	
}
