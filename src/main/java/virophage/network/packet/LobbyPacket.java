package virophage.network.packet;

import virophage.core.Player;

import java.util.List;

public class LobbyPacket implements Packet {

    public Player[] players;

    public LobbyPacket(List<Player> players) {
        this.players = players.toArray(new Player[players.size()]);
    }

}
