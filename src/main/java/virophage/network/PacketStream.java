package virophage.network;

import virophage.Start;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a continuous stream of Packet objects, abstracting away the Network client and server.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class PacketStream {

    private Socket socket;
    private PacketWriter packetWriter;
    private PacketReader eventWriter;

    private List<Object> write = Collections.synchronizedList(new ArrayList<Object>());

    private ArrayList<PacketStreamListener> listeners = new ArrayList<PacketStreamListener>();

    /**
     * Create a duplex PacketStream from a socket.
     *
     * @param socket the socket
     */
    public PacketStream(Socket socket) {
        this.socket = socket;
        this.packetWriter = new PacketWriter();
        this.eventWriter = new PacketReader();
    }

    /**
     * Queue an object for writing on this stream.
     *
     * @param obj an object for writing.
     */
    public void write(Object obj) {
        write.add(obj);
    }

    /**
     * Add a listener on this stream for whenever an object is recieved.
     *
     * @param listener an object for listening.
     */
    public void addListener(PacketStreamListener listener) {
        listeners.add(listener);
    }

    private class PacketWriter implements Runnable {

        private ObjectInputStream in;

        private PacketWriter() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            } catch (IOException e) {
                Start.log.warning("Error on creating input stream to socket");
                e.printStackTrace();
            }
            while (socket.isConnected()) {
                Serializable data;
                try {
                    data = (Serializable) in.readObject();
                    System.out.println("read: " + data);
                    for (PacketStreamListener listener : listeners) {
                        listener.onEvent(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try {
                in.close();
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                Start.log.warning("Error on closing socket");
                e.printStackTrace();
            }
        }

    }

    private class PacketReader implements Runnable {

        private ObjectOutputStream out;

        private PacketReader() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            } catch (IOException e) {
                Start.log.warning("Error on creating output stream to socket");
                e.printStackTrace();
            }
            while (socket.isConnected()) {
                try {
                    Iterator<Object> it = write.iterator();
                    while (it.hasNext()) {
                        Object obj = it.next();
                        if (obj != null) {
                            System.out.println("wrote: " + obj);
                            out.writeObject(obj);
                        }
                        it.remove();
                    }
                    out.flush();
                    out.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for(PacketStreamListener listener: listeners) {
                listener.onDisconnect(socket);
            }
            try {
                out.close();
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                Start.log.warning("Error on closing socket");
                e.printStackTrace();
            }
        }

    }

}
