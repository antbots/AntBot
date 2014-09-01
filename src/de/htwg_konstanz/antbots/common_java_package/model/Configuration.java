package de.htwg_konstanz.antbots.common_java_package.model;

import de.htwg_konstanz.antbots.bots.AntBot;

public class Configuration {

	public static final int GROUPSIZE = 3;
	public static final int EXPLORERANTSLIMIT = 5;
	public static final int DANGERRADIUS = (int)Math.sqrt(AntBot.getGameI().getAttackRadius2()) +2;
	
	
}
