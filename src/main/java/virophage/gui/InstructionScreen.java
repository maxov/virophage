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
public class InstructionScreen extends TextScreen {
	
	public InstructionScreen(GameClient g) {
		super(g);
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int x = this.getWidth();
        int y = this.getHeight();
        buttonBack.setBounds(x/10, y - y/10, 100, 30);
        y -= 90;
        
        // Draw the Game Title
        Font f = new Font("arial", Font.BOLD, 30);
        g.setFont(f);
        g.drawString("Virophage - Instructions", x / 3 , y / 4);

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

}
