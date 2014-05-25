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
        players = new Player[GameConstants.MAX_PLAYERS + 1];
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
     * @param players the list of players
     */
    public void gameStart(List<Player> players) {
        Start.log.info("Game Started!");

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

        // initialize player locations
        for(int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            p.setTissue(t);
            t.addPlayer(p);
            Location playerCenterLoc = null;
            switch(i) {
                case 0:
                    playerCenterLoc = new Location(0, -GameConstants.PLAYER_DISTANCE);
                    break;
                case 1:
                    playerCenterLoc = new Location(GameConstants.PLAYER_DISTANCE, -GameConstants.PLAYER_DISTANCE);
                    break;
                case 2:
                    playerCenterLoc = new Location(GameConstants.PLAYER_DISTANCE, 0);
                    break;
                case 3:
                    playerCenterLoc = new Location(0, GameConstants.PLAYER_DISTANCE);
                    break;
                case 4:
                    playerCenterLoc = new Location(-GameConstants.PLAYER_DISTANCE, GameConstants.PLAYER_DISTANCE);
                    break;
                case 5:
                    playerCenterLoc = new Location(-GameConstants.PLAYER_DISTANCE, 0);
                    break;
            }
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        if(x + y + z == 0) {
                            Virus v = new Virus(p, 4);
                            p.addVirus(v);
                            v.schedule();
                            Cell c = t.getCell(new Location(playerCenterLoc.x + x, playerCenterLoc.y + y));
                            c.setOccupant(v);
                            v.setCell(c);
                        }
                    }
                }
            }
        }
//        Start.log.info("f of Cells: " + count);
    
        game = new Game(t, this);
        gameScreen.setGame(game);   
        //set bonus cells
        Location loc = new Location(0, 0);
        if (t.getCell(loc) != null){
        	ArrayList<Location> centerLocs = loc.getNeighbors();
        	BonusCell b = null;
        	for (Location l : centerLocs){
        		b = new BonusCell(t, l);
        		t.setCell(l, b);
        		game.addBonusCell(b);
        	}
        	b = new BonusCell(t, loc);
            t.setCell(loc, b);
            game.addBonusCell(b);
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

        for(Player p: players) {
            if(p instanceof AIPlayer) {
                ((AIPlayer) p).schedule();
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
