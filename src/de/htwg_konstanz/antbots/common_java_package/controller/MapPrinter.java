package de.htwg_konstanz.antbots.common_java_package.controller;

import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class MapPrinter {

	Logger logger;
	GameInformations gameI;
	
	public MapPrinter(GameInformations gameI, Logger logger) {
		this.logger = logger;
		this.gameI = gameI;
	}
	
	
	
	public void printMap() {
		Tile[][] map = gameI.getMap();
		
		for(int row = 0; row < map.length; row++) {
			for(int col = 0; col < map[row].length; col++) {
				Ilk type = map[row][col].getType();
				if(type == Ilk.WATER) {
					logger.logWithoutLineEnding("%");
				} else if ( type == Ilk.UNKNOWN) {
					logger.logWithoutLineEnding("x");
				} else if(type == Ilk.HILL) {
					logger.logWithoutLineEnding("_");
				} else if(type == Ilk.LAND) {
					logger.logWithoutLineEnding(".");
				} else if(type == Ilk.MY_ANT) {
					logger.logWithoutLineEnding("A");
				} else {
					logger.logWithoutLineEnding("0");
				}
				
			}
			logger.log("");
		}
	}
	
	
}
