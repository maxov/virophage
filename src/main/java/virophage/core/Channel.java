package virophage.core;

/**
 * A <code>Channel</code> represents a bridge between two cells. 
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 * 
 */
public class Channel {

    public Location from;
    public Location to;
    public Player player;
    public Virus virus;

    
	/**
	 * Constructs a Channel for a player between two locations. 
	 * @param from - The location the bridge starts from
	 * @param to - The location the bridge goes to
	 * @param player - The current player making this bridge
	 */
    public Channel(Location from, Location to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
        virus = null;
    }

    public void createVirus() {
        this.virus = new Virus(player, 0);
    }

    public boolean hasVirus() {
        return virus != null;
    }

}
