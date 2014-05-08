package virophage.render;

import java.awt.*;

public class HexagonNode extends RenderNode {

    public static final int RADIUS = 100;
    public static final int TRI_HEIGHT = RADIUS / 2;
    public static final int TRI_WIDTH = (int) (TRI_HEIGHT * Math.sqrt(3));

    public static final int BOUNDS_WIDTH = TRI_WIDTH * 2;
    public static final int BOUNDS_HEIGHT = RADIUS * 2;

    private int x;
    private int y;

    public HexagonNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Dimension getPreferredSize() {
        return new Dimension(BOUNDS_WIDTH + 1, BOUNDS_HEIGHT + 1);
    }

    public Point getPreferredPosition() {
        return new Point(x * 200, y * 200);
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;

        RenderTree tree = getRenderTree();
        double zoom = tree.getZoom();

        g.scale(zoom, zoom);

        Polygon hexagon = new Polygon(new int[] {
                TRI_WIDTH,
                TRI_WIDTH * 2,
                TRI_WIDTH * 2,
                TRI_WIDTH,
                0,
                0
        }, new int[] {
                0,
                RADIUS - TRI_HEIGHT,
                RADIUS + TRI_HEIGHT,
                2 * RADIUS,
                RADIUS + TRI_HEIGHT,
                RADIUS - TRI_HEIGHT
        }, 6);

        g.setColor(new Color(227, 239, 255));
        g.fillPolygon(hexagon);
        g.setColor(new Color(17, 17, 17));
        g.drawPolygon(hexagon);

    }

}
