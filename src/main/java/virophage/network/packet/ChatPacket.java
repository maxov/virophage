package virophage.network.packet;

import virophage.core.Player;

/**
 * A player chat packet.
 *
 * @author Max
 */
public class ChatPacket extends BroadcastPacket {

    private Player player;

    /**
     *
     * @param player a player
     * @param message a message
     */
    public ChatPacket(Player player, String message) {
        super(message);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
