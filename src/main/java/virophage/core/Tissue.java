package virophage.core;

import virophage.game.Game;
import virophage.gui.GameScreen;
import virophage.util.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A <code>Tissue</code> represents a collection of related cells that are modified by players.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class Tissue implements Serializable {

    public Cell[][] cells;
    private ArrayList<Player> players = new ArrayList<Player>();
    public transient Game game;

    private ArrayList<BonusCell> bonuses;

    /**
     * Constructs tissue that contains cells.
     *
     * @param start the array of Cells
     */
    public Tissue(Cell[][] start, Game game) {
        cells = start;

        // Initialize cells
        this.game = game;
        bonuses = new ArrayList<BonusCell>();
    }

    /**
     * Gets a cell.
     * @param loc the coordinate location of the cell
     * @return the cell at loc
     */
    public Cell getCell(Location loc) {
        return cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2];
    }

    /**
     * Sets a cell in the gameBoard.
     * @pre c is not null
     * @param loc the coordinate location of the cell
     * @param c the cell to occupy the location.
     */
    public void setCell(Location loc, Cell c) {
        cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2] = c;
    }

    /**
     * 
     * @return all the channels of the tissue for all players.
     */
    public ArrayList<Channel> getChannels() {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (Player p : players) {
            channels.addAll(p.channels);
        }
        return channels;
    }

    /**
     * 
     * @return a list of non empty cells;
     */
    public List<Cell> flatCells() {
        ArrayList<Cell> flat = new ArrayList<Cell>();
        for(Cell[] cls: cells) {
            for(Cell cell: cls) {
                if(cell != null) {
                    flat.add(cell);
                }
            }
        }
        return flat;
    }

    /**
     * Reset the cells
     * @param cells new cells
     */
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * Add a player to this tissue.
     * @pre player is not null
     * @param player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    /**
     * Removes all players in the gameBoard. 
     */
    public void removeAllPlayers() {
    	players = new ArrayList<Player>();
    }
    
    /**
     * Removes all cells in the gameBoard.
     * @post all cells are null
     */
    public void removeAllCells() {
    	for (int i = 0; i < cells.length; i++) {
    		for (int j = 0; j < cells[i].length; j++) {
    			cells[i][j] = null;
    		}
    	}
    }

    /**
     * Removes a specific bonus cell.
     * @pre c is not null and in the tissue.
     * @param c the bonus cell to be removed.
     */
    public void removeBonusCell(BonusCell c){
        Iterator<BonusCell> b = bonuses.iterator();
        while (b.hasNext()) {
            BonusCell n = b.next();
            if (n.equals(c)){
                b.remove();
            }
        }
    }

    public int getOccupiedCells() {
        int n = 0;
        for(Cell cell: flatCells()) {
            if(cell.hasOccupant()) n++;
        }
        return n;
    }
    
    public void addBonusCell(BonusCell c){
        bonuses.add(c);
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public Player[] getPlayerList(){
    	return players.toArray(new Player[players.size()]);
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<BonusCell> getBonuses(){
        return bonuses;
    }

}
