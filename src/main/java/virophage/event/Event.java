package virophage.event;

import virophage.core.Tissue;

public abstract class Event {

    public abstract boolean apply(Tissue tissue);

}