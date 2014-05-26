package virophage.network;

import virophage.core.*;
import virophage.util.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Because Virophage is a mutable game with constantly updating positions, it requires some sort of
 * serialization. Instead of passing a whole Tissue around, a TissueSegment is a fragment of the tissue
 * with some changes from the original tissue.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class TissueSegment implements Serializable {

    private final Cell[] cells;
    private final Location[] locs;
    private final Channel[] channels;

    /**
     * Construct a TissueSegment from a tissue and some locations to use.
     *
     * @param tissue the tissue
     * @param locations locations to use
     */
    public TissueSegment(Tissue tissue, ArrayList<Location> locations) {
        locs = locations.toArray(new Location[locations.size()]);
        cells = new Cell[locations.size()];
        ArrayList<Channel> channels = new ArrayList<Channel>();

        for (int i = 0; i < locations.size(); i++) {
            cells[i] = tissue.getCell(locations.get(i));
        }

        for (Player player : tissue.getPlayers()) {
            for (Channel channel : player.channels) {
                for (Location loc : locations) {
                    if (loc.equals(channel.from) || loc.equals(channel.to)) {
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

    public void apply(Tissue tissue) {
        for (int i = 0; i < locs.length; i++) {
            tissue.setCell(locs[i], cells[i]);
        }

        for (Player player : tissue.getPlayers()) {
            for (Channel channel : player.channels) {
                for (Location loc : locs) {
                    if (loc.equals(channel.from) || loc.equals(channel.to)) {
                        player.removeChannel(channel);
                        channel.destroy();
                    }
                }
            }
        }
        for(Channel channel: channels) {

        }
    }

}
