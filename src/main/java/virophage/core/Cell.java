<<<<<<< Updated upstream
package virophage.core;

public class Cell {

    public Virus occupant;

    public Cell(Virus occupant) {
        this.occupant = occupant;
    }

}
=======
package virophage.core;

import java.util.ArrayList;

public class Cell {

    public Virus occupant;
    public ArrayList<InfectingVirus> infectors = new ArrayList<InfectingVirus>();

    public Cell(Virus occupant) {
        this.occupant = occupant;
    }

}
>>>>>>> Stashed changes
