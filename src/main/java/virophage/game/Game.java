package virophage.game;

import virophage.core.Channel;
import virophage.core.AIPlayer;
import virophage.core.Player;
import virophage.core.Tissue;
import virophage.core.Virus;
import virophage.gui.GameClient;
import virophage.util.Listening;

/**
 * Represents an active game that is going on.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-16
 */
public class Game {

    private Tissue tissue;
    private boolean gameStarted = false;
    private GameClient client;
    private String loserName;
    
    /**
     * Construct this game given a tissue.
     *
     * @param tissue the tissue
     */
    public Game(Tissue tissue, GameClient c) {
        this.tissue = tissue;
        client = c;
        scheduler = new Scheduler(0);
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


    public boolean isGameStarted() {
    	return gameStarted;
    }
    
    public void setGameStarted(boolean g) {
    	gameStarted = g;
    }
    
    public String getLoserName() {
    	return loserName;
    }
    
    public void checkGame() {
    	Player[] players = tissue.getPlayers();
        
        if (isGameStarted()) {
	        for (Player p: players){
	        	if (p instanceof AIPlayer){
	        		if (p.getViruses().size() == 0){
	        			loserName = p.getName();
	        			client.changePanel("loseScreen");
	        			// END THE GAME
	        			//tissue.getTree().getGameClient().gameStop();
	        		}
	        		/*
	        		else if(getViruses().size() + p.getViruses().size() == tissue.getTree().getGameClient().getNumberCellsCount()){
	        			if (getViruses().size() > p.getViruses().size()){
	        				tissue.getTree().getGameClient().changePanel("winScreen");
	            			// END THE GAME
	        				//tissue.getTree().getGameClient().gameStop();
	        			}
	        			else{
	        				tissue.getTree().getGameClient().changePanel("loseScreen");
	            			// END THE GAME
	        				//tissue.getTree().getGameClient().gameStop();
	
	        			}
	        		}*/
	        	}
	        }
        }
    }
}
