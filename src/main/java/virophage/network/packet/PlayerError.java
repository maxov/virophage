package virophage.network.packet;

/**
 * Player error packet
 */
public class PlayerError extends ErrorPacket {

    /**
     * Player  error packet create
     *
     * @param error the string error
     */
    public PlayerError(String error) {
        super(error);
    }

}
