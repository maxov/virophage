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
public class InstructionScreen extends JPanel{
	
	private ButtonGroup group;
	private JRadioButton b1;
	private JRadioButton b2;
	private JPanel buttonPanel;
    private GameClient w;
	
	public InstructionScreen(GameClient g) {
		w = g;
		buttonPanel = new JPanel();
		setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);
		group = new ButtonGroup();
		b1 = new JRadioButton("Single Player", true);
		b1.setBackground(Color.WHITE);
		b1.setSelected(true);
		b1.addActionListener(new RadioButtonHandler());
		b2 = new JRadioButton("Multi-Player (with networking)");
		b2.addActionListener(new RadioButtonHandler());
		b2.setBackground(Color.WHITE);
		group.add(b1);
		group.add(b2);
		buttonPanel.add(b1);
		buttonPanel.add(b2);
		
		add(buttonPanel, BorderLayout.SOUTH);
        
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int x = this.getWidth();
        int y = this.getHeight() - 90;
        
        // Draw the Game Title
        Font f = new Font("arial", Font.BOLD, 30);
        g.setFont(f);
        g.drawString("Virophage", x / 3 + x/10 , y / 4);

        //Draw the Instructions
        f = new Font("calibri", Font.PLAIN, 18);
        g.setFont(f);
        g.drawString("You are a virophage, a virus that can infect other viruses. Your objective is to wipe out all of your opponent's virophages.", x/8, y/4 + y/ 10);
        f = new Font("calibri", Font.BOLD, 18);
        g.setFont(f);
        g.drawString("Instructions", x/7, y/4 + y/6);
        f = new Font("calibri", Font.PLAIN, 18);
        g.setFont(f);
        g.drawString("1. To navigate the map, ctrl + click to pan, scroll with mouse wheel to zoom.", x/8, y/4 + 2*y/9);
        g.drawString("2. To create a channel, click with your mouse button a cell and drag to one of the highlighted gray cells.", x/8, y/4 + y/15+ 2*y/9);
        g.drawString("3. Dead cells (marked black) are not accessible.", x/8, y/4 + 2*y/15+ 2*y/9);
        g.drawString("4. Energy will automatically be transferred through a channel from a higher energy cell to a lower energy cell.", x/8, y/4 + 3*y/15+ 2*y/9);
        g.drawString("5. The energy of all cells will increase by 1 after every 10 seconds.", x/8, y/4 + 4*y/15+ 2*y/9);
        g.drawString("6. To infect an enemy cell, create a channel to it. Once the energy in the channel is greater than the energy of the cell, it is yours!", x/8, y/4 + 5*y/15+ 2*y/9);
        

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
			}
			else if (x == b2){
				//multiplayer
			}
		}
	}

}
