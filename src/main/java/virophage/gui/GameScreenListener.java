package virophage.gui;

import virophage.Start;
import virophage.core.Cell;
import virophage.core.Channel;
import virophage.core.DeadCell;
import virophage.core.Player;
import virophage.core.Tissue;
import virophage.util.HexagonConstants;
import virophage.util.Vector;

import java.awt.*;
import java.awt.event.*;

/**
 * A class that listens for GameScreen events.
 *
 * @author Max Ovsiankin
 * @since 2014-05-19
 */
class GameScreenListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private GameScreen gameScreen;
    private Tissue tissue;
    private Vector prevPos;
    private double zoomFactor;

    /**
     * Creates a GameScreenListener from a GameScreen.
     *
     * @param gameScreen the GameScreen.
     */
    public GameScreenListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        tissue = null;
        zoomFactor = 1;
    }
    
    public void setTissue(Tissue tissue) {
    	this.tissue = tissue;
    }

    private Cell getCellAround(MouseEvent e) {
        Vector p = new Vector(e.getPoint()).scale(1 / gameScreen.zoom).subtract(gameScreen.displacement);
        for (Cell cell: tissue.flatCells()) {
            if (cell != null && insideCell(cell, p)) return cell;
        }
        return null;
    }

    private boolean insideCell(Cell cell, Vector point) {
        Vector pos = cell.location.asCoordinates();
        Vector coord = point.subtract(pos);
        Polygon hexagon = new Polygon(new int[]{
                (int) (HexagonConstants.RADIUS / 2),
                (int) (HexagonConstants.RADIUS * 3 / 2),
                (int) (HexagonConstants.RADIUS * 2),
                (int) (HexagonConstants.RADIUS * 3 / 2),
                (int) (HexagonConstants.RADIUS / 2),
                0
        }, new int[]{
                0,
                0,
                (int) HexagonConstants.TRI_HEIGHT,
                (int) (HexagonConstants.TRI_HEIGHT * 2),
                (int) (HexagonConstants.TRI_HEIGHT * 2),
                (int) HexagonConstants.TRI_HEIGHT
        }, 6);
        return hexagon.contains(coord.toPoint());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            gameScreen.displacement = gameScreen.displacement.add(Vector.i.scale(50 / gameScreen.zoom));
        }
        if (code == KeyEvent.VK_RIGHT) {
            gameScreen.displacement = gameScreen.displacement.add(Vector.i.scale(-50 / gameScreen.zoom));
        }
        if (code == KeyEvent.VK_UP) {
            gameScreen.displacement = gameScreen.displacement.add(Vector.j.scale(50 / gameScreen.zoom));
        }
        if (code == KeyEvent.VK_DOWN) {
            gameScreen.displacement = gameScreen.displacement.add(Vector.j.scale(-50 / gameScreen.zoom));
        }
        if (code == KeyEvent.VK_ESCAPE) {
            // how to stop all thread's timer? and clean up the game?
            System.exit(0);
        }
        gameScreen.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPos = new Vector(e.getPoint());
        if (!e.isControlDown() && tissue != null) {
            Cell c = getCellAround(e);
            if (c != null && c.hasOccupant()) {
                GameScreen.selection.setFrom(c);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.isControlDown()) {
            Vector newPos = new Vector(e.getPoint());
            gameScreen.displacement = gameScreen.displacement.add(newPos.subtract(prevPos).scale(1 / gameScreen.zoom));
            //Start.log.info("DRAG " + gameScreen.displacement);
            prevPos = newPos;
            gameScreen.repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double rot = e.getPreciseWheelRotation();
        Vector offset;

        int x = gameScreen.getWidth();
        int y = gameScreen.getHeight();

        if ((0.2 < gameScreen.zoom && gameScreen.zoom < 5) ||
                (gameScreen.zoom <= 0.2 && rot < 0) ||
                (gameScreen.zoom >= 5 && rot > 0)) {
            gameScreen.zoom = gameScreen.zoom / Math.pow(1.15, rot);

            zoomFactor /= gameScreen.zoom;
            if (gameScreen.zoom > 1) {
                zoomFactor *= 2;
            }

            double ratio = ((gameScreen.zoom > 1) ? gameScreen.zoom : -gameScreen.zoom);
            ratio = (ratio > 0) ? (ratio - 1) : (-1 - ratio);
            //Start.log.info("ZOOM " + renderTree.zoom + " ratio " + ratio);
            //Start.log.info("Before: X " + renderTree.displacement.getX() + " Y " + renderTree.displacement.getY());
            offset = new Vector(x * ratio / zoomFactor, y * ratio / zoomFactor);

            gameScreen.displacement = gameScreen.displacement.subtract(offset);
            //Start.log.info("After: X " + renderTree.displacement.getX() + " Y " + renderTree.displacement.getY());
        }

        gameScreen.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (GameScreen.selection.hasFrom() && tissue != null) {
            Cell to = getCellAround(e);
            Cell from = GameScreen.selection.getFrom();
            Player p = from.getOccupant().getPlayer();
            if (!(to.equals(from)) && !(to instanceof DeadCell) && !p.hasChannelBetween(to.location, from.location)
                    && to.location.isNeighbor(from.location)) {
                Channel c = new Channel(tissue, from.location, to.location, p);
                p.addChannel(c);
            }
            GameScreen.selection = new Selection();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

}
