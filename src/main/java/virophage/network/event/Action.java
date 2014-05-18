package virophage.network.event;

import virophage.core.Player;
import virophage.network.TissueSegment;

/**
 *
 * @author Max Ovsiankin
 * @since 2014-05-17
 */
public class Action extends Event {

    private final Player player;

    public Action(TissueSegment before, TissueSegment after, Player player) {
        super(before, after);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
