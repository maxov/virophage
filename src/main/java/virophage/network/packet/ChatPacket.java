package virophage.network.packet;

import virophage.core.Player;

public class ChatPacket extends BroadcastPacket {

    private Player player;

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
