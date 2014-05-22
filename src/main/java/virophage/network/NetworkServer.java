package virophage.network;

import virophage.util.Listening;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents a server on the network.
 * Be careful with this class, it is blocking. Most likely belongs as a thread.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class NetworkServer extends Listening {

    private int port;
    private ServerSocket serverSocket;
    public PacketStream stream;

    /**
     * Construct a server with a given port.
     *
     * @param port the port
     */
    public NetworkServer(int port) {
        this.port = port;
    }

    /**
     * Begin listening on this server.
     */
    public void start() {
        startListening();

        try {
            serverSocket = new ServerSocket(port);

            while (isListening()) {
                Socket socket = serverSocket.accept();
                stream = new PacketStream(socket);
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
