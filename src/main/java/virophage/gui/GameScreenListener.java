package virophage.gui;

import virophage.Start;
import virophage.core.Cell;
import virophage.core.Channel;
import virophage.core.DeadCell;
import virophage.core.Player;
import virophage.core.Tissue;
import virophage.game.ClientGame;
import virophage.game.ServerGame;
import virophage.network.Chat;
import virophage.network.packet.BroadcastPacket;
import virophage.network.packet.CreateChannelAction;
import virophage.util.HexagonConstants;
import virophage.util.Vector;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * A class that listens for GameScreen events.
 *
 * @author Max Ovsiankin
 * @since 2014-05-19
 */
class GameScreenListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private GameScreen gameScreen;
    private Vector prevPos;
    private double zoomFactor;

    private String currentChat = "";

    /**
     * Creates a GameScreenListener from a GameScreen.
     *
     * @param gameScreen the GameScreen.
     */
    public GameScreenListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        zoomFactor = 1;
    }

    private Cell getCellAround(MouseEvent e) {
        Vector p = new Vector(e.getPoint()).scale(1 / gameScreen.zoom).subtract(gameScreen.displacement);
        for (Cell cell: gameScreen.getGame().getTissue().flatCells()) {
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
        else if (code == KeyEvent.VK_RIGHT) {
            gameScreen.displacement = gameScreen.displacement.add(Vector.i.scale(-50 / gameScreen.zoom));
        }
        else if (code == KeyEvent.VK_UP) {
            gameScreen.displacement = gameScreen.displacement.add(Vector.j.scale(50 / gameScreen.zoom));
        }
        else if (code == KeyEvent.VK_DOWN) {
            gameScreen.displacement = gameScreen.displacement.add(Vector.j.scale(-50 / gameScreen.zoom));
        }
        else if (code == KeyEvent.VK_ESCAPE) {
            // how to stop all thread's timer? and clean up the game?
            System.exit(0);
        } else if(code ==KeyEvent.VK_T && currentChat.length() == 0) {
            if(gameScreen.getIdentityPlayer() != null) {
                Start.chatList.inProgressChat(gameScreen.getIdentityPlayer());
            }
        } else if(code == KeyEvent.VK_ENTER && currentChat.length() > 0) {
            Chat chat = Start.chatList.progressFinished();
            currentChat = "";
            if(gameScreen.getGame() instanceof ServerGame) {
                ((ServerGame) gameScreen.getGame()).writeToAll(new BroadcastPacket(chat));
            } else if(gameScreen.getGame() instanceof ClientGame) {
                try {
                    ((ClientGame) gameScreen.getGame()).writeChat(new BroadcastPacket(chat));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            if(32 < e.getKeyChar() && e.getKeyChar()  < 176) {
                currentChat += e.getKeyChar();
                Start.chatList.progressUpdate(currentChat);
            }
        }
        gameScreen.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPos = new Vector(e.getPoint());
        if (!e.isControlDown() && gameScreen.getGame().getTissue() != null && gameScreen.getIdentityPlayer() != null) {
            Cell c = getCellAround(e);
            if (c != null && c.hasOccupant() && c.getOccupant().getPlayer().getName().equals(gameScreen.getIdentityPlayer().getName())) {
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
        Tissue tissue = gameScreen.getGame().getTissue();
        if (GameScreen.selection.hasFrom() && tissue != null && gameScreen.getIdentityPlayer() != null) {
            Cell to = getCellAround(e);
            Cell from = GameScreen.selection.getFrom();
            Player p = from.getOccupant().getPlayer();
            if (!(to.equals(from)) && !(to instanceof DeadCell) && !p.hasChannelBetween(from.location, to.location)
                    && to.location.isNeighbor(from.location) && p.getName().equals(gameScreen.getIdentityPlayer().getName())) {
                if(gameScreen.getGame() instanceof ServerGame) {
                    synchronized(tissue) {
                        Channel c = new Channel(tissue, from.location, to.location, p);
                        p.addChannel(c);
                    }
                } else if(gameScreen.getGame() instanceof ClientGame) {
                    ClientGame game = (ClientGame) gameScreen.getGame();
                    try {
                        game.writeAction(new CreateChannelAction(p, from.location, to.location));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
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
