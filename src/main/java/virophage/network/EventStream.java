package virophage.network;

import virophage.Start;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class EventStream {

    private Socket socket;
    private EventReader eventReader;
    private EventWriter eventWriter;

    private ArrayList<Object> write = new ArrayList<Object>();
    private ArrayList<Object> read = new ArrayList<Object>();

    public EventStream(Socket socket) {
        this.socket = socket;
        this.eventReader = new EventReader();
        this.eventWriter = new EventWriter();
    }

    public void write(Object obj) {
        write.add(obj);
    }

    public ArrayList<Object> read() {
        ArrayList<Object> temp = read;
        read = new ArrayList<Object>();
        return temp;
    }

    private class EventReader implements Runnable {

        private ObjectInputStream in;

        private EventReader() {
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
            while(socket.isConnected()) {
                Serializable data;
                try {
                    data = (Serializable) in.readObject();
                    read.add(data);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try {
                in.close();
                if(!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                Start.log.warning("Error on closing socket");
                e.printStackTrace();
            }
        }

    }

    private class EventWriter implements Runnable {

        private ObjectOutputStream out;

        private EventWriter() {
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
            while(socket.isConnected()) {
                try {
                    ArrayList<Object> toWrite = write;
                    write = new ArrayList<Object>();
                    for(Object obj: toWrite) {
                        out.writeObject(obj);
                    }
                    out.flush();
                    out.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                out.close();
                if(!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                Start.log.warning("Error on closing socket");
                e.printStackTrace();
            }
        }

    }

}
