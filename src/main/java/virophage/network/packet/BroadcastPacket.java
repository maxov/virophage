package virophage.network.packet;

/**
 * Packet for broadcast.
 *
 * @author Max Ovsiankin
 */
public class BroadcastPacket implements Packet {

    private String message;

    /**
     * Construct this broadcast packet.
     *
     * @param message message
     */
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
