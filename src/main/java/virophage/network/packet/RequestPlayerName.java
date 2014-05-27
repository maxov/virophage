package virophage.network.packet;

/**
 * Request player name packet
 *
 */
public class RequestPlayerName implements Packet {

    private String name;

    /**
     * Request a player name.
     *
     * @param name string name
     */
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
