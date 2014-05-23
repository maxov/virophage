package virophage.gui;

import virophage.Start;
import virophage.core.*;
import virophage.game.Game;
import virophage.util.Location;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * <code>GameClient</code> is responsible for (among other things), the GUI of the game.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class GameClient extends JFrame {

    public static final Dimension SIZE = new Dimension(1280, 720);
    public static final int DEAD_CELL_NUM = 60;
    public static final int N = 10; // max location coordinate
    public static final int MAX_ENERGY = 8;
    private int count = 0;
    private JPanel cardPanel;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private InstructionScreen instructionPanel;
    private CreditsScreen creditsPanel;
    private LobbyScreen multiPlayerPanel;
    private WinScreen winPanel;
    private LoseScreen losePanel;
    private Player players[];
    private boolean gameStarted = false;
    public final static int TOTAL_NUM_PLAYERS = 10;

    /**
     * Constructs a GameClient.
     */
    public GameClient() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameScreen = new GameScreen(this);

        add(gameScreen, BorderLayout.CENTER);

        Start.log.info("Setting frame visible");
        cardPanel = new JPanel();
        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl);

        menuScreen = new MenuScreen(this);
        instructionPanel = new InstructionScreen(this);
        creditsPanel = new CreditsScreen(this);
        multiPlayerPanel = new LobbyScreen(this);
        winPanel = new WinScreen(this);
        losePanel = new LoseScreen(this);

        cardPanel.add(menuScreen, "menuScreen");
        cardPanel.add(gameScreen, "renderTree");
        cardPanel.add(instructionPanel, "instructionScreen");
        cardPanel.add(creditsPanel, "creditsScreen");
        cardPanel.add(multiPlayerPanel, "multiplayerScreen");
        cardPanel.add(winPanel, "winScreen");
        cardPanel.add(losePanel, "loseScreen");

        add(cardPanel);

        setVisible(true);

        //create tissue here
        Cell[][] cells = new Cell[2 * N + 1][2 * N + 1];
        Tissue tissue = new Tissue(cells, gameScreen);
        for (int i = -N; i <= N; i++) {
            for (int j = -N; j <= N; j++) {
                for (int k = -N; k <= N; k++) {
                    if (i + j + k == 0) {
                        Location loc = new Location(i, j);
                        tissue.setCell(loc, new Cell(tissue, loc));
                        count ++;
                    }
                }
            }
        }
        players = new Player[TOTAL_NUM_PLAYERS + 1];
    }

    public void setPlayer(int i, Player p) {
        players[i] = p;
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Changes the Panel.
     *
     * @param s the panel to change to.
     */
    public void changePanel(String s) {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, s);
        requestFocus();
    }

    /**
     * Starts the game by placing the players and dead cells.
     *
     * @param humanPlayers the array of human players
     */
    public void gameStart(ArrayList<Player> humanPlayers) {
        Start.log.info("Game Started!");

	Cell[][] cells = new Cell[2 * N + 1][2 * N + 1];
        Tissue t = new Tissue(cells, gameScreen);
        for (int i = -N; i <= N; i++) {
            for (int j = -N; j <= N; j++) {
                for (int k = -N; k <= N; k++) {
                    if (i + j + k == 0) {
                        Location loc = new Location(i, j);
                        t.setCell(loc, new Cell(t, loc));
                    }
                }
	Tissue tissue = gameScreen.getTissue();
        
        //creates two players
        for (int i = 0; i <= humanPlayers.size(); i++) {
            if (i == humanPlayers.size()) {
                if (players[i] != null) {
                    continue;
                } else {
                    players[i] = new AIPlayer(new Color(200 + i * 50, 250 - i * 50, 200), tissue);
                    players[i].setName("MachinePlayer");                    
                }
            } else {
   
		}
        }
        Game game = new Game(t);
        gameScreen.setGame(game);


        // adds some viruses for both players
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i + j + k == 0) {
                        Location loc1 = new Location(i - 3, j);
                        Virus v1 = new Virus(players[0], 4);
                        players[0].addVirus(v1);
                        t.getCell(loc1).setOccupant(v1);
                        v1.setCell(t.getCell(loc1));
                        v1.schedule();

                        Location loc2 = new Location(i + 3, j);
                        Virus v2 = new Virus(players[1], 4);
                        players[1].addVirus(v2);
                        t.getCell(loc2).setOccupant(v2);
                        v2.setCell(t.getCell(loc2));
                        v2.schedule();
                    }
                }
            }
        }
//        Start.log.info("f of Cells: " + count);

        //place dead cells in the renderTree
        int dead = 0;
        while (dead < DEAD_CELL_NUM) {
            Random rand = new Random();
            int xPos = rand.nextInt(N * 2 + 1) - N;
            int yPos = rand.nextInt(N * 2 + 1) - N;

            Location loc = new Location(xPos, yPos);
            if (t.getCell(loc) != null &&
                    t.getCell(loc).occupant == null) {
                t.setCell(loc, new DeadCell(t, loc));
                dead++;
            }
        }

        count -= DEAD_CELL_NUM;
        (new Thread(gameScreen)).start();
        gameStarted = true;
    }
    
    public void gameStop() {
    	for (int i = 0; i <(TOTAL_NUM_PLAYERS + 1); i++) {
    		if (players[i] != null) {
    			Start.log.info("going to destroy player" + i);
    			players[i].destroy();
    		}
    	}
    	gameScreen.getTissue().removeAllPlayers();
    	gameScreen.getTissue().removeAllCells();
    	
    	for (int i = 0; i <(TOTAL_NUM_PLAYERS + 1); i++) {
    		if (players[i] != null) {
    			players[i] = null;
    		}
    	}
    	gameStarted = false;
    }
    
    public boolean isGameStarted() {
    	return gameStarted;
    }
    
    public int getNumberCellsCount (){
    	return count;
    }

}
