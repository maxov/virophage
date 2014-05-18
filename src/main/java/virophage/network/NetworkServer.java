package virophage.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class NetworkServer implements Runnable {

    private int port;
    private boolean listening;

    public NetworkServer(int port) {
        this.port = port;
    }

    public void stopListening() {
        listening = false;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        listening = true;

        try {
            serverSocket = new ServerSocket(port);

            while(listening) {
                Socket socket = serverSocket.accept();
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
