package de.htwg_konstanz.antbots.visualizer;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

/**
 * This Class is responsible for the visualization of the exploration.
 * 
 * @author Chrisi
 * 
 */
public class ExplorationVisualizer {

	public static final Color EXPLORATION_COLOR = Color.BLACK;

	/**
	 * Must be called in every new Turn after an aferUpdate. TODO This could
	 * also be possible with the Observer pattern. Also nonstatic is possible.
	 */
	public static void visualizeNewStep(GameInformations gameI) {
		// color all Tiles

		Set<Tile> toDraw = new HashSet<Tile>();

		int viewRadius = (int) Math.sqrt(gameI.getViewRadius2());
		int notInViewRadius = (int) Math.sqrt(gameI.getViewRadius2()) + 3;

		for (Ant ant : gameI.getMyAnts()) {
			toDraw.addAll(gameI.getTilesInRadius(ant.getAntPosition(),
					notInViewRadius));
		}

		for (Ant ant : gameI.getMyAnts()) {
			toDraw.removeAll(gameI.getTilesInRadius(ant.getAntPosition(),
					viewRadius));
		}

		// draw
		for (Tile tile : toDraw) {
			if (gameI.getIlk(tile).equals(Ilk.WATER))
				continue;

			Color c = getExplorationFillColor(tile, gameI);
			if (c != null) {
				OverlayDrawer.setFillColor(c);
				OverlayDrawer.drawTile(tile);
			}
		}
	}

	/**
	 * Returns the color
	 * 
	 * @param tile
	 * @param gameI
	 * @return
	 */
	private static Color getExplorationFillColor(Tile tile,
			GameInformations gameI) {
		int value = 0;

		// visible
		// if(tile.getCountExplore() == 0)
		if (1 == 0)
			return null;

		// unknown
		if (gameI.getVisibilityAgo(tile) >= gameI.getTurns())
			return null;

		// draw
		int colorchange = 50;
		value = (int) (((float) gameI.getVisibilityAgo(tile) / (float) gameI.getTurns()) * colorchange);

		Color groundColor = new Color(121, 90, 59);

		int red = groundColor.getRed() - value ;
		int green = groundColor.getGreen() - value ;
		int blue = groundColor.getBlue() - value ;

		Color color = new Color(red, green, blue);

		return color;
	}

}
