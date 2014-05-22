package virophage.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class NetworkServer extends Listening implements Runnable {

    private int port;

    public NetworkServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        startListening();

        try {
            serverSocket = new ServerSocket(port);

            while(isListening()) {
                Socket socket = serverSocket.accept();
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
