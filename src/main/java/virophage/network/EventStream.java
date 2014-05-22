package virophage.network;

import virophage.Start;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class EventStream {

    private Socket socket;
    private Reader reader;
    private Writer writer;

    private ArrayList<Object> write = new ArrayList<Object>();
    private ArrayList<Object> read = new ArrayList<Object>();

    public EventStream(Socket socket) {
        this.socket = socket;
        this.reader = new Reader();
        this.writer = new Writer();
    }

    public void write(Object obj) {
        write.add(obj);
    }

    public ArrayList<Object> read() {
        ArrayList<Object> temp = read;
        read = new ArrayList<Object>();
        return temp;
    }

    private class Reader implements Runnable {

        private ObjectInputStream in;

        private Reader() {
            try {
                in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                new Thread(this).start();
            } catch (IOException e) {
                Start.log.warning("Error on creating input stream to socket");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(socket.isConnected()) {
                Serializable data = null;
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

    private class Writer implements Runnable {

        private ObjectOutputStream out;

        private Writer() {
            try {
                out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                new Thread(this).start();
            } catch (IOException e) {
                Start.log.warning("Error on creating input stream to socket");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
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
