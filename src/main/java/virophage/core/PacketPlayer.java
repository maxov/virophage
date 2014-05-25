package virophage.core;

import virophage.network.PacketStream;

import java.awt.*;

public class PacketPlayer extends Player {

    private PacketStream stream;

    public PacketPlayer(String s) {
        super(s);
    }

    public PacketPlayer(Color color, Tissue tissue) {
        super(color, tissue);
    }

    public PacketStream getStream() {
        return stream;
    }

    public void setStream(PacketStream stream) {
        this.stream = stream;
    }

}
