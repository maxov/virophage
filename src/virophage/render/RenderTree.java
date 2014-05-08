package virophage.render;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderTree {

    private double zoom = 1;
    private double displaceX = 0;
    private double displaceY = 0;
    private ArrayList<RenderNode> nodes = new ArrayList<RenderNode>();
    private JComponent master;

    public RenderTree(JComponent master) {
        this.master = master;
    }

    public void add(RenderNode node) {
        nodes.add(node);
        node.setRenderTree(this);
        master.add(node);
        update();
    }

    private void update() {
        for(RenderNode node: nodes) {
            Dimension preferredSize = node.getPreferredSize();
            Point preferredPos = node.getPreferredPosition();

            Dimension size = new Dimension(
                    (int) Math.ceil(preferredSize.width * zoom),
                    (int) Math.ceil(preferredSize.height * zoom)
            );
            Point pos = new Point(
                    (int) (getZoom() * (preferredPos.getX() + getDisplaceX())),
                    (int) (getZoom() * (preferredPos.getY() + getDisplaceY()))
            );

            node.setBounds(new Rectangle(pos, size));
            node.repaint();
        }
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
        update();
    }

    public double getDisplaceX() {
        return displaceX;
    }

    public void setDisplaceX(double displaceX) {
        this.displaceX = displaceX;
        update();
    }

    public double getDisplaceY() {
        return displaceY;
    }

    public void setDisplaceY(double displaceY) {
        this.displaceY = displaceY;
        update();
    }

}
