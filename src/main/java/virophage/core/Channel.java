package virophage.core;

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
