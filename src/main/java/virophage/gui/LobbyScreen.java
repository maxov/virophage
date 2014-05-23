package virophage.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import virophage.Start;
import virophage.core.AIPlayer;
import virophage.core.Player;
import virophage.util.GameConstants;

/**
 * A screen that holds a 'lobby', or a listing of players.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class LobbyScreen extends JPanel implements ActionListener, ListSelectionListener {

    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JCheckBox c1;
    private JList list;
    private JTextField playerName;
    private Vector<Player> players = new Vector<Player>();
    private JButton buttonBack, buttonContinue;
    private GameClient w;
    private boolean isNetworkedGame;

    public static final Color[] colors = new Color[] {
    	Color.RED,
    	Color.GREEN,
    	Color.BLUE,
    	Color.YELLOW,
    	Color.PINK,
    	Color.MAGENTA,
    	Color.ORANGE,
    };

    /**
     * Constructs a screen in which the user can add or remove players.
     *
     * @param g a GameClient
     */
    public LobbyScreen(GameClient g) {
        w = g;
        isNetworkedGame = false;
        this.setLayout(null);

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

        playerName = new JTextField("Player Name ", 20);
        add(playerName);

        list = new JList<Player>(players);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(-1);
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
            w.changePanel("menuScreen");
        } else if (x == buttonContinue) {
            // multiplayer game starts
            Start.log.info("Multiplayer Game will start now!");
            if (isNetworkedGame) {

            } else {
                w.changePanel("renderTree");
                w.gameStart(Arrays.asList(players.toArray(new Player[players.size()])));
            }
        } else if (x == b2) {
            if (players.size() >= GameConstants.TOTAL_NUM_PLAYERS) {
                return;
            }
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

            Player another = new AIPlayer(new Color(200 + index * 50, 250 - index * 50, 200), null);
            another.setName(name);
            players.add(index, another);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            playerName.requestFocusInWindow();
            playerName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        } else if (x == b3) {
        	// TODO fix this
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
            players.remove(index);

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
                Start.log.info("Network Game selected");
            } else {
                isNetworkedGame = false;
                Start.log.info("Network Game deselected");
            }

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        // TODO Auto-generated method stub

    }


}