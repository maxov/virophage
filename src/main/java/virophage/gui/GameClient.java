package virophage.gui;

import virophage.Start;
import virophage.core.Cell;
import virophage.core.DeadCell;
import virophage.core.Location;
import virophage.core.Player;
import virophage.core.Tissue;
import virophage.core.Virus;
import virophage.render.HexagonNode;
import virophage.render.RenderNode;
import virophage.render.RenderTree;

import javax.swing.*;

import java.awt.*;

public class GameClient extends JFrame {

    public static final Dimension SIZE = new Dimension(1280, 720);
    public static final int DEAD_CELL_NUM = 80;
    public static final int N = 10; // max location coordinate
    public static final int MAX_ENERGY = 30;
    JPanel cardPanel;
    RenderTree tree;
    Player players[];

    public GameClient() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tree = new RenderTree();

        for (int x = -N; x <= N; x++) {
            for (int y = -N; y <= N; y++) {
                for (int z = -N; z <= N; z++) {
                    if (x + y + z == 0)
                        tree.add(new HexagonNode(new Location(x, y)));
                }
            }
        }

        add(tree, BorderLayout.CENTER);

        Start.log.info("Setting frame visible");
        cardPanel = new JPanel();
        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl);

        OptionPanel panel1 = new OptionPanel(this);

        cardPanel.add(panel1, "1");
        cardPanel.add(tree, "2");

        add(cardPanel);

        setVisible(true);

        //create tissue here
        Cell[][] cells = new Cell[2*N + 1][2*N + 1];
        Tissue tissue = new Tissue(cells,tree);
        tree.setTissue(tissue);
        
        players = new Player[2];
    }


    public void changePanel() {
        ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        requestFocus();
    }

    public void gameStart() {
    	Start.log.info("Game Started!");
    	
    	// First place dead cells in the tree
    	DeadCell dc = new DeadCell();
    	int i = 0;
    	while (i < DEAD_CELL_NUM) {
    		int xPos = (int)(Math.random() * N * 2 + 1) - N;
    		if (xPos == -N) {
    			xPos++;
    		}
    		if (xPos == N) {
    			xPos--;
    		}
    		int yPos = (int)(Math.random() * N * 2 + 1) - N;
    		
    		Location loc = new Location(xPos, yPos);
    		if (loc.isValidLoc()) {
    			if (tree.getTissue().getCell(loc) == null) {
    				tree.getTissue().setCell(loc, dc);
    				tree.saveCellInNode(dc, xPos, yPos);
    				i++;
    			}
    		}
    	}
    	
    	// Second create two players
    	for (i = 0; i < players.length; i++) {
    		players[i] = new Player(new Color(200 + i * 50, 250 - i * 50, 200));
    	}
    	
    	
    	// Third add some viruses for both players
    	for (i = 0; i <= N; i++) {
    		Location loc1 = new Location(-N, i);
    		Location loc2 = new Location(N, -i);
    		Virus v1 = new Virus(players[0], (int)(Math.random() * MAX_ENERGY));
    		Cell c1 = new Cell(v1);
    		Virus v2 = new Virus(players[1], (int)(Math.random() * MAX_ENERGY));
    		Cell c2 = new Cell(v2);
    		tree.getTissue().setCell(loc1, c1);
    		tree.saveCellInNode(c1, loc1.getX(), loc1.getY());
    		tree.getTissue().setCell(loc2, c2);
    		tree.saveCellInNode(c2, loc2.getX(), loc2.getY());
    	}
    }
}
