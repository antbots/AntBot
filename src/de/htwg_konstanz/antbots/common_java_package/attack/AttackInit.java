package de.htwg_konstanz.antbots.common_java_package.attack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;





import de.htwg_konstanz.antbots.common_java_package.Ant;
import de.htwg_konstanz.antbots.common_java_package.GameInformations;
import de.htwg_konstanz.antbots.common_java_package.Logger;

public class AttackInit {

	GameInformations gameI;
	Logger logger;

	public AttackInit(GameInformations gameI) {
		this.gameI = gameI;
		logger = gameI.getLogger();
	}

	public Map<Set<Ant>, Set<Ant>> initAttack() {

		Map<Set<Ant>, Set<Ant>> attack = new HashMap<Set<Ant>, Set<Ant>>();

		for (Ant myAnt : gameI.getMyAntsDangered()) {
			Set<Ant> myAntSet = new HashSet<Ant>();
			myAntSet.add(myAnt);
			attack.put(myAntSet, myAnt.getEnemysInViewRadius());
			
		}
		try{
			merge(attack);		
		} catch(Exception e) {
			logger.log(e.toString());
		}
	
		
		return attack;
	}
	
	private void merge(Map<Set<Ant>, Set<Ant>> attack) {
		for (Entry<Set<Ant>, Set<Ant>> area : attack.entrySet()) {
			for (Entry<Set<Ant>, Set<Ant>> areaTwo : attack.entrySet()) {
				if (areaTwo != area) {
					Set<Ant> tmp = new HashSet<>(areaTwo.getValue());
					tmp.retainAll(area.getValue());

					if (tmp.size() != 0) {

						area.getKey().addAll(areaTwo.getKey());
						area.getValue().addAll(areaTwo.getValue());
						attack.remove(areaTwo.getKey());
						merge(attack);
						return;
					}
				}
			}
		}
	}
}
