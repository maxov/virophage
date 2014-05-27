package virophage.network.packet;

import virophage.core.Player;

/**
 * A packet which is supposed to assign a player to the game on join.
 *
 * @author Max Ovsiankin
 * @since 2014-05-22
 */
public class AssignPlayer implements Packet {

    private Player player;

    /**
     * Construct an assign player packet.
     * @param player
     */
    public AssignPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
