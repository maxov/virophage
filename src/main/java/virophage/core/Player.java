package virophage.core;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>Player</code> represents a player that is a user in this game.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Player {

    private Color color;
    public ArrayList<Channel> channels = new ArrayList<Channel>();
    public ArrayList<Virus> viruses = new ArrayList<Virus>();

    /**
     * Constructs a player with the given Color c
     * @param c the color 
     */
    public Player(Color c) {
        color = c;
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
    
    public boolean hasChannelBetween(Location from, Location to) {
    	for(Channel channel: channels) {
    		if(channel.from.equals(from) && channel.to.equals(to)) return true;
    	}
    	return false;
    }
    
}
