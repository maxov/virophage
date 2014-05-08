package virophage.game;

public class DeadCell extends Cell {

    @Override
    public void infectWith(Virus virus) throws InfectCellException {
        throw new InfectCellException("Cannot infect a dead cell");
    }

}
