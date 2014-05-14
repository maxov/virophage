package virophage.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class OptionPanel extends JPanel implements ActionListener {

    private GameClient w;
   
    public OptionPanel(GameClient w) {
        this.w = w;
     	  	
	  	JButton button = new JButton("Press to Start the Virophage Game!");
        button.addActionListener(this);
        add(button);
    }

    public void paintComponent(Graphics g)
  	{
    	super.paintComponent(g);
    	
    	// Draw the Game Title
    	Font f = new Font ("arial", Font.BOLD, 30);
      	g.setFont(f);
      	g.drawString ("Welcome to Virophage", 480, 160);
      	
        //Draw the Instructions
      	f = new Font ("courier", Font.PLAIN, 16);
      	g.setFont(f);
      	g.drawString ("Instructions", 194, 240);
      	g.drawString ("1. Click the hexagon", 213, 280);
     	g.drawString ("2. Do something", 213, 320);

	}

    public void actionPerformed(ActionEvent e) {
    	w.gameStart();
        w.changePanel();
    }

}