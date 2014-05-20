package virophage.network.event;

import virophage.core.*;
import virophage.util.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class TissueSegment implements Serializable {

    private Cell[] cells;
    private Channel[] channels;

    public TissueSegment(Tissue tissue, ArrayList<Location> locations) {
        cells = new Cell[locations.size()];

        for(int i = 0; i < locations.size(); i++) {
            cells[i] = tissue.getCell(locations.get(i));
        }

        for(Player player: tissue.getPlayers()) {
            for(Channel channel: player.channels) {

            }
        }

    }

}
