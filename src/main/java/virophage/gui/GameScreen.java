package virophage.gui;

import virophage.Start;
import virophage.core.*;
import virophage.game.Game;
import virophage.util.GameConstants;
import virophage.util.HexagonConstants;
import virophage.util.Location;
import virophage.util.Vector;

import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;

/**
 * A <code>GameScreen</code> is a GUI component that renders the game.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class GameScreen extends Canvas implements Runnable {

    public double zoom = 1;
    public Vector displacement = new Vector(0, 0);
    private Game game;
    private GameScreenListener listener;

    public static Timer timer = new Timer();
    public static Selection selection = new Selection();

    /**
     * Constructs a RenderTree and adds the listeners.
     */
    public GameScreen() {
        setIgnoreRepaint(true);
        setFocusable(true);

        listener = new GameScreenListener(this);

        addKeyListener(listener);

        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);
    }

    public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	/**
     * Draws the graphics for a Channel.
     *
     * @param channel the channel
     * @param g       a graphics object
     */
    public void drawChannel(Channel channel, Graphics2D g) {
        // loop caluclations
        int rad = (int) (HexagonConstants.RADIUS * 2 * 0.80);
        int pos = rad / 2;
        Vector center = new Vector(HexagonConstants.RADIUS, HexagonConstants.TRI_HEIGHT);
        Vector length = new Vector(HexagonConstants.TRI_HEIGHT * 2, 0)
                .rotate(channel.from.getDirectionTowards(channel.to));
        double mult = (rad / 2 + 5) / length.magnitude();
        Vector startpoint = length.scale(mult).add(center);
        Vector endpoint = length.add(center);

        // draw line with masking circle at end
        g.setColor(channel.player.getColor().darker().darker());
        g.setStroke((new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)));
        g.drawLine((int) startpoint.x, (int) startpoint.y, (int) endpoint.x, (int) endpoint.y);

        // draw loop
        g.setColor(channel.player.getColor().darker().darker().darker().darker());
        g.setStroke(new BasicStroke(3));
        g.drawOval((int) center.x - pos, (int) center.y - pos, rad, rad);

        // draw endpoint
        g.setColor(channel.player.getColor().darker().darker());
        g.setStroke(new BasicStroke());
        if (channel.hasVirus()) {
            Virus v = channel.virus;
            double circleRadius = (v.getEnergy() / (double) GameConstants.MAX_ENERGY) *
                    (HexagonConstants.RADIUS * 0.7);
            int circleDiameter = (int) (circleRadius * 2);
            int x = (int) (endpoint.x - circleRadius);
            int y = (int) (endpoint.y - circleRadius);
            g.fillOval(x, y, circleDiameter, circleDiameter);
        }
    }

    /**
     * Draw the graphics for a Cell.
     *
     * @param cell the cell
     * @param g    a Graphics object
     */
    public void drawCell(Cell cell, Graphics2D g) {
        Polygon hexagon = new Polygon(new int[]{
                (int) (HexagonConstants.RADIUS / 2),
                (int) (HexagonConstants.RADIUS * 3 / 2),
                (int) (HexagonConstants.RADIUS * 2),
                (int) (HexagonConstants.RADIUS * 3 / 2),
                (int) (HexagonConstants.RADIUS / 2),
                0
        }, new int[]{
                0,
                0,
                (int) HexagonConstants.TRI_HEIGHT,
                (int) (HexagonConstants.TRI_HEIGHT * 2),
                (int) (HexagonConstants.TRI_HEIGHT * 2),
                (int) HexagonConstants.TRI_HEIGHT
        }, 6);

        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        //FontMetrics fm = g.getFontMetrics(g.getFont());
        Virus occupant = cell.getOccupant();
        if (occupant != null) {
            Color light = occupant.getPlayer().getColor();
            Color dark = light.darker();
            if (selection.isFrom(cell)) {
                light = Color.GRAY;
            } else if (selection.isPossible(cell)) {
                light = Color.LIGHT_GRAY;
            }
            if (cell instanceof BonusCell){
            	g.setColor(Color.YELLOW);
            }
            else{
            	g.setColor(light);
            }
            
            g.fillPolygon(hexagon);
            g.setColor(dark);
            double circleRadius = (occupant.getEnergy() / (double) GameConstants.MAX_ENERGY) *
                    (HexagonConstants.RADIUS * 0.7);
            int circleDiameter = (int) (circleRadius * 2);
            int x = (int) (HexagonConstants.RADIUS - circleRadius);
            int y = (int) (HexagonConstants.TRI_HEIGHT - circleRadius);
            Ellipse2D ellipse = new Ellipse2D.Double(x, y, circleDiameter, circleDiameter);
            g.fill(ellipse);
        } else {
            if (cell instanceof DeadCell) {
                g.setColor(Color.BLACK);
            }
            else if (cell instanceof BonusCell){
            	g.setColor(Color.YELLOW);
            	g.setStroke(new BasicStroke(13));
            	g.drawPolygon(hexagon);
            	return;
            }
            else {
                if (selection.isFrom(cell)) {
                    g.setColor(Color.GRAY);
                } else if (selection.isPossible(cell)) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.WHITE);
                }
            }
            g.fillPolygon(hexagon);
        }

        //g.setStroke(new BasicStroke(4));
        g.setColor(new Color(32, 32, 32));
        g.drawPolygon(hexagon);
        g.setColor(Color.BLACK);
        //int x = (int) (HexagonConstants.RADIUS - fm.stringWidth(loc.toString()) / 2);
        //int y = (int) (HexagonConstants.TRI_HEIGHT);
