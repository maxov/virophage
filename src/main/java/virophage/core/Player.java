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
     * @param name A string that represents the name of the player.
     */
    public Player(String name) {
        color = Color.blue;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    /**
     * Constructs a player with the given Color in a given Tissue
     * @param color the color of the player
     * @param tissue the gameboard.
     */
    public Player(Color color, Tissue tissue) {
        this.color = color;
        this.tissue = tissue;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Add a virus that this player owns.
     * @param v the virus
     * @pre v is not null and is in a Tissue
     * @post v is added into the virus List for the player
     * 
     */
    public void addVirus(Virus v) {
        viruses.add(v);
        if (tissue != null && tissue.getGame() != null)
        	tissue.getGame().checkGame();
    }

    /**
     * Add a channel that this player owns.
     * @param c the channel
     * @post c is added to the channel list for this player
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
     * Removes a channel that this player owns.
     * @param c the channel
     * @pre is not null and inside the tissue
     * @post c is removed from the channel list of this player
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
     * @param v the virus
     * @pre v is not null and is in the Tissue
     * @post v is removed from the list of Viruses for this player.
     */
    public void removeVirus(Virus v) {
        Iterator<Virus> vs = viruses.iterator();
        while (vs.hasNext()) {
            Virus q = vs.next();

            if (q.equals(v)) {
                vs.remove();
                Start.log.info("player "+name+" has " + viruses.size() + " viruses");
                tissue.getGame().checkGame();
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

    /**
     * Destroys the player;
     * @post all viruses and channels to and from this player are destoryed.
     */
    public void destroy() {
    	if (channels != null) {
    		Iterator<Channel> channels = this.getChannels().iterator();
            while (channels.hasNext()) {
                Channel c = channels.next();             
                channels.remove();
                c.destroy();
            }
    	}
    	
    	if (viruses != null) {
    		Iterator<Virus> viruses = this.getViruses().iterator();
            while (viruses.hasNext()) {
                Virus v = viruses.next();             
                viruses.remove();
                v.destroy();
            }
    	}
    }
    
    public String toString() {
    	return name + " [color = " + color.toString() + "]";
    }
    
}
