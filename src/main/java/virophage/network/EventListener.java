package virophage.network;

/**
 * Represents a listener that constantly listens to object events.
 * Used by the {@link virophage.network.PacketStream} class.
 *
 * @author Max Ovsiankin
 * @since 2014-05-22
 */
public interface EventListener {

    /**
     * Do something on an event.
     *
     * @param evt an event object
     */
    public void onEvent(Object evt);

}
