package virophage.gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * A <code>MenuScree</code> presents a user with the instructions and an option to start.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class MenuScreen extends JPanel implements ActionListener {

    private GameClient w;

    public MenuScreen(GameClient w) {
        this.w = w;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JButton button = new JButton("Press to Start!");
        button.addActionListener(this);
        add(button, BorderLayout.SOUTH);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the Game Title
        Font f = new Font("arial", Font.BOLD, 30);
        g.setFont(f);
        g.drawString("Virophage", 550, 160);

        //Draw the Instructions
        f = new Font("courier", Font.PLAIN, 16);
        g.setFont(f);
        g.drawString("Instructions", 194, 240);
        g.drawString("1. Click with LMB for red cell, SHIFT + LMB for green cell", 213, 280);
        g.drawString("2. Scroll to zoom, CTRL + LMB to pan", 213, 320);

    }

    public void actionPerformed(ActionEvent e) {
        w.gameStart();
        w.changePanel();
    }

}