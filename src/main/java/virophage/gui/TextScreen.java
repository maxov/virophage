package virophage.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A <code>TextScreen</code> represents a screen of the game with text on it.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class TextScreen extends JPanel implements ActionListener {

    protected JButton buttonBack;
    private GameClient w;

    /**
     * Constructs a TextScreen with a single back button.
     *
     * @param g a GameClient
     */
    public TextScreen(GameClient g) {
        w = g;
        setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        buttonBack = new JButton("Back");
        Font f = new Font("Verdana", Font.ITALIC | Font.BOLD, 16);
        buttonBack.setFont(f);
        buttonBack.addActionListener(this);
        add(buttonBack);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object x = e.getSource();
        if (x == buttonBack) {
            w.changePanel("menuScreen");
        }
    }
    
    public GameClient getGameClient() {
    	return w;
    }

}