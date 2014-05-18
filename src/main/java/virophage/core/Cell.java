package virophage.core;

import virophage.core.Virus;

/**
 * A <code>Cell</code> is an area that can contain a virus.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class Cell implements Cloneable {

	public Tissue tissue;
    public Virus occupant;

    /**
     * Constructs a cell with a <code>Virus</code> occupant
     * @param occupant
     */
    public Cell(Tissue tissue, Virus occupant) {
    	this.tissue = tissue;
        this.occupant = occupant;
    }
    
    public Cell(Tissue tissue) {
    	this.tissue = tissue;
    	this.occupant = null;
    }

    public Virus getOccupant() {
        return occupant;
    }

    public void setOccupant(Virus occupant) {
        this.occupant = occupant;
    }

    public Cell clone() throws CloneNotSupportedException {
        Cell c = (Cell) super.clone();
        c.setOccupant(occupant);
        return c;
    }

}