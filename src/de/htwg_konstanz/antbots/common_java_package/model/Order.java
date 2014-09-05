package de.htwg_konstanz.antbots.common_java_package.model;

import de.htwg_konstanz.antbots.common_java_package.controller.GameInformations;

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

    public Tile getNewPosition(){
    	return GameInformations.getTile(new Tile(row, col),direction);
    }
    
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Order) {
            Order order = (Order)o;
            result = row == order.row && col == order.col;
        }
        return result;
    }
    

    public Aim getDirection(){
    	return direction;
    }
}
