package virophage.network;

import virophage.core.Player;

import java.io.Serializable;

public class Chat implements Serializable {

    private Player player;
    private String message;

    public Chat(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
