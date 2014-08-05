package de.htwg_konstanz.antbots.visualizer;

import java.awt.Color;
import java.io.PrintStream;
import java.util.List;

import de.htwg_konstanz.antbots.common_java_package.Aim;
import de.htwg_konstanz.antbots.common_java_package.Tile;


/**
 * Diese Kalasse funktioniert als Schnittstelle zwischen dem eigenem Bot und dem
 * vis_overlay_visualizer. Der vis_overlay_visualizer gibt es unter
 * https://github.com/j-h-a/aichallenge. Er ergänzt den originalen Visualizer um
 * einige Funktionen. Mit ihm ist es unter anderem möglich Pfeile auf die Karte
 * zu zeichen.
 * 
 * 
 * @author Christian
 * 
 */
public class OverlayDrawer {

	public enum SubTile {
		TL("TL"), TM("TM"), TR("TR"), ML("ML"), MM("MM"), MR("MR"), BL("BL"), BM(
				"BM"), BR("BR");

		private String symbol;

		SubTile(String symbol) {
			this.symbol = symbol;
		}

		@Override
		public String toString() {
			return symbol;
		}

	}

	static PrintStream o = System.out;

	/**
	 * setLineColour and setFillColour set the colour values for all
	 * line-drawing and filled area drawing commands. Colours are specified as
	 * integers for r, g, and b (from 0-255) and the alpha value a is a float
	 * (from 0.0-1.0).
	 * 
	 * TODO alpha is not working
	 * 
	 * @param color
	 */
	public static void setFillColor(Color color) {
		// v setFillColor r g b a
		float a = color.getAlpha() / 255;
		
		// TODO alpha is not working
		a = 1;
		o.printf("v setFillColor %d %d %d %.1f\n", color.getRed(),
				color.getGreen(), color.getBlue(), a);
	}

	/**
	 * setLineColour and setFillColour set the colour values for all
	 * line-drawing and filled area drawing commands. Colours are specified as
	 * integers for r, g, and b (from 0-255) and the alpha value a is a float
	 * (from 0.0-1.0).
	 * 
	 * @param color
	 */
	public static void setLineColour(Color color) {
		// v setLineColour r g b a
		o.printf("v setLineColour %d %d %d %d\n", color.getRed(),
				color.getGreen(), color.getBlue(), color.getAlpha());
	}

	/**
	 * setLayer sets the current visualization layer. Layer 0 is drawn on top of
	 * the map, behind the ants. Layer 1 is drawn on top of the ants and behind
	 * the fog. Layer 2 is drawn on top of the fog. The default is 1.
	 * 
	 * @param layer
	 */
	public static void setLayer(int layer) {
		// v setLayer layer
		o.printf("v setLayer %d\n", layer);
	}

	/**
	 * setLineWidth sets the with for all line-drawing commands. The line width
	 * w is in screen-units (not map-units).
	 * 
	 * @param width
	 */
	public static void setLineWidth(int width) {
		// v setLineWidth w
		o.printf("v setLineWidth %d\n", width);
	}

	/**
	 * arrow will draw the shortest path between the specified points, taking
	 * map-wrapping at the edges into account. arrow draws a line with an
	 * arrow-head at the end. The arrow-head length will be the smaller of two
	 * map-squares or half the line length.
	 * 
	 * @param start
	 * @param end
	 */
	public static void drawArrow(Tile start, Tile end) {
		// v arrow row1 col1 row2 col2
		o.printf("v arrow %d %d %d %d\n", start.getRow(), start.getCol(),
				end.getRow(), end.getCol());
	}

	public static void drawCircle(Tile t, int radius, boolean fill) {
		// v circle row col radius fill
		o.printf("v circle %d %d %d %s\n", t.getRow(), t.getCol(), radius, fill);
	}

	/**
	 * line will draw the shortest path between the specified points, taking
	 * map-wrapping at the edges into account.
	 * 
	 * @param start
	 * @param end
	 */
	public static void drawLine(Tile start, Tile end) {
		// v line row1 col1 row2 col2
		o.printf("v line %d %d %d %d\n", start.getRow(), start.getCol(),
				end.getRow(), end.getCol());
	}

	public static void drawRect(Tile start, int width, int height, boolean fill) {
		// v rect row col width height fill
		o.printf("v rect %d %d %d %d %s\n", start.getRow(), start.getCol(),
				width, height, fill);
	}

