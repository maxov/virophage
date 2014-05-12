package virophage.event;

import virophage.core.Channel;
import virophage.core.Tissue;
import virophage.core.Virus;

public class EnergyTransferred extends Event {

    public final Channel channel;

    public EnergyTransferred(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void apply(Tissue tissue) {
        Virus from = tissue.getCell(channel.from).occupant;
        Virus to = tissue.getCell(channel.to).occupant;

        if(from == null || to == null) return;
        if(from.energy < 1) return;

        to.energy++;
        from.energy--;
    }

}