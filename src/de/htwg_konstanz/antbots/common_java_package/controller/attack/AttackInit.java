package de.htwg_konstanz.antbots.common_java_package.controller.attack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
		return attack;
	}

	/**
	 * recursive function if their are own diffrent ants which have the same
	 * enemy ant in the view radius they will be merged in a menge
	 * 
	 * @param attack
	 */
	private void merge(Map<Set<Ant>, Set<Ant>> attack) {
		for (Entry<Set<Ant>, Set<Ant>> area : attack.entrySet()) {
			for (Entry<Set<Ant>, Set<Ant>> areaTwo : attack.entrySet()) {
				if (areaTwo != area) {
					Set<Ant> tmp = new HashSet<>(areaTwo.getValue());
					tmp.retainAll(area.getValue());

					if (tmp.size() != 0) {
						if (area.getKey().size() <= Configuration.GROUPSIZE
								|| area.getValue().size() <= Configuration.GROUPSIZE) {
							area.getKey().addAll(areaTwo.getKey());
							area.getValue().addAll(areaTwo.getValue());
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
