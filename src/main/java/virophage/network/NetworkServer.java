package virophage.network;

import virophage.util.Listening;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class NetworkServer extends Listening {

    private int port;
    private ServerSocket serverSocket;

    public NetworkServer(int port) {
        this.port = port;
    }

    public EventStream stream;

    public void start() {
        startListening();

        try {
            serverSocket = new ServerSocket(port);

            while(isListening()) {
                Socket socket = serverSocket.accept();
                stream = new EventStream(socket);
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
