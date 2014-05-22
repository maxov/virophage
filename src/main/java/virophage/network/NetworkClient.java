package virophage.network;

import virophage.util.Listening;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a client on the network.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class NetworkClient extends Listening {

    private int port;
    private String host;
    private Socket socket;

    public PacketStream stream;

    public NetworkClient(String host, int port) {
        this.port = port;
        this.host = host;
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
