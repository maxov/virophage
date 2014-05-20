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


    public MenuScreen(GameClient w) {
        this.w = w;
        this.setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel instructionPanel = new InstructionScreen(w);
        add(instructionPanel);
     
		
		JButton startButton = new JButton("Press to Start!");
		startButton.addActionListener(this);
        add(startButton, BorderLayout.SOUTH);
        
    }


    public void actionPerformed(ActionEvent e) {
        w.gameStart();
        w.changePanel();
    }

}