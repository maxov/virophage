package virophage.gui;

import virophage.Start;
import virophage.render.HexagonNode;
import virophage.render.RenderTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RenderTissue extends JComponent {

    private RenderTree tree;

    private Point prevMousePos;

    public RenderTissue() {
        tree = new RenderTree(this);

        tree.add(new HexagonNode(0,0));
        tree.add(new HexagonNode(1,1));
        tree.add(new HexagonNode(-1, 0));

        MListener m = new MListener();

        prevMousePos = getMousePosition();

        addMouseListener(m);
        addMouseMotionListener(m);
        addMouseWheelListener(m);
    }

    class MListener implements MouseListener, MouseMotionListener, MouseWheelListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            prevMousePos = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        	prevMousePos = e.getPoint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseDragged(MouseEvent e) {
            Point newPoint = e.getPoint();
            tree.setDisplaceX(tree.getDisplaceX() + (newPoint.getX() - prevMousePos.getX()) / tree.getZoom());
            tree.setDisplaceY(tree.getDisplaceY() + (newPoint.getY() - prevMousePos.getY()) / tree.getZoom());
            prevMousePos = newPoint;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        	prevMousePos = e.getPoint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            double zoom = tree.getZoom();
            double rot = e.getPreciseWheelRotation();
            if ((0.2 < zoom && zoom < 5) ||
                    (zoom <= 0.2 && rot < 0) ||
                    (zoom >= 5 && rot > 0)) {
                tree.setZoom(zoom / Math.pow(1.15, rot));
            }
            prevMousePos = e.getPoint();
        }
    }

}
