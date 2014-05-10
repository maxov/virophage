package virophage.core;

public class DeadCell extends Cell {

    public void infectWith(Virus virus) throws InfectCellException {
        throw new InfectCellException("Cannot infect a dead cell");
    }

}
