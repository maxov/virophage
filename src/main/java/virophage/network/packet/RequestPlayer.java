package virophage.network.packet;

public class RequestPlayer implements Packet {

    private String name;

    public RequestPlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
