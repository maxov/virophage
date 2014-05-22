package virophage.core;

import virophage.gui.GameScreen;
import virophage.util.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A <code>Tissue</code> represents a collection of related cells that are modified by players.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class Tissue implements Serializable {

    public Cell[][] cells;
    private ArrayList<Player> players = new ArrayList<Player>();
    public GameScreen tree;

    /**
     * Constructs tissue that contains cells.
     *
     * @param start the array of Cells
     */
    public Tissue(Cell[][] start, GameScreen tree) {
        cells = start;

        // Initialize cells
        this.tree = tree;
    }

    public Cell getCell(Location loc) {
        return cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2];
    }

    public void setCell(Location loc, Cell c) {
        cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2] = c;
    }

    public ArrayList<Channel> getChannels() {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (Player p : players) {
            channels.addAll(p.channels);
        }
        return channels;
    }

    /**
     * Add a player to this tissue.
     *
     * @param player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    public GameScreen getTree() {
        return tree;
    }

}
