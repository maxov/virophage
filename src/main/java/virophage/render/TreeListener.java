package virophage.render;

import virophage.core.Cell;
import virophage.util.Location;
import virophage.util.Vector;

import java.awt.event.*;

/**
*
* @author Max Ovsiankin and Leon Ren
* @since 2014-05-16
*/

class TreeListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private RenderTree renderTree;
    private Vector prevPos;
    private double zoomFactor;

    public TreeListener(RenderTree renderTree) {
        this.renderTree = renderTree;
        zoomFactor = 1;
    }

    private Cell getCellAround(MouseEvent e) {
        Vector z = new Vector(e.getPoint()).scale(1/renderTree.zoom).subtract(renderTree.displacement);
        Location l = Location.mfind(z);
        return renderTree.getTissue().getCell(Location.mfind(z));
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
        if (code == KeyEvent.VK_ESCAPE) {
        	// how to stop all thread's timer? and clean up the game?
        	renderTree.getGameClient().changePanel("menuScreen");
        }
        renderTree.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPos = new Vector(e.getPoint());
        Cell c = getCellAround(e);
        if(c != null) {
        	renderTree.selection.setFrom(c);
        }
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
        Vector offset;
        
        int x = renderTree.getWidth();
        int y = renderTree.getHeight();
        
        if ((0.2 < renderTree.zoom && renderTree.zoom < 5) ||
                (renderTree.zoom <= 0.2 && rot < 0) ||
                (renderTree.zoom >= 5 && rot > 0)) {
            renderTree.zoom = renderTree.zoom / Math.pow(1.15, rot);
            
            zoomFactor /= renderTree.zoom;
            if (renderTree.zoom > 1) {
            	zoomFactor *= 2;
            }
            
            double ratio = ((renderTree.zoom > 1)? renderTree.zoom : -renderTree.zoom);
            ratio = (ratio > 0) ? (ratio - 1) : (-1 - ratio);
            //Start.log.info("ZOOM " + renderTree.zoom + " ratio " + ratio);
            //Start.log.info("Before: X " + renderTree.displacement.getX() + " Y " + renderTree.displacement.getY());
            offset = new Vector (x * ratio / zoomFactor, y * ratio / zoomFactor);
         
            renderTree.displacement = renderTree.displacement.subtract(offset);
            //Start.log.info("After: X " + renderTree.displacement.getX() + " Y " + renderTree.displacement.getY());
        }
        
        renderTree.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        Cell node = getCellAround(e);
        if(node != null) {
        	
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

}
