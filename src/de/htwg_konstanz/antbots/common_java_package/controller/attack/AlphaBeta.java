package de.htwg_konstanz.antbots.common_java_package.controller.attack;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;







import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.SampleTreeFactory;
import de.htwg_konstanz.antbots.common_java_package.SwingDemo;
import de.htwg_konstanz.antbots.common_java_package.TextInBox;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.controller.Logger;
import de.htwg_konstanz.antbots.common_java_package.model.Aim;
import de.htwg_konstanz.antbots.common_java_package.model.Ilk;
import de.htwg_konstanz.antbots.common_java_package.model.Order;
import de.htwg_konstanz.antbots.common_java_package.model.Tile;
import de.htwg_konstanz.antbots.treedrawer.util.DefaultTreeForTreeLayout;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer;
import de.htwg_konstanz.antbots.visualizer.OverlayDrawer.SubTile;


public class AlphaBeta {
	
	public enum Strategy{
		AGGRESSIVE,
		PASSIVE,
		NEUTRAL
	}
	
private Logger logger  = new Logger("log.txt");
	
	LinkedList<Order> bestMove;
	GameInformations board;
	int startDepth;
	
	LinkedList<Aim> possibleDirections;
	LinkedList<Order> possibleMovesTmp;
	LinkedList<Ant> myAntsToGo;
	LinkedList<Ant> enemyAntsToGo;
	
	DefaultTreeForTreeLayout<TextInBox> tree;
	TextInBox last = new TextInBox("root", 40, 20);
	
	int directionPoint = 0;
	int myDeadAnts = 0;
	int enemyDeadAnts = 0;
	
	CommandManager cm;
	Strategy gameStrategy;
	
	public AlphaBeta(){
		possibleDirections = new LinkedList<Aim>();
		possibleDirections.add(Aim.NORTH);
		possibleDirections.add(Aim.SOUTH);
		possibleDirections.add(Aim.DONTMOVE);
		possibleDirections.add(Aim.EAST);
		possibleDirections.add(Aim.WEST);
	}
	
	public LinkedList<Order> alphaBeta(GameInformations board, int depth, Strategy st, List<Set<Ant>> beteiligteAmeisen){
		bestMove = null;
		this.board = board;
		startDepth = depth;
		
		myAntsToGo = new LinkedList<Ant>();
		enemyAntsToGo = new LinkedList<Ant>();
		
		for (Ant ant : beteiligteAmeisen.get(0)) {
			myAntsToGo.add(new Ant(new Tile(ant.getAntPosition().getRow(),ant.getAntPosition().getCol()),ant.getId()));
		}
		for (Ant ant : beteiligteAmeisen.get(1)) {
			enemyAntsToGo.add(new Ant(new Tile(ant.getAntPosition().getRow(),ant.getAntPosition().getCol()),ant.getId()));
		}
		
		cm = new CommandManager();
		gameStrategy = st;
		
		tree = SampleTreeFactory.getTree(last);
		
		OverlayDrawer.setFillColor(Color.BLACK);
		for(Tile t : board.getTilesInAttackRadius(enemyAntsToGo.get(0).getAntPosition(), (int)Math.sqrt(board.getAttackRadius2()))){
			AntBot.getLogger().log(t.toString());
			OverlayDrawer.drawTileSubtile(t.getRow(),t.getCol(), SubTile.MM);
		}
		
		
		
		max(depth,Integer.MIN_VALUE , Integer.MAX_VALUE);
		
//		SampleTreeFactory.setTree(tree);
//		String[] tt = new String[] {""};
//		if(AntBot.getTurn() == 2){
//			SwingDemo.main(tt);
//			try {
//				Thread.sleep(5000000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		return bestMove;
	}

	private int max(int depth, int alpha, int beta) {
	    if (depth == 0 || myAntsToGo.isEmpty() || enemyAntsToGo.isEmpty()){
	    	int result = evaluation(gameStrategy);
	    	TextInBox newT = new TextInBox(Integer.toString(result), 80, 20);
	    	tree.addChild(last, newT);
	       return result;
	    }
	    int maxValue = alpha;
	    LinkedList<LinkedList<Order>> possibleMoves = new LinkedList<LinkedList<Order>>();
	    possibleMovesTmp = new LinkedList<Order>();
	    generatePossibleMoves(myAntsToGo.size(), myAntsToGo, possibleMoves);
	    logger.log("max");
	    //printPossibleMoves(possibleMoves);
	    while (!possibleMoves.isEmpty()) {
	    	LinkedList<Order> childMove = possibleMoves.poll();
	    	ExecuteNextMove(childMove,myAntsToGo,enemyAntsToGo, false);
	    	TextInBox beiUndoLast = last;
	    	TextInBox newT = new TextInBox(childMove.toString(), 80, 20);
	    	tree.addChild(last, newT);
	    	last = newT;
	    	int evaluation = min(depth-1, maxValue, beta);
	    	UndoMove(childMove);
	    	last = beiUndoLast;
	    	if (evaluation > maxValue) {
	    		maxValue = evaluation;
	    		if (maxValue >= beta)
	    			break;
	    		if (depth == startDepth)
	    			bestMove = childMove;
	       }
	    }
	    return maxValue;
	 }

	private void printPossibleMoves(LinkedList<LinkedList<Order>> possibleMoves2) {
		for (LinkedList<Order> linkedList : possibleMoves2) {
			StringBuilder sb = new StringBuilder(Integer.toString(linkedList.size()));
			for (Order order : linkedList) {
				sb.append(order + " ,");
			}
			logger.log(sb + "\n");
		}
	}

