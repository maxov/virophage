package virophage.gui;

import virophage.Start;
import virophage.render.HexagonNode;
import virophage.render.RenderNode;
import virophage.render.RenderTree;

import javax.swing.*;

import java.awt.*;

public class GameClient extends JFrame {

    public static final Dimension SIZE = new Dimension(1280, 720);
    JPanel cardPanel;

    public GameClient() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        RenderTree tree = new RenderTree();

        int N = 10;
       
        for(int x = -N; x <= N; x++) {
            for(int y = -N; y <= N; y++) {
                for(int z = -N; z <= N; z++) {
                    if(x + y + z == 0)
                        tree.add(new HexagonNode(x, y));
                }
            }
        }

        tree.updateNodes();

        add(tree, BorderLayout.CENTER);

        Start.log.info("Setting frame visible");
	    cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
		OptionPanel panel1 = new OptionPanel(this);    
	    	
	    cardPanel.add(panel1,"1");
	    cardPanel.add(tree,"2");
	    
	    add(cardPanel);
	
	    setVisible(true);
        
        //create tissue here?
    }
    
  
	public void changePanel() {
		((CardLayout)cardPanel.getLayout()).next(cardPanel);
		requestFocus();
	}

}
