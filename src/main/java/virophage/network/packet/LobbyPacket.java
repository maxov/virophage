package virophage.network.packet;

import virophage.core.Player;

import java.util.List;

/**
 * A packet that updates the lobby
 */
public class LobbyPacket implements Packet {

    public Player[] players;

    /**
     * Create a new lobby packet
     * @param players list of players
     */
    public LobbyPacket(List<Player> players) {
        this.players = players.toArray(new Player[players.size()]);
    }

}
