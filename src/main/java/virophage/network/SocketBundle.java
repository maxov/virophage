package virophage.network;

import virophage.core.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Max on 5/25/2014.
 */
public class SocketBundle {

    private Socket socket;
    private Player player;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public SocketBundle(Socket socket, Player player, ObjectOutputStream out, ObjectInputStream in) {
        this.socket = socket;
        this.player = player;
        this.out = out;
        this.in = in;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
}
