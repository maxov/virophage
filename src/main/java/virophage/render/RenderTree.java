package virophage.render;

import virophage.Start;
import virophage.util.Vector;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class RenderTree extends JComponent {

    public double zoom = 1;
    private Vector displacement = new Vector(0, 0);

    private ArrayList<RenderNode> nodes = new ArrayList<RenderNode>();

    public RenderTree() {
        setFocusable(true);
        requestFocus();

        KListener k = new KListener();
        addKeyListener(k);

        MListener m = new MListener();
        addMouseListener(m);
        addMouseMotionListener(m);
        addMouseWheelListener(m);
    }

    public void add(RenderNode node) {
        nodes.add(node);
        node.setRenderTree(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        AffineTransform at = new AffineTransform();
        at.translate(displacement.x * zoom, displacement.y * zoom);
        at.scale(zoom, zoom);

        if (nodes != null) {
            for (RenderNode node : nodes) {
                Vector vec = node.getPosition();
                AffineTransform nodeTransform = new AffineTransform(at);
                nodeTransform.translate(vec.x, vec.y);

                Graphics2D nodeGraphics = (Graphics2D) g.create();
                nodeGraphics.transform(nodeTransform);
                node.paint(nodeGraphics);
            }
        }
    }

    private class MListener implements MouseListener, MouseMotionListener, MouseWheelListener {

        private Vector prevPos;

        @Override
        public void mousePressed(MouseEvent e) {
            prevPos = new Vector(e.getPoint());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.isControlDown()) {
                Vector newPos = new Vector(e.getPoint());
                displacement = displacement.add(newPos.subtract(prevPos).scale(1 / zoom));
                Start.log.info("DRAG " + displacement.x + " " + displacement.y);
                prevPos = newPos;
                repaint();
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            double rot = e.getPreciseWheelRotation();
            if ((0.2 < zoom && zoom < 5) ||
                    (zoom <= 0.2 && rot < 0) ||
                    (zoom >= 5 && rot > 0)) {
                zoom = zoom / Math.pow(1.15, rot);
            }
            Start.log.info("ZOOM " + zoom);
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AffineTransform at = new AffineTransform();
            at.translate(displacement.x * zoom, displacement.y * zoom);
            at.scale(zoom, zoom);

            for (RenderNode node : nodes) {
                Vector vec = node.getPosition();
                AffineTransform nodeTransform = new AffineTransform(at);
                nodeTransform.translate(vec.x, vec.y);
                Shape col = nodeTransform.createTransformedShape(node.getCollision());
                if (col.contains(e.getPoint())) {
                    node.onClick(e);
                    repaint();
                    break;
                }
            }

        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }
    }

    private class KListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_LEFT) {
                displacement = displacement.add(Vector.i.scale(50 / zoom));
            }
            if (code == KeyEvent.VK_RIGHT) {
                displacement = displacement.add(Vector.i.scale(-50 / zoom));
            }
            if (code == KeyEvent.VK_UP) {
                displacement = displacement.add(Vector.j.scale(50 / zoom));
            }
            if (code == KeyEvent.VK_DOWN) {
                displacement = displacement.add(Vector.j.scale(-50 / zoom));
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }

}
