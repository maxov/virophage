package virophage.network.packet;

import virophage.core.Tissue;

public class StartGamePacket implements Packet {

    private Tissue tissue;

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