	/**
	 * The plan-string for the routePlan command is a case-insensitive sequence
	 * of direction characters, for example NNEENNWWWWSS. It draws a line using
	 * the current line width and colour from the starting row and column along
	 * the planned route.
	 * 
	 * @param start
	 * @param aimRoutePlan
	 */
	public static void drawRoutePlan(Tile start, List<Aim> aimRoutePlan) {
		String planString = "";
		for (Aim aim : aimRoutePlan) {
			planString += Character.toUpperCase(aim.getSymbol());
		}

		// v routePlan row col plan-string
		o.printf("v routePlan %d %d %s\n", start.getRow(), start.getCol(),
				planString);
	}

	/**
	 * star draws a star centered at (row, col) with points. inner_radius and
	 * outer_radius control the size of the star and points.
	 * 
	 * @param a
	 * @param b
	 */
	public static void drawStar(Tile a, int inner_radius, int outer_radius,
			String points, boolean fill) {
		// v star row col inner_radius outer_radius points fill
		o.printf("v star %d %d %d %d %s %s\n", a.getRow(), a.getCol(),
				inner_radius, outer_radius, points, fill);
	}

	/**
	 * tile draws a filled rectangle over the specified map-square. The same
	 * effect can be achieved with rect, but this is much easier if you want to
	 * exactly cover one square. Be sure to use a transparent fill-colour unless
	 * you want to completely block out the actual game square.
	 * 
	 * @param t
	 */
	public static void drawTile(Tile t) {
		// v tile row col
		drawTile(t.getRow(), t.getCol());
	}

	public static void drawTile(int rows, int cols) {
		// v tile row col
		o.printf("v tile %d %d\n", rows, cols);
	}

	/**
	 * tileBorder and tileSubTile: Imagine each tile (map-square) is divided
	 * into nine sub-tiles like a naughts-and-crosses board, the subtile
	 * parameter is a combination of Top-Middle-Bottom and Left-Middle-Right to
	 * define which of the nine sub-tiles you want to draw. It should be one of:
	 * TL, TM, TR, ML, MM, MR, BL, BM, BR. tileSubTile fills the specified
	 * sub-tile while tileBorder draws a line around the edge of the tile at the
	 * specified sub-tile location, or around the whole tile if subtile is MM.
	 * 
	 * @param t
	 * @param b
	 */
	public static void drawTileBorder(Tile t, OverlayDrawer.SubTile subTile) {
		// v tileBorder row col subtile
		o.printf("v tileBorder %d %d %s\n", t.getRow(), t.getCol(), subTile);
	}

	/**
	 * tileBorder and tileSubTile: Imagine each tile (map-square) is divided
	 * into nine sub-tiles like a naughts-and-crosses board, the subtile
	 * parameter is a combination of Top-Middle-Bottom and Left-Middle-Right to
	 * define which of the nine sub-tiles you want to draw. It should be one of:
	 * TL, TM, TR, ML, MM, MR, BL, BM, BR. tileSubTile fills the specified
	 * sub-tile while tileBorder draws a line around the edge of the tile at the
	 * specified sub-tile location, or around the whole tile if subtile is MM.
	 * 
	 * @param t
	 * @param b
	 */
	public static void drawTileSubtile(Tile t, OverlayDrawer.SubTile subTile) {
		// v tileSubtile row col subtile
		drawTileSubtile(t.getRow(), t.getCol(), subTile);
	}

	public static void drawTileSubtile(int row, int col,
			OverlayDrawer.SubTile subTile) {
		// v tileSubtile row col subtile
		o.printf("v tileSubtile %d %d %s\n", row, col, subTile);
	}

	/**
	 * The i command adds map-information to a specific tile on the current
	 * turn. In the visualizer move your mouse over the tile to see the string
	 * you specified. If you specify this command more than once for the same
	 * row and column on any turn, the additional strings will be appended on a
	 * new line.
	 * 
	 * Only works in the Browser
	 * 
	 * @param t
	 * @param text
	 */
	public static void setTileInfo(Tile t, String text) {
		// i row col rest-of-line-is-any-string-you-like
		setTileInfo(t.getRow(), t.getCol(), text);
	}

	public static void setTileInfo(int row, int col, String text) {
		// i row col rest-of-line-is-any-string-you-like
		o.printf("i %d %d %s\n", row, col, text);
	}

}
