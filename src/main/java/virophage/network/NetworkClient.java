package virophage.network;

import virophage.util.Listening;

import java.io.IOException;
import java.net.Socket;

/**
*
* @author Max Ovsiankin
* @since 2014-05-16
*/
public class NetworkClient extends Listening {

	private int port;
	private String host;
    private Socket socket;
	
	public NetworkClient(String host, int port) {
		this.port = port;
		this.host = host;
	}

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

	public void start() {
        EventStream stream = new EventStream(socket);
        stream.write("Hello how are you");
        stream.write("I'm doing pretty swell");
    }

}
