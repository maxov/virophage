package virophage.render;

import virophage.core.Cell;
import virophage.core.DeadCell;
import virophage.core.Location;
import virophage.core.Virus;
import virophage.gui.GameClient;
import virophage.util.HexagonConstants;
import virophage.util.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;

/**
 * A <code>HexagonNode</code> is a graphical representation of a cell. It can be scaled.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class HexagonNode extends RenderNode {

    private Location loc;
    private Cell cell;

    private Color color;
    private Polygon hexagon;

    /**
     * Constructs a <code>HexagonNode</code> at the Location loc.
     * @param loc The locaiton this node is supposed to represent.
     */
    public HexagonNode(Location loc) {
        this.loc = loc;

        color = Color.WHITE;
        hexagon = new Polygon(new int[]{
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
    }

    public Location getLocation() {
        return loc;
    }

    public void setCell(Cell c) {
        cell = c;
        if (cell != null && cell instanceof DeadCell) {
            color = Color.BLACK;
        } else {
            color = Color.WHITE;
        }
    }

    public void setColor(Color c) {
        color = c;
    }

    public Vector getPosition() {
        return loc.asCoordinates();
    }

    public Shape getCollision() {
        return new Polygon(hexagon.xpoints, hexagon.ypoints, hexagon.npoints);
    }

    public void onClick(MouseEvent e) {
        if (cell != null && cell instanceof DeadCell) {
            return;
        }

        if (cell == null) {
            ArrayList<Location> listOfNeighborLocs = loc.getNeighbors();
        }

        if (e.isShiftDown()) {
            color = new Color(200, 250, 200);
        } else {
            color = new Color(250, 200, 200);
        }
    }

    /**
     * The "drawing" of the hexagons with colors.
     */
    public void render(Graphics2D g) {
        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        //FontMetrics fm = g.getFontMetrics(g.getFont());
        if (cell != null) {
            Virus occupant = cell.getOccupant();
            if (occupant != null) {
                Color light = occupant.getPlayer().getColor();
                Color dark = light.darker();
                g.setColor(light);
                g.fillPolygon(hexagon);
                g.setColor(dark);
                double circleRadius = (occupant.getEnergy() / (double) GameClient.MAX_ENERGY) *
                        (HexagonConstants.RADIUS * 0.7);
                int circleDiameter = (int) (circleRadius * 2);
                int x = (int) (HexagonConstants.RADIUS - circleRadius);
                int y = (int) (HexagonConstants.TRI_HEIGHT - circleRadius);
                g.fillOval(x, y, circleDiameter, circleDiameter);
            } else {
                if (cell instanceof DeadCell) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(color);
                }
                g.fillPolygon(hexagon);
            }
        } else {
            g.setColor(color);
            g.fillPolygon(hexagon);
        }

        //g.setStroke(new BasicStroke(4));
        g.setColor(new Color(32, 32, 32));
        g.drawPolygon(hexagon);
        g.setColor(Color.BLACK);
        //int x = (int) (HexagonConstants.RADIUS - fm.stringWidth(loc.toString()) / 2);
        //int y = (int) (HexagonConstants.TRI_HEIGHT);
        //g.drawString(loc.toString(), x, y);

    }

}
