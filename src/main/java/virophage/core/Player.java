package virophage.core;

import virophage.util.Location;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A <code>Player</code> represents a player that is a user in this game.
 *
 * @author  Max Ovsiankin and Leon Ren
 * @since 2014-05-6
 */
public class Player implements Serializable {

    private Color color;
    private String name;
    private Tissue tissue;
    public ArrayList<Channel> channels = new ArrayList<Channel>();
    public ArrayList<Virus> viruses = new ArrayList<Virus>();

    public Player() {
    	color = Color.red;
    }
    /**
     * Constructs a player with a given name.
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
     * @param color the color
     */
    public Player(Color color, Tissue tissue) {
        this.color = color;
        this.tissue = tissue;
        tissue.addPlayer(this);
    }

    public Color getColor() {
        return color;
    }

    public void addVirus(Virus v) {
        viruses.add(v);
    }

    public void addChannel(Channel c) {
        channels.add(c);
    }
    
    public ArrayList<Virus> getViruses(){
    	return viruses;
    }
    
    public ArrayList<Channel> getChannels() {
    	return channels;
    }
    
    public void removeChannel(Channel c) {
    	Iterator<Channel> cs = channels.iterator();
    	while(cs.hasNext()) {
    		Channel q = cs.next();
    		if(q.equals(c)) {
    			cs.remove();
    		}
    	}
    }
    
    public void removeVirus(Virus v) {
    	Iterator<Virus> vs = viruses.iterator();
    	while(vs.hasNext()) {
    		Virus q = vs.next();
    		if(q.equals(v)) {
    			vs.remove();
    		}
    	}
    }
    
    public boolean hasChannelBetween(Location from, Location to) {
    	for(Channel channel: channels) {
    		if(channel.from.equals(from) && channel.to.equals(to)) return true;
    	}
    	return false;
    }
    
}
