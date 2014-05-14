package virophage.core;

import virophage.core.Virus;

public class Cell {

    public Virus occupant;

    public Cell(Virus occupant) {
        this.occupant = occupant;
    }

    public Virus getOccupant() {
    	return occupant;
    }
}