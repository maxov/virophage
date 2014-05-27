package virophage.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import virophage.Start;
import virophage.core.AIPlayer;
import virophage.core.Player;
import virophage.game.ServerGame;
import virophage.util.GameConstants;

/**
 * A screen that holds a 'lobby', or a listing of players.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class LobbyScreen extends JPanel implements ActionListener, ListSelectionListener {

    protected JButton b2;
    protected JButton b3;
    protected JCheckBox c1;
    protected JList list;
    protected JTextField playerName;
    protected Vector<Player> players = new Vector<Player>();
    protected ServerGame serverGame;
    protected JButton buttonBack, buttonContinue;
    private boolean isNetworkedGame;
    protected Player youPlayer;
    private GameClient w;

    /**
     * Constructs a screen in which the user can add or remove players.
     *
     * @param g a GameClient
     */
    public LobbyScreen(GameClient g) {
        w = g;
        isNetworkedGame = false;

        this.setLayout(null);

        serverGame = new ServerGame(null, 4444);
        serverGame.setLobbyScreen(this);
        serverGame.constructTissue();

        youPlayer = new Player(GameConstants.PLAYER_COLORS[0], null);
        youPlayer.setName("Server");
        players.add(youPlayer);
        serverGame.getTissue().addPlayer(youPlayer);

        buttonBack = new JButton("Back");
        Font f = new Font("Verdana", Font.ITALIC | Font.BOLD, 16);
        buttonBack.setFont(f);
        buttonBack.addActionListener(this);
        add(buttonBack);

        buttonContinue = new JButton("Begin Game");
        buttonContinue.setFont(f);
        buttonContinue.addActionListener(this);
        add(buttonContinue);

        f = new Font("Arial", Font.PLAIN, 20);
        b2 = new JButton("Add AI");
        b2.addActionListener(this);
        b2.setFont(f);

        b3 = new JButton("Remove");
        b3.addActionListener(this);
        b3.setFont(f);

        add(b2);
        add(b3);


        c1 = new JCheckBox(" Networked Game");
        c1.setFont(f);
        c1.addActionListener(this);
        add(c1);

        playerName = new JTextField("Player" + (int) (Math.random() * 500), 20);
        add(playerName);

        list = new JList<Player>(players);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setCellRenderer(new CellRenderer());
        add(list);
    }

    /**
     * Paint this screen.
     *
     * @param g a Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = this.getWidth();
        int y = this.getHeight();

        Font f = new Font("calibri", Font.PLAIN, 18);
        g.setFont(f);
        g.drawString("Player List:", x / 12, y / 18);
        try {
            String ip = "IP address: " + InetAddress.getLocalHost();
            int width = g.getFontMetrics().stringWidth(ip);
            g.drawString(ip, x / 12 * 11 - width / 2 - 100, y / 18);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        list.setBounds(x / 12, y / 15, x / 3, y / 2);

        b2.setBounds(x - x / 3, y * 2 / 5, 300, 30);
        b3.setBounds(x - x / 3, y * 3 / 5, 300, 30);
        buttonBack.setBounds(x / 15, y - y / 10, 100, 30);
        buttonContinue.setBounds(x - x / 7, y - y / 10, 150, 30);
        c1.setBounds(x - x / 3, y / 12, 300, 30);

        playerName.setBounds(x / 12, y * 10 / 15, 300, 30);
    }


    private boolean alreadyInList(String name) {
        return players.contains(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object x = e.getSource();


        if (x == buttonBack) {
            players.removeAllElements();
            synchronized (players) {
                players.add(youPlayer);
            }
            updateData();
            c1.setSelected(false);
            w.changePanel("menuScreen");
            serverGame.stopListening();
            serverGame.getTissue().removeAllPlayers();
            serverGame.getTissue().addPlayer(youPlayer);
        } else if (x == buttonContinue) {
            w.changePanel("renderTree");

            serverGame.setInLobbyMode(false);
            serverGame.beginGame();
            for(Player p: serverGame.getTissue().getPlayers()) {
                if(p.equals(youPlayer)) w.gameScreen.setIdentityPlayer(youPlayer);
            }
            w.gameScreen.gameStart(serverGame);
        } else if (x == b2) {
            synchronized (players) {
                if (players.size() >= GameConstants.MAX_PLAYERS) {
                    return;
                }
                b3.setEnabled(true);
                String name = playerName.getText();

                //User didn't type in a unique name...
                if (name.equals("") || alreadyInList(name)) {
                    playerName.requestFocusInWindow();
                    playerName.selectAll();
                    return;
                }

                int index = list.getSelectedIndex(); //get selected index
                if (index == -1) { //no selection, so insert at beginning
                    index = 0;
                } else {           //add after the selected item
                    index++;
                }

                Player another = new AIPlayer(serverGame.requestPlayerColor(), null);
                another.setName(name);
                players.add(index, another);
                serverGame.getTissue().addPlayer(another);
                serverGame.updateLobby(players);
                updateData();
                //If we just wanted to add to the end, we'd do this:
                //Select the new item and make it visible.
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }

            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            playerName.requestFocusInWindow();
            playerName.setText("Player" + (int) (Math.random() * 500));

        } else if (x == b3) {
            int index = list.getSelectedIndex();
            boolean found = false;
            String name = players.get(index).getName();
            int i;
            for (i = 0; i < players.size(); i++) {
                if (players.get(i).getName() != null && players.get(i).getName().equals(name)) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                return;
            }
            synchronized (players) {
                players.remove(index);
            }

            serverGame.removePlayer(name);
            serverGame.updateLobby(players);
            updateData();

            int size = players.size();

            if (size == 0) { //Nobody's left, disable firing.
                b3.setEnabled(false);

            } else {

                //Select an index.
                if (index == players.size()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);

            }
        } else if (x == c1) {
            if (c1.isSelected()) {
                isNetworkedGame = true;
                serverGame.start();
                Start.log.info("Network Game selected");
            } else {
                isNetworkedGame = false;
                serverGame.stopListening();
                Start.log.info("Network Game deselected");
            }

        }
    }

    public void updateData() {
        synchronized (players) {
            list.setListData(players);
        }

    }

    public void resetPlayers() {
        synchronized (players) {
            players = new Vector<Player>();
            for(Player p: getServerGame().getTissue().getPlayers()) {
                players.add(p);
            }
            updateData();
        }

    }

    public Player getYouPlayer() {
        return youPlayer;
    }

    public void setYouPlayer(Player youPlayer) {
        this.youPlayer = youPlayer;
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {}

    public ServerGame getServerGame() {
        return serverGame;
    }

    public void setServerGame(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    private class CellRenderer extends JLabel implements ListCellRenderer<Player> {

        private CellRenderer() {
            setOpaque(true);
        }

        public Dimension getPreferredSize() {
            return new Dimension(list.getWidth(), 24);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus) {

            setSize(getHeight(), list.getWidth());

            if (isSelected) {
                setBackground(list.getSelectionBackground());
            } else {
                setBackground(list.getBackground());
            }

            //Set the icon and text.  If icon was null, say so.
            if(index < players.size()) {
                Player player = players.get(index);
                String name = player.getName();
                if(player instanceof AIPlayer) {
                    name += " (Machine)";
                } else {
                    name += " (Human)";
                }
                setForeground(player.getColor());
                setText(name);
            }

            return this;
        }

    }


}