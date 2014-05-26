package virophage.network.packet;

public class RequestPlayerName implements Packet {

    private String name;

    public RequestPlayerName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
