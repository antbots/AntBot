package de.htwg_konstanz.antbots.common_java_package;


/**
 * Represents a tile of the game map.
 */
public class Tile  {
    private final int row;
    
    private final int col;
    private Ilk ilk = Ilk.UNKNOWN;
    
    public void setType(Ilk ilk) {
    	this.ilk = ilk;
    }
    
    public Ilk getType() {
    	return ilk;
    }

    /**
     * Creates new {@link Tile} object.
     * 
     * @param row row index
     * @param col column index
     */
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Returns row index.
     * 
     * @return row index
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Returns column index.
     * 
     * @return column index
     */
    public int getCol() {
        return col;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return row * GameInformations.MAX_MAP_SIZE + col;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Tile) {
            Tile tile = (Tile)o;
            result = row == tile.row && col == tile.col;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return row + " " + col;
    }
}
