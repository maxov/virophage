package virophage.core;

import virophage.util.Location;

import java.io.Serializable;

/**
 * A <code>BonusCell</code> is a space that is part of a series of bonus "regions".
 * When a player takes an entire bonus region, he/she will have an upgrade.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class BonusCell extends Cell implements Serializable {

    /**
     * Constructs a dead cell that cannot be occupied.
     *
     * @param tissue the gameboard that will contain this cell.
     * @param loc    the location of the BonusCell
     */
    public BonusCell(Tissue tissue, Location loc) {
        super(tissue, loc, null);
    }

}
