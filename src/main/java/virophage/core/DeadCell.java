package virophage.core;

import virophage.util.Location;

import java.io.Serializable;

/**
 * A <code>DeadCell</code> is a space that no virus can "infect".
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class DeadCell extends Cell implements Serializable {

    /**
     * Constructs a dead cell that cannot be occupied.
     *
     * @param tissue the gameboard that will contain this cell.
     * @param loc    the location of the DeadCell
     */
    public DeadCell(Tissue tissue, Location loc) {
        super(tissue, loc, null);
    }

}
