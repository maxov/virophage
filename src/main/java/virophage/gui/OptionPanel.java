package virophage.gui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class OptionPanel extends JPanel implements ActionListener {
	
	GameClient w;
	
	public OptionPanel(GameClient w) {
		this.w = w;
		JButton button = new JButton("Press to Start!");
		button.addActionListener(this);
		add(button);
	}
	
	public void actionPerformed(ActionEvent e) {
		w.changePanel();
	}
	
}