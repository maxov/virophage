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
 * @author Max Ovsiankin and Leon Ren
 * @since 2014-05-6
 */
public class PlayScreen extends JPanel{
	
	private ButtonGroup group;
	private JRadioButton b1;
	private JRadioButton b2;
	private JButton buttonBack, buttonContinue;
    private GameClient w;
    private boolean singlePlayerSelected;
	
	public PlayScreen(GameClient g) {
		w = g;
		singlePlayerSelected = false;
        this.setLayout(null);
        
		buttonBack = new JButton("Back");
		Font f = new Font("Verdana", Font.ITALIC|Font.BOLD, 16);
        buttonBack.setFont(f);
        buttonBack.addActionListener(new RegularButtonHandler());    
        add(buttonBack);
        
        buttonContinue = new JButton("Continue");
		buttonContinue.setFont(f);
		buttonContinue.addActionListener(new RegularButtonHandler());    
        add(buttonContinue);
        		
        f = new Font("Courier", Font.BOLD, 26);
		group = new ButtonGroup();
		b1 = new JRadioButton("Single Player");
		b1.setFont(f);
		b1.addActionListener(new RadioButtonHandler());
		b2 = new JRadioButton("Multi-Player (with networking)");
		b2.addActionListener(new RadioButtonHandler());
		b2.setFont(f);
		group.add(b1);
		group.add(b2);
		group.clearSelection();
		add(b1);
		add(b2);
		
        
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int x = this.getWidth();
        int y = this.getHeight();
      
        b1.setBounds(x/3, y/3, 300, 30);
        b2.setBounds(x/3, y *2/5, 800, 30);
        buttonBack.setBounds(x/15, y - y/10, 100, 30);
        buttonContinue.setBounds(x- x/7, y - y/10, 150, 30);
    }
	
	
	class RadioButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object x = arg0.getSource();
			if(x == b1){
				if (w.getPlayer(1) == null) {
					MachinePlayer p = new MachinePlayer(new Color(250, 200, 200), w.getTree().getTissue());
					w.setPlayer(1, p);
				}
				singlePlayerSelected = true;
			}
			else if (x == b2){
				singlePlayerSelected = false;
			} 
		}
	}
	
	class RegularButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object x = e.getSource();
			if(x == buttonBack){
				w.changePanel("menuScreen");
			} else if (x == buttonContinue && singlePlayerSelected) {
				w.changePanel("renderTree");
				w.gameStart();
			}
			else if (x == buttonContinue && !singlePlayerSelected) {
				w.changePanel("multiplayerScreen");
			}
		}
	}

}
