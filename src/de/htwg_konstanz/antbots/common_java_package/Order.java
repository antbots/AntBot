package de.htwg_konstanz.antbots.common_java_package;

/**
 * Represents an order to be issued.
 */
public class Order {
    private final int row;
    
    private final int col;
    
    private final Aim direction;
    
    /**
     * Creates new {@link Order} object.
     * 
     * @param tile map tile with my ant
     * @param direction direction in which to move my ant
     */
    public Order(Tile tile, Aim direction) {
        row = tile.getRow();
        col = tile.getCol();
        this.direction = direction;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
    	if(direction.getSymbol() == 'd'){
    		return "";
    	}
        return "o " + row + " " + col + " " + direction.getSymbol();
    }
    
    public Tile getPosition(){
    	return new Tile(row,col);
    }
    
    public Aim getDirection(){
    	return direction;
    }
}
