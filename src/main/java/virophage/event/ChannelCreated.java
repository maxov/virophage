package virophage.event;

import virophage.core.Location;
import virophage.core.Player;
import virophage.core.Tissue;
import virophage.core.Virus;

public class ChannelCreated extends Event {

    public final Location locationFrom;
    public final Location locationTo;
    public final Player player;

    public ChannelCreated(Location locationFrom, Location locationTo, Player player) {
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.player = player;
    }

    @Override
    public boolean apply(Tissue tissue) {
        Virus to = tissue.getCell(this.locationTo).occupant;
        Virus from = tissue.getCell(this.locationFrom).occupant;
        if(to == null) return true;
        if(from.player != player) return true;
        return false;
    }

}