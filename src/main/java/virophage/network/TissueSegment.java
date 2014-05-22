package virophage.network;

import virophage.core.*;
import virophage.util.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class TissueSegment implements Serializable {

    private final Cell[] cells;
    private final Location[] locs;
    private final Channel[] channels;

    public TissueSegment(Tissue tissue, ArrayList<Location> locations) throws CloneNotSupportedException {
        locs = locations.toArray(new Location[locations.size()]);
        cells = new Cell[locations.size()];
        ArrayList<Channel> channels = new ArrayList<Channel>();

        for(int i = 0; i < locations.size(); i++) {
            cells[i] = tissue.getCell(locations.get(i));
        }

        for(Player player: tissue.getPlayers()) {
            for(Channel channel: player.channels) {
                for(Location loc: locations) {
                    if(loc.equals(channel.from) || loc.equals(channel.to)) {
                        channels.add(channel);
                    }
                }
            }
        }

        this.channels = channels.toArray(new Channel[channels.size()]);

    }

    public Channel[] getChannels() {
        return channels;
    }

    public Cell[] getCells() {
        return cells;
    }

    public Location[] getLocs() {
        return locs;
    }

}
