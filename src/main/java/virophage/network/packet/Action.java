package virophage.network.packet;

import virophage.core.Player;
import virophage.network.TissueSegment;

/**
 * Represents a Player action.
 *
 * @author Max Ovsiankin
 * @since 2014-05-17
 */
public class Action extends Event {

    private final Player player;

    /**
     * Constructs an action.
     *
     * @param before a before segmment
     * @param after an after segment
     * @param player the player who executed this action
     */
    public Action(TissueSegment before, TissueSegment after, Player player) {
        super(before, after);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
