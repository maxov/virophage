package virophage.core;

/**
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Channel {

    public Location from;
    public Location to;
    public Player player;

    public Channel(Location from, Location to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
    }

}
