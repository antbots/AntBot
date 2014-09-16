package de.htwg_konstanz.antbots.common_java_package.controller.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.Logger;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;

public class Pathfinding {
	public Logger logger;
	public GameInformations gameI;

	public Pathfinding(GameInformations gameI) {
		this.logger = gameI.getLogger();
		this.gameI = gameI;
	}

	/**
	 * Breitensuche
	 * 
	 * @param postion
	 * @param map
	 * @param visited
	 * @return
	 */
	public Set<Tile> visitableInXSteps(Tile postion, int x) {
		HashMap<Tile, Integer> pathCosts = new HashMap<Tile, Integer>();
		Set<Tile> visitable = new HashSet<Tile>();
		Tile tmp;

		Queue<Tile> q = new LinkedList<Tile>();
		pathCosts.put(postion, 0);
		q.offer(postion);

		while (!q.isEmpty()) {
			tmp = q.remove();

			if (visitable.contains(tmp)) {
				continue;
			}

			visitable.add(tmp);

			for (Tile next : gameI.getNeighbour(tmp).keySet()) {
				// increase pathcost
				pathCosts.put(next, pathCosts.get(tmp) + 1);

				// check water
				if (next.getType() != Ilk.WATER) {
					// check path cost
					if (x >= pathCosts.get(next)) {

						if (!q.contains(next)) {
							q.offer(next);
						}
					}
				}
			}
		}
		return visitable;
	}

	/**
	 * A
	 * 
	 * @param graph
	 *            *
	 * @param startknoten
	 * @param zielknoten
	 * @return
	 */
	public boolean searchShortestPath(Tile start, Tile end, List<Tile> graph,
			Tile[] parent, int[] distance) {
		Map<Integer, Tile> kl = new HashMap<Integer, Tile>();

		for (int i = 0; i < graph.size(); i++) {
			distance[i] = Integer.MAX_VALUE;
			parent[i] = null;
		}

		int startpos = graph.indexOf(start);
		distance[startpos] = 0;

		kl.put(startpos, start);

		while (!kl.isEmpty()) {
			// TODO getmin

			Tile tmp = kl.remove(getMinValueIndex(distance, kl));
			// logger.log("get element " + tmp.toString() + " ");
			if (tmp.equals(end)) {
				logger.log("ende gefunden vorgänger von " + end.toString()
						+ " is " + parent[graph.indexOf(end)]);

				return true;
			}

			for (Tile i : gameI.getNeighbour(tmp).keySet()) {
				if (graph.contains(i)) {
					// logger.log("drinne");
					if (distance[graph.indexOf(i)] == Integer.MAX_VALUE) {
						kl.put(graph.indexOf(i), i);
						// logger.log(tmp.toString() + " vorgänger von " +
						// i.toString());
					}
					if (distance[graph.indexOf(tmp)] + 1 < distance[graph
							.indexOf(i)]) {
						// logger.log("Tile i " + i.toString() + " nachfolger "
						// + tmp.toString());
						parent[graph.indexOf(i)] = tmp;
						distance[graph.indexOf(i)] = distance[graph
								.indexOf(tmp)] + 1;
					}
				}
				// logger.log("nope " + i.toString());
			}

		}
		return false;
	}

