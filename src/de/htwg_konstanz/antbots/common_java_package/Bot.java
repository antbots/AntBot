package de.htwg_konstanz.antbots.common_java_package;

/**
 * Provides basic game state handling.
 */
public abstract class Bot extends AbstractSystemInputParser {
	private GameInformations gameI;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup(int loadTime, int turnTime, int rows, int cols,
			int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
		setAnts(new GameInformations(loadTime, turnTime, rows, cols, turns,
				viewRadius2, attackRadius2, spawnRadius2));
	}

	/**
	 * Returns game state information.
	 * 
	 * @return game state information
	 */
	public GameInformations gameStateInforamtions() {
		return gameI;
	}

	/**
	 * Sets game state information.
	 * 
	 * @param ants
	 *            game state information to be set
	 */
	protected void setAnts(GameInformations ants) {
		this.gameI = ants;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeUpdate() {
		gameI.setTurnStartTime(System.currentTimeMillis());
		gameI.increaseTurn();
		gameI.clearMyAnts();
		gameI.clearEnemyAnts();
		gameI.clearMyHills();
		gameI.clearEnemyHills();
		gameI.clearFood();
		gameI.clearDeadAnts();
		gameI.getOrders().clear();
		gameI.clearVision();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWater(int row, int col) {
		gameI.update(Ilk.WATER, new Tile(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnt(int row, int col, int owner) {
		Ilk type = Ilk.MY_ANT;
		switch (owner) {
		case 0:
			type = Ilk.MY_ANT;
			break;
		case 1:
			type = Ilk.ENEMY_ANT_1;
			break;
		case 2:
			type = Ilk.ENEMY_ANT_2;
			break;
		case 3:
			type = Ilk.ENEMY_ANT_3;
			break;
		case 4:
			type = Ilk.ENEMY_ANT_4;
			break;
		case 5:
			type = Ilk.ENEMY_ANT_5;
			break;
		case 6:
			type = Ilk.ENEMY_ANT_6;
			break;
		case 7:
			type = Ilk.ENEMY_ANT_7;
			break;
		case 8:
			type = Ilk.ENEMY_ANT_8;
			break;
		case 9:
			type = Ilk.ENEMY_ANT_9;
			break;

		default:
			break;
		}
		gameI.update(type, new Tile(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addFood(int row, int col) {
		gameI.update(Ilk.FOOD, new Tile(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    public void removeAnt(int row, int col, int owner) {
        gameI.update(Ilk.DEAD, new Tile(row, col));
        
        //TODO remove own death ants
        if(owner == 0) {
        	gameI.getLogger().log("nicht tot " + row + " " + col);
        	
        	
        	for(int i = 0; i < gameI.getMyAnts().size(); i++) {
        		
        		Ant ownAnt = gameI.getMyAnts().get(i);
        		Tile t = ownAnt.getAntPosition();
        		
        		if(t.getCol() == col && t.getRow() == row) {
        			gameI.getMyAnts().remove(i);
        			return;
        		}
        	}
        	
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addHill(int row, int col, int owner) {
		gameI.updateHills(owner, new Tile(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterUpdate() {
		gameI.setVision();
	}
}
