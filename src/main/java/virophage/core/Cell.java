package virophage.core;

import virophage.core.Virus;

/**
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Cell {

    public Virus occupant;

    public Cell(Virus occupant) {
        this.occupant = occupant;
    }

    public Virus getOccupant() {
        return occupant;
    }
}