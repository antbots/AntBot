package de.htwg_konstanz.antbots.common_java_package.controller.attack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.htwg_konstanz.antbots.bots.AntBot;
import de.htwg_konstanz.antbots.common_java_package.controller.Ant;
import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.model.Configuration;

/**
 * 
 * @author Benjamin
 */
public class AttackInit {

	GameInformations gameI;

	public AttackInit(GameInformations gameI) {
		this.gameI = gameI;
	}

	/**
	 * 
	 * @return a map with the a set of own ants and the corosponding enemy ants
	 *         in the view radius of the own ants
	 */
	public Map<Set<Ant>, Set<Ant>> initAttackGroups() {
		Map<Set<Ant>, Set<Ant>> attack = new HashMap<Set<Ant>, Set<Ant>>();

		for (Ant myAnt : gameI.getMyAntsDangered()) {
			Set<Ant> myAntSet = new HashSet<Ant>();
			myAntSet.add(myAnt);
			attack.put(myAntSet, myAnt.getEnemysInViewRadius());

		}
		merge(attack);
		helpAnts(attack);
		
		return attack;
	}
	
	private Map<Set<Ant>, Set<Ant>> helpAnts(Map<Set<Ant>, Set<Ant>> attack){
		for(Set<Ant> myAttackAnts : attack.keySet()) {
			Set<Ant> tmp = new HashSet<>(myAttackAnts);
			
			for(Ant ant : myAttackAnts) {
				List<Ant> helperAnts = new LinkedList<>();				
				helperAnts.addAll(gameI.getOwnAntsInViewRadiusNotDangered(ant,myAttackAnts.size()));
				if(helperAnts.size() == 0) {
					continue;
				}
				while (tmp.size() < Configuration.GROUPSIZE) {
					Ant help = helperAnts.remove(helperAnts.size() - 1);
					if(!help.isDanger()) {
						help.isDanger();
						tmp.add(help);
					}
					
					if(helperAnts.size() == 0) {
						break;
					}
					
				}

				
			}
			myAttackAnts = tmp;
		}
		return attack;
	}
	
	

	/**
	 * recursive function if their are own diffrent ants which have the same
	 * enemy ant in the view radius they will be merged in a menge
	 * 
	 * @param attack
	 */
	private void merge(Map<Set<Ant>, Set<Ant>> attack) {
		for (Entry<Set<Ant>, Set<Ant>> attackGroups : attack.entrySet()) {
			for (Entry<Set<Ant>, Set<Ant>> areaTwo : attack.entrySet()) {
				if (areaTwo != attackGroups) {
					Set<Ant> tmp = new HashSet<>(areaTwo.getValue());
					tmp.retainAll(attackGroups.getValue());

					if (tmp.size() != 0) {
						if (attackGroups.getKey().size() < Configuration.GROUPSIZE
								|| attackGroups.getValue().size() < Configuration.GROUPSIZE) {
							attackGroups.getKey().addAll(areaTwo.getKey());
							attackGroups.getValue().addAll(areaTwo.getValue());
							attack.remove(areaTwo.getKey());
							merge(attack);
						}

						return;
					}
				}
			}
		}
	}
}
