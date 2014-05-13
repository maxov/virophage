package virophage.render;

import virophage.core.Cell;
import virophage.core.Location;
import virophage.util.Vector;

import java.awt.*;

public class HexagonNode extends RenderNode{

    public static final double RADIUS = 100;
    public static final double TRI_WIDTH = RADIUS;
    public static final double TRI_HEIGHT = (int) (TRI_WIDTH / 2 * Math.sqrt(3));

    public static final double BOUNDS_WIDTH = RADIUS * 2;
    public static final double BOUNDS_HEIGHT = TRI_WIDTH * 2;

    private int x;
    private int y;
    private Color c;
    private Cell space;

    public HexagonNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.c = Color.white;
    }

    public Dimension getPreferredSize() {
        return new Vector(BOUNDS_WIDTH + 1, BOUNDS_HEIGHT + 1).toDimension();
    }

    public Point getPreferredPosition() {
        return new Location(x, y).asCoordinates().toPoint();
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;

        RenderTree tree = getRenderTree();
        double zoom = tree.zoom;

        g.scale(zoom, zoom);

        Polygon hexagon = new Polygon(new int[] {
                (int) (RADIUS / 2),
                (int) (RADIUS * 3 / 2),
                (int) (RADIUS * 2),
                (int) (RADIUS * 3 / 2),
                (int) (RADIUS / 2),
                0
        }, new int[] {
                0,
                0,
                (int) TRI_HEIGHT,
                (int) (TRI_HEIGHT * 2),
                (int) (TRI_HEIGHT * 2),
                (int) TRI_HEIGHT
        }, 6);

        g.setColor(c);
        g.fillPolygon(hexagon);
        g.setColor(new Color(17, 17, 17));
        g.drawPolygon(hexagon);
        g.setColor(Color.BLACK);
        g.drawString("5", (int)RADIUS, (int)RADIUS);

    }

}
