package virophage.network;

import java.net.Socket;

/**
 * Represents a listener that constantly listens to object events.
 * Used by the {@link virophage.network.PacketStream} class.
 *
 * @author Max Ovsiankin
 * @since 2014-05-22
 */
public class PacketStreamListener {

    /**
     * Do something on an event.
     *
     * @param evt an event object
     */
    public void onEvent(Object evt) { }

    /**
     * Do something on a socket disconnect.
     *
     * @param socket a socket
     */
    public void onDisconnect(Socket socket) {}

}
