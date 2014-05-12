package virophage.render;

import virophage.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RenderTree extends JPanel {

    public double zoom = 1;
    private double displaceX = 0;
    private double displaceY = 0;

    private Point prevMousePos;
    private ArrayList<RenderNode> nodes = new ArrayList<RenderNode>();

    public RenderTree() {
        setLayout(null);
        setFocusable(true);
        setBackground(Color.WHITE);

        KListener k = new KListener();
        addKeyListener(k);

        MListener m = new MListener();
        addMouseListener(m);
        addMouseMotionListener(m);
        addMouseWheelListener(m);
    }

    public void add(RenderNode node) {
        super.add(node);
        nodes.add(node);
        node.setRenderTree(this);
    }

    public void updateNodes() {
        if(nodes != null) {
            for(RenderNode node: nodes) {
                Dimension preferredSize = node.getPreferredSize();
                Point preferredPos = node.getPreferredPosition();

                Dimension size = new Dimension(
                        (int) Math.ceil(preferredSize.width * zoom),
                        (int) Math.ceil(preferredSize.height * zoom)
                );
                Point pos = new Point(
                        (int) (zoom * (preferredPos.getX() + displaceX)),
                        (int) (zoom * (preferredPos.getY() + displaceY))
                );

                node.setBounds(new Rectangle(pos, size));
                node.repaint();
            }
        }
    }

    private class MListener implements MouseListener, MouseMotionListener, MouseWheelListener {

        @Override
        public void mousePressed(MouseEvent e) {
            prevMousePos = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point newPoint = e.getPoint();
            displaceX += (newPoint.getX() - prevMousePos.getX()) / zoom;
            displaceY += (newPoint.getY() - prevMousePos.getY()) / zoom;
            Start.log.info("DRAG " + displaceX + " " + displaceY);
            updateNodes();
            prevMousePos = newPoint;
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
            updateNodes();
        }

        public void mouseClicked(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseMoved(MouseEvent e) {}
    }

    private class KListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_LEFT) {
                displaceX += 50 / zoom;
            }
            if(code == KeyEvent.VK_RIGHT) {
                displaceX -= 50 / zoom;
            }
            if(code == KeyEvent.VK_UP) {
                displaceY += 50 / zoom;
            }
            if(code == KeyEvent.VK_DOWN) {
                displaceY -= 50 / zoom;
            }
            updateNodes();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }

}
