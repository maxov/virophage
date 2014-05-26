package virophage.network.packet;

public class BroadcastPacket implements Packet {

    private String message;

    public BroadcastPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