//        g.setColor(Color.RED);
//        g.drawString(cell.location.toString(),10, 100);
        if (cell.getOccupant() != null) {
            Virus tempV = cell.getOccupant();
            String s;
            s = tempV.getPlayer().getName();
            g.drawString(s, 100, 100);
        }
    }

    private Graphics2D createGraphics(Graphics g, Vector transform) {
        Point point = transform.toPoint();
        Graphics2D gNew = (Graphics2D) g.create();
        gNew.translate(point.x, point.y);
        return gNew;
    }

    /**
     * Render this GameScreen for one frame.
     *
     * @param gr a Graphics object.
     */
    public void render(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        Tissue tissue = game.getTissue();
        // makes the game look really nice, but also really slow
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(230, 230, 230));
        g.fillRect(0, 0, getWidth(), getHeight());

        AffineTransform at = new AffineTransform();
        at.scale(zoom, zoom);
        at.translate(displacement.x, displacement.y);
        g.setTransform(at);

        for(Cell cell: tissue.flatCells()) {
            Location loc = cell.location;
            drawCell(tissue.getCell(loc), createGraphics(g, loc.asCoordinates()));
        }

        for (Channel c : tissue.getChannels()) {
            drawChannel(c, createGraphics(g, c.from.asCoordinates()));
        }

        int x = this.getWidth();
        int y = this.getHeight();
        
        Font f = new Font("arial", Font.BOLD, 20);
        g.setFont(f);
        Player[] p = tissue.getPlayers();
        g.setTransform(new AffineTransform());
        g.setColor(Color.GRAY);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 9 * 0.1f));
        g.fillRect(0, y - 40, x, y);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("NumPlayers: " + p.length, 10, y - 10);
        f = new Font("arial", Font.PLAIN, 18);
        g.setFont(f);
        int spaceBetween = (x - 200)/(p.length);
        int i = 0;
        for (Player myPlayer: p){
        	g.setColor(myPlayer.getColor().brighter().brighter());
        	g.drawString(myPlayer.getName() + ": " + myPlayer.getViruses().size() + " cells", 280 + i * spaceBetween, y - 10);
        	i ++;
        }
//        g.setColor(Color.RED);
//        int virusSize = p[1].getViruses().size();
//        String vSize = virusSize + "";
//        g.drawString("Red: #cells - " + virusSize + "", x - 128 - 15 * vSize.length(), y - 10);
        g.setTransform(new AffineTransform());
        if(game.isGameEnded()) {
            g.setColor(new Color(50, 50, 50, 200));
            g.fillRect(100, 100, x - 200, y - 200);
            Font font = new Font("SansSerif", Font.BOLD, 96);
            g.setFont(font);
            g.setColor(Color.WHITE);
            FontMetrics fm = g.getFontMetrics();
            String s1 = "Victory!";
            int sw = fm.stringWidth(s1) / 2;
            int sh = fm.getHeight();
            g.drawString(s1, x / 2 - sw, 100 + sh);
            g.draw3DRect(x / 2 - 50, y / 2 - 25, 100, 50, true);
        }
    }

    /**
     * Run this GameScreen as per Runnable.
     */
    @Override
    public void run() {
        this.createBufferStrategy(2);
        BufferStrategy strategy = this.getBufferStrategy();
        while (true) {
            do {
                do {
                    Graphics gr = strategy.getDrawGraphics();
                    render(gr);
                    gr.dispose();
                } while (strategy.contentsRestored());

                strategy.show();
            } while (strategy.contentsLost());
        }
    }

    /**
     * Starts the game by placing the players and dead cells.
     *
     * @param players the list of players
     * @param gameClient
     */
    public void gameStart(java.util.List<Player> players, GameClient gameClient) {
        Start.log.info("Game Started!");

        Cell[][] cells = new Cell[2 * GameConstants.N + 1][2 * GameConstants.N + 1];
        Tissue t = new Tissue(cells, this);
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

        Game game = new Game(t, gameClient);
        setGame(game);
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
        listener.setTissue(t);

        (new Thread(this)).start();
        game.setGameStarted(true);
    }
}
