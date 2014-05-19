package virophage.render;

import virophage.core.Cell;
import virophage.core.Player;
import virophage.util.Location;
import virophage.core.Tissue;
import virophage.util.Vector;

import java.util.Timer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A <code>RenderTree</code> contains an array of renderNodes, it is also a GUI componet.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class RenderTree extends Canvas implements Runnable {

    public double zoom = 1;
    public Vector displacement = new Vector(0, 0);
    private Tissue tissue;
    
    public static Timer timer = new Timer();

    public ArrayList<RenderNode> nodes = new ArrayList<RenderNode>();

    /**
     * Constructs a RenderTree and adds the listeners.
     */
    public RenderTree() {
        setIgnoreRepaint(true);
        setFocusable(true);
        requestFocus();

        TreeListener listener = new TreeListener(this);


        addKeyListener(listener);

        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);
    }

    public synchronized void add(RenderNode node) {
        nodes.add(node);
        node.setRenderTree(this);
        Collections.sort(nodes);
    }

    public void setTissue(Tissue t) {
        tissue = t;
    }

    public Tissue getTissue() {
        return tissue;
    }

    /**
     * Establishes a connection between a cell and a RenderNode.
     * @param c the cell to be saved in the HexagonNode
     * @param xPos the x Coordinate of the node
     * @param yPos the y Coordinate of the node
     */
    public void saveCellInNode(Cell c, int xPos, int yPos) {
        Location other = new Location(xPos, yPos);
        for (RenderNode node : nodes) {
            if (((HexagonNode) node).getLocation().equals(other)) {
                ((HexagonNode) node).setCell(c);
            }
        }
    }

    public void render(Graphics gr) {
        long t1 = System.nanoTime();
        Graphics2D g = (Graphics2D) gr;
        // makes the game look really nice, but also really slow
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform at = new AffineTransform();
        at.translate(displacement.x * zoom, displacement.y * zoom);
        at.scale(zoom, zoom);
        g.setColor(new Color(230, 230, 230));
        g.fillRect(0, 0, getWidth(), getHeight());

        if (nodes != null) {
            int i = 0;
            while (i < nodes.size()) {
                RenderNode node = nodes.get(i);
                Vector vec = node.getPosition();
                AffineTransform nodeTransform = new AffineTransform(at);
                nodeTransform.translate(vec.x, vec.y);

                Graphics2D nodeGraphics = (Graphics2D) g.create();
                nodeGraphics.transform(nodeTransform);

                node.render(nodeGraphics);
                i++;
            }
        }
        //Start.log.info("TIME " + ((System.nanoTime() - t1) / 1000000d));
        int x = this.getWidth();
        int y = this.getHeight();
        g.setColor(Color.GREEN);
        Font f = new Font("arial", Font.BOLD, 30);
        g.setFont(f);
        Player[] p = tissue.getPlayers();
        g.drawString(p[0].getViruses().size() + "", 10, y - 20);
        g.setColor(Color.RED);
        int virusSize = p[1].getViruses().size();
        g.drawString(virusSize + "", x - 40, y - 20);
    }

    @Override
    public void run() {
        this.createBufferStrategy(2);
        BufferStrategy strategy = this.getBufferStrategy();
        while(true) {
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
}
