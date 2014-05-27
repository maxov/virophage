package virophage.network.packet;

import virophage.core.Tissue;

/**
 * A packet to start the game.
 *
 * @author Max
 */
public class StartGamePacket implements Packet {

    private Tissue tissue;

    /**
     * Start the game
     * @param tissue a tissue
     */
    public StartGamePacket(Tissue tissue) {
        this.tissue = tissue;
    }

    public Tissue getTissue() {
        return tissue;
    }

    public void setTissue(Tissue tissue) {
        this.tissue = tissue;
    }

}
