package virophage.network.packet;

import virophage.network.Chat;

/**
 * Packet for broadcast.
 *
 * @author Max Ovsiankin
 */
public class BroadcastPacket implements Packet {

    private Chat chat;

    public BroadcastPacket(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
