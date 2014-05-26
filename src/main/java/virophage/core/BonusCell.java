package virophage.core;

import virophage.util.Location;

import java.io.Serializable;

/**
 * A <code>BonusCell</code> is a space that is part of a series of bonus "regions".
 * When a player takes an entire bonus region, he/she will have an upgrade - the time to update is halved.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class BonusCell extends Cell implements Serializable {

	private Player occupant;
	
	/**
	 * Constructs a new bonus cell in a given Tissue at a given location.
	 * @param tissue the gameboard that will contain this BonusCell
	 * @param loc the coordinates of this cell
	 */
    public BonusCell(Tissue tissue, Location loc) {
        super(tissue, loc, null);
    }
    
    public void setPlayer(Player p){
    	occupant = p;
    }
    
    public Player getPlayer(){
    	return occupant;
    }

}
