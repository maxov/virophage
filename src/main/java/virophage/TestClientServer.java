package virophage;

import virophage.network.NetworkClient;
import virophage.network.NetworkServer;

import java.util.ArrayList;

/**
 * Main Class - Starts the game, and also creates a network server. 
 *
 * @author      Max Ovsiankin, Leon Ren
 * @since       2014-05-21
 */
public class TestClientServer {

    public static void main(String[] args) {
        final NetworkServer server = new NetworkServer(4444);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NetworkClient client = new NetworkClient("localhost", 4444);
                while(!client.connect()) {}
                client.start();
                while(true) {
                    ArrayList<String> messages = new ArrayList<String>();
                    if(server.stream != null) {
                        for(Object o: server.stream.read()) {
                            messages.add((String) o);
                        }
                        if(messages.size() > 0) {
                            System.out.println(messages);
                        }
                    }

                }
            }
        }).start();
        server.start();


    }

}
