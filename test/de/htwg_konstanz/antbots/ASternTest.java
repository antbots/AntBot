package de.htwg_konstanz.antbots;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import org.junit.Test;

import de.htwg_konstanz.antbots.common_java_package.Bot;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;

import de.htwg_konstanz.antbots.common_java_package.Tile;
import de.htwg_konstanz.antbots.common_java_package.helper.Pathfinding;

public class ASternTest {

	public class TestBot extends Bot {

		@Override
		public void doTurn() {

		}
	}

	private GameInformations gameI;
	private Pathfinding pathfinding;
	private Tile start;
	private Tile end;

	@Before
	public void setUp() throws Exception {

		Bot testBot = new TestBot();

		File mapFile = new File(
				"test/de/htwg_konstanz/antbots/maps/cell_maze_p02_06.map");
//		 mapFile = new File("test/de/htwg_konstanz/antbots/maps/cell_maze_p02_13.map");
//		 mapFile = new File("test/de/htwg_konstanz/antbots/maps/cell_maze_p09_03.map");

		assertTrue("no File", mapFile.isFile());

		String mapString = readFile(mapFile);

		String[] split = mapString.split("\n");
		int rows = 0;
		int cols = 0;
		// find map start
		for (int i = 0; i < split.length; i++) {
			String l = split[i];
			if (l.startsWith("rows "))
				rows = Integer.parseInt(l.substring("rows ".length()));
			if (l.startsWith("cols "))
				cols = Integer.parseInt(l.substring("cols ".length()));
		}
		System.out.println("rows " + rows + " " + "cols " + cols);

		List<String> input = new LinkedList(Arrays.asList(split));
		testBot.setup(10, 5, rows, cols, 200, 77, 2, 10);
		testBot.parseUpdate(input);

		gameI = testBot.gameStateInforamtions();

		gameI.initWholeMap(split);

		gameI.setVision();

		start = gameI.getMyHills().iterator().next();
		end = gameI.getEnemyHills().iterator().next();

		pathfinding = new Pathfinding(gameI);
	}

	@Test
	public void testAStar() {
		// aStar
		List<Tile> aStar = pathfinding.aStar(start, end, 1);
		assertNotNull(aStar);
		assertEquals(start, aStar.get(0));
		assertEquals(end, aStar.get(aStar.size() - 1));
	}

	@Test
	public void testDjikstra() {

		// djikstra
		List<Tile> djikstra = pathfinding.searchShortestPath(gameI.getMyHills()
				.iterator().next(), end, gameI.getMapSet());
		assertNotNull(djikstra);
		assertEquals(start, djikstra.get(0));
		assertEquals(end, djikstra.get(djikstra.size() - 1));

	}

	@Test
	public void testCompare() {
		List<Tile> djikstra = pathfinding.searchShortestPath(gameI.getMyHills()
				.iterator().next(), end, gameI.getMapSet());
		List<Tile> aStar = pathfinding.aStar(start, end);

		assertEquals(djikstra.size(), aStar.size());

		System.out.println("start: " + start + "\t" + "end: " + end + "\t"
				+ "routeSize: " + aStar.size());
		System.out.println("djikstra " + djikstra.size() + "\t\t" + djikstra);
		System.out.println("aStar " + aStar.size() + "\t\t" + aStar);
	}

	private String readFile(File file) {
		FileInputStream fis = null;
		String s = "";
		try {
			fis = new FileInputStream(file);

			int content;
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				// System.out.print((char) content);
				s += (char) content;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return s;
	}

}
