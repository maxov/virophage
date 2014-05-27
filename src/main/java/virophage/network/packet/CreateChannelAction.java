package virophage.network.packet;

import virophage.core.Channel;
import virophage.core.Player;
import virophage.util.Location;

public class CreateChannelAction extends Action {

    private Location from;
    private Location to;

    public CreateChannelAction(Player player, Location from, Location to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }


}
