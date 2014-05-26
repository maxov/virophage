package virophage.gui;

import virophage.Start;
import virophage.core.*;
import virophage.game.Game;
import virophage.game.ServerGame;
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
            g.setColor(light);
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
        if(cell instanceof BonusCell) {
            g.setStroke(new BasicStroke(
                    5,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f,
                    new float[] {15.0f, 7.0f},
                    0));
            //g.setColor(Color.WHITE);
            g.setColor(new Color(180, 180, 180));
        }
        g.drawPolygon(hexagon);
        g.setColor(Color.BLACK);
        //int x = (int) (HexagonConstants.RADIUS - fm.stringWidth(loc.toString()) / 2);
        //int y = (int) (HexagonConstants.TRI_HEIGHT);
//        g.setColor(Color.RED);
//        g.drawString(cell.location.toString(),10, 100);
        if (cell.getOccupant() != null) {
            Virus tempV = cell.getOccupant();
            String s = tempV.getPlayer().getName();
            FontMetrics fm = g.getFontMetrics();
            g.drawString(s, 100 - fm.stringWidth(s) / 2, 100);
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
       // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
        Player[] p = tissue.getPlayerList();
        g.setTransform(new AffineTransform());
        g.setColor(Color.GRAY);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 9 * 0.1f));
        g.fillRect(0, y - 40, x, y);
        g.setColor(Color.WHITE);
        g.drawString("NumPlayers: " + p.length, 10, y - 10);
        f = new Font("arial", Font.PLAIN, 18);
        g.setFont(f);
        int spaceBetween = (x - 200)/(p.length);
        int i = 0;
        int numPlayersOut = 0;
        int winnerIndex = 0;
        for (Player myPlayer: p){
        	int size = myPlayer.getViruses().size();
        	g.setColor(myPlayer.getColor());
        	if (size == 0){
        		g.drawString(myPlayer.getName() + " is out!", x/6 + i * spaceBetween, y - 10);
        		numPlayersOut++;
        	}
        	else{
        		g.drawString(myPlayer.getName() + ": " + size  + " cells", x/6 + i * spaceBetween, y - 10);
        		winnerIndex = i;
        	}
        	i ++;
        }
        
       if (numPlayersOut == (p.length - 1)){
        	g.setColor(Color.GRAY);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 9 * 0.1f));
            g.fillRect(x / 5, y /5, x *3 / 5, y * 3 / 5);
            g.setColor(p[winnerIndex].getColor());
            f = new Font("arial", Font.BOLD, 32);
            g.setFont(f);
            g.drawString(p[winnerIndex].getName() + " Wins!", x * 3 / 7, y/2);
            g.setColor(Color.WHITE);
            f = new Font("arial", Font.PLAIN, 15);
            g.setFont(f);
            g.drawString("Press ESC to exit the game.", x * 3 / 7, y/2 + y/6);
        }
        g.setTransform(new AffineTransform());
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
     * @param game a ServerGame
     */
    public void gameStart(Game game) {
        Start.log.info("Game Started!");
        this.game = game;

        listener.setTissue(game.getTissue());

        (new Thread(this)).start();
    }

}
