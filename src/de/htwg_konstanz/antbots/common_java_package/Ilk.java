package de.htwg_konstanz.antbots.common_java_package;
/**
 * Represents type of tile on the game map.
 */
public enum Ilk {
    /** Water tile. */
    WATER,
    
    /** Food tile. */
    FOOD,
    
    /** Land tile. */
    LAND,
    
    /** UNKNOWN tile. */
    UNKNOWN,
    
    /** Dead ant tile. */
    DEAD,
    
    /** My ant tile. */
    MY_ANT,
    
    /** Enemy ant tile. */
    ENEMY_ANT_1,
    
    /** Enemy ant tile. */
    ENEMY_ANT_2,
    
    /** Enemy ant tile. */
    ENEMY_ANT_3,
    
    /** Enemy ant tile. */
    ENEMY_ANT_4,
    
    /** Enemy ant tile. */
    ENEMY_ANT_5,
    
    /** Enemy ant tile. */
    ENEMY_ANT_6,
    
    /** Enemy ant tile. */
    ENEMY_ANT_7,
    
    /** Enemy ant tile. */
    ENEMY_ANT_8,
    
    /** Enemy ant tile. */
    ENEMY_ANT_9,
    
    /** My hill */
    HILL,
    
    /** Enemy hill */
    ENEMY_HILL;
    
    /**
     * Checks if this type of tile is passable, which means it is not a water tile.
     * 
     * @return <code>true</code> if this is not a water tile, <code>false</code> otherwise
     */
    public boolean isPassable() {
        return ordinal() > WATER.ordinal();
    }
    
    /**
     * Checks if this type of tile is unoccupied, which means it is a land tile or a dead ant tile.
     * 
     * @return <code>true</code> if this is a land tile or a dead ant tile, <code>false</code>
     *         otherwise
     */
    public boolean isUnoccupied() {
        return this == LAND || this == DEAD;
    }
}
