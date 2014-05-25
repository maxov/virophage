package virophage.game;

import virophage.core.Channel;
import virophage.core.Tissue;
import virophage.core.Virus;
import virophage.util.Listening;

/**
 * Represents an active game that is going on.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class Game {

    private Tissue tissue;
    
    /**
     * Construct this game given a tissue.
     *
     * @param tissue the tissue
     */
    public Game(Tissue tissue) {
        this.tissue = tissue;
    }

    public Tissue getTissue() {
        return tissue;
    }

    private boolean canInfect(Channel c) {
        Virus virus = tissue.getCell(c.from).getOccupant();
        return virus != null && virus.getEnergy() > 2;
    }

    public void tick(int tick) {

    }

}
