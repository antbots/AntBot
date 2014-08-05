package de.htwg_konstanz.antbots;
import static org.junit.Assert.*;

import org.junit.Test;

import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Tile;


public class distance {

	private int viewRadius2;
	private GameInformations gameInformations;

	
	
	@Test
	public void test() {
		int width = 100;
		int height =100;
		gameInformations = new GameInformations(10, 10, width, height, 100, viewRadius2, 40, 40);
		
		int distance = gameInformations.getDistance(new Tile(0, 0), new Tile(1,2));
		assertEquals(3, distance);
		
		distance = gameInformations.getDistance(new Tile(0, 0), new Tile(50,0));
		assertEquals(50, distance);
		
		distance = gameInformations.getDistance(new Tile(0, 0), new Tile(50,2));
		assertEquals(52, distance);
		
		distance = gameInformations.getDistance(new Tile(0, 0), new Tile(width-1,0));
		assertEquals(1, distance);
		
	}

}
