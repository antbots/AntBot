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
import de.htwg_konstanz.antbots.common_java_package.model.Food;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class BreadthFirstSearch {

	GameInformations gameI;

	public BreadthFirstSearch(GameInformations gameI) {
		this.gameI = gameI;

	}

	
	
	public List<Ant> extendedBSF(Tile source, Set<Ant> targets,	boolean onlyOnTarget, boolean isStepUsed, int steps, Set<Tile> visitableTiles) {

		List<Ant> result = new LinkedList<Ant>();
		Map<Tile, Integer> pathCosts = new HashMap<Tile, Integer>();
		Tile tmp;
		Queue<Tile> q = new LinkedList<Tile>();

		if(visitableTiles != null) {
			visitableTiles.add(source);
		}
		
		pathCosts.put(source, 0);
		q.add(source);

		while (!q.isEmpty()) {
			tmp = q.remove();

			for (Tile next : gameI.getNeighbour(tmp).keySet()) {

				pathCosts.put(next, pathCosts.get(tmp) + 1);

				// check water
				if (next.getType() != Ilk.WATER) {

					if (!q.contains(next)) {

						for (Ant target : targets) {
							Tile pos = target.getAntPosition();
							if (pos.getCol() == next.getCol() && pos.getRow() == next.getRow()) {

								if (onlyOnTarget == true) {

									result.add(target);

									return result;

								} else {
									result.add(target);
								}

							}
						}
						// OverlayDrawer.setFillColor(Color.BLACK);
						// OverlayDrawer.drawTileSubtile(next.getRow(),
						// next.getCol(), SubTile.MM);
						if (isStepUsed == true && steps >= pathCosts.get(next)) {
							q.add(next);
							if(visitableTiles != null) {
								visitableTiles.add(next);
							}
						} else if (!isStepUsed) {
							q.add(next);
							if(visitableTiles != null) {
								visitableTiles.add(next);
							}
						}

					}
				}
			}

		}
		return result;
	}

	public Tile BSF(Tile source, Set<Tile> targets) {

		Tile tmp;
		Queue<Tile> q = new LinkedList<Tile>();

		q.add(source);

		while (!q.isEmpty()) {
			tmp = q.remove();

			for (Tile next : gameI.getNeighbour(tmp).keySet()) {

				// check water
				if (next.getType() != Ilk.WATER) {

					if (!q.contains(next)) {

						for (Tile target : targets) {

							if (target.getCol() == next.getCol()
									&& target.getRow() == next.getRow()) {

								return target;
							}

						}
					}

					q.add(next);

				}
			}
		}
		return null;
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
