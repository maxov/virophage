package virophage.gui;

import virophage.Start;
import virophage.core.Player;
import virophage.network.Chat;
import virophage.network.InProgressChat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

public class ChatList {

    public ArrayList<Chat> chats = new ArrayList<Chat>();

    public synchronized void queueChat(final Chat chat) {
        synchronized (chats) {
            chats.add(chat);
        }
        GameScreen.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (chats) {
                    chats.remove(chat);
                }
            }
        }, 2000);
    }

    public void inProgressChat(Player player) {
        synchronized (chats) {
            chats.add(0, new InProgressChat(player, ""));
        }

    }

    public void progressUpdate(String message) {
        synchronized (chats) {
            for(Chat chat: chats) {
                if(chat instanceof InProgressChat) {
                    chat.setMessage(message);
                }
            }
        }
    }

    public Chat progressFinished() {
        Chat ch = null;
        synchronized (chats) {
            Iterator<Chat> chatIterator = chats.iterator();
            while(chatIterator.hasNext()) {
                Chat chat = chatIterator.next();
                if(chat instanceof InProgressChat) {
                    ch = chat;
                    chatIterator.remove();
                }
            }
        }
        return ch;
    }

}
