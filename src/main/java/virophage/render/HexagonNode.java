package virophage.render;

import virophage.core.Cell;
import virophage.core.Channel;
import virophage.core.DeadCell;
import virophage.core.Location;
import virophage.core.Player;
import virophage.core.Virus;
import virophage.gui.GameClient;
import virophage.gui.Selection;
import virophage.util.HexagonConstants;
import virophage.util.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;

/**
 * A <code>HexagonNode</code> is a graphical representation of a cell. It can be scaled.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class HexagonNode extends RenderNode {

    private Location loc;
    public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	private Cell cell;

    public Cell getCell() {
		return cell;
	}

    private Polygon hexagon;
    private boolean selected = false;
    public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isPossible() {
		return possible;
	}

	public void setPossible(boolean possible) {
		this.possible = possible;
	}

	private boolean possible = false;
    
    private static Selection selection;

    /**
     * Constructs a <code>HexagonNode</code> at the Location loc.
     * @param loc The locaiton this node is supposed to represent.
     */
    public HexagonNode(Location loc) {
        this.loc = loc;

        hexagon = new Polygon(new int[]{
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
    }

    public Location getLocation() {
        return loc;
    }

    public void setCell(Cell c) {
        cell = c;
    }

    public Vector getPosition() {
        return loc.asCoordinates();
    }

    public Shape getCollision() {
        return new Polygon(hexagon.xpoints, hexagon.ypoints, hexagon.npoints);
    }

    public void onClick(MouseEvent e) {
		if (cell instanceof DeadCell) {
			return;
		} else {
			if ((selection == null || selection.hasTo()) && cell.occupant != null) {
				selection = new Selection(this);
				selection.select(this);
			} else if(selection != null) {
				HexagonNode from = selection.getFrom();
				
				Player p = from.getCell().getOccupant().getPlayer();
				if(from != this && getLoc().isNeighbor(from.getLoc()) && !p.hasChannelBetween(from.getLoc(), getLoc())) {
					selection.setTo(this);
    				
    				Channel c = new Channel(
    						getRenderTree().getTissue(),
    						from.getLoc(), 
    						getLoc(), 
    						p);
    				p.addChannel(c);
    				getRenderTree().add(new ChannelNode(c));
    				selection.deselect(from);
    				selection = null;
				} else {
					selection.deselect(from);
    				selection = null;
				}
				
			}
		}
        
    }

    /**
     * The "drawing" of the hexagons with colors.
     */
    public void render(Graphics2D g) {
        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        //FontMetrics fm = g.getFontMetrics(g.getFont());
        Virus occupant = cell.getOccupant();
        if (occupant != null) {
            Color light = occupant.getPlayer().getColor();
            Color dark = light.darker();
            if(selected) {
            	light = Color.DARK_GRAY;
            } else if(possible) {
            	light = Color.LIGHT_GRAY;
            }
            g.setColor(light);
            g.fillPolygon(hexagon);
            g.setColor(dark);
            double circleRadius = (occupant.getEnergy() / (double) GameClient.MAX_ENERGY) *
                    (HexagonConstants.RADIUS * 0.7);
            int circleDiameter = (int) (circleRadius * 2);
            int x = (int) (HexagonConstants.RADIUS - circleRadius);
            int y = (int) (HexagonConstants.TRI_HEIGHT - circleRadius);
            g.fillOval(x, y, circleDiameter, circleDiameter);
        } else {
            if (cell instanceof DeadCell) {
                g.setColor(Color.BLACK);
            } else {
            	if(selected) {
                	g.setColor(Color.DARK_GRAY);
                } else if(possible) {
                	g.setColor(Color.LIGHT_GRAY);
                } else {
                	g.setColor(Color.WHITE);
                }
            }
            g.fillPolygon(hexagon);
        }

        //g.setStroke(new BasicStroke(4));
        g.setColor(new Color(32, 32, 32));
        g.drawPolygon(hexagon);
        g.setColor(Color.BLACK);
        //int x = (int) (HexagonConstants.RADIUS - fm.stringWidth(loc.toString()) / 2);
        //int y = (int) (HexagonConstants.TRI_HEIGHT);
        //g.drawString(loc.toString(), x, y);

    }

}
