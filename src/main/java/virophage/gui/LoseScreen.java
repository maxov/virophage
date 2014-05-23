package virophage.gui;

import java.awt.Font;
import java.awt.Graphics;

/**
 * 
 * @author Leon
 *
 */
public class LoseScreen extends TextScreen{

	/**
	 * Constructs a screen that inidicated to the user that he/she has won.
	 * @param g
	 */
	public LoseScreen(GameClient g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Paints this CreditsScreen.
     *
     * @param g a Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = this.getWidth();
        int y = this.getHeight();
        buttonBack.setBounds(x / 10, y - y / 10, 100, 30);
        y -= 90;

        // Draw the Title
        Font f = new Font("arial", Font.BOLD, 30);
        g.setFont(f);
        g.drawString("You Lose- Try again!", x / 3 + x / 10, y / 4);

        

    }

}
