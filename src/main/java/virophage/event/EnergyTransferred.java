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
    public boolean apply(Tissue tissue) {
        Virus from = tissue.getCell(channel.from).occupant;
        Virus to = tissue.getCell(channel.to).occupant;

        if(from == null || from.energy < 1) return false;

        if(to == null) {
            tissue.getCell(channel.to).occupant = new Virus(channel.player, 1);
            from.energy--;
        } else {
            if(to.player == channel.player) {
                to.energy++;
                from.energy--;
            }
        }

        if(from.energy == 0) {
            tissue.getCell(channel.from).occupant = null;
        }

        return true;
    }

}