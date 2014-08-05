package de.htwg_konstanz.antbots.bots.lemke_bot1;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import de.htwg_konstanz.antbots.common_java_package.Aim;
import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.Bot;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Ilk;
import de.htwg_konstanz.antbots.common_java_package.Tile;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;

/**
 * Starter bot implementation. And some Tests 
 * 
 * @author Christian
 */
public class LemkeBot1 extends Bot {
	/**
	 * Main method executed by the game engine for starting the bot.
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		new LemkeBot1().readSystemInput();
	}

	/**
	 * For every ant check every direction in fixed order (N, E, S, W) and move
	 * it if the tile is Unoccupied.
	 */
	@Override
	public void doTurn() {
		GameInformations ants = gameStateInforamtions(); 
		
		
		// vis test
		Tile testTile = new Tile(3+ ants.getFoodTiles().size(),28);
		
		OverlayDrawer.setLayer(2);
		
		Tile test11 = new Tile(19,47);
		OverlayDrawer.setTileInfo(test11 , "hallo ich bin drauf" );
		System.out.println("asdasdadasdasdasdhgaszudgazsgdzagdzuags");
		
		//OverlayDrawer.drawArrow(test11, testTile);
		
		LinkedList<Aim> route = new LinkedList<Aim>();
		for(int i = 0; i<6;i++){
			route.add(Aim.EAST);
			route.add(Aim.EAST);
			route.add(Aim.EAST);
			route.add(Aim.SOUTH);
			route.add(Aim.WEST);
			route.add(Aim.WEST);
			route.add(Aim.WEST);
			route.add(Aim.SOUTH);
		}
		
		OverlayDrawer.drawCircle(testTile, 5, false);
		
		OverlayDrawer.drawRoutePlan(testTile, route);
		
		OverlayDrawer.drawTileSubtile(new Tile(0,0), SubTile.BR);
		OverlayDrawer.drawTileSubtile(new Tile(0,0), SubTile.BR);
		OverlayDrawer.drawTileSubtile(new Tile(0,0), SubTile.TR);
		OverlayDrawer.drawTileSubtile(new Tile(0,0), SubTile.TM);
		OverlayDrawer.drawTileSubtile(new Tile(0,0), SubTile.MM);

		for (Ant ant : ants.getMyAnts()) {
			
			Tile tileWithMyAnt = ant.getAntPosition();

			Aim direction = null;
			Ilk ilk = null;

			switch (new Random().nextInt(4)) {
			case 0:
				direction = Aim.NORTH;
				ilk = ants.getIlk(tileWithMyAnt, direction);
				if (ilk.isUnoccupied()) {
					ants.issueOrder(tileWithMyAnt, direction);
					OverlayDrawer.drawArrow(tileWithMyAnt, ants.getTile(tileWithMyAnt, direction));
					break;
				}
			case 1:
				direction = Aim.EAST;
				ilk = ants.getIlk(tileWithMyAnt, direction);
				if (ilk.isUnoccupied()) {
					ants.issueOrder(tileWithMyAnt, direction);
					OverlayDrawer.drawArrow(tileWithMyAnt, ants.getTile(tileWithMyAnt, direction));
					break;
				}
			case 2:
				direction = Aim.SOUTH;
				ilk = ants.getIlk(tileWithMyAnt, direction);
				if (ilk.isUnoccupied()) {
					ants.issueOrder(tileWithMyAnt, direction);
					OverlayDrawer.drawArrow(tileWithMyAnt, ants.getTile(tileWithMyAnt, direction));
					break;
				}
			case 3:
				direction = Aim.WEST;
				ilk = ants.getIlk(tileWithMyAnt, direction);
				if (ilk.isUnoccupied()) {
					ants.issueOrder(tileWithMyAnt, direction);
					OverlayDrawer.drawArrow(tileWithMyAnt, ants.getTile(tileWithMyAnt, direction));
					break;
				}

			}
		}
	}
}
