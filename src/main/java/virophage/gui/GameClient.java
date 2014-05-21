package virophage.gui;

import virophage.Start;
import virophage.core.*;
import virophage.render.HexagonNode;
import virophage.render.RenderTree;
import virophage.util.Location;

import javax.swing.*;

import java.awt.*;
import java.util.Random;

/**
 * A <code>GameClient</code> is responsible for (among other things), the GUI of the game.
 * @author Max Ovsiankin and Leon Ren
 * @since 2014-05-6
 */
public class GameClient extends JFrame {

    public static final Dimension SIZE = new Dimension(1280, 720);
    public static final int DEAD_CELL_NUM = 120;
    public static final int N = 10; // max location coordinate
    public static final int MAX_ENERGY = 8;
    private JPanel cardPanel;
    private RenderTree renderTree;
    private MenuScreen menuScreen;
    private PlayScreen playScreen;
    private InstructionScreen instructionPanel;
    private CreditsScreen creditsPanel;
    private MultiPlayerScreen multiPlayerPanel;
    private Player players[];

    /**
     * Constructs a GameClient.
     */
    public GameClient() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        renderTree = new RenderTree(this);

        for (int x = -N; x <= N; x++) {
            for (int y = -N; y <= N; y++) {
                for (int z = -N; z <= N; z++) {
                    if (x + y + z == 0)
                        renderTree.add(new HexagonNode(new Location(x, y)));
                }
            }
        }

        add(renderTree, BorderLayout.CENTER);

        Start.log.info("Setting frame visible");
        cardPanel = new JPanel();
        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl);

        menuScreen = new MenuScreen(this);
        playScreen = new PlayScreen(this);
        instructionPanel = new InstructionScreen(this);
        creditsPanel = new CreditsScreen(this);
        multiPlayerPanel = new MultiPlayerScreen(this);
        

        cardPanel.add(menuScreen, "menuScreen"); 
        cardPanel.add(playScreen, "playScreen");
        cardPanel.add(renderTree, "renderTree");
        cardPanel.add(instructionPanel, "instructionScreen");
        cardPanel.add(creditsPanel, "creditsScreen");
        cardPanel.add(multiPlayerPanel, "multiplayerScreen");

        add(cardPanel);

        setVisible(true);

        //create tissue here
        Cell[][] cells = new Cell[2 * N + 1][2 * N + 1];
        Tissue tissue = new Tissue(cells, renderTree);
        renderTree.setTissue(tissue);

        players = new Player[2];
    }
    
    public void setPlayer(int i, Player p){
    	players[i] = p;
    }
    
    public Player getPlayer(int i) {
    	return players[i];
    }
    
    public RenderTree getTree(){
    	return renderTree;
    }

    /**
     * Changes the Panel
     */
	public void changePanel(String s) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel, s);
		requestFocus();
	}

    /**
     * Starts the game by placing the players and dead cells.
     */
    public void gameStart() {
        Start.log.info("Game Started!");

        int i = 0;

        Tissue tissue = renderTree.getTissue();
        int count = 0;
        //creates two players
        for (i = 0; i < players.length; i++) {
        	if (i == 1) {
        		if ( players[i] != null){
        			continue;
        		}
        		else {
        			players[i] = new MachinePlayer(new Color(200 + i * 50, 250 - i * 50, 200), tissue);
        		}
        	} else {
        		players[i] = new Player(new Color(200 + i * 50, 250 - i * 50, 200), tissue);
        	}
        }
        
        for (int x = -N; x <= N; x++) {
            for (int y = -N; y <= N; y++) {
                for (int z = -N; z <= N; z++) {
                    if (x + y + z == 0) {
                    	Cell c = new Cell(tissue);
                    	count ++;
                    	renderTree.saveCellInNode(c, x, y);
                    	tissue.setCell(new Location(x, y), c);

                    }
                }
            }
        }
        
//        Start.log.info("Number of Cells: " + count);

        // adds some viruses for both players
        for (i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i + j + k == 0) {
                        Location loc1 = new Location(i - 3, j);
                        Location loc2 = new Location(i + 3, j);
                        Virus v1 = new Virus(players[0], 4);
                       
                        Cell c1 = new Cell(tissue, v1);  
                        v1.setCell(c1);
                        Virus v2 = new Virus(players[1], 4);
                        
                        Cell c2 = new Cell(tissue, v2);
                        v2.setCell(c2);
//                       v1.schedule();
//                       v2.schedule();
                        renderTree.getTissue().setCell(loc1, c1);
                        renderTree.saveCellInNode(c1, loc1.getX(), loc1.getY());
                        renderTree.getTissue().setCell(loc2, c2);
                        renderTree.saveCellInNode(c2, loc2.getX(), loc2.getY());
                    }
                }
            }
        }

        //place dead cells in the renderTree
        DeadCell dc = new DeadCell(tissue);
        i = 0;
        while (i < DEAD_CELL_NUM) {
            Random rand = new Random();
            int xPos = rand.nextInt(N * 2 + 1) - N;
            int yPos = rand.nextInt(N * 2 + 1) - N;

            Location loc = new Location(xPos, yPos);
            if (renderTree.getTissue().getCell(loc) == null ||
            		renderTree.getTissue().getCell(loc).occupant == null) {
                renderTree.getTissue().setCell(loc, dc);
                renderTree.saveCellInNode(dc, xPos, yPos);
                i++;
            }
            
        }
        
        for (i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i + j + k == 0) {
                    	Location loc1 = new Location(i - 3, j);
                        Location loc2 = new Location(i + 3, j);
                        Cell c1 = renderTree.getTissue().getCell(loc1); 
                        Virus v1 = c1.getOccupant();
                        if (v1 != null)
                        	v1.schedule();
                        Cell c2 = renderTree.getTissue().getCell(loc2); 
                        Virus v2 = c2.getOccupant();
                        if (v2 != null)
                        	v2.schedule();
                    }
                }
            }
        }
        (new Thread(renderTree)).start();
    }

}
