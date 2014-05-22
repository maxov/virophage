package virophage.gui;

import java.util.ArrayList;

import virophage.math.Location;
import virophage.core.Cell;
import virophage.core.DeadCell;
import virophage.core.Tissue;

/**
 * A <code>Selection</code> represents the active selection a user has.
 */
public class Selection {
	
	private Cell from;
	
	public Selection(Cell from) {
		this.setFrom(from);
	}
	
	public boolean hasFrom() {
		return from != null;
	}
	
	public Cell getFrom() {
		return from;
	}

	public void setFrom(Cell from) {
		this.from = from;
	}
	
	public ArrayList<Cell> possible() {
		ArrayList<Cell> possible = new ArrayList<Cell>();
		for(Location loc: from.location.getNeighbors()) {
			Cell c = from.tissue.getCell(loc);
			if(!(c instanceof DeadCell)) {
				possible.add(c);
			}
		}
		return possible;
	}
	
}
