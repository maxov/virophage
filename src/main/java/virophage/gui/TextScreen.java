package virophage.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import virophage.core.MachinePlayer;

/**
 * @author Leon Ren
 * @since 2014-05-6
 */
public class TextScreen extends JPanel implements ActionListener {
	
	public JButton buttonBack;
    private GameClient w;
	
    /**
     * Constructs a TextScreen with a single back button.
     * @param g
     */
	public TextScreen(GameClient g) {
		w = g;
		setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        
		buttonBack = new JButton("Back");
		Font f = new Font("Verdana", Font.ITALIC|Font.BOLD, 16);
        buttonBack.setFont(f);
        buttonBack.addActionListener(this);    
        add(buttonBack);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object x = e.getSource();
		if(x == buttonBack){
			w.changePanel("menuScreen");
		}
	}

}