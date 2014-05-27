package virophage.gui;


import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * A <code>MenuScree</code> presents a user with the instructions and an option to start.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class MenuScreen extends JPanel implements ActionListener {

    private GameClient w;
    private JButton startGameButton;
    private JButton joinGameButton;
    private JButton instrButton;
    private JButton credButton;
    private ImageIcon icon = new ImageIcon("Viro-Background.png");

    /**
     * Construct a MenuScreen.
     *
     * @param w a GameScreen
     */
    public MenuScreen(GameClient w) {
        this.w = w;
        setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        int x = w.getWidth();
        int y = w.getHeight();

        Font f = new Font("Verdana", Font.BOLD, 16);

        startGameButton = new JButton("Start Game");
        startGameButton.setFont(f);
        startGameButton.addActionListener(this);
        add(startGameButton);

        joinGameButton = new JButton("Join Game");
        joinGameButton.setFont(f);
        joinGameButton.addActionListener(this);
        add(joinGameButton);

        instrButton = new JButton("Instructions");
        instrButton.setFont(f);
        instrButton.addActionListener(this);
        add(instrButton);

        credButton = new JButton("Credits");
        credButton.setFont(f);
        credButton.addActionListener(this);
        add(credButton);

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
        g.drawImage(icon.getImage(), 0, 0, x, y, this);
        startGameButton.setBounds(x * 2 / 5 + x / 43, y / 3, 200, 60);
        joinGameButton.setBounds(x * 2 / 5 + x / 43, y / 3 + y / 10, 200, 60);
        instrButton.setBounds(x * 2 / 5 + x / 43, y / 3 + y / 5, 200, 60);
        credButton.setBounds(x * 2 / 5 + x / 43, y / 3 + 3 * y / 10, 200, 60);
        y -= 90;

        // Draw the Game Title
        Font f = new Font("arial", Font.BOLD, 30);
        g.setFont(f);
        g.setColor(Color.WHITE);
        g.drawString("Virophage", x / 3 + (int) (x / 9.7), y / 4);
    }

    public void actionPerformed(ActionEvent e) {
        Object x = e.getSource();
        if (x == startGameButton) {
            w.changePanel("lobbyScreen");
        } else if (x == instrButton) {
            w.changePanel("instructionScreen");
        } else if (x == credButton) {
            w.changePanel("creditsScreen");
        } else if(x == joinGameButton) {
            new ConnectionDialog(w);
        }

    }

}