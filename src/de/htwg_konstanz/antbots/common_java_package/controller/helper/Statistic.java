package de.htwg_konstanz.antbots.common_java_package.controller.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;

import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;

public class Statistic {
	
	
	private LinkedList<Measure> measures;
	private GameInformations gameI;
	private PrintStream logStream = null;
	
	public Statistic(GameInformations gameI){
		measures = new LinkedList<Measure>();
		this.gameI = gameI;
		try {
			logStream = new PrintStream(new File("statistics.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		logStream.println("Turn:\tAnzahl Ameisen:\tAnzahl Futter:\tAnzahl Gegner:\tSichtbarer Bereich von " + gameI.getRows()*gameI.getCols() + " (in Tiles):");
	}
	
	private String trackNumberOfAnts(){
		return Integer.toString(gameI.getMyAnts().size());
	}
	
	private String trackNumberOfFood(){
		return Integer.toString(0);
	}
	
	private String trackNumberOfEnemies(){
		return Integer.toString(gameI.getEnemyAnts().size());
	}
	
	private String trackNumberOfVisibleTiles(){
		int count = 0;
		for (int i = 0; i < gameI.getRows(); i++) {
			for (int j = 0; j < gameI.getCols(); j++) {
				if (gameI.getVisibleTilesAsArray()[i][j]){
					count++;
				}
			}
		}
		return Integer.toString(count);
	}
	
	public void trackStatistics(){
		StringBuilder s = new StringBuilder();
		s.append(gameI.getCurrentTurn());
		s.append(";\t\t");
		s.append(trackNumberOfAnts());
		s.append(";\t\t\t\t");
		s.append(trackNumberOfFood());
		s.append(";\t\t\t\t");
		s.append(trackNumberOfEnemies());
		s.append(";\t\t\t\t");
		s.append(trackNumberOfVisibleTiles());
		s.append(";");
		logStream.println(s.toString());
	}
	
	public Measure createMeasure(String name){
		Measure m = new Measure(name);
		measures.add(m);
		
		return m;
	}
	
	public void saveMeasures(){
		PrintStream measureStream = null;
		try {
			measureStream = new PrintStream(new File("measures.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		measureStream.print("Turn:");
		for (Measure m : measures) {
			measureStream.print("\t");
			measureStream.print(m.getName() + " [ms]:");
		}
		measureStream.print("\n");
		
		
		for (int i = 0; i < gameI.getCurrentTurn(); i++) {
			measureStream.print(i);
			measureStream.print(";\t\t");
			for (Measure m : measures) {
				try {
					measureStream.print(m.getSamples().get(i));
					measureStream.print(";\t\t\t");
				} catch (IndexOutOfBoundsException e) {
					
				}
			}
			measureStream.print("\n");
		}
	}
	
	public class Measure{
		
		private String name;
		private long start;
		private LinkedList times;
		
		public Measure(String name){
			this.name=name;
			times = new LinkedList();
		}
		
		private String getName() {
			return name;
		}

		public void startSample(){
			start = System.nanoTime();
		}
		
		public void sample(){
			times.addLast((System.nanoTime() - start) / 1000000);
		}
		
		private int getNumberOfSamples(){
			return times.size();
		}
		
		private LinkedList getSamples(){
			return times;
		}
	}
}

