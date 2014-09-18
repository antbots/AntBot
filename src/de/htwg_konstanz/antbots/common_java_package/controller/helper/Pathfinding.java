package de.htwg_konstanz.antbots.common_java_package.controller.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class Pathfinding {
	public GameInformations gameI;

	public Pathfinding(GameInformations gameI) {
		this.gameI = gameI;
	}

	/**
	 * A Star algorithm using manhattandistance for the heuristic. Using the
	 * graph from gameinformations.
	 * 
	 * @param start
	 * @param target
	 * @return
	 */
	public List<Tile> aStar(Tile source, Tile target) {

		return new AStarAlgorithm().aStar(source, target, 1);
	}

	/**
	 * A Star algorithm using manhattandistance for the heuristic.\n Using the
	 * graph from gameinformations.\n The weight speeds it up by. The heuristic
	 * is multiplied with the weight.
	 * 
	 * @param start
	 * @param target
	 * @param weight
	 * @return
	 */
	public List<Tile> aStar(Tile source, Tile target, int weight) {

		return new AStarAlgorithm().aStar(source, target, weight);
	}

	/**
	 * https://code.google.com/p/jianwikis/wiki/AStarAlgorithmForPathPlanning
	 * 
	 * @author Chrisi
	 * 
	 */
	private class AStarAlgorithm {

		private class AStarNode {
			private Tile node;

			// used to construct the path after the search is done
			private AStarNode cameFrom;

			// Distance from source along optimal path
			private int g;

			// Heuristic estimate of distance from the current node to the
			// target node
			private int h;

			public AStarNode(Tile source, int distanceSource, int distanceHeuristic) {
				node = source;
				g = distanceSource;
				h = distanceHeuristic;
			}

			// the sum of instance variables g and h
			public int getF() {
				return g + h;
			}

			public Tile getId() {
				return node;
			}

			public int getG() {
				return g;
			}

			public void setCameFrom(AStarNode x) {
				cameFrom = x;

			}

			public void setG(int g) {
				this.g = g;

			}

			public void setH(int h) {
				this.h = h;
			}

			public AStarNode getCameFrom() {
				return cameFrom;
			}

			public Tile getNode() {
				return node;
			}
		}

		private class AStarNodeComparator implements Comparator<AStarNode> {

			public int compare(AStarNode first, AStarNode second) {
				if (first.getF() < second.getF()) {
					return -1;
				} else if (first.getF() > second.getF()) {
					return 1;
				} else {
					return 0;
				}
			}
		}

		public List<Tile> aStar(Tile source, Tile target, int weight) {
			
			Map<Tile, AStarNode> openSet = new HashMap<Tile, AStarNode>();
			PriorityQueue<AStarNode> pQueue = new PriorityQueue<AStarNode>(20,	new AStarNodeComparator());
			Map<Tile, AStarNode> closeSet = new HashMap<Tile, AStarNode>();

			AStarNode start = new AStarNode(source, 0, gameI.getDistance(source, target));
			openSet.put(source, start);
			pQueue.add(start);

			AStarNode goal = null;
			while (openSet.size() > 0) {
				AStarNode current = pQueue.poll();
				

				if(current == null) {
					return null;
				} 

					
				
				openSet.remove(current.getNode());
				if (current.getNode().equals(target)) {
					// found
					goal = current;
					break;
				} else {
					closeSet.put(current.getNode(), current);

					// get neigbours of the current node
					Set<Tile> neighbors =  gameI.getMoveAbleNeighbours(current.getNode()).keySet();
									
					for (Tile neighbor : neighbors) {

						AStarNode visited = closeSet.get(neighbor);
						if (visited == null) {
							int g = current.getG() + weight * gameI.getDistance(current.getNode(), neighbor);

							AStarNode n = openSet.get(neighbor);
							if (n == null) {
								// not in the open set
								n = new AStarNode(neighbor, g, weight
										* gameI.getDistance(neighbor, target));
								n.setCameFrom(current);
								openSet.put(neighbor, n);
								pQueue.add(n);
							} else if (g < n.getG()) {
								// Have a better route to the current node,
								// change its parent
								n.setCameFrom(current);
								n.setG(g);
								n.setH(weight
										* gameI.getDistance(neighbor, target));
								openSet.put(neighbor, n);
							}
						}
					}
				}
			}

			// after found the target, start to construct the path
			if (goal != null) {
				List<Tile> list = new ArrayList<Tile>();
				list.add(goal.getNode());
				AStarNode parent = goal.getCameFrom();
				while (parent != null) {
					list.add(0, parent.getNode());
					parent = parent.getCameFrom();
				}
				return list;
			}
			return null;
		}
	}
}

