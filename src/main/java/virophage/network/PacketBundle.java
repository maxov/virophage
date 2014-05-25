package virophage.network;

import virophage.core.RemotePlayer;

import java.net.Socket;

public class PacketBundle {

    private RemotePlayer player;
    private PacketStream stream;
    private Socket socket;

    public PacketBundle(RemotePlayer player, PacketStream stream, Socket socket) {
        this.player = player;
        this.stream = stream;
        this.socket = socket;
    }

    public RemotePlayer getPlayer() {
        return player;
    }

    public void setPlayer(RemotePlayer player) {
        this.player = player;
    }

    public PacketStream getStream() {
        return stream;
    }

    public void setStream(PacketStream stream) {
        this.stream = stream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
