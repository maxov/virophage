package virophage.network.event;

import virophage.network.TissueSegment;

import java.io.Serializable;

/**
 *
 * @author Max Ovsiankin
 * @since 2014-05-17
 */
public class Event implements Serializable {

    private final TissueSegment before;
    private final TissueSegment after;

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
