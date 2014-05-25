package virophage.network.packet;

public class ErrorPacket implements Packet {

    private String error;

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
