package virophage;

import virophage.network.EventListener;
import virophage.network.NetworkClient;
import virophage.network.NetworkServer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main Class - Starts the game, and also creates a network server. Example for networks.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-21
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
                final NetworkClient client = new NetworkClient("localhost", 4444);
                while (!client.connect()) {
                }
                client.start();
                Timer t = new Timer();
                t.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        client.stream.write("ping");
                    }
                }, 1000, 200);
                client.stream.addListener(new EventListener() {

                    private int m = 0;

                    @Override
                    public void onEvent(Object evt) {
                        m++;
                        System.out.println("client " + m + ": " + evt);
                    }
                });
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (server.stream == null) {
                }
                server.stream.addListener(new EventListener() {

                    private int n = 0;

                    @Override
                    public void onEvent(Object evt) {
                        n++;
                        System.out.println("server " + n + ": " + evt);
                        server.stream.write("pong");
                    }
                });
            }
        }).start();
        server.start();

    }

}