	private int min(int depth, int alpha, int beta) {
	    if (depth == 0 || enemyAntsToGo.isEmpty() || myAntsToGo.isEmpty()){
	    	int result = evaluation(gameStrategy);
	    	TextInBox newT = new TextInBox(Integer.toString(result), 80, 20);
	    	tree.addChild(last, newT);
	       return result;
	    }
	       
	    int minValue = beta;
	    LinkedList<LinkedList<Order>> possibleMoves = new LinkedList<LinkedList<Order>>();
	    possibleMovesTmp = new LinkedList<Order>();
	    generatePossibleMoves(enemyAntsToGo.size(), enemyAntsToGo, possibleMoves);
	    logger.log("min");
	    //printPossibleMoves(possibleMoves);
	    while (!possibleMoves.isEmpty()) {
	    	LinkedList<Order> childMove = possibleMoves.poll();
	    	ExecuteNextMove(childMove,enemyAntsToGo,myAntsToGo,true);
	    	TextInBox beiUndoLast = last;
	    	TextInBox newT = new TextInBox(childMove.toString(), 80, 20);
	    	tree.addChild(last, newT);
	    	last = newT;
	    	int evaluation = max(depth-1, 
	                      alpha, minValue);
	    	UndoMove(childMove);
	    	last = beiUndoLast;
	    	if (evaluation < minValue) {
	    		minValue = evaluation;
	    		if (minValue <= alpha)
	    			break;
	    		}
	    	}
	    return minValue;
	}
	
	private void UndoMove(LinkedList<Order> childMove) {
		cm.undo();
	}

	private void ExecuteNextMove(LinkedList<Order> childMove, LinkedList<Ant> antsToGo, LinkedList<Ant> enemysToGo, boolean min){
		cm.executeCommand(new MoveCommand(childMove, antsToGo, enemysToGo, this, min));
	}
	
	private void generatePossibleMoves(int depth, LinkedList<Ant> antsToGo, LinkedList<LinkedList<Order>> possibleMoves) {
		// TODO Auto-generated method stub
		if (depth == 0){
			LinkedList<Order> tmp = new LinkedList<Order>();
			for (Order order : possibleMovesTmp) {
				tmp.add(order);
			}
			possibleMoves.add(tmp);
		    return;
		}
		Ant ant = antsToGo.get(depth-1); 
		for (Aim aim : possibleDirections) {
			if(board.getTile(ant.getAntPosition(), aim).getType() == Ilk.WATER){
				continue;
			}else{
				boolean skip = false;
				for (Order order1 : possibleMovesTmp) {
					for (Order order2 : possibleMovesTmp) {
						if(order1.getNewPosition().equals(order2.getNewPosition()) && !order1.equals(order2)){
							skip = true;
						}
					}
				}
				if(skip){
					// nicht vollkommen sicher zb wenn eien ameise alle Wege blockiert bekommt, bzw kreuzen
					continue;
				}
				possibleMovesTmp.addFirst(new Order(ant.getAntPosition(), aim));
			}
			generatePossibleMoves(depth-1, antsToGo, possibleMoves);
			try {
				possibleMovesTmp.removeFirst();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private int evaluation(Strategy strategy) {
		int t1 = enemyDeadAnts;
		int t2 = myDeadAnts;
		int t3 = 1;
		
		int w1;
		int w2;
		int w3 = 0;
		
		switch (strategy) {
		case AGGRESSIVE:
			w1 = 100;
			w2 = -50; // um die haelfte auf die gegnerischen Toten Ameisen gewichtet
			w3 = 0;
			break;
			
		case PASSIVE:
			w1 = 50;
			w2 = -100; // um die haelfte auf die eigenen Toten Ameisen gewichtet
			w3 = 0;
			break;
			
		case NEUTRAL:
			w1 = 1;
			w2 = -1;
			w3 = 0;
			break;

		default:
			w1 = 1;
			w2 = -1;
			break;
		}
		if(!( w1 * t1 + w2 * t2 == 0 && t1!= 0 && t2!= 0)){
			w3 = directionPoint;
		}else{
			w3 = -1;
		}
		AntBot.getLogger().log(w1 + " " + t1 + " " + w2 + " " +t2 + " " + w3 + " " + t3);
		return w1 * t1 + w2 * t2 + w3 * t3;
	}

	private double calculateDistanceGain() {
		double prevsum = 0;
		for (Ant ant : board.getMyAnts()) {
			double prev = 0;
			for (Ant enemy : enemyAntsToGo) {
				prev = prev + board.getDistance(ant.getAntPosition(), enemy.getAntPosition());
			}
			prevsum = prevsum + prev/(double)enemyAntsToGo.size();
		}
		
		
		double nowsum = 0;
		for (Ant ant : myAntsToGo) {
			double now = 0;
			for (Ant enemy : enemyAntsToGo) {
				now = now + board.getDistance(ant.getAntPosition(), enemy.getAntPosition());
			}
			nowsum = nowsum + now/(double)enemyAntsToGo.size();
		}
		return prevsum - nowsum;
	}

	public LinkedList<Ant> getMyAntsToGo() {
		return myAntsToGo;
	}

	public LinkedList<Ant> getEnemyAntsToGo() {
		return enemyAntsToGo;
	}

	public GameInformations getBoard() {
		return board;
	}

	public void setMyDeadAnts(int deadAnts) {
		myDeadAnts = myDeadAnts + deadAnts;
		
	}

	public void setEnemyDeadAnts(int deadAnts) {
		enemyDeadAnts = enemyDeadAnts + deadAnts;
	}

	public void setDirectionPoint(int directionPoint) {
		this.directionPoint = this.directionPoint + directionPoint;
	}
	
	
	
	
}
