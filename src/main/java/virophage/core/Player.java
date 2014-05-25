package virophage.core;

import virophage.Start;
import virophage.util.Location;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A <code>Player</code> represents a player that is a user in this game.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class Player implements Serializable {

    private Color color;
    private String name;
    private Tissue tissue;
    public ArrayList<Channel> channels = new ArrayList<Channel>();
    public ArrayList<Virus> viruses = new ArrayList<Virus>();

    /**
     * Constructs a player with a given name.
     *
     * @param s
     */
    public Player(String s) {
        color = Color.blue;
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    /**
     * Constructs a player with the given Color c
     *
     * @param color the color
     */
    public Player(Color color, Tissue tissue) {
        this.color = color;
        this.tissue = tissue;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Add a virus that this player owns.
     *
     * @param v the virus
     */
    public void addVirus(Virus v) {
        viruses.add(v);
        //Player[] players = tissue.getPlayers();
        // TODO move game end logic to Game class
        /*
        if (tissue.getTree().getGameClient().isGameStarted()) {
	        for (Player p: players){
	        	if (p instanceof AIPlayer){
	        		if (p.getViruses().size() == 0){
	        			tissue.getTree().getGameClient().changePanel("winScreen");
	        			// END THE GAME
	        			tissue.getTree().getGameClient().gameStop();
	        		}
	        		else if(getViruses().size() + p.getViruses().size() == tissue.getTree().getGameClient().getNumberCellsCount()){
	        			if (getViruses().size() > p.getViruses().size()){
	        				tissue.getTree().getGameClient().changePanel("winScreen");
	            			// END THE GAME
	        				tissue.getTree().getGameClient().gameStop();
	        			}
	        			else{
	        				tissue.getTree().getGameClient().changePanel("loseScreen");
	            			// END THE GAME
	        				tissue.getTree().getGameClient().gameStop();
	
	        			}
	        		}
	        	}
	        }
        }*/
        if (tissue != null && tissue.getTree() != null && tissue.getTree().getGame()!=null)
        	tissue.getTree().getGame().checkGame();
    }

    /**
     * Add a channel that this player owns.
     *
     * @param c the channel
     */
    public void addChannel(Channel c) {
        channels.add(c);
    }

    public ArrayList<Virus> getViruses() {
        return viruses;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    /**
     * Remove a channel that this player owns.
     *
     * @param c the channel
     */
    public void removeChannel(Channel c) {
        Iterator<Channel> cs = channels.iterator();
        while (cs.hasNext()) {
            Channel q = cs.next();
            if (q.equals(c)) {
                cs.remove();
            }
        }
    }

    /**
     * Remove a virus that this player owns.
     *
     * @param v the virus
     */
    public void removeVirus(Virus v) {
        Iterator<Virus> vs = viruses.iterator();
        while (vs.hasNext()) {
            Virus q = vs.next();
            // TODO move game end logic to Game class

            if (q.equals(v)) {
                vs.remove();
                /*
                if (tissue.getTree().getGameClient().isGameStarted()) {
                	Player[] players = tissue.getPlayers();
			        if (getViruses().size() == 0){
			        	tissue.getTree().getGameClient().changePanel("loseScreen");
			        	tissue.getTree().getGameClient().gameStop();
			        }
			        for (Player p: players){
			        	if (p instanceof AIPlayer){
			        		if (p.getViruses().size() == 0){
			        			
			        			tissue.getTree().getGameClient().changePanel("winScreen");
			        			// END THE GAME
			        			tissue.getTree().getGameClient().gameStop();
			        		}
			        		else if(getViruses().size() + p.getViruses().size() == tissue.getTree().getGameClient().getNumberCellsCount()){
			        			if (getViruses().size() > p.getViruses().size()){
			        				tissue.getTree().getGameClient().changePanel("winScreen");
			            			// END THE GAME
			        				tissue.getTree().getGameClient().gameStop();
			        			}
			        			else{
			        				tissue.getTree().getGameClient().changePanel("loseScreen");
			            			// END THE GAME
			        				tissue.getTree().getGameClient().gameStop();
			
			        			}
			        		}
			        	}
			        }          	
                }*/

                Start.log.info("player "+name+" has " + viruses.size() + " viruses");
                if (tissue != null && tissue.getTree() != null && tissue.getTree().getGame()!=null)
                	tissue.getTree().getGame().checkGame();
            }
        }
        
    }

    public void removeVirusFromList(Virus v) {
        Iterator<Virus> vs = viruses.iterator();
        while (vs.hasNext()) {
            Virus q = vs.next();
            if (q.equals(v)) {
                vs.remove();
            }
        }
        
    }
    /**
     * Detects if there is a channel owned by the player between two locations.
     *
     * @param from the location from
     * @param to   the location to
     * @return if they are connected by a channel
     */
    public boolean hasChannelBetween(Location from, Location to) {
        for (Channel channel : channels) {
            if (channel.from.equals(from) && channel.to.equals(to)) return true;
        }
        return false;
    }

    public Tissue getTissue() {
        return tissue;
    }
    
    public void setTissue(Tissue tissue) {
    	this.tissue = tissue;
    }

    public void destroy() {
    	if (channels != null) {
    		Iterator<Channel> channels = this.getChannels().iterator();
            while (channels.hasNext()) {
                Channel c = channels.next();             
                    channels.remove();
                    this.removeChannel(c);
                    c.destroy();               
            }
    	}
    	
    	if (viruses != null) {
    		Iterator<Virus> viruses = this.getViruses().iterator();
            while (viruses.hasNext()) {
                Virus v = viruses.next();             
                    viruses.remove();
                    this.removeVirusFromList(v);
                    v.destroy();               
            }
    	}
    }
    
    public String toString() {
    	return name + " [color = " + color.toString() + "]";
    }
    
}
