package virophage.game;

import virophage.core.Tissue;
import virophage.gui.GameClient;
import virophage.network.PacketStream;

import java.io.IOException;
import java.net.Socket;

public class ClientGame extends Game {

    private int port;
    private String host;
    private Socket socket;

    public PacketStream stream;

    public ClientGame(Tissue tissue, String host, int port) {
        super(tissue);
        this.host = host;
        this.port = port;
    }

    /**
     * Attempt to connect with the given socket information.
     *
     * @return whether the connection attempt was successful
     */
    public boolean connect() {
        try {
            socket = new Socket(host, port);
            socket.setKeepAlive(true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Begin listening on this socket.
     */
    public void start() {
        stream = new PacketStream(socket);
    }

}
