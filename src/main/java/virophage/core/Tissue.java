package virophage.core;

import virophage.render.RenderTree;
import virophage.util.Location;

import java.util.ArrayList;

/**
 * A <code>Tissue</code> represents a collection of related cells that are modified by players.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class Tissue {

    public Cell[][] cells;
    private ArrayList<Player> players = new ArrayList<Player>();
    public RenderTree tree;

    /**
     * Constructs tissue that contains cells.
     * @param start the array of Cells
     */
    public Tissue(Cell[][] start, RenderTree tree) {
        cells = start;

        // Initialize cells
        /*for(Cell[] arr: cells){
            for(int i = 0; i < arr.length; i++) {
                Cell c = arr[i];
                if(c == null) {
                    arr[i] = new Cell();
                }
            }
        }*/
        this.tree = tree;
    }

    public Cell getCell(Location loc) {
        return cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2];
    }

    public void setCell(Location loc, Cell c) {
        cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2] = c;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

}
