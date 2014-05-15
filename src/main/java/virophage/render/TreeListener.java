package virophage.render;

import virophage.Start;
import virophage.util.Vector;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

class TreeListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private RenderTree renderTree;
    private Vector prevPos;

    public TreeListener(RenderTree renderTree) {
        this.renderTree = renderTree;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            renderTree.displacement = renderTree.displacement.add(Vector.i.scale(50 / renderTree.zoom));
        }
        if (code == KeyEvent.VK_RIGHT) {
            renderTree.displacement = renderTree.displacement.add(Vector.i.scale(-50 / renderTree.zoom));
        }
        if (code == KeyEvent.VK_UP) {
            renderTree.displacement = renderTree.displacement.add(Vector.j.scale(50 / renderTree.zoom));
        }
        if (code == KeyEvent.VK_DOWN) {
            renderTree.displacement = renderTree.displacement.add(Vector.j.scale(-50 / renderTree.zoom));
        }
        renderTree.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPos = new Vector(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.isControlDown()) {
            Vector newPos = new Vector(e.getPoint());
            renderTree.displacement = renderTree.displacement.add(newPos.subtract(prevPos).scale(1 / renderTree.zoom));
            //Start.log.info("DRAG " + renderTree.displacement.x + " " + renderTree.displacement.y);
            prevPos = newPos;
            renderTree.repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double rot = e.getPreciseWheelRotation();
        if ((0.2 < renderTree.zoom && renderTree.zoom < 5) ||
                (renderTree.zoom <= 0.2 && rot < 0) ||
                (renderTree.zoom >= 5 && rot > 0)) {
            renderTree.zoom = renderTree.zoom / Math.pow(1.15, rot);
        }
        //Start.log.info("ZOOM " + renderTree.zoom);
        renderTree.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AffineTransform at = new AffineTransform();
        at.translate(renderTree.displacement.x * renderTree.zoom, renderTree.displacement.y * renderTree.zoom);
        at.scale(renderTree.zoom, renderTree.zoom);

        for (RenderNode node : renderTree.nodes) {
            Vector vec = node.getPosition();
            AffineTransform nodeTransform = new AffineTransform(at);
            nodeTransform.translate(vec.x, vec.y);
            Shape col = nodeTransform.createTransformedShape(node.getCollision());
            if (col.contains(e.getPoint())) {
                node.onClick(e);
                renderTree.repaint();
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
