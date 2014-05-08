package virophage.game;

public class Cell {

    public Virus occupant;

    public void infectWith(Virus virus) throws InfectCellException {
        if (occupant == null) {
            occupant = virus;
        } else {
            if (virus.energy > occupant.energy) {
                int energy = occupant.energy;
                virus.energy += energy;
                occupant = virus;
            } else {
                throw new InfectCellException("Other virus has too much energy");
            }
        }
    }

}
