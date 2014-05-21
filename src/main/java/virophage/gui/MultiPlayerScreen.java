package virophage.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import virophage.Start;
import virophage.core.MachinePlayer;
import virophage.core.Player;

/**
 * @author Max Ovsiankin and Leon Ren
 * @since 2014-05-6
 */
public class MultiPlayerScreen extends JPanel implements ActionListener{
	
	private JButton b1;
	private JButton b2;
	private JButton buttonBack, buttonContinue;
    private GameClient w;
    private ArrayList<Player> humanPlayers;
    private ArrayList<MachinePlayer> machinePlayers;
    private final static int TOTAL_NUM_PLAYERS = 10;
	
	public MultiPlayerScreen(GameClient g) {
		w = g;
		humanPlayers = new ArrayList<Player>();
		machinePlayers = new ArrayList<MachinePlayer>();
        this.setLayout(null);
        
		buttonBack = new JButton("Back");
		Font f = new Font("Verdana", Font.ITALIC|Font.BOLD, 16);
        buttonBack.setFont(f);
        buttonBack.addActionListener(this);    
        add(buttonBack);
        
        buttonContinue = new JButton("Start");
		buttonContinue.setFont(f);
		buttonContinue.addActionListener(this);    
        add(buttonContinue);
        		
        f = new Font("Courier", Font.BOLD, 26);
		b1 = new JButton("Add Machine Player");
		b1.setFont(f);
		b1.addActionListener(this);
		b2 = new JButton("Add Human Player");
		b2.addActionListener(this);
		b2.setFont(f);
		add(b1);
		add(b2);
		
        
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int x = this.getWidth();
        int y = this.getHeight();
      
        g.setColor(Color.WHITE);
        g.fillRect(x/ 12, y / 15, x/3, y/2);
        
        Font f = new Font("arial", Font.PLAIN, 16);
        g.setFont(f);
        g.setColor(Color.BLACK);
        int i = 1;
        for (MachinePlayer p : machinePlayers) {
        	g.drawString("Machine Player: " + i++, x/8, i*y/20);
        }
        int j = i;
        for (Player p : humanPlayers) {
        	int k = i++ - j + 1;
        	g.drawString("Human Player: " + k, x/8,  i*y/25);
        }
        
        b1.setBounds(x- x/3, y/3, 300, 30);
        b2.setBounds(x -x/3, y *2/5, 300, 30);
        buttonBack.setBounds(x/15, y - y/10, 100, 30);
        buttonContinue.setBounds(x- x/7, y - y/10, 150, 30);
        
        
    }
	
	

		@Override
		public void actionPerformed(ActionEvent e) {
			Object x = e.getSource();
			if(x == buttonBack){
				w.changePanel("menuScreen");
			} else if (x == buttonContinue) {
				// multiplayer game starts
				Start.log.info("Multiplayer Game will start now!");
			} else if (x == b1) {
				if (machinePlayers.size() + humanPlayers.size() < TOTAL_NUM_PLAYERS) {
					machinePlayers.add(new MachinePlayer());
					repaint();
				}
			} else if (x == b2) {
				if (machinePlayers.size() + humanPlayers.size() < TOTAL_NUM_PLAYERS) {
					humanPlayers.add(new Player());
					repaint();
				}
			}
		}


}