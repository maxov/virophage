package virophage.gui;

import virophage.Start;
import virophage.core.*;
import virophage.util.Location;
import virophage.render.RenderTree;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * <code>GameClient</code> is responsible for (among other things), the GUI of the game.
 * @author Leon Ren
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
    public final static int TOTAL_NUM_PLAYERS = 10;

    /**
     * Constructs a GameClient.
     */
    public GameClient() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        renderTree = new RenderTree(this);

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
        for(int i = -N; i <= N; i++) {
        	for(int j = -N; j <= N; j++) {
        		for(int k = -N; k <= N; k++) {
                	if(i + j + k == 0) {
                		Location loc = new Location(i, j);
                		tissue.setCell(loc, new Cell(tissue, loc));
                	}
                }
            }
        }
        renderTree.setTissue(tissue);

        players = new Player[TOTAL_NUM_PLAYERS + 1];
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
    public void gameStart(ArrayList<Player> humanPlayers) {
        Start.log.info("Game Started!");

        int i = 0;

        Tissue tissue = renderTree.getTissue();
        int count = 0;
        //creates two players
        for (i = 0; i <= humanPlayers.size(); i++) {
        	if (i == humanPlayers.size()) {
        		if ( players[i] != null){
        			continue;
        		}
        		else {
        			players[i] = new MachinePlayer(new Color(200 + i * 50, 250 - i * 50, 200), tissue);
        			players[i].setName("MachinePlayer");
        		}
        	} else {
        		players[i] = humanPlayers.get(i);
        	}
        }
        
//        Start.log.info("Number of Cells: " + count);

        // adds some viruses for both players
        for (i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i + j + k == 0) {
                        Location loc1 = new Location(i - 3, j);
                        Virus v1 = new Virus(players[0], 4);
                        tissue.getCell(loc1).setOccupant(v1);
                        v1.setCell(tissue.getCell(loc1));
                        
                        Location loc2 = new Location(i + 3, j);
                        Virus v2 = new Virus(players[1], 4);
                        tissue.getCell(loc2).setOccupant(v2);
                    }
                }
            }
        }

        //place dead cells in the renderTree
        i = 0;
        while (i < DEAD_CELL_NUM) {
            Random rand = new Random();
            int xPos = rand.nextInt(N * 2 + 1) - N;
            int yPos = rand.nextInt(N * 2 + 1) - N;

            Location loc = new Location(xPos, yPos);
            if (renderTree.getTissue().getCell(loc) == null ||
            		renderTree.getTissue().getCell(loc).occupant == null) {
                renderTree.getTissue().setCell(loc, new DeadCell(tissue, loc));
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