	/**
	 * Search the shortest path with Dijkstra
	 * 
	 * @param start
	 * @param target
	 * @param visitable
	 * @return routeList beginning with the start and ends with the target or
	 *         null if no route is possible
	 */
	public List<Tile> searchShortestPath(Tile start, Tile target,
			Set<Tile> visitable) {
		Map<Integer, Tile> kl = new HashMap<Integer, Tile>();
		List<Tile> tileList = new LinkedList<Tile>(visitable);
		Tile[] parent = new Tile[tileList.size()];
		int[] distance = new int[tileList.size()];

		for (int i = 0; i < tileList.size(); i++) {
			distance[i] = Integer.MAX_VALUE;
			parent[i] = null;
		}

		int startpos = tileList.indexOf(start);
		distance[startpos] = 0;

		kl.put(startpos, start);

		while (!kl.isEmpty()) {
			Tile tmp = kl.remove(getMinValueIndex(distance, kl));

			// früher abbruch
			// if (tmp.equals(end)) {
			// logger.log("ende gefunden vorgänger von " + end.toString() +
			// " is " + parent[tileList.indexOf(end)]);
			// return null;
			// }

			for (Tile i : gameI.getNeighbour(tmp).keySet()) {
				if (tileList.contains(i)) {
					// logger.log("drinne");
					if (distance[tileList.indexOf(i)] == Integer.MAX_VALUE) {
						kl.put(tileList.indexOf(i), i);
						// logger.log(tmp.toString() + " vorgänger von " +
						// i.toString());
					}
					if (distance[tileList.indexOf(tmp)] + 1 < distance[tileList
							.indexOf(i)]) {
						// logger.log("Tile i " + i.toString() + " nachfolger "
						// + tmp.toString());
						parent[tileList.indexOf(i)] = tmp;
						distance[tileList.indexOf(i)] = distance[tileList
								.indexOf(tmp)] + 1;
					}
				}
				// logger.log("nope " + i.toString());
			}

		}

		LinkedList<Tile> route = new LinkedList<Tile>();

		Tile tmp = target;
		route.add(target);
		while (true) {
			tmp = parent[tileList.indexOf(tmp)];

			// kein start gefunden
			if (tmp == null) {
				return null;
			}

			if (tmp.equals(start)) {
				route.addFirst(tmp);
				break;
			}

			route.addFirst(tmp);
		}

		return route;
	}

	private int getMinValueIndex(int d[], Map<Integer, Tile> kl) {
		int tmpMin = Integer.MAX_VALUE;
		int tempIndexMin = -1;

		for (Entry<Integer, Tile> t : kl.entrySet()) {
			if (d[t.getKey()] < tmpMin) {
				tempIndexMin = t.getKey();
				tmpMin = d[t.getKey()];
			}
		}

		// logger.log("found " + d[tempIndexMin] + "index " + tempIndexMin);
		return tempIndexMin;
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
			
//			Set<AStarNode> openSet = new HashSet();
//			AStarNode start = new AStarNode(source, 0, gameI.getDistance(source, target));
//			
//			Set<Vertex> kl;// Kandidatenliste
//					for (jeden Knoten v) {
//					d[v] = !;
//					p[v] = undef;
//					}
//			
//					d[s] = 0; // Startknoten
//					kl.insert(s);
//					while (! kl.empty() ) {
//					lösche Knoten v aus kl mit d[v] + h(v,z) minimal;
//					if (v == z) // Zielknoten z erreicht
//					return true;
//					for ( jeden adjazenten Knoten w von v ) {
//					if (d[w] == !) // w noch nicht besucht und nicht in Kandidatenliste
//					kl.insert(w);
//					if (d[v] + c(v,w) < d[w]) {
//					p[w] = v;
//					d[w] = d[v] + c(v,w);
//					}
//					}
//					}
//					return false;		
			
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
							// int h = weight * gameI.getDistance(x.getNode(),
							// neighbor);
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
				// Stack<Tile> stack = new Stack<Tile>();
				List<Tile> list = new ArrayList<Tile>();
				// stack.push(goal.node);
				list.add(goal.getNode());
				AStarNode parent = goal.getCameFrom();
				while (parent != null) {
					list.add(0, parent.getNode());
					// stack.push(parent.getNode());
					parent = parent.getCameFrom();
				}
				// if (log.isDebugEnabled()) {
				// log.debug("Constructing search path: ");
				// }
				// while(stack.size() > 0){
				// if (log.isDebugEnabled()) {
				// log.debug("\t" + stack.peek().getId());
				// }
				// list.add(stack.pop());
				// }
				return list;
			}
//			AntBot.debug().log("Am ende");
			
			if(goal == null) {
				AntBot.debug().log("Start " + source + " Ziel " + target + " target Ilk " + target.getType());
			}
			return null;
		}
	}
}

