package virophage.gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;


public class MenuScreen extends JPanel implements ActionListener {

    private GameClient w;

    public MenuScreen(GameClient w) {
        this.w = w;
        setLayout(new BorderLayout());

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
        g.drawString("1. Click the hexagon", 213, 280);
        g.drawString("2. Do something", 213, 320);

    }

    public void actionPerformed(ActionEvent e) {
        w.gameStart();
        w.changePanel();
    }

}