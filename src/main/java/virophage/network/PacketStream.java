package virophage.network;

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
public class PacketStream  implements Runnable {

    private Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    private List<Object> write = new ArrayList<Object>();

    private ArrayList<PacketStreamListener> listeners = new ArrayList<PacketStreamListener>();

    /**
     * Create a duplex PacketStream from a socket.
     *
     * @param socket the socket
     */
    public PacketStream(Socket socket) {
        this.socket = socket;
    }

    /**
     * Queue an object for writing on this stream.
     *
     * @param obj an object for writing.
     */
    public synchronized void write(Object obj) {
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

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                    //disconnect();
                }
            }
        }).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                    //disconnect();
                }
            }
        }).start();
        Object data = null;
        try {
            while(socket.isConnected()) {
                try {
                    if(in != null)
                    data = in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(data != null) {
                    System.out.println("read: " + data);
                    for (PacketStreamListener listener : listeners) {
                        listener.onEvent(data);
                    }
                }
                synchronized(this) {
                    Iterator<Object> it = write.iterator();
                    while (it.hasNext()) {
                        Object obj = it.next();
                        if (obj != null) {
                            System.out.println("wrote: " + obj);
                            if(out != null)
                                out.writeObject(obj);
                        }
                        it.remove();
                    }
                }
                if(out != null) {
                    out.flush();
                    out.reset();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void disconnect() {
        try {
            if(in != null) in.close();
            if(out != null) out.close();
            if(!socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(PacketStreamListener listener: listeners) {
            listener.onDisconnect(socket);
        }
    }

}
