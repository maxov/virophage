package virophage.network.packet;

/**
 * A packet on error
 */
public class ErrorPacket implements Packet {

    private String error;

    /**
     * Create an error packet
     *
     * @param error string message
     */
    public ErrorPacket(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
