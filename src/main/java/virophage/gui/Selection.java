package virophage.gui;

import java.util.ArrayList;

import virophage.util.Location;
import virophage.core.Cell;
import virophage.core.DeadCell;

/**
 * A <code>Selection</code> represents the active selection a user has.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class Selection {

    private Cell from;

    /**
     * Create a Selection from a cell.
     *
     * @param from a cell
     */
    public Selection(Cell from) {
        this.from = from;
    }

    /**
     * Create a selection from no cell.
     *
     */
    public Selection() {
        this(null);
    }

    public boolean hasFrom() {
        return from != null;
    }

    public Cell getFrom() {
        return from;
    }

    public void setFrom(Cell from) {
        this.from = from;
    }

    private ArrayList<Cell> possible() {
        ArrayList<Cell> possible = new ArrayList<Cell>();
        if (hasFrom()) {
            for (Location loc : from.location.getNeighbors()) {
                Cell c = from.tissue.getCell(loc);
                if (!(c instanceof DeadCell)) {
                    possible.add(c);
                }
            }
        }
        return possible;
    }

    /**
     * Checks to cee if this cell is the 'from' cell of this Selection.
     *
     * @param cell a Cell
     * @return a boolean
     */
    public boolean isFrom(Cell cell) {
        return hasFrom() && from.equals(cell);
    }

    /**
     * Checks to see if this cell is one of the possible move locations within this selection.
     *
     * @param cell a Cell
     * @return a boolean
     */
    public boolean isPossible(Cell cell) {
        return possible().contains(cell);
    }

}
