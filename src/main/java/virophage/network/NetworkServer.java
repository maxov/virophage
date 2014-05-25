package virophage.network;

import virophage.util.Listening;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

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
    public ArrayList<PacketStream> streams = new ArrayList<PacketStream>();
    public ArrayList<Socket> sockets = new ArrayList<Socket>();
    private boolean accepting;

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

            while (isListening()) if (accepting) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                final PacketStream ps = new PacketStream(socket);
                ps.addListener(new PacketStreamListener() {
                    @Override
                    public void onDisconnect(Socket socket) {
                        Iterator<PacketStream> packetStreamIterator = streams.iterator();
                        while(packetStreamIterator.hasNext()) {
                            PacketStream stream = packetStreamIterator.next();
                            if(stream.equals(ps)) {
                                packetStreamIterator.remove();
                                break;
                            }
                        }
                        Iterator<Socket> socketIterator = sockets.iterator();
                        while(socketIterator.hasNext()) {
                            Socket s = socketIterator.next();
                            if(s.equals(socket)) {
                                socketIterator.remove();
                                break;
                            }
                        }
                    }
                });
                streams.add(ps);
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccepting(boolean accepting) {
        this.accepting = accepting;
    }

}
