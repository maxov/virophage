package virophage.network.packet;

import virophage.network.TissueSegment;

import java.io.Serializable;

/**
 * Represents a discrete event that happens on the Virophage timeline.
 *
 * @author Max Ovsiankin
 * @since 2014-05-17
 */
public class Event implements Packet {

    private final TissueSegment before;
    private final TissueSegment after;

    /**
     * Create an event out of a state transition of game state.
     *
     * @param before
     * @param after
     */
    public Event(TissueSegment before, TissueSegment after) {
        this.before = before;
        this.after = after;
    }

    public TissueSegment getAfter() {
        return after;
    }

    public TissueSegment getBefore() {
        return before;
    }


}
