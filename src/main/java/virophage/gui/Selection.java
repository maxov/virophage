package virophage.gui;

import java.util.ArrayList;

import virophage.core.Location;
import virophage.core.Tissue;
import virophage.render.HexagonNode;
import virophage.render.RenderNode;

public class Selection {
	
	private HexagonNode from;
	private HexagonNode to;
	
	public Selection(HexagonNode from) {
		this.setFrom(from);
	}
	
	public HexagonNode getFrom() {
		return from;
	}
	
	public synchronized void select(HexagonNode node) {
		node.setSelected(true);
		Tissue tissue = node.getRenderTree().getTissue();
		ArrayList<Location> neighbors = node.getLoc().getNeighbors();
		for(Location loc: neighbors) {
			for(RenderNode n: node.getRenderTree().nodes) {
				if(n instanceof HexagonNode) {
					HexagonNode hn = (HexagonNode) n;
					if(hn.getLoc().equals(loc)) {
						hn.setPossible(true);
					}
				}
			}
		}
	}
	
	public synchronized void deselect(HexagonNode node) {
		node.setSelected(false);
		Tissue tissue = node.getRenderTree().getTissue();
		ArrayList<Location> neighbors = node.getLoc().getNeighbors();
		for(Location loc: neighbors) {
			for(RenderNode n: node.getRenderTree().nodes) {
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
	
	public boolean hasTo() {
		return to != null;
	}

	public HexagonNode getTo() {
		return to;
	}

	public void setTo(HexagonNode to) {
		this.to = to;
	}

}
