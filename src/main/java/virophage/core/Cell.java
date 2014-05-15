package virophage.core;

import virophage.core.Virus;

/**
 * A <code>Cell</code> is an area that can contain a virus.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 * 
 */
public class Cell {

    public Virus occupant;

    /**
     * Constructs a cell with a <code>Virus</code> occupant
     * @param occupant
     */
    public Cell(Virus occupant) {
        this.occupant = occupant;
    }

    public Virus getOccupant() {
        return occupant;
    }
}