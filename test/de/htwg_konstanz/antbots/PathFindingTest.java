package de.htwg_konstanz.antbots;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.htwg_konstanz.antbots.bots.kartentest_bot.DummyBot;
import de.htwg_konstanz.antbots.bots.lemke_bot1.ExplorerBot1;
import de.htwg_konstanz.antbots.common_java_package.Bot;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Ilk;
import de.htwg_konstanz.antbots.common_java_package.Tile;

/**
 * 
 */

/**
 * @author Chrisi
 * 
 */
public class PathFindingTest {

	public class TestBot extends Bot {

		@Override
		public void doTurn() {

		}
	}
	
	private ExplorerBot1 explorerBot1;
	private GameInformations gameI;

	/**
	
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Bot testBot = new TestBot();
		
		String map1 = "m ...%%%%%..\n" +
				"m ..%%...%..\n" +
				"m .%...%.%..\n" +
				"m .%.%%%.%..\n" +
				"m .%.%...%..\n" +
				"m .%.%.a.%..\n" +
				"m .%.%...%..\n" +
				"m .%.%%%%%..\n" +
				"m .%....... \n" +
				"m .%........";
		
		String[] split = map1.split("\n");
		List<String> input = new LinkedList(Arrays.asList(split));
		testBot.setup(10, 5, 10, 10, 200, 77, 2, 10);
		testBot.parseUpdate(input);
		
		gameI = testBot.gameStateInforamtions();
		gameI.setVision();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMapInit(){
		assertTrue("no water", gameI.getIlk(new Tile(1,3)) == Ilk.WATER);
	}
	
	/**
	 * Test method for
	 * {@link de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding#visitableInXSteps(de.htwg_konstanz.antbots.common_java_package.Tile, de.htwg_konstanz.antbots.common_java_package.Tile[][], int)}
	 * .
	 */
	@Test
	public void testVisitableInXSteps() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding#searchShortestPath(de.htwg_konstanz.antbots.common_java_package.Tile, de.htwg_konstanz.antbots.common_java_package.Tile, java.util.Set)}
	 * .
	 */
	@Test
	public void testSearchShortestPathTileTileSetOfTile() {
//		fail("Not yet implemented");
	}
	
}
