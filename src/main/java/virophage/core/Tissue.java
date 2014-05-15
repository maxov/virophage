package virophage.core;

import virophage.render.RenderTree;

/**
 * <code>Tissue</code> represents an array of cells that are corresponding to the GUI tree.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Tissue {

    public Cell[][] cells;
    private RenderTree tree;

    /**
     * Constructs tissue that contains cells and points to the tree (t).
     * @param start the array of Cells
     * @param t the RenderTree to be pointed to
     */
    public Tissue(Cell[][] start, RenderTree t) {
        cells = start;
        tree = t;

        // Initialize cells
        /*for(Cell[] arr: cells){
            for(int i = 0; i < arr.length; i++) {
                Cell c = arr[i];
                if(c == null) {
                    arr[i] = new Cell();
                }
            }
        }*/

    }

    public Cell getCell(Location loc) {
        return cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2];
    }

    public void setCell(Location loc, Cell c) {
        cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2] = c;
    }
}
