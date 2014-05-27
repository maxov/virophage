package virophage.network.packet;

import virophage.core.Channel;
import virophage.core.Player;
import virophage.util.Location;

/**
 * An action to create a channel.
 *
 * @author Max
 */
public class CreateChannelAction extends Action {

    private Location from;
    private Location to;

    /**
     * Create a channel action.
     *
     * @param player a player
     * @param from a from
     * @param to a to
     */
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
