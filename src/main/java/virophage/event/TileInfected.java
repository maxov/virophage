package virophage.event;

import virophage.core.Location;
import virophage.core.Tissue;
import virophage.core.Virus;

public class TileInfected extends Event {

    public final Location loc;
    public final Virus virus;

    public TileInfected(Location loc, Virus virus) {
        this.loc = loc;
        this.virus = virus;
    }

    @Override
    public boolean apply(Tissue tissue) {
        return false;
    }

}