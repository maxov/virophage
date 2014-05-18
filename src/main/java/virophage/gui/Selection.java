package virophage.gui;

import java.util.ArrayList;

import virophage.util.Location;
import virophage.core.Tissue;
import virophage.render.HexagonNode;
import virophage.render.RenderNode;

/**
 * A <code>Selection</code> represents the active selection a user has.
 */
public class Selection {
	
	private HexagonNode from;
	private HexagonNode to;
	
	public Selection(HexagonNode from) {
		this.setFrom(from);
	}
	
	public HexagonNode getFrom() {
		return from;
	}
	
	public synchronized void select() {
		from.setSelected(true);
		Tissue tissue = from.getRenderTree().getTissue();
		ArrayList<Location> neighbors = from.getLoc().getNeighbors();
		for(Location loc: neighbors) {
			for(RenderNode n: from.getRenderTree().nodes) {
				if(n instanceof HexagonNode) {
					HexagonNode hn = (HexagonNode) n;
					if(hn.getLoc().equals(loc)) {
						hn.setPossible(true);
					}
				}
			}
		}
	}
	
	public synchronized void deselect() {
		from.setSelected(false);
		Tissue tissue = from.getRenderTree().getTissue();
		ArrayList<Location> neighbors = from.getLoc().getNeighbors();
		for(Location loc: neighbors) {
			for(RenderNode n: from.getRenderTree().nodes) {
				if(n instanceof HexagonNode) {
					HexagonNode hn = (HexagonNode) n;
					if(hn.getLoc().equals(loc)) {
						hn.setPossible(false);
					}
				}
			}
		}
	}

	public void setFrom(HexagonNode from) {
		this.from = from;
	}

    public boolean isCompleted() {
        return to != null && from != null;
    }

	public HexagonNode getTo() {
		return to;
	}

	public void setTo(HexagonNode to) {
		this.to = to;
	}

}
