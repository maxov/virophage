package virophage.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import virophage.core.*;
import virophage.gui.GameScreen;
import virophage.util.GameConstants;
import virophage.util.Location;

/**
 * Represents an active game that is going on.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-16
 */
public class Game {

    private Tissue tissue;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private Player activePlayer;

    
    /**
     * Construct this game given a tissue.
     *
     * @param tissue the tissue
     */
    public Game(Tissue tissue) {
        this.tissue = tissue;
        this.activePlayer = null;

    }

    public Game() {
        this(null);
    }

    public Tissue getTissue() {
        return tissue;
    }
    public Tissue setTissue(Tissue tissue) {
        return tissue;
    }

    public void constructTissue() {
        Cell[][] cells = new Cell[2 * GameConstants.N + 1][2 * GameConstants.N + 1];
        Tissue t = new Tissue(cells, this);
        for (int i = -GameConstants.N; i <= GameConstants.N; i++) {
            for (int j = -GameConstants.N; j <= GameConstants.N; j++) {
                for (int k = -GameConstants.N; k <= GameConstants.N; k++) {
                    if (i + j + k == 0) {
                        Location loc = new Location(i, j);
                        t.setCell(loc, new Cell(t, loc));
                    }
                }
            }
        }
        this.tissue = t;
    }

    private boolean canInfect(Channel c) {
        Virus virus = tissue.getCell(c.from).getOccupant();
        return virus != null && virus.getEnergy() > 1;
    }

    private ArrayList<Channel> getInfectors(Cell c) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for(Channel channel: tissue.getChannels()) {
            if(channel.to.equals(c.location) && canInfect(channel)) {
                
            }
        }
        return channels;
    }

    public void tick(int tick) {

    }


    public boolean isGameStarted() {
    	return gameStarted;
    }
    
    public void setGameStarted(boolean g) {
    	gameStarted = g;
    }

    
    public void checkGame() {
    	List<Player> players = tissue.getPlayers();
        
        if (isGameStarted()) {
	        for (Player p: players){
                if (p.getViruses().size() == 0){
                    gameEnded = true;
                    // END THE GAME
                    //tissue.getTree().getGameScreen().gameStop();
                }
                /*
                else if(getViruses().size() + p.getViruses().size() == tissue.getTree().getGameScreen().getNumberCellsCount()){
                    if (getViruses().size() > p.getViruses().size()){
                        tissue.getTree().getGameScreen().changePanel("winScreen");
                        // END THE GAME
                        //tissue.getTree().getGameScreen().gameStop();
                    }
                    else{
                        tissue.getTree().getGameScreen().changePanel("loseScreen");
                        // END THE GAME
                        //tissue.getTree().getGameScreen().gameStop();

                    }
                }*/
	        }
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

}
