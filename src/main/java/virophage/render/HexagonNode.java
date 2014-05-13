package virophage.render;

import virophage.core.Cell;
import virophage.core.Location;
import virophage.util.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class HexagonNode extends RenderNode{

    public static final double RADIUS = 100;
    public static final double TRI_WIDTH = RADIUS;
    public static final double TRI_HEIGHT = (int) (TRI_WIDTH / 2 * Math.sqrt(3));

    public static final double BOUNDS_WIDTH = RADIUS * 2;
    public static final double BOUNDS_HEIGHT = TRI_WIDTH * 2;

    private int x;
    private int y;
    private Cell space;

    private Color color;
    private Polygon hexagon;

    public HexagonNode(int x, int y) {
        this.x = x;
        this.y = y;

        color = Color.WHITE;
        hexagon = new Polygon(new int[] {
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
    }

    public Dimension getPreferredSize() {
        return new Vector(BOUNDS_WIDTH + 1, BOUNDS_HEIGHT + 1).toDimension();
    }

    public Point getPreferredPosition() {
        return new Location(x, y).asCoordinates().toPoint();
    }
    
    public Shape getCollision() {
    	Polygon poly = new Polygon(hexagon.xpoints, hexagon.ypoints, hexagon.npoints);
    	Point p = new Location(x, y).asCoordinates().toPoint();
    	poly.translate((int) p.getX(), (int) p.getY());
    	return poly;
    }
    
    public void onClick(MouseEvent e) {
    	if(e.isShiftDown()) {
    		color = new Color(200, 250, 200);
    	} else {
    		color = new Color(250, 200, 200);
    	}
		repaint();
	}

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;

        RenderTree tree = getRenderTree();
        double zoom = tree.zoom;

        g.scale(zoom, zoom);
        g.setColor(color);
        g.fillPolygon(hexagon);
        g.setColor(new Color(17, 17, 17));
        g.drawPolygon(hexagon);
        g.setColor(Color.BLACK);
        g.drawString("5", (int)RADIUS, (int)RADIUS);

    }

}
