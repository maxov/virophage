package virophage.network.packet;

import virophage.core.Tissue;

public class TissueUpdate implements Packet {

    private final Tissue tissue;

    public TissueUpdate(Tissue tissue) {
        this.tissue = tissue;
    }

    public Tissue getTissue() {
        return tissue;
    }

}
