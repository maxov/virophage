package virophage.render;

import virophage.core.Cell;
import virophage.core.Location;
import virophage.util.HexagonConstants;
import virophage.util.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HexagonNode extends RenderNode {

    private Location loc;
    private Cell space;

    private Color color;
    private Polygon hexagon;

    public HexagonNode(Location loc) {
        this.loc = loc;

        color = Color.WHITE;
        hexagon = new Polygon(new int[] {
                (int) (HexagonConstants.RADIUS / 2),
                (int) (HexagonConstants.RADIUS * 3 / 2),
                (int) (HexagonConstants.RADIUS * 2),
                (int) (HexagonConstants.RADIUS * 3 / 2),
                (int) (HexagonConstants.RADIUS / 2),
                0
        }, new int[] {
                0,
                0,
                (int) HexagonConstants.TRI_HEIGHT,
                (int) (HexagonConstants.TRI_HEIGHT * 2),
                (int) (HexagonConstants.TRI_HEIGHT * 2),
                (int) HexagonConstants.TRI_HEIGHT
        }, 6);
    }

    public Vector getPosition() {
        return loc.asCoordinates();
    }
    
    public Shape getCollision() {
    	return new Polygon(hexagon.xpoints, hexagon.ypoints, hexagon.npoints);
    }
    
    public void onClick(MouseEvent e) {
    	if(e.isShiftDown()) {
    		color = new Color(200, 250, 200);
    	} else {
    		color = new Color(250, 200, 200);
    	}
	}

    public void paint(Graphics2D g) {

        g.setColor(color);
        g.fillPolygon(hexagon);
        g.setStroke(new BasicStroke(4));
        g.setColor(new Color(17, 17, 17));
        g.drawPolygon(hexagon);
        g.setColor(Color.BLACK);
    }

}
