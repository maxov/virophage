package virophage.gui;

import virophage.Start;
import virophage.core.*;
import virophage.game.Game;
import virophage.util.GameConstants;
import virophage.util.Location;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <code>GameClient</code> is responsible for (among other things), the GUI of the game.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class GameClient extends JFrame {

    public static final Dimension SIZE = new Dimension(1280, 720);
    private int count = 0;
    
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    private GameScreen gameScreen;
    private Player players[];
    private Game game;
    
    /**
     * Constructs a GameClient.
     */
    public GameClient() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameScreen = new GameScreen();

        add(gameScreen, BorderLayout.CENTER);

        Start.log.info("Setting frame visible");
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        MenuScreen menuScreen = new MenuScreen(this);
        InstructionScreen instructionPanel = new InstructionScreen(this);
        CreditsScreen creditsPanel = new CreditsScreen(this);
        LobbyScreen multiPlayerPanel = new LobbyScreen(this);
        WinScreen winPanel = new WinScreen(this);
        LoseScreen losePanel = new LoseScreen(this);

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
        Cell[][] cells = new Cell[2 * GameConstants.N + 1][2 * GameConstants.N + 1];
        Tissue tissue = new Tissue(cells, gameScreen);
        for (int i = -GameConstants.N; i <= GameConstants.N; i++) {
            for (int j = -GameConstants.N; j <= GameConstants.N; j++) {
                for (int k = -GameConstants.N; k <= GameConstants.N; k++) {
                    if (i + j + k == 0) {
                        Location loc = new Location(i, j);
                        tissue.setCell(loc, new Cell(tissue, loc));
                        count ++;
                    }
                }
            }
        }
        players = new Player[GameConstants.TOTAL_NUM_PLAYERS + 1];
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
        cardLayout.show(cardPanel, s);
        requestFocus();
    }

    /**
     * Starts the game by placing the players and dead cells.
     *
     * @param list the array of human players
     */
    public void gameStart(List<Player> list) {
        Start.log.info("Game Started!, num of players is: " + list.size());

        Cell[][] cells = new Cell[2 * GameConstants.N + 1][2 * GameConstants.N + 1];
        Tissue t = new Tissue(cells, gameScreen);
        for (int i = -GameConstants.N; i <= GameConstants.N; i++) {
            for (int j = -GameConstants.N; j <= GameConstants.N; j++) {
                for (int k = -GameConstants.N; k <= GameConstants.N; k++) {
                    if (i + j + k == 0) {
                        Location loc = new Location(i, j);
                        t.setCell(loc, new Cell(t, loc));
                    }
                }
            }
        }
        
        game = new Game(t, this);
        gameScreen.setGame(game);


        // TODO fix this
        for (int ii = 0; ii < list.size(); ii++) {
        	setPlayer(ii, (Player)list.get(ii));
        	((Player)list.get(ii)).setTissue(t);
        	t.addPlayer(((Player)list.get(ii)));
        
	        // adds some viruses for players
	        for (int i = -1; i <= 1; i++) {
	            for (int j = -1; j <= 1; j++) {
	                for (int k = -1; k <= 1; k++) {
	                    if (i + j + k == 0) {
	                    	Location loc1 = null;
	                    	if (ii < 4) {
	                    		loc1 = new Location(i - 9 + ii*6, j);
	                    	} else if (ii < 8) {
	                    		loc1 = new Location(i, j - 9 + (ii -4)*6);
	                    	} else if (ii < 12) {
	                    		loc1 = new Location(i - 9 + (ii-8)*6, j + 9 - (ii -8)*6);
	                    	}
	                        Virus v1 = new Virus(players[ii], 4);
	                        players[ii].addVirus(v1);
	                        t.getCell(loc1).setOccupant(v1);
	                        v1.setCell(t.getCell(loc1));
	                        v1.schedule();
	
//	                        Location loc2 = new Location(i + 3, j);
//	                        Virus v2 = new Virus(players[1], 4);
//	                        players[1].addVirus(v2);
//	                        t.getCell(loc2).setOccupant(v2);
//	                        v2.setCell(t.getCell(loc2));
//	                        v2.schedule();
	                    }
	                }
	            }
	        }
        }
//        Start.log.info("f of Cells: " + count);
        //set bonus cells
        Location loc = new Location(0, 0);
        if (t.getCell(loc) != null){
        	ArrayList<Location> centerLocs = loc.getNeighbors();
        	for (Location l : centerLocs){
        		t.setCell(l, new BonusCell(t, l));
        	}
            t.setCell(loc, new BonusCell(t, loc));
        }
        
        //place dead cells in the renderTree
        int dead = 0;
        while (dead < GameConstants.DEAD_CELL_NUM) {
            Random rand = new Random();
            int xPos = rand.nextInt(GameConstants.N * 2 + 1) - GameConstants.N;
            int yPos = rand.nextInt(GameConstants.N * 2 + 1) - GameConstants.N;

            loc = new Location(xPos, yPos);
            if (t.getCell(loc) != null &&
                    t.getCell(loc).occupant == null && !(t.getCell(loc) instanceof BonusCell)) {
                t.setCell(loc, new DeadCell(t, loc));
                dead++;
            }
        }

        count -= GameConstants.DEAD_CELL_NUM;
        
        
        
        (new Thread(gameScreen)).start();
        game.setGameStarted(true);
    }
    
    public int getNumberCellsCount (){
    	return count;
    }
    
    public Game getGame() {
    	return game;
    }

}
