package de.htwg_konstanz.antbots.common_java_package.controller.helper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.Logger;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class BreadthFirstSearch {

	GameInformations gameI;
	Logger logger;

	public BreadthFirstSearch(GameInformations gameI) {
		this.gameI = gameI;
		this.logger = gameI.getLogger();
	}

	/**
	 * 
	 * @param postion
	 *            position of the source
	 * @param targets
	 *            a set of Ants which will be used as a target
	 * @param onlyOnTarget
	 *            if ture = if found the first target the method will return if
	 *            false = the bsf searched for more targets
	 * @param step
	 *            if true = the search will be only to with the path costs given
	 *            by steps if flase = steps will be ignored
	 * @param steps
	 * @param visitableTiles
	 *            give a map which will be filed with the Tiles which where
	 *            visited by the bsf
	 * @return
	 */

	public Set<Tile> extendedBSF(Tile postion, Set<Tile> targets, boolean onlyOnTarget, boolean isStepUsed, int steps,	Set<Tile> visitableTiles) {

		Set<Tile> result = new HashSet<Tile>();
		Map<Tile, Integer> pathCosts = new HashMap<Tile, Integer>();
		Tile tmp;
		Queue<Tile> q = new LinkedList<Tile>();
		// visitableTiles = new HashSet<Tile>();

;
		q.add(postion);
			if (visitableTiles != null) {
				visitableTiles.add(postion);
			}

			pathCosts.put(postion, 0);


		while (!q.isEmpty()) {
			tmp = q.remove();

			for (Tile next : gameI.getNeighbour(tmp).keySet()) {

				pathCosts.put(next, pathCosts.get(tmp) + 1);

				// check water
				if (next.getType() != Ilk.WATER) {

					if (!q.contains(next)) {

						for (Tile pos : targets) {
							//Tile pos = target.getAntPosition();
							if (pos.getCol() == next.getCol()
									&& pos.getRow() == next.getRow()) {

								if (onlyOnTarget == true) {

									result.add(pos);

									return result;

								} else {
									result.add(pos);
								}

							}
						}
						// OverlayDrawer.setFillColor(Color.BLACK);
						// OverlayDrawer.drawTileSubtile(next.getRow(),
						// next.getCol(), SubTile.MM);
						if (isStepUsed == true && steps >= pathCosts.get(next)) {
							q.add(next);
							if (visitableTiles != null) {
								visitableTiles.add(next);
							}
						} else if (!isStepUsed) {
							q.add(next);
							if (visitableTiles != null) {
								visitableTiles.add(next);
							}
						}

					}
				}
			}

		}
		return result;
	}

	public Map<Tile, Set<Tile>> visitableFromSet(Map<Tile, Set<Tile>> position) {
		Set<Tile> visitableTiles = new HashSet<>();
		Map<Tile, Set<Tile>> ret = new HashMap<>();
		for (Entry<Tile, Set<Tile>> pos : position.entrySet()) {
			visitableTiles = visitableFromSet(pos.getKey(), pos.getValue());
			ret.put(pos.getKey(), visitableTiles);
		}
		return ret;
	}

	/**
	 * 
	 * @param postion
	 * @param x
	 * @return
	 */
	public Set<Tile> visitableFromSet(Tile postion, Set<Tile> visitableTiles) {

		Set<Tile> visitable = new HashSet<Tile>();
		Tile tmp;

		Queue<Tile> q = new LinkedList<Tile>();
		q.offer(postion);

		while (!q.isEmpty()) {
			tmp = q.remove();

			if (visitable.contains(tmp)) {
				continue;
			}

			visitable.add(tmp);

			for (Tile next : gameI.getNeighbour(tmp).keySet()) {
				if (visitableTiles.contains(next)) {

					// check water
					if (next.getType() != Ilk.WATER) {
						// check path cost

						if (!q.contains(next)) {
							q.offer(next);
						}

					}
				}

			}
		}
		return visitable;
	}

}
