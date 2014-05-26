package virophage.gui;

import virophage.Start;
import virophage.core.AIPlayer;
import virophage.core.Player;
import virophage.game.ClientGame;
import virophage.game.ServerGame;
import virophage.util.GameConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ClientLobbyScreen extends LobbyScreen {

    private ClientGame clientGame;
    /**
     * Constructs a screen in which the user can add or remove players.
     *
     * @param g a GameClient
     */
    public ClientLobbyScreen(GameClient g) {
        super(g);
        players = new Vector<Player>();
        updateData();
        serverGame = null;
        remove(buttonBack);
        remove(buttonContinue);
        remove(b2);
        remove(b3);
        remove(c1);
        remove(playerName);
        remove(list);
        setBorder(new EmptyBorder(100, 100, 100, 100));
        setLayout(new BorderLayout(50, 50));
        JLabel label = new JLabel("Waiting for game to begin...");
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);
        add(list, BorderLayout.CENTER);
    }

    /**
     * Paint this screen.
     *
     * @param g a Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    public void resetPlayers() {
        synchronized (players) {
            players = new Vector<Player>();
            for(Player p: clientGame.getTissue().getPlayers()) {
                players.add(p);
            }
            updateData();
        }
    }


    public ClientGame getClientGame() {
        return clientGame;
    }

    public void setClientGame(ClientGame clientGame) {
        this.clientGame = clientGame;
    }
}
